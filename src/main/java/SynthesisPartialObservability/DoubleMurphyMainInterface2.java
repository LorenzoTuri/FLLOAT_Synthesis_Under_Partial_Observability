package SynthesisPartialObservability;

import SynthesisPartialObservability.Timer.TimingHandler;
import SynthesisPartialObservability.Utility.AutomatonConsole;
import SynthesisPartialObservability.Utility.DoubleMurphyFormulas2;
import SynthesisPartialObservability.Utility.Utility;
import main.Main;
import rationals.Automaton;
import rationals.transformations.Mix;
import rationals.transformations.Union;

/**
 * This class is used to generate a solution of the double murphy problem.
 * Actions, ecc are described in the class DoubleMurphyFormulas.java, that is only a information container.
 * This class generates the automaton and uses SynthesisPartialObservability.java class to compute
 * if there's a always winning solution.
 */
public class DoubleMurphyMainInterface2 {
	public DoubleMurphyMainInterface2(){
		TimingHandler timingHandler = new TimingHandler();
		long StartingTime = System.currentTimeMillis();
		timingHandler.add("Starting Time",StartingTime);
		/**
		 * LTLf formulas used to generate a game world are made like this:
		 * (G(regolemondo) && init && G(actions) && objectives) || !G(regolemondo)
		 * 1. generare l'automa delle regole (G(regolemondo))
		 * 2. generare l'automa init
		 * 3. generare l'automa G(actions)
		 * 4. generare l'automa objectives
		 * 5. generare l'automa !G(regolemondo) (ovvero negare l'automa (1))
		 * 6. usare la classe Mix per intersecare (1) con (2)
		 * 7. usare la classe Mix per intersecare (6) con (3)
		 * 8. usare la classe Mix per intersecare (7) con (4)
		 * 9. usare la classe Union per unire (8) con (5)
		 * Solve the resulting automaton.
		 * WARNING: use for every automaton the PropositionalSignature from class DoubleMurphyFormulas;
		 *      it contains the right sign, with every proposition used in these formulas.
 		 */

		DoubleMurphyFormulas2 formulas = new DoubleMurphyFormulas2();

		Automaton rulesAutomaton = Main.ltlfString2Aut(
				"G("+formulas.getRegolemondo()+")",
				formulas.getSignature(),
				false,
				true,
				false,
				true,
				false
		).getAutomaton();
			Utility.printAutomaton(rulesAutomaton,"Automatons/rules automaton.gv");
			Utility.printAutomatonOptimized(rulesAutomaton,"Automatons/[OPT]rules automaton.gv");
		Automaton initAutomaton = Main.ltlfString2Aut(
				formulas.getInit(),
				formulas.getSignature(),
				false,
				true,
				false,
				true,
				false
		).getAutomaton();
			Utility.printAutomaton(initAutomaton,"Automatons/init automaton.gv");
			Utility.printAutomatonOptimized(initAutomaton,"Automatons/[OPT]init automaton.gv");
		Automaton actionsAutomaton = Main.ltlfString2Aut(
				"G("+formulas.getActions()+")",
				formulas.getSignature(),
				false,
				true,
				false,
				true,
				false
		).getAutomaton();
			Utility.printAutomaton(actionsAutomaton,"Automatons/actions automaton.gv");
			Utility.printAutomatonOptimized(actionsAutomaton,"Automatons/[OPT]actions automaton.gv");
		Automaton objectivesAutomaton = Main.ltlfString2Aut(
				formulas.getObjectives(),
				formulas.getSignature(),
				false,
				true,
				false,
				true,
				false
		).getAutomaton();
			Utility.printAutomaton(objectivesAutomaton,"Automatons/objectives automaton.gv");
			Utility.printAutomatonOptimized(objectivesAutomaton,"Automatons/[OPT]objectives automaton.gv");
		Automaton negatedRulesAutomaton = Utility.negateAutomaton(rulesAutomaton);
			Utility.printAutomaton(negatedRulesAutomaton,"Automatons/negated rules automaton.gv");
			Utility.printAutomatonOptimized(negatedRulesAutomaton,"Automatons/[OPT]negated rules automaton.gv");

		Automaton sixAutomaton = (new Mix()).transform(rulesAutomaton,initAutomaton);
			Utility.printAutomaton(sixAutomaton,"Automatons/first mix automaton.gv");
			Utility.printAutomatonOptimized(sixAutomaton,"Automatons/[OPT]first mix automaton.gv");

		Automaton sevenAutomaton = (new Mix()).transform(sixAutomaton,actionsAutomaton);
			Utility.printAutomaton(sevenAutomaton,"Automatons/second mix automaton.gv");
			Utility.printAutomatonOptimized(sevenAutomaton,"Automatons/[OPT]second mix automaton.gv");

		Automaton eigthAutomaton = (new Mix()).transform(sevenAutomaton,objectivesAutomaton);
			Utility.printAutomaton(eigthAutomaton,"Automatons/third mix automaton.gv");
			Utility.printAutomatonOptimized(eigthAutomaton,"Automatons/[OPT]third mix automaton.gv");

		Automaton completeAutomaton = (new Union<>()).transform(eigthAutomaton,negatedRulesAutomaton);
			Utility.printAutomaton(completeAutomaton,"Automatons/complete automaton.gv");
			Utility.printAutomatonOptimized(completeAutomaton,"Automatons/[OPT]complete automaton.gv");

		SynthesisPartialObservability synthesis = new SynthesisPartialObservability(
				completeAutomaton,
				formulas.getPropositionDomain(),
				true/*print partial automaton*/);

		System.out.println("Double Murphy problem "+(synthesis.solve()?"has":"hasn't")+" solution");

		AutomatonConsole console = (new AutomatonConsole(completeAutomaton));
		console.start();


		/*
		//Summary of the timing.
		System.out.println("\n\n\n---------------------------------  TIMING SUMMARY  -----------------------------------\n");
		List<DataContainer> timers = timingHandler.get();
		long previousTime = StartingTime;
		for (DataContainer d:timers){
			System.out.println("\t"+d.description +"\n" +
					"\t\tTime Elapsed (Millis) : "+(d.time-previousTime)+"\n" +
					"\t\tTime Elapsed(Seconds) : "+((int)(d.time-previousTime)/1000)+"\n"+
					"\t\tTime Elapsed from beginning(Seconds) : "+((int)(d.time-StartingTime)/1000));
			previousTime = d.time;
		}*/
	}

	public static void main(String args[]){
		new DoubleMurphyMainInterface2();
	}
}
