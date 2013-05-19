package beat.gen.csv;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import beat.gen.pattern.Pattern;

public class PatternDir {

	public static Pattern[] getPatterns(String locationDir) {
		File dir = new File(locationDir);

    	File[] listFiles = dir.listFiles(new FilenameFilter() { 
    	         public boolean accept(File dir, String filename)
    	              { return filename.endsWith(".csv"); }
    	} );
    	
    	List<Pattern> patterns = new ArrayList<Pattern>();
    	PatternReader patternReader = new PatternReader();
    	for (File file : listFiles) {
			String fileName = file.getPath();
			String name = file.getName();
			try {
				Pattern pattern = patternReader.getPattern(name, fileName);
				patterns.add(pattern);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    	Comparator<Pattern> comparator = new Comparator<Pattern>() {

			@Override
			public int compare(Pattern pattern1, Pattern pattern2) {
				return pattern1.getName().compareTo(pattern2.getName());
			}
		};
		
		Collections.sort(patterns, comparator);
		return patterns.toArray(new Pattern[patterns.size()]);
	}

}
