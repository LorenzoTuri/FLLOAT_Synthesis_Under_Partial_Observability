/*
 * FFLOAT  Copyright (C) 2015  Riccardo De Masellis.
 *
 * This program comes with ABSOLUTELY NO WARRANTY.
 * This is free software, and you are welcome to redistribute it
 * under certain conditions; see http://www.gnu.org/licenses/gpl-3.0.html for details.
 */

package FLLOAT.formula.ltlf;

import FLLOAT.formula.FormulaType;
import FLLOAT.formula.ldlf.LDLfDiamondFormula;
import FLLOAT.formula.ldlf.LDLfTempAndFormula;
import FLLOAT.formula.ldlf.LDLfttFormula;
import FLLOAT.formula.regExp.RegExpConcat;
import FLLOAT.formula.regExp.RegExpLocalTrue;
import FLLOAT.formula.regExp.RegExpStar;
import FLLOAT.formula.regExp.RegExpTest;

/**
 * Created by Riccardo De Masellis on 15/05/15.
 * For any issue please write to r.demasellis@trentorise.eu.
 */
public class LTLfUntilFormula extends LTLfBinaryFormula implements LTLfTempOpTempFormula {

    public LTLfUntilFormula(LTLfFormula left, LTLfFormula right) {
        super(left, right);
    }

    public String stringOperator() {
        return "U";
    }


    @Override
    public LTLfFormula nnf() {
        LTLfFormula left = (LTLfFormula) this.getLeftFormula().nnf();
        LTLfFormula right = (LTLfFormula) this.getRightFormula().nnf();
        return new LTLfUntilFormula(left, right);
    }

    @Override
    public LTLfFormula negate() {
        LTLfFormula left = (LTLfFormula) this.getLeftFormula().negate();
        LTLfFormula right = (LTLfFormula) this.getRightFormula().negate();
        return new LTLfReleaseFormula(left, right);
    }

    @Override
    public FormulaType getFormulaType() {
        return FormulaType.LTLf_UNTIL;
    }


    @Override
    public LDLfDiamondFormula toLDLfRec() {
        // phi U psi --> <(phi? ; true)*> (<true>tt && psi)
        RegExpTest test = new RegExpTest(this.getLeftFormula().toLDLfRec());
        RegExpConcat concat = new RegExpConcat(test, new RegExpLocalTrue());
        RegExpStar star = new RegExpStar(concat);
        LDLfDiamondFormula doAStep = new LDLfDiamondFormula(new RegExpLocalTrue(), new LDLfttFormula());
        LDLfTempAndFormula tempAndFormula = new LDLfTempAndFormula(doAStep, this.getRightFormula().toLDLfRec());
        return new LDLfDiamondFormula(star, tempAndFormula);
    }

    @Override
    public LTLfFormula antinnf() {
        return new LTLfUntilFormula(this.getLeftFormula().antinnf(), this.getRightFormula().antinnf());
    }

}
