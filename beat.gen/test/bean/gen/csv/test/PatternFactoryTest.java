package bean.gen.csv.test;
import org.junit.Test;

import beat.gen.csv.PatternWriter;
import beat.gen.pattern.Pattern;
import beat.gen.pattern.PatternFactory;


public class PatternFactoryTest {

	@Test
	public void testWrite() throws Exception {
		Pattern pattern = PatternFactory.getMovingDotPattern(75);
		PatternWriter patternWriter = new PatternWriter("");
		String fileName = "movingDot.csv";
		
		patternWriter.write(pattern);
		
	}

}
