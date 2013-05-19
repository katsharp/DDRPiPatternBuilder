package beat.gen.ui;

import static beat.gen.ui.Pixel.PIXEL_DIM;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import beat.gen.csv.PatternWriter;
import beat.gen.pattern.Frame;

public class FrameCanvas {

	public static final int HEIGHT_IN_PIXELS = 18;
	private static final int WIDTH_IN_PIXELS = 24;

	private Canvas canvas;
	private Frame frame;
	private List<Pixel> pixels = new ArrayList<Pixel>();
	private final PatternWriter writer;

	private int startY;
	private int startX;
	private int endX;
	private int endY;
	protected boolean drag;
	private RGB leftColor;
	private RGB rightColor;
	protected RGB color;

	public FrameCanvas(Composite parent, final PatternWriter writer) {
		this.writer = writer;
		canvas = new Canvas (parent, SWT.NONE);

		GridData data = GridDataFactory.fillDefaults().create();
		data.widthHint = WIDTH_IN_PIXELS*PIXEL_DIM+6;
		data.heightHint = HEIGHT_IN_PIXELS*PIXEL_DIM+6;
		canvas.setLayoutData(data);
		canvas.addListener (SWT.Paint, new Listener () {
			public void handleEvent (Event event) {
				draw(event.gc);
			}
		});

		canvas.addMouseListener(new MouseListener() {

			@Override
			public void mouseUp(MouseEvent e) {
				if(frame != null){
					endX = e.x;
					endY = e.y;

					updatePixels();
					canvas.redraw();
					drag = false;
				}
			}

			@Override
			public void mouseDown(MouseEvent e) {
				if(e.button == 1){
					color = leftColor;
				} else {
					color = rightColor;
				}
				startX = e.x;
				startY = e.y;
				drag = true;
			}

			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}
		});

		canvas.addMouseMoveListener(new MouseMoveListener() {

			@Override
			public void mouseMove(MouseEvent e) {
				if(drag && frame != null){
					endX = e.x;
					endY = e.y;

					updatePixels();
					canvas.redraw();
				}

			}
		});
	}


	public void draw(GC gc){

		pixels = new ArrayList<Pixel>();
		int y = 5;
		for (int i = 0; i < HEIGHT_IN_PIXELS; i++) {
			int x = 5;
			for (int j = 0; j < WIDTH_IN_PIXELS; j++) {
				Pixel pixel = new Pixel(i, j, x, y);
				pixels.add(pixel);
				gc.drawRectangle(x, y, PIXEL_DIM, PIXEL_DIM); 
				RGB rgb = getRGB(i, j);
				Color color = new Color(gc.getDevice(), rgb);
				gc.setBackground(color); 
				color.dispose();
				gc.fillRectangle(x+1,y+1,PIXEL_DIM-1,PIXEL_DIM-1);
				x+=PIXEL_DIM;
			}
			y+= PIXEL_DIM;
		}
		gc.dispose();
	}

	private void updatePixels(){
		int minX = Math.min(startX, endX);
		int minY = Math.min(startY, endY);

		int maxX = Math.max(startX, endX);
		int maxY = Math.max(startY, endY);

		int width = maxX - minX;
		int height = maxY - minY;

		for (Pixel pixel : pixels) {
			if(pixel.contains(minX, minY, width, height)){
				RGB[][] rgbs = frame.getRGBs();
				int i = pixel.getI();
				int j = pixel.getJ();
				rgbs[i][j] = color;
			}
		}
		writer.write(frame.getPattern());
	}

	private RGB getRGB(int i, int j) {
		RGB rgb;
		if(frame == null){
			rgb = Pixel.WHITE;
		} else {
			rgb = frame.getRGBs()[i][j];
		}
		return rgb;
	}

	public void frameChanged(Frame frame) {
		this.frame = frame;
		canvas.redraw();
	}


	public void colourChangedLeft(RGB color) {
		this.leftColor = color;
	}


	public void colourChangedRight(RGB color) {
		this.rightColor = color;
		
	}

}
