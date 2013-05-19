package beat.gen.ui;

import org.eclipse.swt.widgets.Display;

import beat.gen.pattern.Frame;
import beat.gen.pattern.Pattern;

public class PatternPlayer {

	private final FrameCanvas canvas;
	private PatternRunnable runnable;
	private int interval;

	public PatternPlayer(FrameCanvas canvas) {
		this.canvas = canvas;
	}

	public void play(final Pattern pattern) {
		stop();
		if(!pattern.getFrames().isEmpty()){
			final Display display = Display.getDefault();
			runnable = new PatternRunnable () {
				public void run () {
					if(stop){
						return;
					}
					Frame frame = pattern.getNextFrame();
					canvas.frameChanged(frame);
					display.timerExec (interval, this);
				}
			};
			display.timerExec (interval, runnable);
		}

	}

	public void stop() {
		if(runnable != null){
			runnable.stop();
		}
	}

	public void setInterval(int interval){
		this.interval = interval;
	}
}
