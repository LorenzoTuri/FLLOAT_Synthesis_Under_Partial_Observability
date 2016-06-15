package SynthesisPartialObservability;

import SynthesisPartialObservability.Utility.Domain;
import SynthesisPartialObservability.Utility.Utility;
import rationals.Automaton;

/**
 * Created by loren on 15/06/2016.
 */
public class SynthesisPartialObservability {
    Automaton automaton;
    Domain domain;

    public SynthesisPartialObservability(Automaton automaton, Domain domain){
        this.automaton = automaton;
        this.domain = domain;
    }

    public boolean solve(){
        //creo una copia di backup dell'automa
        Automaton originalAutomaton = automaton.clone();
        //nego l'automa
        automaton = Utility.negateAutomaton(automaton);
        //cancello le variabili proposizionali nascoste
        automaton = Utility.eraseHiddens(automaton,Utility.METHOD_CREATE,domain);
        //normalizzo l'automa risultante
        automaton = Utility.normalize(automaton);
        //nego nuovamente l'automa
        automaton = Utility.negateAutomaton(automaton);
        //risolvo l'automa come fosse un normale DFAGames
        boolean result = Utility.solveDFAGames(automaton,domain);
        //ripristino la copia di backup
        automaton = originalAutomaton;

        return result;
    }

}
