package SynthesisPartialObservability;

import SynthesisPartialObservability.Utility.DoubleMurphyFormulas;
import rationals.Automaton;

/**
 * Created by loren on 30/06/2016.
 */
public class DoubleMurphyMainInterface {
	public DoubleMurphyMainInterface(){
		/**
		 * TODO things:
		 * LTLf formulas used to generate a game world are made like this:
		 * init && ((exclusion && actions) || true) && objectives
		 *      init is a simple formula
		 *      exclusion is a simple formula like G(something)
		 *      action is a set of formulas like G(X(something) -> X(somethingElse))
		 *      objectives is a simple formula like F(something)
		 * FLLOAT need to compute automaton separately:
		 * 1.Generate an automaton for init formulas
		 * 2.Generate an automaton for exclusion formulas
		 * 3.Generate an automaton for every actions formulas
		 * 4.Generate an automaton for true formula
		 * 5.Generate an automaton for objectives
		 * 6.Use class Reducer to reduce every automaton.
		 * 7.Use class Mix to compute the union of the actions from (3)
		 * 8.Use class (TODO find class) to compute intersection between automaton from (7) and exclusion from (2)
		 * 9.Use class Mix to compute union between automaton from (8) e automaton from (4)
		 * 10.Use class from (8) to compute the intersection between automatons from (1), (9) and (5)
		 * Solve the resulting automaton.
		 * WARNING: use for every automaton the PropositionalSignature from class DoubleMurphyFormulas;
		 *      it contains the rign sign, with every proposition used in theese formulas.
 		 */

		DoubleMurphyFormulas formulas = new DoubleMurphyFormulas();
		Automaton initAutomaton = (new AutomatonCreation(
				formulas.getInit(),
				formulas.getSignature(),
				false,  //declare
				true,   //minimize
				false,  //trim
				false,  //noEmptyTrace
				false   //printing
		)).getAutomatonLTLf();
		Automaton exclusionAutomaton = (new AutomatonCreation(
				formulas.getExclusions(),
				formulas.getSignature(),
				false,  //declare
				true,   //minimize
				false,  //trim
				false,  //noEmptyTrace
				false   //printing
		)).getAutomatonLTLf();
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
		}
		Automaton trueAutomaton = (new AutomatonCreation(
				"true",
				formulas.getSignature(),
				false,  //declare
				true,   //minimize
				false,  //trim
				false,  //noEmptyTrace
				false   //printing
		)).getAutomatonLTLf();
		Automaton objectivesAutomaton = (new AutomatonCreation(
				formulas.getObjective(),
				formulas.getSignature(),
				false,  //declare
				true,   //minimize
				false,  //trim
				false,  //noEmptyTrace
				false   //printing
		)).getAutomatonLTLf();

		//TODO: see doc on top, from (6) on.

	}




	public static void main(String args[]){
		new DoubleMurphyMainInterface();
	}
}
