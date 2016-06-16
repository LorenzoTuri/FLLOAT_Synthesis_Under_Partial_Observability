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

    private static TransitionLabel updateLabel(Transition transition, Domain domain){
        TransitionLabel result = null;
        if (transition.label() instanceof EmptyTrace){
            result = new EmptyTrace();

        }else if (transition.label() instanceof PossibleWorld){
            result = new PossibleWorldWrap();
            PossibleWorld pw = (PossibleWorld) transition.label();
            pw.retainAll(domain.getDomain());
            result = (TransitionLabel) pw;
        }else return null;

        return result;
    }

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
            for (State s: winningStates) {
                Set<Transition> tmp = automaton.deltaMinusOne(s);
                Set<State> states = new HashSet<>();
                for (Transition t:tmp) states.add(t.start());
                possibleWinningStates.addAll(states);
            }
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

        return result;
    }

    private static boolean haveObligedPath(Automaton automaton,State state,Set<State> winningState, Domain domain){
        Set<Set<Proposition>> environmentPATHS = domain.getCombinantionEnvironmentDomain();
        Set<Set<Proposition>> agentPATHS = domain.getCombinantionAgentsDomain();
        for (Set<Proposition> agentPath: agentPATHS){
            boolean isWinning = true;
            for (Set<Proposition> environmentPath:environmentPATHS){
                HashSet<Proposition> path = new HashSet<>();
                path.addAll(agentPath);
                path.addAll(environmentPath);
                Set<Transition> transitions = automaton.delta(state,path);
                for (Transition transition:transitions) if (!winningState.contains(transition.end()))isWinning = false;
            }
            if (isWinning) return true;
        }
        return false;
    }

    public static void print(Automaton automaton,String name){
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(name);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        PrintStream ps = new PrintStream(fos);
        ps.println(AutomatonUtils.toDot(automaton));
        ps.flush();
        ps.close();
    }
}
