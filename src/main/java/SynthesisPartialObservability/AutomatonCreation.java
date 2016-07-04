package SynthesisPartialObservability;

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
    String input;
    PropositionalSignature signature;
    boolean declare;
    boolean minimize;
    boolean trim;
    boolean noEmptyTrace;
    boolean printing;

    /**
     * Constructor of the class. Stores config variables for successive use
     * @param input         formula
     * @param signature     signature of the formula
     * @param declare       TODO don't know
     * @param minimize      minimization of the automaton
     * @param trim          TODO don't know
     * @param noEmptyTrace  TODO don't know
     * @param printing      print the automaton in a DOT file (.gv)
     */
    AutomatonCreation(
            String input, PropositionalSignature signature, boolean declare,
            boolean minimize, boolean trim, boolean noEmptyTrace, boolean printing){
        this.input = input;
        this.signature = signature;
        this.declare = declare;
        this.minimize = minimize;
        this.trim = trim;
        this.noEmptyTrace = noEmptyTrace;
        this.printing= printing;
    }

    /**
     * Create the automaton from a LTLf formula. Error is the formula isn't LTLf
     * @return the automaton
     */
    public Automaton getAutomatonLTLf() {
        LTLfFormulaParserLexer lexer = new LTLfFormulaParserLexer(new ANTLRInputStream(input));
        LTLfFormulaParserParser parser = new LTLfFormulaParserParser(new CommonTokenStream(lexer));
        ParseTree tree = parser.expression();
        LTLfVisitor visitor = new LTLfVisitor();
        LTLfFormula formula = visitor.visit(tree);

        LTLfFormula antinnfFormula = formula.antinnf();
        LDLfFormula ldlff = antinnfFormula.toLDLf();

        PropositionalSignature usedSignature = (signature==null? formula.getSignature() : signature);

        Automaton automaton = AutomatonUtils.ldlf2Automaton(ldlff, usedSignature);

        checkFlag(automaton,"ltlfAutomaton.gv");

        return automaton;
    }

    /**
     * Create the automaton from a LDLf formula. Error if the formula isn't LDLf
     * @return the automaton
     */
    public Automaton getAutomatonLDLf() {
        LDLfFormulaParserLexer lexer = new LDLfFormulaParserLexer(new ANTLRInputStream(input));
        LDLfFormulaParserParser parser = new LDLfFormulaParserParser(new CommonTokenStream(lexer));
        ParseTree tree = parser.expression();

        LDLfVisitor visitor = new LDLfVisitor();
        LDLfFormula formula = visitor.visit(tree);

	    PropositionalSignature usedSignature = (signature==null? new PropositionalSignature() : signature);

        Automaton automaton = AutomatonUtils.ldlf2Automaton(formula, usedSignature);
        checkFlag(automaton,"ldlfAutomaton.gv");

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
        if (printing) Utility.print(automaton,printingPath);
    }
}
