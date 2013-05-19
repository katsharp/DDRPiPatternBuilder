package beat.gen.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import beat.gen.csv.PatternWriter;
import beat.gen.dnd.FrameDropListener;
import beat.gen.dnd.MyDragListener;
import beat.gen.listeners.FrameChangedNotifier;
import beat.gen.listeners.PatternChangedListener;
import beat.gen.pattern.Frame;
import beat.gen.pattern.Pattern;
import beat.gen.pattern.PatternFactory;

public class FrameNavigator implements PatternChangedListener {

	private final TableViewer viewer;
	protected final FrameChangedNotifier frameChangedNotifier;
	private final PatternWriter writer;
	private Pattern pattern;

	public FrameNavigator(Composite parent, FrameChangedNotifier frameChangedNotifier, PatternWriter writer) {
		this.frameChangedNotifier = frameChangedNotifier;
		this.writer = writer;

		Composite mainComposite = new Composite(parent, SWT.NONE);

		GridData data = GridDataFactory.fillDefaults().create();
		mainComposite.setLayoutData(data);
		mainComposite.setLayout(new GridLayout(1, false));

		createButtons(mainComposite);

		viewer = new TableViewer(mainComposite, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);

		GridData tableData = new GridData(SWT.FILL, SWT.FILL, true, true);
		tableData.widthHint = 300;
		viewer.getTable().setLayoutData(tableData);
		FrameContentProvider patternContentProvider = new FrameContentProvider();
		viewer.setContentProvider(patternContentProvider);
		PatternLabelProvider patternLabelProvider = new PatternLabelProvider();
		viewer.setLabelProvider(patternLabelProvider);

		int operations = DND.DROP_COPY | DND.DROP_MOVE;
		Transfer[] transferTypes = new Transfer[]{LocalSelectionTransfer.getTransfer()};
		viewer.addDragSupport(DND.DROP_MOVE, transferTypes , new MyDragListener(viewer));

		viewer.addDropSupport(operations, transferTypes, new FrameDropListener(this));

		addDoubleClickListener();
	}

	private void createButtons(Composite mainComposite) {
		Composite buttonComposite = new Composite(mainComposite, SWT.NONE);
		GridData data = GridDataFactory.fillDefaults().create();
		buttonComposite.setLayoutData(data);
		buttonComposite.setLayout(new GridLayout(5, false));

		Button newButton = new Button(buttonComposite, SWT.PUSH);
		newButton.setText("new");



		Button deleteButton = new Button(buttonComposite, SWT.PUSH);
		deleteButton.setText("delete");

		newButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				addFrame(PatternFactory.getBlackFrame());
			}

		});

		Button dupeButton = new Button(buttonComposite, SWT.PUSH);
		dupeButton.setText("duplicate");

		dupeButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Frame frame = getSelectedFrame();
				if(frame != null){
					Frame dupeFrame = frame.copy();
					addFrame(dupeFrame, frame);
				}
			}
		});

		Button invertButton = new Button(buttonComposite, SWT.PUSH);
		invertButton.setText("invert");

		invertButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				List<Frame> frames = getSelectedFrames();
				for (Frame frame : frames) {
					frame.invert();
				}
				updatePattern();
			}
		});
		

		Button flipButton = new Button(buttonComposite, SWT.PUSH);
		flipButton.setText("flipButton");

		flipButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				List<Frame> frames = pattern.removeFrames();
				for (int i = frames.size()-1; i >=0; i--) {
					Frame frame = frames.get(i);
					pattern.addFrame(frame);
				}
				updatePattern();
			}
		});

		deleteButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Frame frame = getSelectedFrame();
				if(frame != null){
					pattern.removeFrame(frame);
					updatePattern();
				}
			}
		});
	}


	public void addFrame(Frame frame) {
		if(pattern != null){
			pattern.addFrame(frame);
			updatePattern();
		}
	}

	public void addFrame(Frame frame, Frame target) {
		pattern.addFrameAfter(frame, target);
		updatePattern();
	}

	public void replaceFrame(Frame newFrame, Frame oldFrame, Frame target) {
		pattern.addFrameAfter(newFrame, target);
		pattern.removeFrame(oldFrame);
		updatePattern();
	}

	protected Frame getSelectedFrame() {
		Frame frame = null;
		IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
		if(selection != null){
			Object firstElement = selection.getFirstElement();
			frame = (Frame)firstElement;
		}
		return frame;
	}

	protected List<Frame> getSelectedFrames() {
		List<Frame> frames = new ArrayList<Frame>();
		IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
		if(selection != null){
			Iterator iterator = selection.iterator();
			while (iterator.hasNext()) {
				Object object = (Object) iterator.next();
				Frame frame = (Frame)object;
				frames.add(frame);
			}
		}
		return frames;
	}

	protected void updatePattern() {
		writer.write(pattern);
		setPattern(pattern);
	}

	private void addDoubleClickListener() {
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection thisSelection = (IStructuredSelection) event.getSelection(); 
				Object selectedNode = thisSelection.getFirstElement(); 
				frameChangedNotifier.fireFrameChangedEvent((Frame) selectedNode);
			}
		});
		//		viewer.addDoubleClickListener(new IDoubleClickListener() {
		//			@Override
		//			public void doubleClick(DoubleClickEvent event) {
		//				IStructuredSelection thisSelection = (IStructuredSelection) event.getSelection(); 
		//				Object selectedNode = thisSelection.getFirstElement(); 
		//				frameChangedNotifier.fireFrameChangedEvent((Frame) selectedNode);
		//			}
		//		});
	}

	private void setPattern(Pattern pattern){
		this.pattern = pattern;
		viewer.setInput(pattern.getFrames().toArray());
	}

	@Override
	public void patternChanged(Pattern pattern) {
		setPattern(pattern);
	}

	public Viewer getViewer() {
		return viewer;
	}


}
