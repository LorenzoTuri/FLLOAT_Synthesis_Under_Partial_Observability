/*
 * FFLOAT  Copyright (C) 2015  Riccardo De Masellis.
 *
 * This program comes with ABSOLUTELY NO WARRANTY.
 * This is free software, and you are welcome to redistribute it
 * under certain conditions; see http://www.gnu.org/licenses/gpl-3.0.html for details.
 */

package FLLOAT.formula.ldlf;

import FLLOAT.automaton.TransitionLabel;
import FLLOAT.formula.FormulaType;
import FLLOAT.formula.NotFormula;
import FLLOAT.formula.quotedFormula.QuotedFormula;

/**
 * Created by Riccardo De Masellis on 15/05/15.
 * For any issue please write to r.demasellis@trentorise.eu.
 */
public class LDLfTempNotFormula extends LDLfUnaryFormula implements LDLfBoolOpTempFormula, NotFormula {

    public LDLfTempNotFormula(LDLfFormula nestedFormula) {
        super(nestedFormula);
    }

    public String stringOperator() {
        return "Te" + NotFormula.super.stringOperator();
    }

    @Override
    public FormulaType getFormulaType() {
        return FormulaType.LDLf_TEMP_NOT;
    }


    @Override
    public QuotedFormula delta(TransitionLabel label) {
        return ((LDLfFormula) this.nnf()).delta(label);
    }
}
