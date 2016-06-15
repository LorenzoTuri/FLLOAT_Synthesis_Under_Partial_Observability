package SynthesisPartialObservability.Utility;

import net.sf.tweety.logics.pl.syntax.Proposition;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by loren on 15/06/2016.
 */
public class Domain {
    public static final int FORMULALTLf = 1;
    public static final int FORMULALDLf = 2;

    private String input = "";
    private Set<Proposition> X;
    private Set<Proposition> Y;
    public Domain(int formulatype){
        X = new HashSet<>();
        Y = new HashSet<>();
        if (formulatype == FORMULALDLf){
            /*
            input = "[true*](([true]ff) || (<!a>tt) || (<true*>(<b>tt)))";
            X.add(new Proposition(""));
            */
            /*
            input = "<((a)*)*>b";
            X.add(new Proposition(""));
             */
            /*
            input = "[true; true*; !((e -> (!l & !buy)) & (l -> (!e & !buy)) & (buy -> (!e & !l)))]ff";
            X.add(new Proposition(""));
             */
        }else if (formulatype == FORMULALTLf){
            /*
            input = "(a R b)";
            X.add(new Proposition(""));
             */
            /*
            input = "G (a -> (F b))";
            X.add(new Proposition(""));
             */
            /*
            input = "(F((a U (b|c)) R ((X e) || ((WX f) && (G h) ) ) )) -> ((F d) R (((g)||(i)) U (l)))";
            X.add(new Proposition(""));
             */
            /*
            input = "(G(rl -> (F aa))) & (G(aa -> (F dl))) & (G(aa -> (X dl)))";
            X.add(new Proposition(""));
             */
            ///*
            input = "(G(rl -> (F aa))) & (G(aa -> (X dl)))";
            X.add(new Proposition("rl"));
            Y.add(new Proposition("aa"));
            //*/
        }
        else input = "";
    }

    public String getInput() {return input;}
    public Set<Proposition> getEnvironmentDomain() {return X;}
    public Set<Proposition> getAgentDomain() {return Y;}
    public Set<Proposition> getDomain(){
        HashSet<Proposition> tmp = new HashSet();
        tmp.addAll(X);
        tmp.addAll(Y);
        return tmp;
    }
}
