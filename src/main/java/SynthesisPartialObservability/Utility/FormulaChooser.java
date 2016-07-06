package SynthesisPartialObservability.Utility;

import formula.ltlf.LTLfLocalVar;
import synthesis.symbols.PropositionSet;

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
    public PropositionSet X;
    public PropositionSet Y;

    /**
     * Construtor that assign the formula and it's given config, like the environment and agents set and type of formula.
     * Is istantiated from the domain element
     */
    public FormulaChooser(){
        X = new PropositionSet();
        Y = new PropositionSet();
        /////////////////////// FORMULAE LDLf //////////////////////////////
        /*
            input = "[true*](([true]ff) || (<!a>tt) || (<true*>(<b>tt)))";
            X.add(new LTLfLocalVar(new Proposition("a")));
            Y.add(new LTLfLocalVar(new Proposition("b")));
            formulaType = FORMULALDLf;
	        toStringOutput = input+"\nSystem:\t"+X+"\nAgent:\t"+Y;
	        toStringOutput = input+"\nSystem:\t"+X+"\nAgent:\t"+Y;
        */
        /*
            input = "<((a)*)*>b";
            X.add(new LTLfLocalVar(new Proposition("a")));
            Y.add(new LTLfLocalVar(new Proposition("b")));
            formulaType = FORMULALDLf;
	        toStringOutput = input+"\nSystem:\t"+X+"\nAgent:\t"+Y;
	        toStringOutput = input+"\nSystem:\t"+X+"\nAgent:\t"+Y;
        */
        /*
            input = "[true; true*; !((e -> (!l & !buy)) & (l -> (!e & !buy)) & (buy -> (!e & !l)))]ff";
            X.add(new LTLfLocalVar(new Proposition("e")));
            Y.add(new LTLfLocalVar(new Proposition("l")));
            formulaType = FORMULALDLf;
	        toStringOutput = input+"\nSystem:\t"+X+"\nAgent:\t"+Y;
        */
        /////////////////////// FORMULE LTLf //////////////////////////////
        /*
            input = "(a R b)";
            X.add(new LTLfLocalVar("a"));
            Y.add(new LTLfLocalVar("b"));
            formulaType = FORMULALTLf;
	        toStringOutput = input+"\nSystem:\t"+X+"\nAgent:\t"+Y;
        /*/
        /*
            input = "G (a -> (F b))";
            Y.add(new LTLfLocalVar("a"));
            X.add(new LTLfLocalVar("b"));
            formulaType = FORMULALTLf;
	        toStringOutput = input+"\nSystem:\t"+X+"\nAgent:\t"+Y;
        */
        /*
            input = "(F((a U (b|c)) R ((X e) || ((WX f) && (G h) ) ) )) -> ((F d) R (((g)||(i)) U (l)))";
            X.add(new LTLfLocalVar("a"));
            X.add(new LTLfLocalVar("c"));
            X.add(new LTLfLocalVar("e"));
            X.add(new LTLfLocalVar("g"));
            X.add(new LTLfLocalVar("i"));
            Y.add(new LTLfLocalVar("b"));
            Y.add(new LTLfLocalVar("d"));
            Y.add(new LTLfLocalVar("f"));
            Y.add(new LTLfLocalVar("h"));
            Y.add(new LTLfLocalVar("l"));
            formulaType = FORMULALTLf;
	        toStringOutput = input+"\nSystem:\t"+X+"\nAgent:\t"+Y;
        */
        ///*
            input = "(G(rl -> (F aa))) & (G(aa -> (F dl))) & (G(aa -> (X dl)))";
            X.add(new LTLfLocalVar("rl"));
            Y.add(new LTLfLocalVar("aa"));
            formulaType = FORMULALTLf;
	        toStringOutput = input+"\nSystem:\t"+X+"\nAgent:\t"+Y;
        //*/
        /*
            input = "(G(rl -> (F aa))) & (G(aa -> (X dl)))";
            X.add(new LTLfLocalVar("rl"));
            Y.add(new LTLfLocalVar("aa"));
            formulaType = FORMULALTLf;
	        toStringOutput = input+"\nSystem:\t"+X+"\nAgent:\t"+Y;
        */

    }

	@Override
	public String toString() {
		return toStringOutput;
	}
}
