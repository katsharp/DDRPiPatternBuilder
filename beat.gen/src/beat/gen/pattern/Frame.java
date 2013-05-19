package beat.gen.pattern;

import static beat.gen.ui.Pixel.BLACK;
import static beat.gen.ui.Pixel.WHITE;

import org.eclipse.swt.graphics.RGB;

public class Frame {

	private final RGB[][] rgbs;
	private Pattern pattern;

	public Frame(RGB[][] rgbs) {
		this.rgbs = rgbs;
	}

	public RGB[][] getRGBs() {
		return rgbs;
	}
	
	public void shiftRight(){
		for (int i = 0; i < rgbs.length; i++) {
			System.out.println("i " +i);
			int width = rgbs[i].length;
			RGB temp = rgbs[i][width-1];
			for (int j = width -1; j > 0; j--) {
				rgbs[i][j] = rgbs[i][j-1];
			}
			rgbs[i][0] = temp;
		}
	}
	
	public void setPattern(Pattern pattern) {
		this.pattern = pattern;
	}

	public Pattern getPattern() {
		return pattern;
	}

	public Frame copy() {
		RGB [][] newFrame = new RGB[rgbs.length][];
		for(int i = 0; i < rgbs.length; i++){
			newFrame[i] = new RGB[rgbs[i].length];
			for (int j = 0; j < rgbs[i].length; j++) {
				RGB oldRGB = rgbs[i][j];
				newFrame[i][j] = new RGB(oldRGB.red, oldRGB.green, oldRGB.blue);
			}
		}
		return new Frame(newFrame);
	}

	public void invert() {
		for(int i = 0; i < rgbs.length; i++){
			for (int j = 0; j < rgbs[i].length; j++) {
				RGB oldRGB = rgbs[i][j];
				if(oldRGB.equals(WHITE)){
					rgbs[i][j] = BLACK;
				} else {
					rgbs[i][j] = WHITE;
				}
			}
		}
	}

}
