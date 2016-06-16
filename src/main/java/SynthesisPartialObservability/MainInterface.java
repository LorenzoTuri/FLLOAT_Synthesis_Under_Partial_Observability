/*
 * FFLOAT  Copyright (C) 2015  Riccardo De Masellis.
 *
 * This program comes with ABSOLUTELY NO WARRANTY.
 * This is free software, and you are welcome to redistribute it
 * under certain conditions; see http://www.gnu.org/licenses/gpl-3.0.html for details.
 */

package SynthesisPartialObservability;

import SynthesisPartialObservability.Utility.Domain;
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
    boolean trim = true;            //TODO non so che faccia
    boolean noEmptyTrace = true;    //TODO non so che faccia
    boolean printing = true;        //stampa l'automa in formato DOT su un file

    //VARIABLES
    PropositionalSignature signature = (new Signature()).getSignature();
    Domain domain = new Domain();
    String input = domain.getInput();
    int formulaType = domain.getFormulatype();

    public MainInterface(){

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

        //Compute the synthesis of the partial observability winning solution
        SynthesisPartialObservability synthesisPartialObservability = new SynthesisPartialObservability(automaton,domain);
        boolean result = synthesisPartialObservability.solve();

        System.out.println((result? "":"non ")+"esiste una strategia sempre vincente");
    }

    public static void main(String[] args) {
        new MainInterface();
        return;
    }
}
