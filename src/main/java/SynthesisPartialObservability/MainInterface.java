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


public class MainInterface {
    Automaton automaton;


    //CONFIGURATION PART
    boolean declare = false;
    boolean minimize = true;
    boolean trim = true;
    boolean noEmptyTrace = true;
    boolean printing = true;


    PropositionalSignature signature = (new Signature()).getSignature();
    Domain domain = new Domain();
    String input = domain.getInput();
    int formulaType = domain.getFormulatype();

    public MainInterface(){

        if (formulaType == Domain.FORMULALTLf){

            automaton = (new AutomatonCreation(input,
                    signature,declare,minimize,
                    trim,noEmptyTrace,printing)).getAutomatonLTLf();

        }else if (formulaType == Domain.FORMULALDLf){

            automaton = (new AutomatonCreation(input,
                    signature,declare,minimize,
                    trim,noEmptyTrace,printing)).getAutomatonLDLf();

        }else return;

        SynthesisPartialObservability synthesisPartialObservability = new SynthesisPartialObservability(automaton,domain);
        boolean result = synthesisPartialObservability.solve();

        System.out.println((result? "":"non")+" esiste una strategia sempre vincente");
    }

    public static void main(String[] args) {
        new MainInterface();
        return;
    }
}
