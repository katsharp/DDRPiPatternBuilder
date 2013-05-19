package beat.gen.csv;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.eclipse.swt.graphics.RGB;

import au.com.bytecode.opencsv.CSVWriter;
import beat.gen.pattern.Frame;
import beat.gen.pattern.Pattern;

public class PatternWriter {
	
	private final String dir;
	
	public PatternWriter(String dir){
		this.dir = dir;
	}
	
	public void write(Pattern pattern) {
		try {
			String fileName = dir + "/" + pattern.getName();
			CSVWriter writer = new CSVWriter(new FileWriter(fileName),
					CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_ESCAPE_CHARACTER);

			String[] firstLine = { String.valueOf(pattern.getWidth()),
					String.valueOf(pattern.getHeight()),
					String.valueOf(pattern.getFramesPerSecond()) };
			writer.writeNext(firstLine);

			List<Frame> frames = pattern.getFrames();
			for (Frame frame : frames) {
				RGB[][] rgbs = frame.getRGBs();
				for (int i = 0; i < rgbs.length; i++) {
					String[] line = new String[rgbs[i].length];
					for (int j = 0; j < rgbs[i].length; j++) {
						line[j] = getRGBHex(rgbs[i][j]);
					}
					writer.writeNext(line);
				}
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getRGBHex(RGB rgb) {
		String hex = String.format("#%02x%02x%02x", rgb.red, rgb.green, rgb.blue);
		return hex;
	}

	public String getDir() {
		return dir;
	}

}
