package bean.gen.csv.test;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.eclipse.swt.graphics.RGB;
import org.junit.Test;

import beat.gen.csv.PatternReader;
import beat.gen.csv.PatternWriter;
import beat.gen.pattern.Frame;
import beat.gen.pattern.Pattern;


public class PatternReaderWriterTest {

	@Test
	public void testWrite() throws Exception {
		PatternWriter patternWriter = new PatternWriter("patterns");
		String fileName = "matrix2";
		Pattern pattern = new Pattern();
		int height = 18;
		int width = 24;
		RGB[][] rgbs = new RGB[height][width];

		fillRGB(rgbs);

		pattern.addFrame(new Frame(rgbs));
		pattern.addFrame(new Frame(rgbs));
		patternWriter.write(pattern);
		
		PatternReader reader = new PatternReader();
		Pattern readPattern = reader.getPattern(fileName, fileName);
		
		assertEquals(height, readPattern.getHeight());
		assertEquals(width, readPattern.getWidth());
		
		List<Frame> frames = readPattern.getFrames();
		assertEquals(2, frames.size());
		
		for (Frame frame : frames) {
			System.out.println("new frame");
			for (int i = 0; i < frame.getRGBs().length; i++) {
				for (int j = 0; j < frame.getRGBs()[i].length; j++) {
					System.out.println(i + " " + j);
					RGB actualRGB = frame.getRGBs()[i][j];
					RGB expectedRGB = rgbs[i][j];
					assertEquals(expectedRGB.red, actualRGB.red);
					assertEquals(expectedRGB.green, actualRGB.green);
					assertEquals(expectedRGB.blue, actualRGB.blue);
				}
			}
		}
	}

	private void fillRGB(RGB[][] rgb) {
		for (int i = 0; i < rgb.length; i++) {
			for (int j = 0; j < rgb[i].length; j++) {
				rgb[i][j] = new RGB(0, i, j);
			}
		}
	}

}
