package beat.gen.ui;

import static beat.gen.ui.FrameCanvas.HEIGHT_IN_PIXELS;
import static beat.gen.ui.Pixel.PIXEL_DIM;

import java.io.IOException;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import beat.gen.csv.PatternWriter;
import beat.gen.listeners.FrameChangedNotifier;
import beat.gen.listeners.PatternNotifier;


public class PatternApp {


	public static final String locationDir = "patterns";

	public static void main (String[] args) throws IOException {

		final Display display = new Display ();

		Shell shell = new Shell (display);
		shell.setBounds (0, 0, 1500, HEIGHT_IN_PIXELS*PIXEL_DIM+150);
		shell.setLayout(new FillLayout());
		
		PatternWriter writer = new PatternWriter(locationDir);

		PatternNotifier patternChangedNotifier = new PatternNotifier();
		FrameChangedNotifier frameChangedNotifier = new FrameChangedNotifier();
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayout(new GridLayout(3, false));
		composite.setLayoutData(GridDataFactory.fillDefaults().create());
		
		PatternDisplayer patternDisplayer = new PatternDisplayer(composite, writer);
		frameChangedNotifier.addListener(patternDisplayer);
		patternChangedNotifier.addPatternChangedListener(patternDisplayer);
		
		FrameNavigator frameNavigator = new FrameNavigator(composite, frameChangedNotifier, writer);
		patternChangedNotifier.addPatternChangedListener(frameNavigator);
		
		patternChangedNotifier.addPatternPlayListener(patternDisplayer);
		
		PatternNavigator patternNavigator = new PatternNavigator(composite, patternChangedNotifier, writer);
		patternNavigator.refreshInput();

		shell.open ();
		while (!shell.isDisposed ()){
			if (!display.readAndDispatch ()) display.sleep ();
		}
		display.dispose ();
	}


}