package SynthesisPartialObservability;

import SynthesisPartialObservability.Utility.Domain;
import SynthesisPartialObservability.Utility.Utility;
import rationals.Automaton;

/**
 * This class utilize the Utility class to compute the synthesis.
 *
 */
public class SynthesisPartialObservability {
    Automaton automaton;
    Domain domain;
	boolean printing;

    /**
     * Constructor of the class. Simply stores value, used in function solve()
     * @param automaton the automaton that we want to verify
     * @param domain the domain of the original formula
     */
    public SynthesisPartialObservability(Automaton automaton, Domain domain, boolean printOnFilePartialAutomaton){
	    this.printing = printOnFilePartialAutomaton;
        this.automaton = automaton;
        this.domain = domain;
    }

    /**
     * Main function, computes the synthesis of the solution
     * @return true if there is a partial observability winning solution, false otherwise
     */
    public boolean solve(){
        //creo una copia di backup dell'automa
        Automaton originalAutomaton = automaton.clone();
	    //determinizzo l'automa iniziale (ho osservato che potrebbe non esserlo dopo la traduzione da formula)
	    automaton = Utility.determinize(automaton);
        //nego l'automa
        automaton = Utility.negateAutomaton(automaton);
        //cancello le variabili proposizionali nascoste
        automaton = Utility.eraseHiddens(automaton,Utility.METHOD_CREATE,domain);
        //determinizzo l'automa risultante
        automaton = Utility.determinize(automaton);
        //nego nuovamente l'automa
        automaton = Utility.negateAutomaton(automaton);
	    //stampo su file l'automa parziale
	    if (printing) Utility.print(automaton,"partialAutomaton.gv");
        //risolvo l'automa come fosse un normale DFAGames
        boolean result = Utility.solveDFAGames(automaton,domain);
        //ripristino la copia di backup
        automaton = originalAutomaton;
        return result;
    }

}
