package bean.gen.csv.test;

/*
 * DirectoryDialog example snippet: prompt for a directory
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Snippet33 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	shell.open ();
	DirectoryDialog dialog = new DirectoryDialog (shell);
	String platform = SWT.getPlatform();
	dialog.setFilterPath (platform.equals("win32") || platform.equals("wpf") ? "c:\\" : "/");
	System.out.println ("RESULT=" + dialog.open ());
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}

} 