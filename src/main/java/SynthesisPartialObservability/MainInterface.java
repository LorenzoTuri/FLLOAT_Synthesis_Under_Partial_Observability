/*
 * FFLOAT  Copyright (C) 2015  Riccardo De Masellis.
 *
 * This program comes with ABSOLUTELY NO WARRANTY.
 * This is free software, and you are welcome to redistribute it
 * under certain conditions; see http://www.gnu.org/licenses/gpl-3.0.html for details.
 */

package SynthesisPartialObservability;

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
    public boolean printing = true;        //print of the automa on file with DOT format

    //VARIABLES
    public PropositionalSignature signature = null;        //per ora non serve a niente
    public FormulaChoser formulaChoser = new FormulaChoser();
    //public Domain domain = new Domain(formulaChoser.input,formulaChoser.X,formulaChoser.Y,formulaChoser.formulaType);
	public PropositionDomain domain = new PropositionDomain(formulaChoser.X,formulaChoser.Y);
    public String input = formulaChoser.input;
    public int formulaType = formulaChoser.formulaType;

    public MainInterface() {
	    long millis = System.currentTimeMillis();
	    Date date = new Date(millis);
	    System.out.println("Exact initial time (HH,MM,SS,mil)-> "+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds()+":"+millis%1000);
	    System.out.println(formulaChoser);

	    long startTime = System.nanoTime();
	    //Creation of the automa, starting from a formula
	    if (formulaType == formulaChoser.FORMULALTLf) {
		    automaton = (new AutomatonCreation(input,
				    signature, declare, minimize,
				    trim, noEmptyTrace, printing)).getAutomatonLTLf();

	    } else if (formulaType == formulaChoser.FORMULALDLf) {
		    automaton = (new AutomatonCreation(input,
				    signature, declare, minimize,
				    trim, noEmptyTrace, printing)).getAutomatonLDLf();

	    } else return;

	    System.out.println("Automata creation time: " +
			    (System.nanoTime() - startTime) + " ns, = " +
			    (System.nanoTime() - startTime) / 1000000 + " ms");
	    //Compute the synthesis of the partial observability winning solution
	    startTime = System.nanoTime();
	    System.out.println("Partial solution computing time: " +
			    (System.nanoTime() - startTime) + " ns, = " +
			    (System.nanoTime() - startTime) / 1000000 + " ms");

	    SynthesisPartialObservability synthesisPartialObservability = new SynthesisPartialObservability(
			    automaton,
			    domain,
			    true);
	    boolean result = synthesisPartialObservability.solve();

	    System.out.println((result ? "there is " : "there isn't ") + "a always winning solution");

    }
    public static void main(String[] args) {
        new MainInterface();
        return;
    }
}
