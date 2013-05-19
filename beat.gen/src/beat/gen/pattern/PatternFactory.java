package beat.gen.pattern;

import static beat.gen.ui.Pixel.BLACK;
import static beat.gen.ui.Pixel.WHITE;

import org.eclipse.swt.graphics.RGB;

import beat.gen.ui.Pixel;

public class PatternFactory {


	public static Pattern getSequencePattern(int numFrames){
		Pattern pattern = new Pattern();


		for (int k = 0; k < numFrames; k++) {
			RGB[][] rgb = new RGB[pattern.getHeight()][pattern.getWidth()];	
			for (int i = 0; i < rgb.length; i++) {
				for (int j = 0; j < rgb[i].length; j++) {
					rgb[i][j] = new RGB(0+k, i+k+50, j+k);
				}
			}
			pattern.addFrame(new Frame(rgb));
		}

		return pattern;
	}

	public static Pattern getMovingDotPattern(int numFrames){
		Pattern pattern = new Pattern();

		int dotY = 0;
		int dotX = 0;

		for (int frame = 0; frame < numFrames; frame++) {
			RGB[][] rgb = new RGB[pattern.getHeight()][pattern.getWidth()];	
			for (int y = 0; y < rgb.length; y++) {
				for (int x = 0; x < rgb[y].length; x++) {
					if(y==dotY && x == dotX){
						rgb[y][x] = Pixel.WHITE;
					} else {
						rgb[y][x] = Pixel.BLACK;
					}
				}
			}
			pattern.addFrame(new Frame(rgb));
			int shiftY = frame%2;
			int shiftX = (frame+1)%2;
			dotY+=shiftY;
			dotX+=shiftX;
			if(dotY > pattern.getHeight()){
				dotY = 0;
			}
			if(dotX > pattern.getWidth()){
				dotX = 0;
			}
		}

		return pattern;
	}

	public static Pattern getRepeatingPattern(int smallWidth, int smallHeight){
		Pattern pattern = new Pattern();

		RGB[][] full = new RGB[pattern.getHeight()][pattern.getWidth()];	
		RGB[][] mid = new RGB[pattern.getHeight()][pattern.getWidth()];	
		RGB[][] small = new RGB[pattern.getHeight()][pattern.getWidth()];	
		RGB[][] none = new RGB[pattern.getHeight()][pattern.getWidth()];	

		for (int y = 0; y < full.length; y++) {
			for (int x = 0; x < full[y].length; x++) {
				full[y][x] = Pixel.BLACK;
				mid[y][x] = Pixel.BLACK;
				small[y][x] = Pixel.WHITE;
				none[y][x] = Pixel.WHITE;

				int newX = x % smallWidth;
				int newY = y % smallHeight;
				if(newY == 0 || newY == smallHeight-1){
					if(newX == 0 || newX == smallWidth-1){
						mid[y][x] = Pixel.WHITE;
					} 
				} else if(newY == smallHeight/2) {
					if(newX == smallWidth/2-1 || newX == smallWidth/2){
						small[y][x] = Pixel.BLACK;
					} 
				}

			}
		}

		pattern.addFrame(new Frame(full));
		pattern.addFrame(new Frame(mid));
		pattern.addFrame(new Frame(small));
		pattern.addFrame(new Frame(none));
		pattern.addFrame(new Frame(small));
		pattern.addFrame(new Frame(mid));

		return pattern;
	}

	public static Pattern getRandomPattern(int numFrames){
		Pattern pattern = new Pattern();


		for (int k = 0; k < numFrames; k++) {
			RGB[][] rgb = new RGB[pattern.getHeight()][pattern.getWidth()];	
			for (int i = 0; i < rgb.length; i++) {
				for (int j = 0; j < rgb[i].length; j++) {
					rgb[i][j] = new RGB(0, i, j);
				}
			}
			pattern.addFrame(new Frame(rgb));
		}

		return pattern;
	}

	public static Frame getBlackFrame() {
		return getFrame(BLACK);
	}

	public static Frame getWhiteFrame() {
		return getFrame(WHITE);
	}
	
	public static Frame getFrame(RGB rgb){
		RGB[][] rgbs = new RGB[18][24];
		for (int i = 0; i < rgbs.length; i++) {
			for (int j = 0; j < rgbs[i].length; j++) {
				rgbs[i][j] = rgb;
			}
		}
		return new Frame(rgbs);
	}

}
