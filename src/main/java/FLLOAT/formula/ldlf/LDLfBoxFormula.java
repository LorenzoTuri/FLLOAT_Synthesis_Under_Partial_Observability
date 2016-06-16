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
import FLLOAT.formula.quotedFormula.QuotedFormula;
import FLLOAT.formula.regExp.RegExp;
import net.sf.tweety.logics.pl.syntax.PropositionalSignature;

/**
 * Created by Riccardo De Masellis on 15/05/15.
 * For any issue please write to r.demasellis@trentorise.eu.
 */
public class LDLfBoxFormula extends LDLfTempOpTempFormula {

    public LDLfBoxFormula(RegExp regExp, LDLfFormula goalFormula) {
        super(regExp, goalFormula);
    }

    public String toString() {
        return "[" + this.getRegExp() + "]" + "(" + this.getGoalFormula() + ")";
    }

    @Override
    public LDLfFormula nnf() {
        return new LDLfBoxFormula((RegExp) this.getRegExp().nnf(), (LDLfFormula) this.getGoalFormula().nnf());
    }

    @Override
    public LDLfFormula negate() {
        return new LDLfDiamondFormula((RegExp) this.getRegExp().clone(), (LDLfFormula) this.getGoalFormula().negate());
    }

    @Override
    public FormulaType getFormulaType() {
        return FormulaType.LDLf_BOX;
    }

    public PropositionalSignature getSignature() {
        PropositionalSignature sig = new PropositionalSignature();
        this.getSignatureRic(sig);
        return sig;
    }

    public void getSignatureRic(PropositionalSignature sig) {
        this.getGoalFormula().getSignatureRic(sig);
        this.getRegExp().getSignatureRic(sig);
    }

    @Override
    public QuotedFormula delta(TransitionLabel label) {
        return this.getRegExp().deltaBox(this.getGoalFormula(), label);
    }
}