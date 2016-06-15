package util;

import FLLOAT.automaton.EmptyTrace;
import FLLOAT.automaton.PossibleWorldWrap;
import FLLOAT.automaton.TransitionLabel;
import FLLOAT.formula.ldlf.LDLfFormula;
import FLLOAT.formula.ltlf.LTLfFormula;
import FLLOAT.formula.ltlf.LTLfLocalVar;
import net.sf.tweety.logics.pl.syntax.Proposition;
import rationals.Automaton;
import rationals.NoSuchStateException;
import rationals.State;
import rationals.Transition;
import rationals.transformations.Reducer;
import synthesis.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * AutomatonUtils
 * <br>
 * Created by Simone Calciolari on 01/04/16.
 * @author Simone Calciolari.
 */
public class AutomatonUtils {

	public static Automaton buildLTLfAutomaton(LTLfFormula formula){
		LDLfFormula ldLfFormula = formula.toLDLf();
		return new Reducer<>().transform(FLLOAT.utils.AutomatonUtils.ldlf2Automaton(ldLfFormula, ldLfFormula.getSignature()));
	}

	public static Automaton transalteToGameAutomaton(Automaton original, PartitionedDomain domain){
		Automaton res = new Automaton();

		PropositionSet environment = domain.getEnvironmentDomain();
		PropositionSet system = domain.getSystemDomain();

		//Get original states iterator
		Iterator<State> originalStates = original.states().iterator();
		//Map to translate states
		HashMap<State, State> oldToNewStates = new HashMap<>();

		//Add states to the new FLLOAT.automaton and fill the map
		while (originalStates.hasNext()){
			State oldState = originalStates.next();
			State newState = res.addState(oldState.isInitial(), oldState.isTerminal());
			oldToNewStates.put(oldState, newState);
		}

		//ADDING TRANSITION TO NEW AUTOMATON
		//For each original state, get all transitions starting from it, translate the label,
		//and insert the new translated transition in the translated FLLOAT.automaton

		//Get the iterator on the original states (again)
		originalStates = original.states().iterator();

		while (originalStates.hasNext()){

			State oldStart = originalStates.next();
			Set<Transition<TransitionLabel>> oldTransitions = original.delta(oldStart);

			//Iterate over all transition starting from the current (old) state.
			for (Transition<TransitionLabel> oldTransition : oldTransitions){

				//Get end state
				State oldEnd = oldTransition.end();
				//Get old label
				TransitionLabel oldLabel = oldTransition.label();

				//New label
				SynthTransitionLabel newLabel;

				if (oldLabel instanceof EmptyTrace){
					newLabel = new SynthEmptyTrace();
				} else if (oldLabel instanceof PossibleWorldWrap){
					newLabel = partitionPossibleWorld((PossibleWorldWrap) oldLabel, domain);
				} else {
					throw new RuntimeException("Unknown label type");
				}

				//Create new transition
				//Get start and end states
				State newStart = oldToNewStates.get(oldStart);
				State newEnd = oldToNewStates.get(oldEnd);

				Transition<SynthTransitionLabel> newTransition = new Transition<>(newStart, newLabel, newEnd);

				//Add it to translated FLLOAT.automaton
				try {
					res.addTransition(newTransition);
				} catch (NoSuchStateException e){
					throw new RuntimeException(e);
				}
			}
		}

		return res;
	}

	public static void writeAutomatonGv(Automaton automaton, String fileName){
		FileOutputStream fos = null;

		try {
			fos = new FileOutputStream(fileName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PrintStream ps = new PrintStream(fos);
		ps.println(FLLOAT.utils.AutomatonUtils.toDot(automaton));
		ps.flush();
		ps.close();
	}

	private static PartitionedWorldLabel partitionPossibleWorld(PossibleWorldWrap pw, PartitionedDomain domain){
		PropositionSet environment = new PropositionSet();
		PropositionSet system = new PropositionSet();

		for (Proposition p : pw){
			LTLfLocalVar lv = new LTLfLocalVar(p);

			if (domain.getEnvironmentDomain().contains(lv)){
				environment.add(lv);
			} else if (domain.getSystemDomain().contains(lv)) {
				system.add(lv);
			} else {
				throw new RuntimeException("Found propositional variable not declared in domain");
			}
		}

		return new PartitionedWorldLabel(environment, system);
	}
}
