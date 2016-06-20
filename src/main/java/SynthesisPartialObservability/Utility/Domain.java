package SynthesisPartialObservability.Utility;

import net.sf.tweety.logics.pl.syntax.Proposition;

import java.util.HashSet;
import java.util.Set;

/**
 * Class used to store various information about the formula and it's domain set
 */
public class Domain {
    public static final int FORMULALTLf = FormulaChoser.FORMULALTLf;
    public static final int FORMULALDLf = FormulaChoser.FORMULALDLf;

    private Set<Set<Proposition>> combinationAgentDomain = null;
    private Set<Set<Proposition>> combinationEnvironmentDomain = null;

    //VARIABLES
    private String input = "";
    private int formulaType = 0;
    private Set<Proposition> X;
    private Set<Proposition> Y;

	/**
	 * Default constructor for a empty Domain
     */
    public Domain(){
	    X = new HashSet<>();
	    Y = new HashSet<>();
    }

	/**
	 * Constructor for the domain
	 * @param input input formula
	 * @param X input environment proposition set
	 * @param Y input agent proposition set
	 */
	public Domain(String input,Set<Proposition> X, Set<Proposition> Y, int formulaType){
		this.input = input;
		this.Y = Y;
		this.X = X;
		this.formulaType =  formulaType;
	}

    ///// GETTER METHODS
    public String getInput() {return input;}
    public int getFormulaType() {return formulaType;}
    public Set<Proposition> getEnvironmentDomain() {return X;}
    public Set<Proposition> getAgentDomain() {return Y;}
    public Set<Proposition> getDomain(){
        HashSet<Proposition> tmp = new HashSet();
        tmp.addAll(X);
        tmp.addAll(Y);
        return tmp;
    }

	///// SETTER METHODS
	public void setInput(String input) {this.input = input;}
	public void setFormulaType(int formulaType){this.formulaType = formulaType;}
	public void setEnvironmentDomain(Set<Proposition> X){
		this.X = X;
		combinationEnvironmentDomain = null;
	}
	public void setAgentDomain(Set<Proposition> Y){
		this.Y = Y;
		combinationAgentDomain = null;
	}

    ///// GETTER METHOD THAT CAN REQUIRE SETS COMPUTATION
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
        for (int i = 1;i<=setCopy.size();i++) {
            container.addAll(permutations(setCopy,i));
        }
	    HashSet<Proposition> tmp = new HashSet<>();
	    container.add(tmp);

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
        return "Domain:\n" +
                "\tEnvironment: "+X+"\n" +
                "\tAgent: "+Y+"\n" +
                "\tInput: "+input+"\n" +
                "\tFormulaType: "+(formulaType ==FORMULALTLf?"LTLf":"LDLf");
    }
}
