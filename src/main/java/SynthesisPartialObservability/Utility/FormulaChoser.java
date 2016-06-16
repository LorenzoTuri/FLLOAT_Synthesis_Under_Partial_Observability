package SynthesisPartialObservability.Utility;

import net.sf.tweety.logics.pl.syntax.Proposition;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by loren on 16/06/2016.
 */
public class FormulaChoser {
    public static final int FORMULALTLf = 1;
    public static final int FORMULALDLf = 2;

    public int formulaType=0;
    public String input="";
    public Set<Proposition> X;
    public Set<Proposition> Y;

    FormulaChoser(){
        X = new HashSet<>();
        Y = new HashSet<>();
        /////////////////////// FORMULE LDLf //////////////////////////////
        /*
            input = "[true*](([true]ff) || (<!a>tt) || (<true*>(<b>tt)))";
            X.add(new Proposition(""));
            formulaType = FORMULALDLf;
        */
        /*
            input = "<((a)*)*>b";
            X.add(new Proposition(""));
            formulaType = FORMULALDLf;
        */
        /*
            input = "[true; true*; !((e -> (!l & !buy)) & (l -> (!e & !buy)) & (buy -> (!e & !l)))]ff";
            X.add(new Proposition(""));
            formulaType = FORMULALDLf;
        */
        /////////////////////// FORMULE LTLf //////////////////////////////
        /*
            input = "(a R b)";
            X.add(new Proposition("a"));
            Y.add(new Proposition("b"));
            formulaType = FORMULALTLf;
        */
        /*
            input = "G (a -> (F b))";
            X.add(new Proposition("a"));
            Y.add(new Proposition("b"));
            formulaType = FORMULALTLf;
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
        */
        ///*
            input = "(G(rl -> (F aa))) & (G(aa -> (F dl))) & (G(aa -> (X dl)))";
            X.add(new Proposition("rl"));
            Y.add(new Proposition("aa"));
            formulaType = FORMULALTLf;
        //*/
        /*
            input = "(G(rl -> (F aa))) & (G(aa -> (X dl)))";
            X.add(new Proposition("rl"));
            Y.add(new Proposition("aa"));
            formulaType = FORMULALTLf;
        */
    }
}
