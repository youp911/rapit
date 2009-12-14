package org.eclipse.rap.rwt.commandbar;

import org.eclipse.rap.rwt.commandbar.CmdBarButton.BtnStyle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

public class CommandBarFactory {

	public static final int LARGE_BUTTON_MINIMUM_WIDTH = 60;

	public CmdBarGroup createGroup(final CmdBar parent) {
		final CmdBarGroup group = new CmdBarGroup(parent, SWT.NONE, "cmdGroupFrame");
		return group;
	}
	
	public void removeGroup(final CmdBarGroup group) {
		Composite parent = group.getGrpContainer().getParent();
		group.dispose();
		parent.layout();
	}
	
	public CmdBar createCmdBar(final Composite parent) {
		return new CmdBar(parent);
	}

	public CmdBarButton createButton(final CmdBarGroup parent, final BtnStyle style) {
		CmdBarButton btn;
		if (BtnStyle.LARGE == style) {
			btn = createLargeButton(parent);
		} else {
			btn = createSmallButton(parent);
		}
		return btn;
	}
	
	public void removeButton(CmdBarButton btn) {
		btn.dispose();
	}

	
	public CmdBarButton createLargeButton(final CmdBarGroup parent) {
		final CmdBarButton btn = new CmdBarButton(parent, BtnStyle.LARGE);
		final GridData layoutData = createLargeButtonLayout();
		btn.getBtn().setLayoutData(layoutData);
		return btn;
	}

	private GridData createLargeButtonLayout() {
		final GridData layoutData = new GridData(SWT.FILL, SWT.FILL, false, false);
		layoutData.verticalSpan = 2;
		layoutData.heightHint = 60;
		layoutData.minimumWidth = 60;
		return layoutData;
	}

	public CmdBarButton createSmallButton(final CmdBarGroup parent) {
		final CmdBarButton btn = new CmdBarButton(parent, BtnStyle.SMALL);
		btn.getBtn().getParent().layout();
		return btn;
	}
	
	public CmdBarMenu createMenu(final CmdBarButton button) {
		final CmdBarMenu menu = new CmdBarMenu(button);
		return menu;
	}

	CmdBarGroup createMenuGroup(final CmdBar parent) {
		return new CmdBarGroup(parent, SWT.NONE, "cmdGroupFrameMenu");
	}
}
