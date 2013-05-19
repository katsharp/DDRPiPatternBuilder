package beat.gen.ui;

/*
 * Canvas snippet: update a portion of a Canvas frequently
 * 
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class DisplayTestOLD {

	private static final int WIDTH_IN_PIXELS = 24;
	private static final int HEIGHT_IN_PIXELS = 18;
	private static final int PIXEL_DIM = 30;
	static String value;
	public static void main (String[] args) {
		final int INTERVAL = 888;
		final Display display = new Display ();
		final Image image = new Image (display, WIDTH_IN_PIXELS*PIXEL_DIM, HEIGHT_IN_PIXELS*PIXEL_DIM);
		
		Shell shell = new Shell (display);
		shell.setBounds (0, 0, WIDTH_IN_PIXELS*PIXEL_DIM+100, HEIGHT_IN_PIXELS*PIXEL_DIM+100);
		final Canvas canvas = new Canvas (shell, SWT.NONE);
		canvas.setBounds (10, 10, WIDTH_IN_PIXELS*PIXEL_DIM+100, HEIGHT_IN_PIXELS*PIXEL_DIM+100);
		canvas.addListener (SWT.Paint, new Listener () {
			private int switchColors = 0;
			public void handleEvent (Event event) {
				paint(event.gc, switchColors++);
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

	private static void paint(GC gc, int switchColors) {
		int y = 5;	
		for (int i = 0; i < HEIGHT_IN_PIXELS; i++) {
			int x = 5;
			for (int j = 0; j < WIDTH_IN_PIXELS; j++) {
				gc.drawRectangle(x, y, PIXEL_DIM, PIXEL_DIM); 
				Color color = getColor(gc.getDevice(), i, j, switchColors);
				gc.setBackground(color); 
				gc.fillRectangle(x+1,y+1,PIXEL_DIM-1,PIXEL_DIM-1);
				x+=PIXEL_DIM;
			}
			y+= PIXEL_DIM;
		}
	}

	private static Color getColor(final Device device, int i,
			int j, int switchColors) {
		int odd = j%2;
		int even = (j+1)%2;
		int oddHeight = i%2;
		switchColors %= 2 ;
		RGB rgb = new RGB((odd^oddHeight^switchColors)*255, 0, (even^oddHeight^switchColors)*255);
		return new Color(device, rgb);
	}

}