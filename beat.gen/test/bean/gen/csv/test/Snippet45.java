package bean.gen.csv.test;

/*
 * Scale example snippet: create a scale (maximum 40, page increment 5)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Shell;

public class Snippet45 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	Scale scale = new Scale (shell, SWT.BORDER);
	Rectangle clientArea = shell.getClientArea ();
	scale.setBounds (clientArea.x, clientArea.y, 200, 64);
	scale.setMaximum (40);
	scale.setPageIncrement (5);
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
} 