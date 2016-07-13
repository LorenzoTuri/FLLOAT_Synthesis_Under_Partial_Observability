package SynthesisPartialObservability.Utility;

import rationals.Automaton;
import rationals.State;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Class used to print various information about an automata on the console.
 */
public class AutomatonConsole {
	private Automaton automaton;
	public AutomatonConsole(Automaton automaton){this.automaton = automaton;}
	public void start(){
		boolean exitcommand = false;
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("\n\n" +
				"Insert 0 to exit\n" +
				"Insert 1 to print states\n" +
				"Insert 2 to print terminals\n" +
				"Insert 3 to print initials\n" +
				"Insert 4 to print transitions\n" +
				"Insert 5 to print transitions between two states\n" +
				"Insert 6 to print transitions from a state");
		while (!exitcommand){
			int command=0;
			try {
				System.out.print("Command: ");
				String read = reader.readLine();
				command = (isInteger(read) ? Integer.parseInt(read) : 10000);
			} catch (Exception e) {e.getMessage();}
			switch (command){
				case 0: exitcommand=true;break;
				case 1: System.out.println(automaton.states());break;
				case 2: System.out.println(automaton.terminals());break;
				case 3: System.out.println(automaton.initials());break;
				case 4: for (Object t:automaton.delta()) System.out.println(t);break;
				case 5: try{
					System.out.println("Insert starting state:");
					String readstate = reader.readLine();
					int state1 = (isInteger(readstate) ? Integer.parseInt(readstate) : 0);
					System.out.println("Insert ending state:");
					readstate = reader.readLine();
					int state2 = (isInteger(readstate) ? Integer.parseInt(readstate) : 0);
					for (Object t:automaton.deltaFrom(
							(State)automaton.states().toArray()[state1],
							(State)automaton.states().toArray()[state2]))
						System.out.println(t);
				}catch (Exception e){System.err.println(e.getMessage());} break;
				case 6:try {
					System.out.println("Insert starting state");
					String readstate = reader.readLine();
					int state1 = (isInteger(readstate) ? Integer.parseInt(readstate) : 0);
					for (Object t:automaton.delta((State) automaton.states().toArray()[state1]))
						System.out.println(t);
				}catch (Exception e){System.err.println(e.getMessage());} break;
				default: System.out.println("Selected command isn't implemented yet.");
			}
		}
	}

	private static boolean isInteger(String s) {
		try {Integer.parseInt(s);}
		catch(NumberFormatException e) {return false;}
		catch(NullPointerException e) {return false;}
		return true;
	}

}
