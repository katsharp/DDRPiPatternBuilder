package beat.gen.listeners;

import beat.gen.pattern.Pattern;

public interface PatternPlayListener extends PatternChangedListener {

	public void playPattern(Pattern pattern);
}
