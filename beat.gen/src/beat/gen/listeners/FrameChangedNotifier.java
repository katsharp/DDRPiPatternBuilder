package beat.gen.listeners;

import java.util.ArrayList;
import java.util.List;

import beat.gen.pattern.Frame;

public class FrameChangedNotifier {
	
	private List<FrameChangeListener> listeners = new ArrayList<FrameChangeListener>();

	public void fireFrameChangedEvent(Frame frame) {
		for (FrameChangeListener listener : listeners) {
			listener.frameChanged(frame);
		}
	}
	
	public void addListener(FrameChangeListener listener){
		listeners.add(listener);
	}
	
	public void removeListener(FrameChangeListener listener){
		listeners.remove(listener);
	}

}
