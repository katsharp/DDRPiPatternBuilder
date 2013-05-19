package beat.gen.ui;

public abstract class PatternRunnable implements Runnable {

	protected boolean stop;

	public void stop() {
		stop = true;
	}

}
