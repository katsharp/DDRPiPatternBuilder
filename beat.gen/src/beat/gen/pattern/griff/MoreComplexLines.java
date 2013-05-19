package beat.gen.pattern.griff;

import org.eclipse.swt.graphics.RGB;

import beat.gen.pattern.Frame;
import beat.gen.pattern.Pattern;

public class MoreComplexLines implements PatternBuilder {

	@Override
	public Pattern make() {
		Pattern p = new Pattern("IntersectionLines.csv");
		
		int width = p.getWidth();
	    int height = p.getHeight();
	    int space = 3;
	    
	    for (int idx = 0; idx < space; idx++) {
	    	addFrame(idx, space, height, width, p);
	    }
	    
	    return p;
	}
	
	private void addFrame(int idx, int space, int height, int width, Pattern p) {
		RGB[][] frameBuffer = new RGB[p.getHeight()][p.getWidth()];
		//set all pixels as black to baseline
		setBlack(frameBuffer);
		
		//calc x,y co-ord point
		for (int cord = idx; cord < width; cord += space) {
			for (int i = cord; i < height; i++) {
				frameBuffer[i][cord] = new RGB(0,0,0);
			}
			if (cord < frameBuffer.length) {
				for (int j = cord; j < width; j++) {
					frameBuffer[cord][j] = new RGB(0,0,0);
				}
			}
		}
		
		Frame frame = new Frame(frameBuffer);
		p.addFrame(frame);
	}
	
	private void setBlack(RGB[][] frameBuffer) {
		for(RGB[] row : frameBuffer) {
			for (int i =0; i < row.length; i++) {
				row[i] = new RGB(255,255,255);
			}
		}
	}
	
}
