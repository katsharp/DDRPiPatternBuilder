package beat.gen.csv;

import java.io.FileReader;
import java.io.IOException;

import org.eclipse.swt.graphics.RGB;

import au.com.bytecode.opencsv.CSVReader;
import beat.gen.pattern.Frame;
import beat.gen.pattern.Pattern;

public class CSVTransposer {

	public static void main(String[] args) throws IOException {
		String name = "frame";
		CSVReader reader = new CSVReader(new FileReader(name));

		String[] nextLine = reader.readNext();
		int width = Integer.parseInt(nextLine[0]);
		int height = Integer.parseInt(nextLine[1]);
		long fps = Long.parseLong(nextLine[2]);

		Pattern pattern = new Pattern(name, height, width, fps);

		RGB[][] frame = new RGB[height][width];
		int row = 0;
		while ((nextLine = reader.readNext()) != null) {
			int column = 0;
			for (String RGBString : nextLine) {
				RGB rgb = new RGB(Integer.valueOf( RGBString.substring( 1, 3 ), 16 ),
						Integer.valueOf( RGBString.substring( 3, 5 ), 16 ),
						Integer.valueOf( RGBString.substring( 5, 7 ), 16 ));
				frame[row][column] = rgb;
				column++;
			}
			row++;
			if(row == height){
				RGB[][] rgb = transpose(frame, height, width);
				Frame transposedFrame = new Frame(rgb);
				pattern.addFrame(transposedFrame);
				frame = new RGB[height][width];
				row = 0;
			}
		}


		PatternWriter writer = new PatternWriter("frameTransposed");
		writer.write(pattern);
	}

	private static RGB[][] transpose(RGB[][] frame, int height, int width) {
		RGB[][] transposed = new RGB[width][height];
		for (int i = 0; i < frame.length; i++) {
			for (int j = 0; j < frame[j].length; j++) {
				transposed[j][i] = frame[i][j];
			}
		}
		return transposed;
	}
	
}
