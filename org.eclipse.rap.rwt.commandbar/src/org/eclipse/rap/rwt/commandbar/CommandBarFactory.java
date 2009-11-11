package org.eclipse.rap.rwt.commandbar;

import org.eclipse.rap.rwt.commandbar.CmdBarButton.BtnStyle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

public class CommandBarFactory {

	public static final int LARGE_BUTTON_MINIMUM_WIDTH = 60;

	public CmdBarGroup createGroup(CmdBar parent) {
		return new CmdBarGroup(parent, SWT.NONE);
		
	}
	
	public CmdBar createCmdBar(Composite parent) {
		return new CmdBar(parent);
	}

	public CmdBarButton createButton(CmdBarGroup parent, BtnStyle style) {
		if (BtnStyle.LARGE == style) {
			return createLargeButton(parent);
		} else {
			return createSmallButton(parent);
		}
	}
	
	public CmdBarButton createLargeButton(CmdBarGroup parent) {
		CmdBarButton btn = new CmdBarButton(parent, BtnStyle.LARGE);
		GridData layoutData = createLargeButtonLayout();
		btn.getBtn().setLayoutData(layoutData);
		return btn;
	}

	private GridData createLargeButtonLayout() {
		GridData layoutData = new GridData(SWT.FILL, SWT.FILL, false, false);
		layoutData.verticalSpan = 2;
		layoutData.heightHint = 60;
		layoutData.minimumWidth = 60;
		return layoutData;
	}

	public CmdBarButton createSmallButton(CmdBarGroup parent) {
		CmdBarButton btn = new CmdBarButton(parent, BtnStyle.SMALL);
		return btn;
	}
	
	public CmdBarMenu createMenu(CmdBarButton button) {
		CmdBarMenu menu = new CmdBarMenu(button);
		return menu;
	}
}
