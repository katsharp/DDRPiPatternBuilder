package beat.gen.dnd;

import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;

import beat.gen.pattern.Frame;
public class MyDragListener implements DragSourceListener {

	private final Viewer viewer;

	public MyDragListener(Viewer viewer) {
		this.viewer = viewer;
	}

	@Override
	public void dragFinished(DragSourceEvent event) {
		System.out.println("Finshed Drag");
	}

	@Override
	public void dragSetData(DragSourceEvent event) {
		System.out.println("drag");
		IStructuredSelection selection = (IStructuredSelection) viewer
				.getSelection();
		Object element = selection.getFirstElement();
		System.out.println(element);
		if(element instanceof Frame){
			Frame frame = (Frame) element;
			event.data = frame;
		}
	}

	@Override
	public void dragStart(DragSourceEvent event) {
		System.out.println("Start Drag");
		IStructuredSelection selection = (IStructuredSelection) viewer
				.getSelection();
		Object element = selection.getFirstElement();
		if(element instanceof Frame){
			LocalSelectionTransfer.getTransfer().setSelection(selection);
		}
	}

} 
