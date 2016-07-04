package SynthesisPartialObservability;

import SynthesisPartialObservability.Utility.DoubleMurphyFormulas;
import SynthesisPartialObservability.Utility.TimingHandler;
import SynthesisPartialObservability.Utility.Utility;
import rationals.Automaton;
import rationals.transformations.Mix;
import rationals.transformations.Union;

import java.util.List;

/**
 * This class is used to generate a solution of the double murphy problem.
 * Actions, ecc are described in the class DoubleMurphyFormulas.java, that is only a information container.
 * This class generates the automaton and uses SynthesisPartialObservability.java class to compute
 * if there's a always winning solution.
 */
public class DoubleMurphyMainInterface {
	public DoubleMurphyMainInterface(){
		TimingHandler timingHandler = new TimingHandler();
		long StartingTime = System.currentTimeMillis();
		timingHandler.add("Starting Time",StartingTime);
		/**
		 * LTLf formulas used to generate a game world are made like this:
		 * init && ((exclusion && actions) || true) && objectives
		 *      init is a simple formula
		 *      exclusion is a simple formula like G(something)
		 *      action is a set of formulas like G(X(something) -> X(somethingElse))
		 *      objectives is a simple formula like F(something)
		 * FLLOAT need to compute automaton separately:
		 * 1 .Generate an automaton for init formulas
		 * 2 .Generate an automaton for exclusion formulas
		 * 3 .Generate an automaton for every actions formulas
		 * 4 .Generate an automaton for true formula
		 * 5 .Generate an automaton for objectives
		 * 6 .Use class Reducer to reduce every automaton.
		 * 7 .Use class Union to compute union between actions from (3).
		 * 8 .Use class Mix to compute the intersection between actions and exclusions.
		 * 9 .Use class Union to compute union between automaton from (8) e automaton from (4)
		 * 10.Use class Mix to compute the intersection between automatons from (1), (9) and (5)
		 * Solve the resulting automaton.
		 * WARNING: use for every automaton the PropositionalSignature from class DoubleMurphyFormulas;
		 *      it contains the right sign, with every proposition used in these formulas.
 		 */

		DoubleMurphyFormulas formulas = new DoubleMurphyFormulas();

		//Creation of the automaton with the initialization formula
		Automaton initAutomaton = ((new AutomatonCreation(
				formulas.getInit(),
				formulas.getSignature(),
				false,  //declare
				true,   //minimize
				false,  //trim
				false,  //noEmptyTrace
				false   //printing
		)).getAutomatonLTLf());
		//initAutomaton = (new Reducer<>()).transform(initAutomaton);
		Utility.print(initAutomaton,"DoubleMurphy/initAutomaton.gv");
		System.out.println("created: initAutomaton");
		timingHandler.add("Init Automaton Creation",System.currentTimeMillis());

		//Creation of the automaton with the exclusion formula
		Automaton exclusionAutomaton = (new AutomatonCreation(
				formulas.getExclusions(),
				formulas.getSignature(),
				false,  //declare
				true,   //minimize
				false,  //trim
				false,  //noEmptyTrace
				false   //printing
		)).getAutomatonLTLf();
		//exclusionAutomaton = (new Reducer<>()).transform(exclusionAutomaton);
		Utility.print(exclusionAutomaton,"DoubleMurphy/exclusionAutomaton.gv");
		System.out.println("created: exclusionAutomaton");
		timingHandler.add("Exclusion Automaton Creation",System.currentTimeMillis());

		//Creation of the automatons with the actions formulas
		Automaton actionsAutomaton[] = new Automaton[formulas.getActions().size()];
		for(int i = 0;i<formulas.getActions().size();i++){
			actionsAutomaton[i] = (new AutomatonCreation(
					formulas.getActions().get(i),
					formulas.getSignature(),
					false,  //declare
					true,   //minimize
					false,  //trim
					false,  //noEmptyTrace
					false   //printing
			)).getAutomatonLTLf();
			//Utility.print(actionsAutomaton[i],"DoubleMurphy/actions-"+i+"-AutomatonNoReduction.gv");
			//actionsAutomaton[i] = (new Reducer<>()).transform(actionsAutomaton[i]);
			Utility.print(actionsAutomaton[i],"DoubleMurphy/actions-"+i+"-Automaton.gv");
			System.out.println("created: actionsAutomaton["+i+"]");
			timingHandler.add("Action["+i+"] Automaton Creation",System.currentTimeMillis());
		}
		timingHandler.add("Actions Automaton Completion",System.currentTimeMillis());

		//Creation of the automaton with the "true" formula
		Automaton trueAutomaton = (new AutomatonCreation(
				"true",
				formulas.getSignature(),
				false,  //declare
				true,   //minimize
				false,  //trim
				false,  //noEmptyTrace
				false   //printing
		)).getAutomatonLTLf();
		//trueAutomaton = (new Reducer<>()).transform(trueAutomaton);
		Utility.print(trueAutomaton,"DoubleMurphy/trueAutomaton.gv");
		System.out.println("created: trueAutomaton");
		timingHandler.add("True Automaton Creation",System.currentTimeMillis());

		//Creation of the automaton with the objectives formula
		Automaton objectivesAutomaton = (new AutomatonCreation(
				formulas.getObjective(),
				formulas.getSignature(),
				false,  //declare
				true,   //minimize
				false,  //trim
				false,  //noEmptyTrace
				false   //printing
		)).getAutomatonLTLf();
		//objectivesAutomaton = (new Reducer<>()).transform(objectivesAutomaton);
		Utility.print(objectivesAutomaton,"DoubleMurphy/objectivesAutomaton.gv");
		System.out.println("created: objectivesAutomaton");
		timingHandler.add("Objectives Automaton Creation",System.currentTimeMillis());

		//Computing the union of the actions formulas.
		Automaton unionActionsAutomaton = actionsAutomaton[0];
		for (int i = 1;i<actionsAutomaton.length;i++)
			unionActionsAutomaton = (new Union<>()).transform(unionActionsAutomaton,actionsAutomaton[i]);
		Utility.print(unionActionsAutomaton,"DoubleMurphy/unionActionsAutomaton.gv");
		System.out.println("computed: unionActionsAutomaton");
		timingHandler.add("Union of Actions Automaton Computation",System.currentTimeMillis());

		//Computing intersection between actions and exclusions.
		Automaton mixedActionsExclusionsAutomaton = (new Mix<>()).transform(unionActionsAutomaton,exclusionAutomaton);
		Utility.print(mixedActionsExclusionsAutomaton,"DoubleMurphy/mixedActionsExsclusionsAutomaton.gv");
		System.out.println("computed: mixedActionsExclusionsAutomaton");
		timingHandler.add("Mixing between Action and Exclusion Automaton Computation",System.currentTimeMillis());

		//Computing "body part" of the formula (union of mixedActionsEsclusionsAutomaton and trueAutomaton)
		Automaton bodyAutomaton = (new Union<>()).transform(mixedActionsExclusionsAutomaton,trueAutomaton);
		Utility.print(bodyAutomaton,"DoubleMurphy/bodyAutomaton.gv");
		System.out.println("computed: bodyAutomaton");
		timingHandler.add("Body Formulas Automaton Computation",System.currentTimeMillis());

		//Computing intersection (MIX) of initialization, body and objectives automaton
		Automaton completeAutomaton = (new Mix<>()).transform(initAutomaton,bodyAutomaton);
		completeAutomaton = (new Mix<>()).transform(completeAutomaton,objectivesAutomaton);
		Utility.print(completeAutomaton,"DoubleMurphy/completeAutomaton.gv");
		System.out.println("computed: completeAutomaton");
		timingHandler.add("Complete Automaton Computation",System.currentTimeMillis());

		//Computation of the winning solution
		SynthesisPartialObservability synthesis = new SynthesisPartialObservability(
				completeAutomaton,
				formulas.getDomain(),
				true/*Print = true*/);
		System.out.println("The Double Murphy problem "+(synthesis.solve()?"has":"hasn't")+" a always winning solution");
		timingHandler.add("Solution Computation",System.currentTimeMillis());


		//Summary of the timing.
		System.out.println("\n\n\n---------------------------------  TIMING SUMMARY  -----------------------------------\n");
		List<TimingHandler.DataContainer> timers = timingHandler.get();
		long previousTime = StartingTime;
		for (TimingHandler.DataContainer d:timers){
			System.out.println("\t"+d.description +"\n" +
					"\t\tTime Elapsed (Millis) : "+(d.time-previousTime)+"\n" +
					"\t\tTime Elapsed(Seconds) : "+((int)(d.time-previousTime)/1000)+"\n"+
					"\t\tTime Elapsed from beginning(Seconds) : "+((int)(d.time-StartingTime)/1000));
			previousTime = d.time;
		}
	}




	public static void main(String args[]){
		new DoubleMurphyMainInterface();
	}
}
