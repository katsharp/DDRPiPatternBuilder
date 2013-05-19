package beat.gen.ui;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import beat.gen.pattern.Pattern;

public class PatternContentProvider implements ITreeContentProvider {

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object[] getElements(Object inputElement) {
		return (Object[]) inputElement;
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if(parentElement instanceof Pattern){
			Pattern pattern = (Pattern) parentElement;
			return pattern.getFrames().toArray();
		}
		return null;
	}

	@Override
	public Object getParent(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		return element instanceof Pattern;
	}

}
