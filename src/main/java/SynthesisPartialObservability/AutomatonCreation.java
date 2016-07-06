package SynthesisPartialObservability;

import SynthesisPartialObservability.Utility.FormulaChoser;
import SynthesisPartialObservability.Utility.Utility;
import antlr4_generated.LDLfFormulaParserLexer;
import antlr4_generated.LDLfFormulaParserParser;
import antlr4_generated.LTLfFormulaParserLexer;
import antlr4_generated.LTLfFormulaParserParser;
import formula.ldlf.LDLfFormula;
import formula.ltlf.LTLfFormula;
import net.sf.tweety.logics.pl.syntax.PropositionalSignature;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import rationals.Automaton;
import rationals.transformations.Reducer;
import utils.AutomatonUtils;
import visitors.LDLfVisitors.LDLfVisitor;
import visitors.LTLfVisitors.LTLfVisitor;

/**
 * Created by loren on 15/06/2016.
 */
public class AutomatonCreation {
    public static final int FORMULALTLf= FormulaChoser.FORMULALTLf;
    public static final int FORMULALDLf= FormulaChoser.FORMULALDLf;

    String input;
    PropositionalSignature signature;
    boolean declare;
    boolean minimize;
    boolean trim;
    boolean noEmptyTrace;
    boolean printing;
	int formulatype;

    /**
     * Constructor of the class. Stores config variables for successive use
     * @param input         formula
     * @param signature     signature of the formula
     * @param declare       TODO don't know
     * @param minimize      minimization of the automaton
     * @param trim          TODO don't know
     * @param noEmptyTrace  TODO don't know
     * @param printing      printAutomaton the automaton in a DOT file (.gv)
     * @param FormulaType   type of formula between FORMULALTLf or FORMULALDLf
     */
    AutomatonCreation(
            String input, PropositionalSignature signature, boolean declare,
            boolean minimize, boolean trim, boolean noEmptyTrace, boolean printing, int FormulaType){
        this.input = input;
        this.signature = signature;
        this.declare = declare;
        this.minimize = minimize;
        this.trim = trim;
        this.noEmptyTrace = noEmptyTrace;
        this.printing= printing;
	    this.formulatype = FormulaType;
    }

	public Automaton getAutomaton(){
		Automaton automaton = null;

		LDLfFormula formula;
		if (formulatype == FORMULALDLf){
			LDLfFormulaParserLexer lexer = new LDLfFormulaParserLexer(new ANTLRInputStream(input));
			LDLfFormulaParserParser parser = new LDLfFormulaParserParser(new CommonTokenStream(lexer));
			ParseTree tree = parser.expression();

			LDLfVisitor visitor = new LDLfVisitor();
			formula = visitor.visit(tree);

		}else if (formulatype == FORMULALTLf){
			LTLfFormulaParserLexer lexer = new LTLfFormulaParserLexer(new ANTLRInputStream(input));
			LTLfFormulaParserParser parser = new LTLfFormulaParserParser(new CommonTokenStream(lexer));
			ParseTree tree = parser.expression();
			LTLfVisitor visitor = new LTLfVisitor();
			LTLfFormula ltlfFormula = visitor.visit(tree);

			LTLfFormula antinnfFormula = ltlfFormula.antinnf();
			formula = antinnfFormula.toLDLf();

		}else return null;

		PropositionalSignature usedSignature = (signature==null? formula.getSignature() : signature);
		automaton = AutomatonUtils.ldlf2Automaton(formula, usedSignature);

		checkFlag(automaton,(formulatype==FORMULALTLf?"ltlfAutomaton.gv":"ldlfAutomaton.gv"));
		return automaton;
	}

    /**
     * Checks configuration flag
     * @param automaton the created automaton
     * @param printingPath the path of the possible printing
     */
    private void checkFlag(Automaton automaton,String printingPath){
        if (declare) ;
        if (minimize) automaton = new Reducer<>().transform(automaton);
        if (trim);
        if (noEmptyTrace);
        if (printing) Utility.printAutomaton(automaton,printingPath);
    }
}
