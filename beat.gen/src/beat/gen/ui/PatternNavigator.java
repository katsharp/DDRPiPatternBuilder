package beat.gen.ui;

import java.util.List;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;

import beat.gen.csv.PatternDir;
import beat.gen.csv.PatternWriter;
import beat.gen.dnd.MyDragListener;
import beat.gen.listeners.PatternNotifier;
import beat.gen.pattern.Frame;
import beat.gen.pattern.Pattern;

public class PatternNavigator {


	private TreeViewer viewer;
	protected PatternNotifier patternChangedNotifier;
	private final PatternWriter writer;

	public PatternNavigator(Composite parent, PatternNotifier patternChangedNotifier, PatternWriter writer) {
		this.patternChangedNotifier = patternChangedNotifier;
		this.writer = writer;

		Composite mainComposite = new Composite(parent, SWT.NONE);

		GridData data = GridDataFactory.fillDefaults().create();
		mainComposite.setLayoutData(data);
		mainComposite.setLayout(new GridLayout(1, false));

		createButtons(mainComposite);

		viewer = new TreeViewer(mainComposite, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		Tree tree = viewer.getTree();
		GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
		layoutData.widthHint = 300;
		tree.setLayoutData(layoutData);

		PatternContentProvider patternContentProvider = new PatternContentProvider();
		viewer.setContentProvider(patternContentProvider);
		PatternLabelProvider patternLabelProvider = new PatternLabelProvider();
		viewer.setLabelProvider(patternLabelProvider);


		int operations = DND.DROP_COPY;
		Transfer[] transferTypes = new Transfer[]{LocalSelectionTransfer.getTransfer()};
		viewer.addDragSupport(operations, transferTypes , new MyDragListener(viewer));

		addMouseListener();
	}

	private void createButtons(Composite mainComposite) {
		Composite buttonComposite = new Composite(mainComposite, SWT.NONE);
		GridData data = GridDataFactory.fillDefaults().create();
		buttonComposite.setLayoutData(data);
		buttonComposite.setLayout(new GridLayout(3, false));

		final Button newButton = new Button(buttonComposite, SWT.PUSH);
		newButton.setText("new");

		newButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				runPatternDialog(newButton, null);
			}


		});
		final Button dupeButton = new Button(buttonComposite, SWT.PUSH);
		dupeButton.setText("duplicate");

		dupeButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Pattern pattern = getSelectedPattern();
				if(pattern != null){
					runPatternDialog(dupeButton, pattern);
				}
			}
		});

	}
	private void runPatternDialog(final Button button, final Pattern patternToCopy) {
		final Shell dialog = new Shell (button.getShell(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		dialog.setText("Pattern name");
		FormLayout formLayout = new FormLayout ();
		formLayout.marginWidth = 10;
		formLayout.marginHeight = 10;
		formLayout.spacing = 10;
		dialog.setLayout (formLayout);

		Label label = new Label (dialog, SWT.NONE);
		label.setText ("Pattern name:");
		FormData data = new FormData ();
		label.setLayoutData (data);

		Button cancel = new Button (dialog, SWT.PUSH);
		cancel.setText ("Cancel");
		data = new FormData ();
		data.width = 60;
		data.right = new FormAttachment (100, 0);
		data.bottom = new FormAttachment (100, 0);
		cancel.setLayoutData (data);
		cancel.addSelectionListener (new SelectionAdapter () {
			public void widgetSelected (SelectionEvent e) {

				dialog.close ();
			}
		});

		final Text text = new Text (dialog, SWT.BORDER);
		data = new FormData ();
		data.width = 200;
		data.left = new FormAttachment (label, 0, SWT.DEFAULT);
		data.right = new FormAttachment (100, 0);
		data.top = new FormAttachment (label, 0, SWT.CENTER);
		data.bottom = new FormAttachment (cancel, 0, SWT.DEFAULT);
		text.setLayoutData (data);

		Button ok = new Button (dialog, SWT.PUSH);
		ok.setText ("OK");
		data = new FormData ();
		data.width = 60;
		data.right = new FormAttachment (cancel, 0, SWT.DEFAULT);
		data.bottom = new FormAttachment (100, 0);
		ok.setLayoutData (data);
		ok.addSelectionListener (new SelectionAdapter () {
			public void widgetSelected (SelectionEvent e) {
				Pattern pattern = new Pattern(text.getText()+".csv");
				if(patternToCopy != null){
					List<Frame> frames = patternToCopy.getFrames();
					for (Frame frame : frames) {
						pattern.addFrame(frame.copy());
					}
				}

				writer.write(pattern);
				refreshInput();


				dialog.close ();
			}
		});

		dialog.setDefaultButton (ok);
		dialog.pack ();
		dialog.open ();
	}

	protected Pattern getSelectedPattern() {
		Pattern pattern = null;
		IStructuredSelection thisSelection = (IStructuredSelection) viewer.getSelection(); 
		Object selectedNode = thisSelection.getFirstElement(); 
		if(selectedNode instanceof Pattern){
			pattern = (Pattern) selectedNode;
		}
		return pattern;
	}

	private void addMouseListener() {
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				Pattern pattern = getSelectedPattern();
				if(pattern != null){
					patternChangedNotifier.firePatternChangedEvent(pattern);
				}
			}
		});
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				Pattern pattern = getSelectedPattern();
				if(pattern != null){
					patternChangedNotifier.firePatternPlayEvent(pattern);
				}
			}
		});
	}

	public void refreshInput(){
		Pattern[] patterns = PatternDir.getPatterns(writer.getDir());
		viewer.setInput(patterns);
	}

}
