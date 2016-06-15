package util;

import FLLOAT.antlr4_generated.LTLfFormulaParserLexer;
import FLLOAT.antlr4_generated.LTLfFormulaParserParser;
import FLLOAT.formula.ltlf.LTLfFormula;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import FLLOAT.visitors.LTLfVisitors.LTLfVisitor;

/**
 * ParsingUtils
 * <br>
 * Created by Simone Calciolari on 01/04/16.
 * @author Simone Calciolari.
 */
public class ParsingUtils {

	public static LTLfFormula parseLTLfFormula(String input){
		LTLfFormula output;

		LTLfFormulaParserLexer lexer = new LTLfFormulaParserLexer(new ANTLRInputStream(input));
		LTLfFormulaParserParser parser = new LTLfFormulaParserParser(new CommonTokenStream(lexer));

		ParseTree tree = parser.expression();

		output = new LTLfVisitor().visit(tree);

		return output;
	}

}
