package SynthesisPartialObservability.Utility;

import FLLOAT.automaton.EmptyTrace;
import FLLOAT.automaton.PossibleWorldWrap;
import FLLOAT.automaton.TransitionLabel;
import FLLOAT.utils.AutomatonUtils;
import net.sf.tweety.logics.pl.semantics.PossibleWorld;
import net.sf.tweety.logics.pl.syntax.Proposition;
import rationals.Automaton;
import rationals.State;
import rationals.Transition;
import rationals.transformations.ToDFA;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;
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
    public static Automaton eraseHidden(Automaton automaton, int method, Domain domain){
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
            Set<State> initials = automaton.initials();
            Set<State> terminals = automaton.terminals();

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
    private static TransitionLabel updateLabel(Transition transition, Domain domain){
        TransitionLabel result = null;
        if (transition.label() instanceof EmptyTrace){
            //transition haven't elements of the domain, but must be kept
            result = new EmptyTrace();

        }else if (transition.label() instanceof PossibleWorld){
            //transition can have elements not contained in the domain.
            PossibleWorld pw = (PossibleWorld) transition.label();
            pw.retainAll(domain.getDomain());
            result = (TransitionLabel) pw;
        }else return null;

        return result;
    }

    /**
     * Used to negate the automaton. A negated automaton is an automaton with inverted terminals
     * @param automaton original automaton
     * @return the modified automaton
     */
    public static Automaton negateAutomaton(Automaton automaton){
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
     * Main function that solves a normal DFAGames
     * @param automaton the automaton
     * @param domain the domain of the original formula
     * @return true, if there exists a winning solution, false otherwise
     */
    public static boolean solveDFAGames(Automaton automaton,Domain domain){
        boolean result = false;
        Set<State> winningStates = new HashSet<>();
        winningStates.addAll(automaton.terminals());

        boolean cont = true;

        while (cont){
            /*
            This function uses this intuition:
                We can compute the possible winning states set by finding states that can
                take to a winning state
                Observing those state we have 3 possibilities:
                    1. the state is already a winning state
                    2. the state is a new winning state
                    3. the state is not a winning state
                If the state is already a winning state we can remove it from the possible winning states set
                If the state is a new winning state we can add it to the winning set
                If the state isn't winning we can remove it from the possible winning set.
                If at the end the possible winning state contains still some elements, this mean we've added a state to
                the winning set, and we must compute all this again
             */
            Set<State> possibleWinningStates = new HashSet<>();
	        possibleWinningStates.addAll(automaton.coAccessibleStates(winningStates));

            Object arr[] = possibleWinningStates.toArray();
            for (Object s:arr){
                State state = (State) s;
                if (winningStates.contains(state)) possibleWinningStates.remove(state);
                else {
                    if (haveObligedPath(automaton,state,winningStates,domain)) winningStates.add(state);
                    else possibleWinningStates.remove(state);
                }
            }
            if (possibleWinningStates.size()==0)cont = false;
        }

	    for (Object s:automaton.initials()) if (winningStates.contains(s)) result = true;
        return result;
    }

    /**
     * Used to compute is a state has a obliged path (every interpretation of the agents proposition take to a
     * winning state, no mather of the environments propositions) to a winning state
     * @param automaton the automaton
     * @param state the starting state
     * @param winningState set of winning states
     * @param domain domain of the original function
     * @return true if there is a obliged path, false otherwise
     */
    private static boolean haveObligedPath(Automaton automaton,State state,Set<State> winningState, Domain domain){
        Set<Set<Proposition>> environmentPATHS = domain.getCombinantionEnvironmentDomain();
        Set<Set<Proposition>> agentPATHS = domain.getCombinantionAgentsDomain();
        for (Set<Proposition> agentPath: agentPATHS){
            boolean isWinning = true;
            for (Set<Proposition> environmentPath:environmentPATHS){
                PossibleWorldWrap path = new PossibleWorldWrap();
                path.addAll(agentPath);
                path.addAll(environmentPath);
                Set<Transition> transitions = automaton.delta(state,path);
                for (Transition transition:transitions) if (!winningState.contains(transition.end()))isWinning = false;
            }
            if (isWinning) return true;
        }
        return false;
    }

    /**
     * Used to print the automaton in a given path
     * @param automaton the automaton
     * @param path path
     */
    public static void print(Automaton automaton,String path){
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
}
