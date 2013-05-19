package beat.gen.ui;

import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;

public class Pixel {

	public static final int PIXEL_DIM = 30;
	public static final RGB BLACK = new RGB(0, 0, 0);
	public static final RGB WHITE = new RGB(255, 255, 255);

	private final int i;
	private final int j;
	private final int x;
	private final int y;

	private Rectangle rectangle;

	public Pixel(int i, int j, int x, int y) {
		this.i = i;
		this.j = j;
		this.x = x;
		this.y = y;	
	}
	
	public boolean contains(int xx, int yy, int width, int height){
		if(rectangle == null){
			rectangle = new Rectangle(x, y, PIXEL_DIM, PIXEL_DIM);
		}
		return	rectangle.intersects(xx,yy,width,height);
	}
	
	public int getJ() {
		return j;
	}
	
	public int getI() {
		return i;
	}

}
