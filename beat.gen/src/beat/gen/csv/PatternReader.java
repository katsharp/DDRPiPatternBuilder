package beat.gen.csv;

import java.io.FileReader;
import java.io.IOException;

import org.eclipse.swt.graphics.RGB;

import au.com.bytecode.opencsv.CSVReader;
import beat.gen.pattern.Frame;
import beat.gen.pattern.Pattern;

public class PatternReader {

	public Pattern getPattern(String name, String fileName) throws IOException{
		CSVReader reader = new CSVReader(new FileReader(fileName));

		String[] nextLine = reader.readNext();
		int width = Integer.parseInt(nextLine[0]);
		int height = Integer.parseInt(nextLine[1]);
		long fps = Long.parseLong(nextLine[2]);

		Pattern pattern = new Pattern(name, width, height, fps);

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
				pattern.addFrame(new Frame(frame));
				frame = new RGB[height][width];
				row = 0;
			}
		}


		return pattern;
	}

}
