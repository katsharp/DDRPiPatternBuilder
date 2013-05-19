package beat.gen.dnd;

import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ViewerDropAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TransferData;

import beat.gen.pattern.Frame;
import beat.gen.ui.FrameNavigator;

public class FrameDropListener extends ViewerDropAdapter {

	private Frame target;
	private final FrameNavigator frameNavigator;

	public FrameDropListener(FrameNavigator frameNavigator) {
		super(frameNavigator.getViewer());
		this.frameNavigator = frameNavigator;
	}

	@Override
	public void drop(DropTargetEvent event) {
		if (LocalSelectionTransfer.getTransfer().isSupportedType(event.currentDataType)){
		    ISelection sel = LocalSelectionTransfer.getTransfer().getSelection();
		    System.out.println("sel" +sel);
		}
		target = (Frame) determineTarget(event);
		super.drop(event);
	}

	@Override
	public boolean performDrop(Object data) {
		Object selectedObject = getSelectedObject();
		if(selectedObject instanceof Frame){
			Frame frame = (Frame) selectedObject;
			frameNavigator.replaceFrame(frame.copy(), frame, target);
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean validateDrop(Object target, int operation,
			TransferData transferType) {
		return true;

	}

	@Override
	public void dropAccept(DropTargetEvent event) {
		//
	}
	


} 