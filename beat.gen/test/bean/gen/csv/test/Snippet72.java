package bean.gen.csv.test;

/*
 * FileDialog example snippet: prompt for a file name (to save)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Snippet72 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	shell.open ();
	ColorDialog colorDialog = new ColorDialog(shell);
    colorDialog.setText("Select your favorite color");
    RGB selectedColor = colorDialog.open();
    System.out.println(selectedColor);
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
} 