package SynthesisPartialObservability.Utility;

import net.sf.tweety.logics.pl.syntax.Proposition;
import net.sf.tweety.logics.pl.syntax.PropositionalSignature;

/**
 * Utility class used to set the formula, it's domain and some configuration
 */
public class FormulaChooser {
    public static final int FORMULALTLf = 1;
    public static final int FORMULALDLf = 2;

    //VARIABLES
    public int formulaType=0;
    public String input="";
	private String toStringOutput = "";
    public PropositionalSignature X;
    public PropositionalSignature Y;
	public PropositionalSignature hidden;

    /**
     * Construtor that assign the formula and it's given config, like the environment and agents set and type of formula.
     * Is istantiated from the domain element
     */
    public FormulaChooser(){
        X = new PropositionalSignature();
        Y = new PropositionalSignature();
	    hidden = new PropositionalSignature();
        /////////////////////// FORMULAE LDLf //////////////////////////////
        /*
            input = "[true*](([true]ff) || (<!a>tt) || (<true*>(<b>tt)))";
            X.add(new Proposition("a"));
            Y.add(new Proposition("b"));
            formulaType = FORMULALDLf;
	        toStringOutput = input+"\nSystem:\t"+X+"\nAgent:\t"+Y;
        */
        /*
            input = "<((a)*)*>b";
            X.add(new Proposition("a"));
            Y.add(new Proposition("b"));
            formulaType = FORMULALDLf;
	        toStringOutput = input+"\nSystem:\t"+X+"\nAgent:\t"+Y;
        */
        /*
            input = "[true; true*; !((e -> (!l & !buy)) & (l -> (!e & !buy)) & (buy -> (!e & !l)))]ff";
            X.add(new Proposition("e"));
            X.add(new Proposition("buy"));
            Y.add(new Proposition("l"));
            hidden.add(new Proposition("buy"));
            formulaType = FORMULALDLf;
	        toStringOutput = input+"\nSystem:\t"+X+"\nAgent:\t"+Y+"\nHidden:\t"+hidden;
        */
        /////////////////////// FORMULE LTLf //////////////////////////////
        /*
            input = "(a R b)";
            X.add(new Proposition("a"));
            Y.add(new Proposition("b"));
            formulaType = FORMULALTLf;
	        toStringOutput = input+"\nSystem:\t"+X+"\nAgent:\t"+Y;
        /*/
        /*
            input = "G (a -> (F b))";
            Y.add(new Proposition("a"));
            X.add((new Proposition("b"));
            formulaType = FORMULALTLf;
	        toStringOutput = input+"\nSystem:\t"+X+"\nAgent:\t"+Y;
        */
        /*
            input = "(F((a U (b|c)) R ((X e) || ((WX f) && (G h) ) ) )) -> ((F d) R (((g)||(i)) U (l)))";
            X.add(new Proposition("a"));
            X.add(new Proposition("c"));
            X.add(new Proposition("e"));
            X.add(new Proposition("g"));
            X.add(new Proposition("i"));
            Y.add(new Proposition("b"));
            Y.add(new Proposition("d"));
            Y.add(new Proposition("f"));
            Y.add(new Proposition("h"));
            Y.add(new Proposition("l"));
            formulaType = FORMULALTLf;
	        toStringOutput = input+"\nSystem:\t"+X+"\nAgent:\t"+Y;
        */
        /*
            input = "(G(rl -> (F aa))) & (G(aa -> (F dl))) & (G(aa -> (X dl)))";
            X.add(new Proposition("rl"));
	        X.add(new Proposition("dl"));
            Y.add(new Proposition("aa"));
	        hidden.add(new Proposition("dl"));
            formulaType = FORMULALTLf;
	        toStringOutput = input+"\nSystem:\t"+X+"\nAgent:\t"+Y+"\nHidden:\t"+hidden;
        */
        ///*
            input = "(G(rl -> (F aa))) & (G(aa -> (X dl)))";
            X.add(new Proposition("rl"));
	        X.add(new Proposition("dl"));
            Y.add(new Proposition("aa"));
            hidden.add(new Proposition("dl"));
            formulaType = FORMULALTLf;
	        toStringOutput = input+"\nSystem:\t"+X+"\nAgent:\t"+Y+"Hidden:\t"+hidden;
        //*/

    }

	@Override
	public String toString() {
		return toStringOutput;
	}
}
