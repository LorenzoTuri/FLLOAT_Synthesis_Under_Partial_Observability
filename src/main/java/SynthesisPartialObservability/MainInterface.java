/*
 * FFLOAT  Copyright (C) 2015  Riccardo De Masellis.
 *
 * This program comes with ABSOLUTELY NO WARRANTY.
 * This is free software, and you are welcome to redistribute it
 * under certain conditions; see http://www.gnu.org/licenses/gpl-3.0.html for details.
 */

package SynthesisPartialObservability;

import SynthesisPartialObservability.Utility.Domain;
import SynthesisPartialObservability.Utility.FormulaChoser;
import SynthesisPartialObservability.Utility.Signature;
import net.sf.tweety.logics.pl.syntax.PropositionalSignature;
import rationals.Automaton;

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
    public PropositionalSignature signature = (new Signature()).getSignature();        //per ora non serve a niente
    public FormulaChoser formulaChoser = new FormulaChoser();
    public Domain domain = new Domain(formulaChoser.input,formulaChoser.X,formulaChoser.Y,formulaChoser.formulaType);
    public String input = domain.getInput();
    public int formulaType = domain.getFormulaType();

    public MainInterface() {
	    long startTime = System.nanoTime();
	    //Creation of the automa, starting from a formula
	    if (formulaType == Domain.FORMULALTLf) {
		    automaton = (new AutomatonCreation(input,
				    signature, declare, minimize,
				    trim, noEmptyTrace, printing)).getAutomatonLTLf();

	    } else if (formulaType == Domain.FORMULALDLf) {
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
	    SynthesisPartialObservability synthesisPartialObservability =
			    new SynthesisPartialObservability(automaton, domain, true);
	    boolean result = synthesisPartialObservability.solve();

	    System.out.println((result ? "there is " : "there isn't ") + "a always winning solution");

    }
    public static void main(String[] args) {
        new MainInterface();
        return;
    }
}
