package bean.gen.csv.test;

import org.eclipse.swt.graphics.RGB;
import org.junit.Test;

import beat.gen.pattern.Frame;
import beat.gen.ui.Pixel;

public class FrameTest {
	
	@Test
	public void testShiftRight(){
		RGB[][] rgbs = new RGB[2][2];
		rgbs[0][0] = Pixel.BLACK;
		rgbs[1][0] = Pixel.BLACK;
		rgbs[0][1] = Pixel.WHITE;
		rgbs[1][1] = Pixel.WHITE;
		
		Frame frame = new Frame(rgbs);
		print(frame);
		frame.shiftRight();
		
		print(frame);
	}
	
	@Test
	public void testInvert(){
		RGB[][] rgbs = new RGB[2][2];
		rgbs[0][0] = Pixel.BLACK;
		rgbs[1][0] = Pixel.BLACK;
		rgbs[0][1] = Pixel.WHITE;
		rgbs[1][1] = Pixel.WHITE;
		
		Frame frame = new Frame(rgbs);
		print(frame);
		frame.invert();
		
		print(frame);
	}

	private void print(Frame frame) {
		System.out.println("printing");
		RGB[][] rgBs2 = frame.getRGBs();
		for (RGB[] rgbs3 : rgBs2) {
			for (RGB rgb : rgbs3) {
				System.out.println(rgb);
			}
		}
	}

}
