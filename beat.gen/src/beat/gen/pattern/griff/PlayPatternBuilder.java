package beat.gen.pattern.griff;

import beat.gen.csv.PatternWriter;
import beat.gen.ui.PatternApp;

public class PlayPatternBuilder {

	//private Pattern 
	
	
	public static void main(String[] args) {
		//build a frame buffer
		PatternBuilder pattern = new MoreComplexLines();//new BouncingLineBuilder();
		new PatternWriter(PatternApp.locationDir).write(pattern.make());
	}
}
