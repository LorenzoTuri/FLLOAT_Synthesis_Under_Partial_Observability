package SynthesisPartialObservability.Utility;

import FLLOAT.automaton.EmptyTrace;
import FLLOAT.automaton.PossibleWorldWrap;
import net.sf.tweety.logics.pl.semantics.PossibleWorld;
import net.sf.tweety.logics.pl.syntax.Proposition;
import rationals.Automaton;
import rationals.State;
import rationals.Transition;
import rationals.transformations.ToDFA;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by loren on 15/06/2016.
 */
public class Utility {
    public static int METHOD_UPDATE = 1;
    public static int METHOD_CREATE = 2;

    public static Automaton eraseHiddens(Automaton automaton,int method, Domain domain){
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

    private static PossibleWorldWrap updateLabel(Transition transition,Domain domain){
        PossibleWorldWrap result = new PossibleWorldWrap();

        if (transition.label() instanceof EmptyTrace){
            result.add(new Proposition("EmptyTr"));

        }else if (transition.label() instanceof PossibleWorld){
            PossibleWorld pw = (PossibleWorld) transition.label();
            while (pw.iterator().hasNext()) result.add(pw.iterator().next());
            result.retainAll(domain.getDomain());

        }else return null;

        return result;
    }

    public static Automaton negateAutomaton(Automaton automaton){
        Set<State> terminals = automaton.terminals();
        Set<State> states = new HashSet<>();
        for (Object state:automaton.states()) states.add((State) state);
        states.retainAll(terminals);
        terminals.clear();
        terminals.addAll(states);

        return automaton;
    }

    public static Automaton normalize(Automaton automaton){
        automaton = (new ToDFA<>()).transform(automaton);
        return automaton;
    }

    public static boolean solveDFAGames(Automaton automaton,Domain domain){
        boolean result = false;
        Set<State> winningStates = new HashSet<>();
        winningStates.addAll(automaton.terminals());

        boolean cont = true;

        while (cont){
            Set<State> possibleWinningStates = new HashSet<>();
            for (State s: winningStates) possibleWinningStates.addAll(automaton.deltaMinusOne(s));
            for (State state:possibleWinningStates){
                if (winningStates.contains(state)) possibleWinningStates.remove(state);
                else {
                    Set<Transition> tmp = automaton.deltaMinusOne(state);
                    Set<State> reachableStates = new HashSet<>();
                    for (Transition transition:tmp) reachableStates.add(transition.end());
                    for (State s : reachableStates) {
                        boolean isWinning = haveObligedPath(automaton,state,s,domain);
                        if (!isWinning) possibleWinningStates.remove(state);
                    }
                }
            }
            if (possibleWinningStates.size()==0)cont = false;

        }

        return result;
    }

    private static boolean haveObligedPath(Automaton automaton,State state1,State state2, Domain domain){
        Set<Transition> transitions = automaton.deltaFrom(state1,state2);


    }
}
