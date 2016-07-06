package SynthesisPartialObservability.Utility;

import net.sf.tweety.logics.pl.syntax.PropositionalSignature;
import synthesis.symbols.PartitionedDomain;

/**
 * Extension of the PartitionedDomain used to get the domain as Proposition and not LTLfLocalVar
 */
public class PropositionDomain extends PartitionedDomain {

	public PropositionDomain(PropositionalSignature environmentDomain, PropositionalSignature systemDomain, PropositionalSignature hidden) {
		super(environmentDomain, systemDomain);
		this.hiddenProposition = hidden;
	}

	private PropositionalSignature hiddenProposition;

	public PropositionalSignature getHiddenProposition(){
		return hiddenProposition;
	}
}
