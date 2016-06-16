package SynthesisPartialObservability.Utility;

import net.sf.tweety.logics.pl.syntax.PropositionalSignature;

/**
 * Class used to get the signature, used from FLLOAT automatons
 */
public class Signature {
    PropositionalSignature signature = null;

    /**
     * Constructor of the signature
     */
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

    /**
     * getter for the signature element
     * @return the signature
     */
    public PropositionalSignature getSignature(){
        return signature;
    }
}
