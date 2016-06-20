package SynthesisPartialObservability.Exception;

/**
 * Exception thrown by automaton creation
 */
public class NotAppropriateFormulaException extends Exception {
	@Override
	public String toString() {
		return "The formula isn't of the appropriate type!";
	}
}
