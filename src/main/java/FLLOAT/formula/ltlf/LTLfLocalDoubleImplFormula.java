/*
 * FFLOAT  Copyright (C) 2015  Riccardo De Masellis.
 *
 * This program comes with ABSOLUTELY NO WARRANTY.
 * This is free software, and you are welcome to redistribute it
 * under certain conditions; see http://www.gnu.org/licenses/gpl-3.0.html for details.
 */

package FLLOAT.formula.ltlf;

import FLLOAT.formula.DoubleImplFormula;
import FLLOAT.formula.FormulaType;
import FLLOAT.formula.regExp.RegExpLocal;

/**
 * Created by Riccardo De Masellis on 15/05/15.
 * For any issue please write to r.demasellis@trentorise.eu.
 */
public class LTLfLocalDoubleImplFormula extends LTLfBinaryFormula implements LTLfBoolOpLocalFormula, DoubleImplFormula {

    public LTLfLocalDoubleImplFormula(LTLfFormula left, LTLfFormula right) {
        super(left, right);
    }


    public FormulaType getFormulaType() {
        return FormulaType.LTLf_LOCAL_DOUBLEIMPL;
    }

    @Override
    public RegExpLocal toRegExpLocal() {
        return ((LTLfLocalFormula) this.nnf()).toRegExpLocal();
    }

}
