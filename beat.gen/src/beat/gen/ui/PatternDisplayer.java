package beat.gen.ui;


import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Scale;

import beat.gen.csv.PatternWriter;
import beat.gen.listeners.FrameChangeListener;
import beat.gen.listeners.PatternPlayListener;
import beat.gen.pattern.Frame;
import beat.gen.pattern.Pattern;

public class PatternDisplayer implements FrameChangeListener, PatternPlayListener {


	private static final int SLOWEST_INTERVAL = 1000;
	private static final int NUM_STEPS = 40;
	private static final int INTERVAL_STEPS = SLOWEST_INTERVAL/NUM_STEPS;
	private FrameCanvas canvas;
	private Pattern pattern;
	protected PatternPlayer patternPlayer;
	private Frame frame;
	private final PatternWriter writer;
	private Label colourLabelLeft;
	private Label colourLabelRight;

	public PatternDisplayer(Composite parent, PatternWriter writer) {
		this.writer = writer;
		Composite mainComposite = new Composite(parent, SWT.NONE);

		GridData data = GridDataFactory.fillDefaults().create();
		mainComposite.setLayoutData(data);
		mainComposite.setLayout(new GridLayout(1, false));

		createControlArea(mainComposite);

		canvas = new FrameCanvas(mainComposite, writer);

		patternPlayer = new PatternPlayer(canvas);
		patternPlayer.setInterval(SLOWEST_INTERVAL/4);
		setLeftSelectedColour(Pixel.WHITE);
		setRightSelectedColour(Pixel.WHITE);

	}

	private void createControlArea(Composite mainComposite) {
		Composite buttonComposite = new Composite(mainComposite, SWT.NONE);
		GridData data = GridDataFactory.fillDefaults().create();
		buttonComposite.setLayoutData(data);
		buttonComposite.setLayout(new GridLayout(8, false));

		Button playButton = new Button(buttonComposite, SWT.PUSH);
		playButton.setText("play");

		Button stopButton = new Button(buttonComposite, SWT.PUSH);
		stopButton.setText("stop");

		Button rightButton = new Button(buttonComposite, SWT.PUSH);
		rightButton.setText("right");
		
		final Button colourButtonLeft = new Button(buttonComposite, SWT.PUSH);
		colourButtonLeft.setText("Left color");
		
		colourLabelLeft = new Label(buttonComposite, SWT.NONE);
		GridData labelData = new GridData(SWT.CENTER, SWT.CENTER, false, false);
		labelData.widthHint = 25;
		labelData.heightHint = 25;
		colourLabelLeft.setLayoutData(labelData);
		
		final Button colourButtonRight = new Button(buttonComposite, SWT.PUSH);
		colourButtonRight.setText("Right color");
		
		
		colourLabelRight= new Label(buttonComposite, SWT.NONE);
		labelData = new GridData(SWT.CENTER, SWT.CENTER, false, false);
		labelData.widthHint = 25;
		labelData.heightHint = 25;
		colourLabelRight.setLayoutData(labelData);

		playButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				play();
			}
		});

		stopButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				patternPlayer.stop();
			}
		});

		rightButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				patternPlayer.stop();
				frame.shiftRight();
				canvas.frameChanged(frame);
				writer.write(pattern);
			}
		});
		
		colourButtonLeft.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ColorDialog colorDialog = new ColorDialog(colourButtonLeft.getShell());
			    colorDialog.setText("Select your favorite color");
			    RGB selectedColor = colorDialog.open();
			    setLeftSelectedColour(selectedColor);
			}
		});
		
		colourButtonRight.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ColorDialog colorDialog = new ColorDialog(colourButtonLeft.getShell());
			    colorDialog.setText("Select your favorite color");
			    RGB selectedColor = colorDialog.open();
			    setRightSelectedColour(selectedColor);
			}
		});
		
		final Scale scale = new Scale (buttonComposite, SWT.BORDER);
		data = new GridData(SWT.FILL, SWT.FILL, true, true);
		scale.setLayoutData(data);
		scale.setMinimum(1);
		scale.setMaximum (NUM_STEPS);
		scale.setSelection(NUM_STEPS/4);
		
		scale.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				//0
				patternPlayer.setInterval(INTERVAL_STEPS*scale.getSelection());
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
	}

	protected void play() {
		if(pattern != null){
			patternPlayer.stop();
			patternPlayer.play(pattern);
		}
	}

	private void setLeftSelectedColour(RGB rgb) {
		Color color = new Color(colourLabelLeft.getDisplay(), rgb);
	    colourLabelLeft.setBackground(color);
	    color.dispose();
	    canvas.colourChangedLeft(rgb);
	}

	private void setRightSelectedColour(RGB rgb) {
		Color color = new Color(colourLabelRight.getDisplay(), rgb);
		colourLabelRight.setBackground(color);
	    color.dispose();
	    canvas.colourChangedRight(rgb);
	}

	@Override
	public void frameChanged(Frame frame) {
		this.frame = frame;
		if(frame != null){
			this.pattern = frame.getPattern();
		}
		patternPlayer.stop();
		canvas.frameChanged(frame);
	}

	@Override
	public void patternChanged(Pattern pattern) {
		this.pattern = pattern;
	}

	@Override
	public void playPattern(Pattern pattern) {
		this.pattern = pattern;
		play();
	}

}
