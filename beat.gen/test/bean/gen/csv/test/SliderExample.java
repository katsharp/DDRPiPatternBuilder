package bean.gen.csv.test;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.widgets.Text;

public class SliderExample {

  Display d;

  Shell s;

  SliderExample() {
    d = new Display();
    s = new Shell(d);
    s.setSize(250, 250);
    
    s.setText("A Slider Example");
    final Slider slide = new Slider(s, SWT.HORIZONTAL);
    slide.setBounds(115, 50, 25, 15);
    slide.setMinimum(0);
    slide.setMaximum(100);
    slide.setIncrement(1);

    final Text t = new Text(s, SWT.BORDER);
    t.setBounds(115, 25, 25, 25);
    t.setText("0");

    slide.addSelectionListener(new SelectionAdapter() {
      public void widgetSelected(SelectionEvent e) {
        t.setText(new Integer(slide.getSelection()).toString());
      }
    });

    s.open();
    while (!s.isDisposed()) {
      if (!d.readAndDispatch())
        d.sleep();
    }
    d.dispose();
  }

  public static void main(String[] args) {
    new SliderExample();
  }

}