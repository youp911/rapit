package org.eclipse.rap.rwt.commandbar;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

public class CommandBarFactory {

	public CmdBarGroup createGroup(CmdBar parent) {
		return new CmdBarGroup(parent, SWT.NONE);
		
	}
	
	public CmdBar createCmdBar(Composite parent) {
		return new CmdBar(parent);
	}
	
	public CmdBarButton createLargeButton(CmdBarGroup parent) {
		CmdBarButton btn = new CmdBarButton(parent);
		GridData layoutData = new GridData(SWT.FILL, SWT.FILL, false, false);
		layoutData.verticalSpan = 2;
		btn.getBtn().setLayoutData(layoutData);
		return btn;
	}

	public CmdBarButton createSmallButton(CmdBarGroup parent) {
		CmdBarButton btn = new CmdBarButton(parent);
		return btn;
	}
	
	public CmdBarMenu createMenu(CmdBarButton button) {
		CmdBarMenu menu = new CmdBarMenu(button);
		return menu;
	}
}
