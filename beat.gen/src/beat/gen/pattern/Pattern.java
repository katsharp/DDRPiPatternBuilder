package beat.gen.pattern;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Pattern {
	
	private final int height;
	private final int width;
	private final long fps;
	
	private List<Frame> frames = new ArrayList<Frame>();
	private Iterator<Frame> iterator;
	private final String name;
	
	public Pattern(String name, int aWidth, int aHeight, long anFPS) {
		this.name = name;
		fps = anFPS;
		height = aHeight;
		width = aWidth;
	}

	public Pattern() {
		this("Unknown");
	}

	public Pattern(String name) {
		this(name, 24, 18, 1);
	}

	public long getFramesPerSecond(){
		return fps;
	}

	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	/**
	 * get the next frame, return to the start if at end
	 * @return
	 */
	public Frame getNextFrame(){
		if(iterator == null || !iterator.hasNext()){
			iterator = frames.iterator();
		}
		return iterator.next();
	}
	
	public List<Frame> getFrames() {
		return frames;
	}

	public void addFrame(Frame frame) {
		frame.setPattern(this);
		frames.add(frame);
		iterator = frames.iterator();
	}

	public String getName() {
		return name;
	}

	public void removeFrame(Frame frame) {
		frame.setPattern(null);
		frames.remove(frame);
		iterator = frames.iterator();
	}

	public void addFrameAfter(Frame frame, Frame prevFrame) {
		frame.setPattern(this);
		int position = frames.indexOf(prevFrame);
		frames.add(position+1, frame);
		iterator = frames.iterator();
	}

	public List<Frame> removeFrames() {
		List<Frame> oldFrames = frames;
		frames = new ArrayList<Frame>();
		return oldFrames;
	}

}
