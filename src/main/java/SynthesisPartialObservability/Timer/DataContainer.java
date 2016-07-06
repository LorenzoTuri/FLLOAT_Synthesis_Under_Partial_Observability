package SynthesisPartialObservability.Timer;

/**
 * Class used to store datas, used in TimingHandler.
 */
public class DataContainer{
	public String description;
	public long time;
	public String toString(){
		return description+": "+time;
	}
}