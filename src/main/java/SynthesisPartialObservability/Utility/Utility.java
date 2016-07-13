package SynthesisPartialObservability.Utility;

import automaton.EmptyTrace;
import automaton.TransitionLabel;
import net.sf.tweety.logics.pl.semantics.PossibleWorld;
import rationals.Automaton;
import rationals.State;
import rationals.Transition;
import rationals.transformations.ToDFA;
import utils.AutomatonUtils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Main class of the automaton operation for partial observability synthesis
 */
public class Utility {
    public static int METHOD_UPDATE = 1;
    public static int METHOD_CREATE = 2;

    /**
     * Used to erase hiddens proposition (not contained in the domain) from Transitions label
     * @param automaton the original automaton
     * @param method method of computin. METHOD_UPDATE updates the original automaton, METHOD_CREATE creates a new one
     * @param domain domain of the orignal formula
     * @return the modified automaton
     */
    public static Automaton eraseHidden(Automaton automaton, int method, PropositionDomain domain){
        Set<Transition> transitions = automaton.delta();
        Automaton result = null;
        if (method == METHOD_UPDATE){
            //method 1: update of the already existing FLLOAT.automaton
            for (Transition transition:transitions){
                automaton.updateTransitionWith(transition,updateLabel(transition,domain));
            }
            result = automaton;

        }else if (method == METHOD_CREATE){
            //method 2: creation of a new automata from existing one
            Set<State> states = automaton.states();

            result = new Automaton();

            HashMap<State,State> m = new HashMap<>();

            for(State state:states){
                boolean init = false, term = false;
                if (state.isInitial())init = true;
                if (state.isTerminal()) term = true;
                m.put(state,result.addState(init,term));
            }
            for (Transition transition:transitions){
                State start = m.get(transition.start());
                State end = m.get(transition.end());
                try{
                    result.addTransition(new Transition(start,updateLabel(transition,domain),end));
                }catch (Exception e){e.printStackTrace();}
            }
        }
        return result;
    }

    /**
     * Update the labels, erasing elements not contained in the domain
     * @param transition original transition
     * @param domain domain of the original formula
     * @return a modified label for a transition
     */
    private static TransitionLabel updateLabel(Transition transition, PropositionDomain domain) {
	    if (transition.label() instanceof EmptyTrace) {
		    return (TransitionLabel) transition.label();

	    } else if (transition.label() instanceof PossibleWorld) {
		    //transition can have elements not contained in the domain.
		    PossibleWorld pw = (PossibleWorld) transition.label();
            pw.removeAll(domain.getHiddenProposition());
		    return (TransitionLabel) pw;
	    }
	    return null;
    }

    /**
     * Used to negate the automaton. A negated automaton is an automaton with inverted terminals
     * @param originalAutomaton original automaton
     * @return the modified automaton
     */
    public static Automaton negateAutomaton(Automaton originalAutomaton){
        Automaton automaton = originalAutomaton.clone();
        Set<State> terminals = automaton.terminals();
        Set<State> negatedTerminals = new HashSet<>();

        for (Object state:automaton.states()) {
            if (!terminals.contains(state))
            negatedTerminals.add((State) state);
        }
        terminals.clear();
        for (State state:negatedTerminals) terminals.add(state);

        return automaton;
    }

    /**
     * Used to make the automaton DFA
     * @param automaton the original automaton
     * @return the modified automaton
     */
    public static Automaton determine(Automaton automaton){
        automaton = (new ToDFA<>()).transform(automaton);
        return automaton;
    }

    /**
     * Used to printAutomaton the automaton in a given path
     * @param automaton the automaton
     * @param path path
     */
    public static void printAutomaton(Automaton automaton, String path){
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(path);
	        PrintStream ps = new PrintStream(fos);
	        ps.println(AutomatonUtils.toDot(automaton));
	        ps.flush();
	        ps.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

	private static Automaton optimizeForPrintingAutomaton(Automaton automaton){
		Automaton result = new Automaton();
		Set<State> states = automaton.states();
		Map<State,State> mapping = new HashMap<>();
		for(State s:states) mapping.put(s,result.addState(s.isInitial(),s.isTerminal()));

		for (int i = 0;i<states.toArray().length;i++){
			State s = (State) states.toArray()[i];
			for (int j = 0;j<states.toArray().length;j++){
				State s2 = (State) states.toArray()[j];

				Set<Transition> transitions = automaton.deltaFrom(s,s2);
				Set<Set<Object>> transitionLabels = new HashSet<>();
				Set<Object> transitionLabel = new HashSet<>();
				int newline = 0;
				for (Transition t : transitions) {
					transitionLabel.add(t.label());
					newline++;
					if (newline % 5 == 0) {
						transitionLabels.add(transitionLabel);
						transitionLabel = new HashSet<>();
					}
				}
				if (transitionLabel.size() > 0) transitionLabels.add(transitionLabel);
				try {
					for (Set<Object> transitionlab : transitionLabels)
						result.addTransition(new Transition(mapping.get(s), transitionlab, mapping.get(s2)));
				} catch (Exception e) {
					System.err.println("ERROR IN ADD TRANSITION:"+e);
				}
			}
		}

		return result;
	}
	public static void printAutomatonOptimized(Automaton automaton, String path){
		Automaton printingAutomaton = optimizeForPrintingAutomaton(automaton);
		printAutomaton(printingAutomaton,path);
	}
}
