package SynthesisPartialObservability;

import FLLOAT.antlr4_generated.LDLfFormulaParserLexer;
import FLLOAT.antlr4_generated.LDLfFormulaParserParser;
import FLLOAT.antlr4_generated.LTLfFormulaParserLexer;
import FLLOAT.antlr4_generated.LTLfFormulaParserParser;
import FLLOAT.formula.ldlf.LDLfFormula;
import FLLOAT.formula.ltlf.LTLfFormula;
import FLLOAT.utils.AutomatonUtils;
import FLLOAT.visitors.LDLfVisitors.LDLfVisitor;
import FLLOAT.visitors.LTLfVisitors.LTLfVisitor;
import SynthesisPartialObservability.Utility.Utility;
import net.sf.tweety.logics.pl.syntax.PropositionalSignature;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import rationals.Automaton;
import rationals.transformations.Reducer;

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

    public Automaton getAutomatonLTLf(){LTLfFormulaParserLexer lexer = new LTLfFormulaParserLexer(new ANTLRInputStream(input));
        LTLfFormulaParserParser parser = new LTLfFormulaParserParser(new CommonTokenStream(lexer));
        ParseTree tree = parser.expression();
        LTLfVisitor visitor = new LTLfVisitor();
        LTLfFormula formula = visitor.visit(tree);

        LTLfFormula antinnfFormula = formula.antinnf();
        LDLfFormula ldlff = antinnfFormula.toLDLf();

        Automaton automaton = AutomatonUtils.ldlf2Automaton(ldlff, ldlff.getSignature());

        automaton = new Reducer<>().transform(automaton);


        if (printing) {
            Utility.print(automaton,"ltlfAutomaton.gv");
        }

        return automaton;
    }


    public Automaton getAutomatonLDLf(){
        LDLfFormulaParserLexer lexer = new LDLfFormulaParserLexer(new ANTLRInputStream(input));
        LDLfFormulaParserParser parser = new LDLfFormulaParserParser(new CommonTokenStream(lexer));
        ParseTree tree = parser.expression();

        LDLfVisitor visitor = new LDLfVisitor();
        LDLfFormula formula = visitor.visit(tree);

        Automaton automaton = AutomatonUtils.ldlf2Automaton(formula, formula.getSignature());
        automaton = new Reducer<>().transform(automaton);

        if (printing) {
            Utility.print(automaton,"ltlfAutomaton.gv");
        }

        return automaton;
    }
}
