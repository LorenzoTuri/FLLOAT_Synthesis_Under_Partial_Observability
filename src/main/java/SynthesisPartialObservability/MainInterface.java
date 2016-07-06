/*
 * FFLOAT  Copyright (C) 2015  Riccardo De Masellis.
 *
 * This program comes with ABSOLUTELY NO WARRANTY.
 * This is free software, and you are welcome to redistribute it
 * under certain conditions; see http://www.gnu.org/licenses/gpl-3.0.html for details.
 */

package SynthesisPartialObservability;

import SynthesisPartialObservability.Timer.TimingHandler;
import SynthesisPartialObservability.Utility.FormulaChoser;
import SynthesisPartialObservability.Utility.PropositionDomain;
import net.sf.tweety.logics.pl.syntax.PropositionalSignature;
import rationals.Automaton;

import java.util.Date;

/**
 * Class used to run the program, not necessary to the project
 */
public class MainInterface {
	public Automaton automaton;

    //CONFIGURATION PART
    public boolean declare = false;        //TODO non so che faccia
    public boolean minimize = true;        //minimize the automa as much as possible
    public boolean trim = false;           //TODO non so che faccia
    public boolean noEmptyTrace = false;   //TODO non so che faccia
    public boolean printing = true;        //printAutomaton of the automa on file with DOT format

    //VARIABLES
    public PropositionalSignature signature = null;        //per ora non serve a niente
    public FormulaChoser formulaChoser = new FormulaChoser();
    //public Domain domain = new Domain(formulaChoser.input,formulaChoser.X,formulaChoser.Y,formulaChoser.formulaType);
	public PropositionDomain domain = new PropositionDomain(formulaChoser.X,formulaChoser.Y);
    public String input = formulaChoser.input;
    public int formulaType = formulaChoser.formulaType;

    public MainInterface() {
	    TimingHandler timingHandler = new TimingHandler();

	    long millis = System.currentTimeMillis();
	    Date date = new Date(millis);
	    timingHandler.add("InitialTimeMillis",millis);
	    timingHandler.add("Exact initial time(HH)",date.getHours());
	    timingHandler.add("Exact initial time(MM)",date.getMinutes());
	    timingHandler.add("Exact initial time(SS)",date.getSeconds());
	    timingHandler.add("Exact initial time(Millis)", millis%1000);

	    System.out.println("MainInterface beginning");
	    System.out.println(formulaChoser);

	    //Creation of the automa, starting from a formula
	    automaton = (new AutomatonCreation(
			    input,
			    signature,
			    declare,
			    minimize,
			    trim,
			    noEmptyTrace,
			    printing,
			    formulaType)).getAutomaton();

	    assert automaton != null;
	    System.out.println("Automaton created");

		timingHandler.add("Automata creation time(Millis)",System.currentTimeMillis());
	    //Compute the synthesis of the partial observability winning solution
	    SynthesisPartialObservability synthesisPartialObservability = new SynthesisPartialObservability(
			    automaton,
			    domain,
			    true);
	    boolean result = synthesisPartialObservability.solve();
	    System.out.println("Solution Computed");

	    timingHandler.add("PartialSolution Computing Time(Millis)",System.currentTimeMillis());
	    System.out.println((result ? "there is " : "there isn't ") + "a always winning solution");

	    System.out.println("\n\n\n--------------------------------SUMMARY------------------------------------");
	    long initialTimeMillis = timingHandler.get().get(0).time;
	    System.out.println("Initial time: ");
	    System.out.println("\t"+timingHandler.get().get(1));
	    System.out.println("\t"+timingHandler.get().get(2));
	    System.out.println("\t"+timingHandler.get().get(3));
	    System.out.println("\t"+timingHandler.get().get(4));
	    System.out.println(timingHandler.get().get(5).description+": "+(timingHandler.get().get(5).time-initialTimeMillis));
	    System.out.println(timingHandler.get().get(6).description+": "+(timingHandler.get().get(6).time-initialTimeMillis));

    }
    public static void main(String[] args) {
        new MainInterface();
        return;
    }
}
