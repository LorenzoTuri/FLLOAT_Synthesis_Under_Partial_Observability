package SynthesisPartialObservability.Utility;

import net.sf.tweety.logics.pl.syntax.Proposition;
import net.sf.tweety.logics.pl.syntax.PropositionalSignature;

/**
 * Created by loren on 12/07/2016.
 */
public class DoubleMurphyFormulas2 {
	private String init = "";
	private String objectives = "";
	private String actions = "";
	private String regolemondo = "";
	private PropositionalSignature signature = new PropositionalSignature();
	private PropositionalSignature X = new PropositionalSignature();
	private PropositionalSignature Y = new PropositionalSignature();
	private PropositionalSignature H = new PropositionalSignature();
	private PropositionDomain propositionDomain = new PropositionDomain(X,Y,H);

	public DoubleMurphyFormulas2(){
		init = "(!cleanr && !cleanl && !isonr && isonl)";
		objectives = "(cleanl && cleanr)";
		actions =
				"(" +
				//azioni di movimento
				"( (moveaction && isonl) -> X(isonr) ) ||" +
				"( (moveaction && isonr) -> X(isonl) ) ||" +
				//azioni di pulizia
				"( (cleanaction && isonl)-> X(isonl && (cleanl || !cleanl) ) ) ||" +
				"( (cleanaction && isonr)-> X(isonr && (cleanr || !cleanr) ) )" +
				")";
		regolemondo =
				"(" +
				//il giocatore deve stare da qualche parte, ma solo in una casella
				"( (isonl->!isonr) && (isonr->!isonl) && (isonl || isonr) ) &&" +
				//pulire non cambia lo stato dell'altra casella
				"( (cleanaction && isonr && cleanl) -> X(cleanl) ) &&" +
				"( (cleanaction && isonr && !cleanl) -> X(!cleanl) ) &&" +
				"( (cleanaction && isonl && cleanr) -> X(cleanr) ) &&" +
				"( (cleanaction && isonl && !cleanr) -> X(!cleanr) ) &&" +
				//muoversi non cambia lo stato della casella origine
				"( (moveaction && isonr && cleanr) -> X(cleanr) ) &&" +
				"( (moveaction && isonr && !cleanr) -> X(!cleanr) ) &&" +
				"( (moveaction && isonl && cleanl) -> X(cleanl) ) &&" +
				"( (moveaction && isonl && !cleanl) -> X(!cleanl) ) &&" +
				//una sola azione alla volta
				"( (moveaction->!cleanaction) && (cleanaction->!moveaction) )" +
				")";




		
		getSignature().add(new Proposition("cleanaction"));
		getSignature().add(new Proposition("moveaction"));
		getSignature().add(new Proposition("cleanl"));
		getSignature().add(new Proposition("cleanr"));
		getSignature().add(new Proposition("isonl"));
		getSignature().add(new Proposition("isonr"));

		/*
		la formula obiettivo da realizzare in questo caso è:
			( G(!regolemondo && regolegiocatore) || (init && (G(actions)->objectives) )
		 */

		X.add(new Proposition("cleanr"));
		X.add(new Proposition("cleanl"));
		X.add(new Proposition("isonr"));
		X.add(new Proposition("isonl"));
		Y.add(new Proposition("cleanaction"));
		Y.add(new Proposition("moveaction"));
	}

	public String getInit() {
		return init;
	}

	public String getObjectives() {
		return objectives;
	}

	public String getActions() {
		return actions;
	}

	public String getRegolemondo() {
		return regolemondo;
	}

	public PropositionalSignature getSignature() {
		return signature;
	}

	public PropositionDomain getPropositionDomain(){
		return propositionDomain;
	}
}
