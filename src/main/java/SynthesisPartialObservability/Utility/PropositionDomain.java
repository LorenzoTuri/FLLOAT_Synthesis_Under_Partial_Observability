package SynthesisPartialObservability.Utility;

import formula.ltlf.LTLfLocalVar;
import net.sf.tweety.logics.pl.syntax.Proposition;
import synthesis.symbols.PartitionedDomain;
import synthesis.symbols.PropositionSet;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by loren on 04/07/2016.
 */
public class PropositionDomain extends PartitionedDomain {
	public PropositionDomain(PropositionSet environmentDomain, PropositionSet systemDomain) {
		super(environmentDomain, systemDomain);
	}

	private Set<Proposition> propositionDomain;

	public Set<Proposition> getCompleteDomainAsPropositions(){
		if (propositionDomain==null){
			propositionDomain = new HashSet<>();
			for (LTLfLocalVar l:this.getCompleteDomain()) { propositionDomain.add(new Proposition(l.toString())); }
			return propositionDomain;
		}
		else return propositionDomain;
	}
}
