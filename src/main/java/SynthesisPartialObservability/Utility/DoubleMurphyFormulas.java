package SynthesisPartialObservability.Utility;

import net.sf.tweety.logics.pl.syntax.Proposition;
import net.sf.tweety.logics.pl.syntax.PropositionalSignature;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by loren on 30/06/2016.
 */
public class DoubleMurphyFormulas {
	private String init;
	private String exclusions;
	private String objective;
	private List<String> actions;
	private PropositionalSignature signature;

	public DoubleMurphyFormulas(){
		init = "( isonl && !isonr && !cleanr && !cleanl )";
		objective = "F( cleanr && cleanl )";

		String movelExclusion = " (movelaction -> !moveraction && !cleanlaction && !cleanraction) ";
		String moverExclusion = " (moveraction -> !movelaction && !cleanlaction && !cleanraction) ";
		String cleanlExclusion = " (cleanlaction -> !movelaction && !moveraction && !cleanraction) ";
		String cleanrExclusion = " (cleanraction -> !movelaction && !moveraction && !cleanlaction) ";
		exclusions = " G( "+movelExclusion+" && "+moverExclusion+" && "+cleanlExclusion+" && "+cleanrExclusion+" ) ";

		String movelpos = "( X(movelaction && isonr && !isonl &&  cleanr) -> X(!isonr && isonl &&  cleanr) )";
		String movelneg = "( X(movelaction && isonr && !isonl && !cleanr) -> X(!isonr && isonl && !cleanr) )";
		String movel = "G( "+movelpos+" || "+movelneg+" )";
		String moverpos = "( X(moveraction && isonl && !isonr &&  cleanl) -> X(!isonl && isonr &&  cleanl) )";
		String moverneg = "( X(moveraction && isonl && !isonr && !cleanl) -> X(!isonl && isonr && !cleanl) )";
		String mover = "G( "+moverpos+" || "+moverneg+" )";
		String cleanlpos = "( X(cleanlaction && isonl && !cleanl && !isonr &&  cleanr) -> X(isonl && !isonr &&  cleanr) )";
		String cleanlneg = "( X(cleanlaction && isonl && !cleanl && !isonr && !cleanr) -> X(isonl && !isonr && !cleanr) )";
		String cleanl= "G( "+cleanlpos+" || "+cleanlneg+" )";
		String cleanrpos = "( X(cleanraction && isonr && !cleanr && !isonl &&  cleanl) -> X(isonr && !isonl &&  cleanl) )";
		String cleanrneg = "( X(cleanraction && isonr && !cleanr && !isonl && !cleanl) -> X(isonr && !isonl && !cleanl) )";
		String cleanr= "G( "+cleanrpos+" || "+cleanrneg+" )";

		actions = new ArrayList<>();
		getActions().add(movel);
		getActions().add(mover);
		getActions().add(cleanl);
		getActions().add(cleanr);

		signature = new PropositionalSignature();
		getSignature().add(new Proposition("cleanl"));
		getSignature().add(new Proposition("cleanr"));
		getSignature().add(new Proposition("isonl"));
		getSignature().add(new Proposition("isonr"));
		getSignature().add(new Proposition("cleanlaction"));
		getSignature().add(new Proposition("cleanraction"));
		getSignature().add(new Proposition("movelaction"));
		getSignature().add(new Proposition("moveraction"));
	}

	public String getInit() {
		return init;
	}

	public String getExclusions() {
		return exclusions;
	}

	public String getObjective() {
		return objective;
	}

	public List<String> getActions() {
		return actions;
	}

	public PropositionalSignature getSignature() {
		return signature;
	}

	/**
	 * Initial situation, everything dirty and vacuum is on the left space.
	 */
	/**
	 * Objective situation, everything clean
	 */
	/**
	 * ACTION movel: used to move the vacuum left.
	 *      movelaction is used for the mutual exclusion
	 *      isonr is the starting point, required to be true.
	 *      isonl is the objective point, required to be false.
	 *      cleanr is the cleaning state of the starting point. Used to mantain the truth value.
	 */
	/**
	 * ACTION mover: used to move the vacuum right.
	 *      moveraction is used for the mutual exclusion
	 *      isonl is the starting point, required to be true
	 *      isonr is the objective point, required to be false
	 *      cleanl is the cleaning state of the starting point. Used to mantain the truth value.
	 */
	/**
	 * ACTION cleanl: used to clean the left place
	 *      cleanlaction is used for the mutual exclusion
	 *      isonl is the current place, required to be true
	 *      cleanl is the cleaning state of the current place, required to be false
	 *          (no use clean an already cleaned state)
	 *      isonr is the other place, required to be false
	 *      cleanr is the cleaning state of the other place. Used to mantain the truth value.
	 */
	/**
	 * ACTION cleanr: used to clean the rigth place
	 *      cleanraction is used for the mutual exclusion
	 *      isonr is the current place, required to be true
	 *      cleanr is the cleaning state of the current place, required to be false
	 *          (no use clean an already cleaned state)
	 *      isonl is the other place, required to be false
	 *      cleanl is the cleaning state of the other place. Used to mantain the truth value.
	 */
	/**
	 * Possible actions, concatenated with "or".
	 * true is used in case when the environment try to cheat with his variables (?The state shouldn't change).
	 * Exclusion at the beginning for better performance
	 */
	/**
	 * Exclusion used to execute only ONE action at every step.
	 */
}
