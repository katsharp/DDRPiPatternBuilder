package beat.gen.listeners;

import java.util.ArrayList;
import java.util.List;

import beat.gen.pattern.Pattern;

public class PatternNotifier {
	
	private List<PatternChangedListener> patternChangedListeners = new ArrayList<PatternChangedListener>();
	private List<PatternPlayListener> patternPlayListeners = new ArrayList<PatternPlayListener>();

	public void firePatternChangedEvent(Pattern pattern) {
		for (PatternChangedListener listener : patternChangedListeners) {
			listener.patternChanged(pattern);
		}
	}
	
	public void addPatternChangedListener(PatternChangedListener listener){
		patternChangedListeners.add(listener);
	}
	
	public void removePatternChangedListener(PatternChangedListener listener){
		patternChangedListeners.remove(listener);
	}
	
	public void addPatternPlayListener(PatternPlayListener listener){
		patternPlayListeners.add(listener);
	}
	
	public void removePatternPlayListener(PatternPlayListener listener){
		patternPlayListeners.remove(listener);
	}

	public void firePatternPlayEvent(Pattern pattern) {
		for (PatternPlayListener listener : patternPlayListeners) {
			listener.playPattern(pattern);
		}
	}

}
