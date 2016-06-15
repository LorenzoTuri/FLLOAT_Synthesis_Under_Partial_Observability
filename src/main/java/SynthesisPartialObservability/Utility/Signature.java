package SynthesisPartialObservability.Utility;

import net.sf.tweety.logics.pl.syntax.PropositionalSignature;

/**
 * Created by loren on 15/06/2016.
 */
public class Signature {
    PropositionalSignature signature = null;
    public Signature(){
        /*
          If you wanna change the signature!
        */
//      PropositionalSignature signature = FLLOAT.formula.getSignature();
//      Proposition w = new Proposition("w");
//      Proposition x = new Proposition("x");
//      Proposition y = new Proposition("y");
//      Proposition z = new Proposition("z");
//      signature.add(w);
//      signature.add(x);
//      signature.add(y);
//      signature.add(z);
    }
    public PropositionalSignature getSignature(){
        return signature;
    }
}
