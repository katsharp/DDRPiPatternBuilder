package bean.gen.csv.test;
import java.util.List;

import org.eclipse.swt.graphics.RGB;
import org.junit.Test;

import beat.gen.csv.PatternReader;
import beat.gen.csv.PatternWriter;
import beat.gen.pattern.Frame;
import beat.gen.pattern.Pattern;


public class CopyTest {

	@Test
	public void testWrite() throws Exception {
		PatternWriter patternWriter = new PatternWriter("patterns");
		String fileName = "patterns/matrixCopy.csv";

		PatternReader reader = new PatternReader();
		String name = "matrixCopy.csv";
		Pattern pattern = reader.getPattern(name, fileName);

		List<Frame> frames = pattern.getFrames();

		for (Frame frame : frames) {
			RGB[][] rgbs = frame.getRGBs();
			for (int column = 0; column < 4; column++) {
				for (int row = 0; row < rgbs.length; row++) {
					RGB[] rowRGBs = rgbs[row];
					for (int rowPos = 4+column; rowPos < rowRGBs.length; rowPos+=4) {
//						System.out.println(rowRGBs.length);
//						System.out.println(rowPos);
						rowRGBs[rowPos] = rowRGBs[0+column];
					}
				}
			}
		}
		patternWriter.write(pattern);
	}

	private void printFrame(Frame frame) {
		RGB[][] rgBs = frame.getRGBs();
		for (RGB[] rgbs2 : rgBs) {
			for (RGB rgb : rgbs2) {
				System.out.print(rgb);
				System.out.print(",");
			}
			System.out.println();
		}
	}

	private Frame getFrame() {
		RGB[][] rgbs = new RGB[2][8];
		fillRGB(rgbs);
		return new Frame(rgbs);
	}

	private void fillRGB(RGB[][] rgb) {
		for (int i = 0; i < rgb.length; i++) {
			for (int j = 0; j < 4; j++) {
				rgb[i][j] = new RGB(0, i, j);
			}
		}
	}

}
