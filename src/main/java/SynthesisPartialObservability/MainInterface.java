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
 * Class used to run the program
 */
public class MainInterface {
    Automaton automaton;

    //CONFIGURATION PART
    boolean declare = false;        //TODO non so che faccia
    boolean minimize = true;        //minimizza l'automa il più possibile
    boolean trim = false;           //TODO non so che faccia
    boolean noEmptyTrace = false;   //TODO non so che faccia
    boolean printing = true;        //stampa l'automa in formato DOT su un file

    //VARIABLES
    PropositionalSignature signature = (new Signature()).getSignature();
    FormulaChoser formulaChoser = new FormulaChoser();
    Domain domain = new Domain(formulaChoser.input,formulaChoser.X,formulaChoser.Y,formulaChoser.formulaType);
    String input = domain.getInput();
    int formulaType = domain.getFormulatype();

    public MainInterface(){
		long startTime = System.nanoTime();
        //Creation of the automa, starting from a formula
        if (formulaType == Domain.FORMULALTLf){
            automaton = (new AutomatonCreation(input,
                    signature,declare,minimize,
                    trim,noEmptyTrace,printing)).getAutomatonLTLf();

        }else if (formulaType == Domain.FORMULALDLf){
            automaton = (new AutomatonCreation(input,
                    signature,declare,minimize,
                    trim,noEmptyTrace,printing)).getAutomatonLDLf();

        }else return;
		System.out.println("Tempo di creazione automa: "+
				(System.nanoTime()-startTime)+" ns, = "+
				(System.nanoTime()-startTime)/1000000+" ms");
        //Compute the synthesis of the partial observability winning solution
	    startTime = System.nanoTime();
	    System.out.println("Tempo di soluzione della strategia vincente: "+
			    (System.nanoTime()-startTime)+" ns, = "+
			    (System.nanoTime()-startTime)/1000000+" ms");
        SynthesisPartialObservability synthesisPartialObservability = new SynthesisPartialObservability(automaton,domain,true);
        boolean result = synthesisPartialObservability.solve();

        System.out.println((result? "":"non ")+"esiste una strategia sempre vincente");
    }

    public static void main(String[] args) {
        new MainInterface();
        return;
    }
}
