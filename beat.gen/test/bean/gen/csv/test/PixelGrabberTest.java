package bean.gen.csv.test;
import java.awt.Image;
import java.awt.image.PixelGrabber;

import org.junit.Test;

public final class PixelGrabberTest 
{
	@Test
	public void testGrabPixels()
	{
		int height = 0;
		int width = 0;
		Image image = null;
		PixelGrabber pixelGrabber=new PixelGrabber(image, 0,0, 
				width, height, false);  

		try
		{
			pixelGrabber.grabPixels();
		}
		catch (Exception e)
		{
			System.out.println("PixelGrabber exception"); 
		}

		Object pixels = pixelGrabber.getPixels();
		System.out.println(pixels);
	}
}