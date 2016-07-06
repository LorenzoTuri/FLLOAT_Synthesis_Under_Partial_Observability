/*
 * FFLOAT  Copyright (C) 2015  Riccardo De Masellis.
 *
 * This program comes with ABSOLUTELY NO WARRANTY.
 * This is free software, and you are welcome to redistribute it
 * under certain conditions; see http://www.gnu.org/licenses/gpl-3.0.html for details.
 */

package SynthesisPartialObservability;

import SynthesisPartialObservability.Timer.TimingHandler;
import SynthesisPartialObservability.Utility.FormulaChooser;
import SynthesisPartialObservability.Utility.PropositionDomain;
import SynthesisPartialObservability.Utility.Utility;
import main.Main;
import net.sf.tweety.logics.pl.syntax.PropositionalSignature;
import rationals.Automaton;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Class used to run the program, not necessary to the project
 */
public class MainInterface {
	public Automaton automaton;

    //CONFIGURATION PART
    private boolean declare = false;        //TODO non so che faccia
    private boolean minimize = true;        //minimize the automa as much as possible
    private boolean trim = false;           //TODO non so che faccia
    private boolean noEmptyTrace = true;    //deleting of "EmptyTrace" transition from the automaton
    private boolean printing = false;       //print of the automaton on console

	private boolean printOnFileAutomaton = true;
	private boolean printOnFilePartialAutomaton = true;

    //VARIABLES
    private PropositionalSignature signature = null;        //per ora non serve a niente
    private FormulaChooser formulaChooser = new FormulaChooser();
    //public Domain domain = new Domain(formulaChooser.input,formulaChooser.X,formulaChooser.Y,formulaChooser.formulaType);
	private PropositionDomain domain = new PropositionDomain(formulaChooser.X, formulaChooser.Y, formulaChooser.hidden);
    private String input = formulaChooser.input;
    private int formulaType = formulaChooser.formulaType;

    public MainInterface() {
	    TimingHandler timingHandler = new TimingHandler();

	    long millis = System.currentTimeMillis();
	    GregorianCalendar date = new GregorianCalendar();
	    timingHandler.add("InitialTimeMillis",millis);
	    timingHandler.add("Exact initial time(HH)",date.get(Calendar.HOUR));
	    timingHandler.add("Exact initial time(MM)",date.get(Calendar.MINUTE));
	    timingHandler.add("Exact initial time(SS)",date.get(Calendar.SECOND));
	    timingHandler.add("Exact initial time(Millis)", millis%1000);

	    System.out.println("MainInterface beginning");
	    System.out.println(formulaChooser);

	    //Creation of the automa, starting from a formula
	    if (formulaType == formulaChooser.FORMULALDLf)
		    automaton = (Main.ldlfString2Aut(
				    input,
				    signature,
				    declare,
				    minimize,
				    trim,
				    noEmptyTrace,
				    printing
		    )).getAutomaton();
	    else if (formulaType == formulaChooser.FORMULALTLf)
		    automaton = (Main.ltlfString2Aut(
				    input,
				    signature,
				    declare,
				    minimize,
				    trim,
				    noEmptyTrace,
				    printing
		    )).getAutomaton();

	    assert automaton != null;
	    System.out.println("Automaton created");

		timingHandler.add("Automata creation time(Millis)",System.currentTimeMillis());

	    //Compute the synthesis of the partial observability winning solution
	    Utility.printAutomaton(automaton,"Automatons/OriginalAutomaton.gv");
	    SynthesisPartialObservability synthesisPartialObservability = new SynthesisPartialObservability(
			    automaton,
			    domain,
			    printOnFilePartialAutomaton);
	    boolean result = synthesisPartialObservability.solve();
	    System.out.println("Solution Computed");

	    timingHandler.add("PartialSolution Computing Time(Millis)",System.currentTimeMillis());
	    System.out.println((result ? "there is " : "there isn't ") + "a always winning solution");




	    System.out.println("\n\n\n--------------------------------SUMMARY------------------------------------");
	    long initialTimeMillis = timingHandler.get().get(0).time;
	    System.out.println("Initial time: ");
	    System.out.println("\tExact initial time(HH.MM.SS): "+timingHandler.get().get(1).time+"."+"" +
			    timingHandler.get().get(2).time+"."+
	            timingHandler.get().get(3).time);
	    System.out.println("\t"+timingHandler.get().get(4));
	    System.out.println(timingHandler.get().get(5).description+": "+(timingHandler.get().get(5).time-initialTimeMillis));
	    System.out.println(timingHandler.get().get(6).description+": "+(timingHandler.get().get(6).time-initialTimeMillis));

    }
    public static void main(String[] args) {
        new MainInterface();
    }
}
