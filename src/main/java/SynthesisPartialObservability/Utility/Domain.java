package SynthesisPartialObservability.Utility;

import net.sf.tweety.logics.pl.syntax.Proposition;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by loren on 15/06/2016.
 */
public class Domain {
    public static final int FORMULALTLf = FormulaChoser.FORMULALTLf;
    public static final int FORMULALDLf = FormulaChoser.FORMULALDLf;

    private Set<Set<Proposition>> combinationAgentDomain = null;
    private Set<Set<Proposition>> combinationEnvironmentDomain = null;

    private String input = "";
    private int formulatype = 0;
    private Set<Proposition> X;
    private Set<Proposition> Y;

    public Domain(){
        FormulaChoser formulaChoser = new FormulaChoser();
        formulatype = formulaChoser.formulaType;
        input = formulaChoser.input;
        X = formulaChoser.X;
        Y = formulaChoser.Y;
    }

    public String getInput() {return input;}
    public int getFormulatype() {return formulatype;}
    public Set<Proposition> getEnvironmentDomain() {return X;}
    public Set<Proposition> getAgentDomain() {return Y;}
    public Set<Proposition> getDomain(){
        HashSet<Proposition> tmp = new HashSet();
        tmp.addAll(X);
        tmp.addAll(Y);
        return tmp;
    }

    public Set<Set<Proposition>> getCombinantionAgentsDomain(){
        if (combinationAgentDomain!=null) return combinationAgentDomain;
        combinationAgentDomain = combinations(Y);
        return combinationAgentDomain;
    }
    public Set<Set<Proposition>> getCombinantionEnvironmentDomain(){
        if (combinationEnvironmentDomain!=null) return combinationEnvironmentDomain;
        combinationEnvironmentDomain = combinations(X);
        return combinationEnvironmentDomain;
    }
    private Set<Set<Proposition>> combinations(Set<Proposition> set){
        HashSet<Proposition> setCopy = new HashSet<>(set);

        HashSet<Set<Proposition>> container = new HashSet<>();
        for (int i = 0;i<set.size();i++) {
            container.addAll(permutations(setCopy,i));
        }

        return container;
    }
    private Set<Set<Proposition>> permutations(Set<Proposition> set,int length){
        HashSet<Set<Proposition>> result = new HashSet<>();
        if (length==1) {
            for (Proposition p:set) {
                HashSet<Proposition> tmp = new HashSet<>();
                tmp.add(p);
                result.add(tmp);
            }
            return result;
        }
        Object arr[] = set.toArray();
        for (int i=0;i<arr.length;i++){
            Proposition proposition = (Proposition) arr[i];
            set.remove(proposition);
            for (Set<Proposition> propositionSet: permutations(set,length-1)){
                propositionSet.add(proposition);
                result.add(propositionSet);
            }
            set.add(proposition);
        }

        return result;
    }

    @Override
    public String toString() {
        return "Environment: "+X+"\nAgent: "+Y+"\nInput: "+input+"\nFormulaType: "+(formulatype==FORMULALTLf?"LTLf":"LDLf");
    }
}