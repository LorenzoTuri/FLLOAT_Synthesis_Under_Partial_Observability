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

	//TODO add of various exceptions to this class, like NotFLLOATGeneratedAutomaException etc...
    /**
     * Constructor of the class. Simply stores value, used in function solve()
     * @param automaton the automaton that we want to verify
     * @param domain the domain of the original formula
     * @param printOnFilePartialAutomaton automaton should be printed or not as partialAutomaton.gv
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
        //backup copy creation of the automa
        Automaton originalAutomaton = automaton.clone();
	    //determination of the initial automata (automa could be NFA after creation from formula)
	    automaton = Utility.determine(automaton);
        //negation of automata's final states
        automaton = Utility.negateAutomaton(automaton);
        //erase of the hidden proposition, aka proposition that doesn't appear in the domain
        automaton = Utility.eraseHidden(automaton,Utility.METHOD_CREATE,domain);
        //determination of the automata
        automaton = Utility.determine(automaton);
        //negation of the automata
        automaton = Utility.negateAutomaton(automaton);
	    //print on file
	    if (printing) Utility.print(automaton,"partialAutomaton.gv");
        //solution of the automa as a normal DFAGames
        boolean result = Utility.solveDFAGames(automaton,domain);
        //reset of backup copy
        automaton = originalAutomaton;
        return result;
    }

}
