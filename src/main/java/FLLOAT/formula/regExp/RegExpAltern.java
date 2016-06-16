/*
 * FFLOAT  Copyright (C) 2015  Riccardo De Masellis.
 *
 * This program comes with ABSOLUTELY NO WARRANTY.
 * This is free software, and you are welcome to redistribute it
 * under certain conditions; see http://www.gnu.org/licenses/gpl-3.0.html for details.
 */

package FLLOAT.formula.regExp;

import FLLOAT.automaton.TransitionLabel;
import FLLOAT.formula.FormulaType;
import FLLOAT.formula.ldlf.LDLfBoxFormula;
import FLLOAT.formula.ldlf.LDLfDiamondFormula;
import FLLOAT.formula.ldlf.LDLfFormula;
import FLLOAT.formula.quotedFormula.QuotedAndFormula;
import FLLOAT.formula.quotedFormula.QuotedFormula;
import FLLOAT.formula.quotedFormula.QuotedOrFormula;
import FLLOAT.formula.quotedFormula.QuotedVar;

/**
 * Created by Riccardo De Masellis on 15/05/15.
 * For any issue please write to r.demasellis@trentorise.eu.
 */
public class RegExpAltern extends RegExpBinary implements RegExpTemp {

    public RegExpAltern(RegExp left, RegExp right) {
        super(left, right);
    }

    @Override
    public String stringOperator() {
        return "+";
    }

    @Override
    public RegExpAltern nnf() {
        return new RegExpAltern((RegExp) this.getLeftFormula().nnf(), (RegExp) this.getRightFormula().nnf());
    }

    // NOOP
    @Override
    public RegExpTest negate() {
        throw new RuntimeException("Method negate() should not be called on RegExpAltern");
    }

    @Override
    public FormulaType getFormulaType() {
        return FormulaType.RE_ALTERN;
    }


    public QuotedFormula deltaDiamond(LDLfFormula goal, TransitionLabel label) {
        LDLfDiamondFormula ldlfLeft = new LDLfDiamondFormula((RegExp) this.getLeftFormula().clone(), (LDLfFormula) goal.clone());
        LDLfDiamondFormula ldlfRight = new LDLfDiamondFormula((RegExp) this.getRightFormula().clone(), (LDLfFormula) goal.clone());

        QuotedVar quotedLeft = new QuotedVar(ldlfLeft);
        QuotedFormula quotedRight = new QuotedVar(ldlfRight);

        return new QuotedOrFormula(quotedLeft.delta(label), quotedRight.delta(label));
    }


    public QuotedFormula deltaBox(LDLfFormula goal, TransitionLabel label) {
        LDLfBoxFormula ldlfLeft = new LDLfBoxFormula((RegExp) this.getLeftFormula().clone(), (LDLfFormula) goal.clone());
        LDLfBoxFormula ldlfRight = new LDLfBoxFormula((RegExp) this.getRightFormula().clone(), (LDLfFormula) goal.clone());

        QuotedVar quotedLeft = new QuotedVar(ldlfLeft);
        QuotedFormula quotedRight = new QuotedVar(ldlfRight);

        return new QuotedAndFormula(quotedLeft.delta(label), quotedRight.delta(label));
    }
}