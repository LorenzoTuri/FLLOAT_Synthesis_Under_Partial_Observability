##FLLOAT Synthesis under partial observability

This code uses FLLOAT library to compute the synthesis of DFA games with partial observability starting LTLf/LDLf formulas.

Using notion from [2016 De Giacomo and Vardi] this code computes synthesis of LTLf/LDLf formulas under partial observability,in order to understand if a winning solution exists for the automa generated from the formula.

#####Steps to use the code:
	1. import every usefull file
	2. use [AutomatonCreation](https://pages.github.com/).
		class to create the automaton
		(This class requires a Domain element)
	3. use [SynthesisPartialObservability](https://github.com/LorenzoTuri/FLLOAT_Synthesis_Under_Partial_Observability/blob/master/src/main/java/SynthesisPartialObservability/SynthesisPartialObservability.java)
		class to compute the synthesis
		The instantiation of this class does nothing really important...
		A call to solve() must be done in order to compute the synthesis
		
#####Important:
	- FormulaChooser class is just a data container, can be replaced by everything else...
	- MainInterface class provides an exhaustive example of the use of this code
	- Signature class has no use rigth now, and the same for every PropositionalSignature 
	  that must be passed to AutomatonCreation... a null reference or every class extending 
	  PropositionalSignature can be used, but doesn't change the results
	
