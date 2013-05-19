package beat.gen.pattern.griff;

import org.eclipse.swt.graphics.RGB;

import beat.gen.pattern.Frame;
import beat.gen.pattern.Pattern;

public class BouncingLineBuilder implements PatternBuilder {

	@Override
	public Pattern make() {
		Pattern bouncingLinePattern = new Pattern("BouncingLine.csv");
		for (int i = 0, width = bouncingLinePattern.getWidth(); i < width; i++) {
			addFrame(i, bouncingLinePattern);
		}
		for (int i = bouncingLinePattern.getWidth() - 1; i > 0; i--) {
			addFrame(i, bouncingLinePattern);
		}
		
		return bouncingLinePattern;
	}

	private void addFrame(int idx, Pattern bouncingLinePattern) {
		RGB[][] frameBuffer = new RGB[bouncingLinePattern.getHeight()][bouncingLinePattern.getWidth()];
		//set all pixels as black to baseline
		setBlack(frameBuffer);
		
		//render in the appropriate vertical line
		for (int i=0; i < frameBuffer.length; i++) {
			frameBuffer[i][idx] = calcRGB(idx, bouncingLinePattern.getWidth());
		}
		
		Frame frame = new Frame(frameBuffer);
		bouncingLinePattern.addFrame(frame);
	}
	
	
	private void setBlack(RGB[][] frameBuffer) {
		for(RGB[] row : frameBuffer) {
			for (int i =0; i < row.length; i++) {
				row[i] = new RGB(255,255,255);
			}
		}
	}
	
	private RGB calcRGB(int idx, int width) {
		//nasty linear approx - as I can't remember Sin / Cos usage off the top of my head :(
		int midpoint = width / 2;
		
		int red = 255 - (int)(255 * (idx / (midpoint * 1.0)));
		int green = (idx >= midpoint) ? 255 - (int)(255 * ((idx - midpoint) / (midpoint * 1.0))) : 
										(int)(255 * (idx / (midpoint * 1.0)));
		int blue = (int) (255 * (idx - midpoint) / (midpoint * 1.0));
		
		//TODO - calculate colour based on offset (R 0 - B [MAX WIDTH])
		return new RGB((red < 0)? 0 : red,(green < 0) ? 0 : green, (blue < 0) ? 0 : blue);
	}
}
