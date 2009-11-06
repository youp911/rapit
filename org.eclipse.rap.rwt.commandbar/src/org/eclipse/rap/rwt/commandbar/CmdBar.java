package org.eclipse.rap.rwt.commandbar;

import org.eclipse.rwt.lifecycle.WidgetUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class CmdBar {

	private Composite commandBar;

	public CmdBar(Composite parent) {
		commandBar = new Composite(parent, SWT.NONE);
		commandBar.setData(WidgetUtil.CUSTOM_VARIANT, "cmdBar");
	}
	
	public Control getControl() {
		return commandBar;
	}

	Composite getContainer() {
		return commandBar;
	}

	void addNewGroup(CmdBarGroup group) {
		if (commandBar.getLayout() == null) {
			commandBar.setLayout(new GridLayout(1, false));
		} else {
			GridLayout layout = (GridLayout) commandBar.getLayout();
			layout.numColumns++;
		}
		commandBar.layout();
	}

	void removeGroup(CmdBarGroup group) {
		if (commandBar.getLayout() != null) {
			GridLayout layout = (GridLayout) commandBar.getLayout();
			layout.numColumns--;
		}
		commandBar.layout();
		
	}
	
	
	
}
