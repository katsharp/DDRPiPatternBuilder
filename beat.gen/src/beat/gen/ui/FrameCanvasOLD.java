//package beat.gen.ui;
//
//import static beat.gen.ui.Pixel.BLACK;
//import static beat.gen.ui.Pixel.PIXEL_DIM;
//import static beat.gen.ui.Pixel.WHITE;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.eclipse.jface.layout.GridDataFactory;
//import org.eclipse.swt.SWT;
//import org.eclipse.swt.events.MouseEvent;
//import org.eclipse.swt.events.MouseListener;
//import org.eclipse.swt.graphics.Color;
//import org.eclipse.swt.graphics.GC;
//import org.eclipse.swt.graphics.RGB;
//import org.eclipse.swt.layout.GridData;
//import org.eclipse.swt.widgets.Canvas;
//import org.eclipse.swt.widgets.Composite;
//import org.eclipse.swt.widgets.Event;
//import org.eclipse.swt.widgets.Listener;
//
//import beat.gen.csv.PatternWriter;
//import beat.gen.pattern.Frame;
//
//public class FrameCanvasOLD {
//
//	public static final int HEIGHT_IN_PIXELS = 18;
//	private static final int WIDTH_IN_PIXELS = 24;
//	
//	private Canvas canvas;
//	private Frame frame;
//	private List<Pixel> pixels;
//	private final PatternWriter writer;
//
//	public FrameCanvasOLD(Composite parent, final PatternWriter writer) {
//		this.writer = writer;
//		canvas = new Canvas (parent, SWT.NONE);
//
//		GridData data = GridDataFactory.fillDefaults().create();
//		data.widthHint = WIDTH_IN_PIXELS*PIXEL_DIM+6;
//		data.heightHint = HEIGHT_IN_PIXELS*PIXEL_DIM+6;
//		canvas.setLayoutData(data);
//		canvas.addListener (SWT.Paint, new Listener () {
//			public void handleEvent (Event event) {
//				draw(event.gc);
//			}
//		});
//		
//		canvas.addMouseListener(new MouseListener() {
//			
//			@Override
//			public void mouseUp(MouseEvent e) {
//				for (Pixel pixel : pixels) {
//					if(pixel.contains(e.x, e.y)){
//						RGB[][] rgbs = frame.getRGBs();
//						int i = pixel.getI();
//						int j = pixel.getJ();
//						RGB rgb = rgbs[i][j];
//						if(rgb.equals(Pixel.WHITE)){
//							rgb = BLACK;
//						} else {
//							rgb = WHITE;
//						}
//						rgbs[i][j] = rgb;
//						break;
//					}
//				}
//				writer.write(frame.getPattern());
//				canvas.redraw();
//			}
//			
//			@Override
//			public void mouseDown(MouseEvent e) {
//				int xDown = e.x;
//				int yDown = e.y;
//			}
//			
//			@Override
//			public void mouseDoubleClick(MouseEvent e) {
////				for (Pixel pixel : pixels) {
////					if(pixel.contains(e.x, e.y)){
////						RGB[][] rgbs = frame.getRGBs();
////						int i = pixel.getI();
////						int j = pixel.getJ();
////						RGB rgb = rgbs[i][j];
////						if(rgb.equals(Pixel.WHITE)){
////							rgb = BLACK;
////						} else {
////							rgb = WHITE;
////						}
////						rgbs[i][j] = rgb;
////						break;
////					}
////				}
////				writer.write(frame.getPattern());
////				canvas.redraw();
//			}
//		});
//	}
//	
//
//	public void draw(GC gc){
//		pixels = new ArrayList<Pixel>();
//		int y = 5;
//		for (int i = 0; i < HEIGHT_IN_PIXELS; i++) {
//			int x = 5;
//			for (int j = 0; j < WIDTH_IN_PIXELS; j++) {
//				Pixel pixel = new Pixel(i, j, x, y);
//				pixels.add(pixel);
//				gc.drawRectangle(x, y, PIXEL_DIM, PIXEL_DIM); 
//				RGB rgb = getRGB(i, j);
//				Color color = new Color(gc.getDevice(), rgb);
//				gc.setBackground(color); 
//				gc.fillRectangle(x+1,y+1,PIXEL_DIM-1,PIXEL_DIM-1);
//				x+=PIXEL_DIM;
//			}
//			y+= PIXEL_DIM;
//		}
//		gc.dispose();
//	}
//
//	private RGB getRGB(int i, int j) {
//		RGB rgb;
//		if(frame == null){
//			rgb = Pixel.WHITE;
//		} else {
//			rgb = frame.getRGBs()[i][j];
//		}
//		return rgb;
//	}
//
//	public void frameChanged(Frame frame) {
//		this.frame = frame;
//		canvas.redraw();
//	}
//
//}
