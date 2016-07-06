package SynthesisPartialObservability.Utility;

import formula.ltlf.LTLfLocalVar;
import net.sf.tweety.logics.pl.syntax.Proposition;
import net.sf.tweety.logics.pl.syntax.PropositionalSignature;
import synthesis.symbols.PropositionSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Container for formulas describing Double Murphy problem.
 */
public class DoubleMurphyFormulas {
	private String init;
	private String exclusions;
	private String objective;
	private List<String> actions;
	private PropositionalSignature signature;
	private PropositionDomain domain;

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

		PropositionSet environment = new PropositionSet();
		PropositionSet agent = new PropositionSet();
		environment.add(new LTLfLocalVar("cleanl"));
		environment.add(new LTLfLocalVar("cleanr"));
		environment.add(new LTLfLocalVar("isonl"));
		environment.add(new LTLfLocalVar("isonr"));
		agent.add(new LTLfLocalVar("movelaction"));
		agent.add(new LTLfLocalVar("moveraction"));
		agent.add(new LTLfLocalVar("cleanlaction"));
		agent.add(new LTLfLocalVar("cleanraction"));
		domain = new PropositionDomain(environment,agent);
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

	public PropositionDomain getDomain(){
		return domain;
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
