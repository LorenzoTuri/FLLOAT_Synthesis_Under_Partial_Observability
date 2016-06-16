/*
 * FFLOAT  Copyright (C) 2015  Riccardo De Masellis.
 *
 * This program comes with ABSOLUTELY NO WARRANTY.
 * This is free software, and you are welcome to redistribute it
 * under certain conditions; see http://www.gnu.org/licenses/gpl-3.0.html for details.
 */

package FLLOAT.formula.ltlf;

import FLLOAT.formula.AndFormula;
import FLLOAT.formula.FormulaType;
import FLLOAT.formula.ldlf.LDLfTempAndFormula;

/**
 * Created by Riccardo De Masellis on 15/05/15.
 * For any issue please write to r.demasellis@trentorise.eu.
 */
public class LTLfTempAndFormula extends LTLfBinaryFormula implements AndFormula, LTLfBoolOpTempFormula {

    public LTLfTempAndFormula(LTLfFormula left, LTLfFormula right) {
        super(left, right);
    }

    public String stringOperator() {
        return "Te" + AndFormula.super.stringOperator();
    }

    @Override
    public FormulaType getFormulaType() {
        return FormulaType.LTLf_TEMP_AND;
    }

    @Override
    public LTLfFormula antinnf() {
        return new LTLfTempAndFormula(this.getLeftFormula().antinnf(), this.getRightFormula().antinnf());
    }

    @Override
    public LDLfTempAndFormula toLDLfRec() {
        return new LDLfTempAndFormula(this.getLeftFormula().toLDLfRec(), this.getRightFormula().toLDLfRec());
    }
}