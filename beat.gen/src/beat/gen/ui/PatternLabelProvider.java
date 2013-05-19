package beat.gen.ui;

import org.eclipse.jface.viewers.LabelProvider;

import beat.gen.pattern.Pattern;

public class PatternLabelProvider extends LabelProvider {

	@Override
	public String getText(Object element) {
		if(element instanceof Pattern){
			return ((Pattern)element).getName();
		}
		return super.getText(element);
	}
}
