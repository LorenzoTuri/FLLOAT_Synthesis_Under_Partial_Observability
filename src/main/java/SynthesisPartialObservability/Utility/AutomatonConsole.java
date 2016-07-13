package SynthesisPartialObservability.Utility;

import rationals.Automaton;
import rationals.State;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by loren on 13/07/2016.
 */
public class AutomatonConsole {
	Automaton automaton;
	public AutomatonConsole(Automaton automaton){this.automaton = automaton;}
	public void start(){
		boolean exitcommand = false;
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("\n\nInserisci 0 per uscire\n" +
				"Inserisci 1 per stampare gli stati\n" +
				"Inserisci 2 per stampare i terminali\n" +
				"Inserisci 3 per stampare gli iniziali\n" +
				"Inserisci 4 per stampare le transizioni\n" +
				"Inserisci 5 per stampare le transizioni da due stati particolari\n" +
				"Inserisci 6 per stampare le transizioni da uno stato\n" +
				"Inserisci 7 per stampare le transizioni per uno stato");
		while (!exitcommand){
			int command=0;
			try {
				System.out.print("Command: ");
				command = Integer.parseInt(reader.readLine());
			} catch (Exception e) {e.getMessage();}
			switch (command){
				case 0: exitcommand=true;break;
				case 1: System.out.println(automaton.states());break;
				case 2: System.out.println(automaton.terminals());break;
				case 3: System.out.println(automaton.initials());break;
				case 4: for (Object t:automaton.delta()) System.out.println(t);break;
				case 5: try{
					System.out.println("Inserisci lo stato di partenza");
					int state1 = Integer.parseInt(reader.readLine());
					System.out.println("Inserisci lo stato di arrivo");
					int state2 = Integer.parseInt(reader.readLine());
					for (Object t:automaton.deltaFrom(
							(State)automaton.states().toArray()[state1],
							(State)automaton.states().toArray()[state2]))
						System.out.println(t);
				}catch (Exception e){} break;
				case 6:try {
					System.out.println("Inserisci lo stato di partenza");
					int state1 = Integer.parseInt(reader.readLine());
					for (Object t:automaton.delta((State) automaton.states().toArray()[state1]))
						System.out.println(t);
				}catch (Exception e){} break;
				case 7:try {
					System.out.println("Inserisci lo stato di partenza");
					int state1 = Integer.parseInt(reader.readLine());
					for (Object t:automaton.deltaMinusOne((State) automaton.states().toArray()[state1]))
						System.out.println(t);
				}catch (Exception e){} break;
				case 8:
				case 9:
				default: System.out.println("Il comando selezionato non è ancora stato inserito.");
			}
		}
	}
}
