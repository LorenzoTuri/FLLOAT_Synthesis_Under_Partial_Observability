package SynthesisPartialObservability.Timer;

import java.util.ArrayList;
import java.util.List;

/**
 * Handler used to store information about the time.
 * Can be used to store messages, along with the current time for a summary
 */
public class TimingHandler {
	private List<DataContainer> data;

	public TimingHandler(){
		data = new ArrayList<>();
	}

	public void add(String description, long time){
		DataContainer a = new DataContainer();
		a.description = description;
		a.time = time;
		data.add(a);
	}

	public List<DataContainer> get(){
		return data;
	}

	public String toString(){
		String result = "";
		for (DataContainer d:get()){
			result.concat(d.toString()+"\n");
		}

		return result;
	}

}
