package beat.gen.ui;

/*
 * Canvas snippet: update a portion of a Canvas frequently
 * 
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import beat.gen.csv.PatternReader;
import beat.gen.pattern.Frame;
import beat.gen.pattern.Pattern;
import beat.gen.pattern.PatternFactory;


public class DisplayTest {

	private static final int PIXEL_DIM = 30;
	
	private static final int INTERVAL = 100;

	private static int WIDTH_IN_PIXELS;

	private static int HEIGHT_IN_PIXELS;

	private static Pattern pattern;
	
	public static void main (String[] args) throws IOException {
		PatternReader patternReader = new PatternReader();
		pattern = PatternFactory.getMovingDotPattern(70);
		
		WIDTH_IN_PIXELS = pattern.getWidth();
		HEIGHT_IN_PIXELS = pattern.getHeight();
		
		final Display display = new Display ();
		final Image image = new Image (display, WIDTH_IN_PIXELS*PIXEL_DIM, HEIGHT_IN_PIXELS*PIXEL_DIM);
		
		Shell shell = new Shell (display);
		shell.setBounds (0, 0, WIDTH_IN_PIXELS*PIXEL_DIM+100, HEIGHT_IN_PIXELS*PIXEL_DIM+100);
		final Canvas canvas = new Canvas (shell, SWT.NONE);
		canvas.setBounds (10, 10, WIDTH_IN_PIXELS*PIXEL_DIM+100, HEIGHT_IN_PIXELS*PIXEL_DIM+100);
		canvas.addListener (SWT.Paint, new Listener () {
			public void handleEvent (Event event) {
				paint(event.gc);
			}
		});
		
		display.timerExec (INTERVAL, new Runnable () {
			public void run () {
				if (canvas.isDisposed ()) return;
				canvas.redraw();
				display.timerExec (INTERVAL, this);
			}
		});
		
		shell.open ();
		while (!shell.isDisposed ()){
			if (!display.readAndDispatch ()) display.sleep ();
		}
		image.dispose ();
		display.dispose ();
	}

	private static void paint(GC gc) {
		int y = 5;	
		Frame nextFrame = pattern.getNextFrame();
		for (int i = 0; i < HEIGHT_IN_PIXELS; i++) {
			int x = 5;
			for (int j = 0; j < WIDTH_IN_PIXELS; j++) {
				gc.drawRectangle(x, y, PIXEL_DIM, PIXEL_DIM); 
				RGB rgb = nextFrame.getRGBs()[i][j];
				Color color = new Color(gc.getDevice(), rgb);
				gc.setBackground(color); 
				gc.fillRectangle(x+1,y+1,PIXEL_DIM-1,PIXEL_DIM-1);
				x+=PIXEL_DIM;
			}
			y+= PIXEL_DIM;
		}
	}

}