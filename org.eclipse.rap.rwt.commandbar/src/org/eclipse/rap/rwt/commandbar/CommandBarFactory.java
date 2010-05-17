package org.eclipse.rap.rwt.commandbar;

import org.eclipse.rap.rwt.commandbar.CmdBarButton.BtnStyle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

/**
 * Facade that allows creating a commandbar and adding some widgets.
 * A commandbar consists of one or multiple groups that are aligned horizontally
 * and have to ability to collapse if there's not enough space. When doing so, the
 * content is still available in a context menu.
 * <br/>
 * Each group can hold multiple buttons that can have different types and sizes.
 * @author Stefan Röck
 *
 */
public class CommandBarFactory {

	static final int LARGE_BUTTON_MINIMUM_WIDTH = 60;

	/**
	 * Creates a new {@link CmdBar} instance that is initially empty.
	 * @param parent
	 * @return
	 */
	public CmdBar createCmdBar(final Composite parent) {
		return new CmdBar(parent);
	}

	/**
	 * Creates a new {@link CmdBarGroup} within a {@link CmdBar}.
	 * @param parent
	 * @return
	 */
	public CmdBarGroup createGroup(final CmdBar parent) {
		final CmdBarGroup group = new CmdBarGroup(parent, SWT.NONE, "cmdGroupFrame");
		return group;
	}
	
	/**
	 * Removes a group and triggers a relayout.
	 * @param group
	 */
	public void removeGroup(final CmdBarGroup group) {
		Composite parent = group.getGrpContainer().getParent();
		group.dispose();
		parent.layout();
	}

	/**
	 * Creates a {@link CmdBarButton} within a {@link CmdBarGroup}.
	 * @param parent
	 * @param style defines the button style {@link BtnStyle}
	 * @return
	 */
	public CmdBarButton createButton(final CmdBarGroup parent, final BtnStyle style) {
		CmdBarButton btn;
		if (BtnStyle.LARGE == style) {
			btn = createLargeButton(parent);
		} else {
			btn = createSmallButton(parent);
		}
		return btn;
	}
	
	/**
	 * Removes a button from a {@link CmdBarGroup}.
	 * @param btn
	 */
	public void removeButton(CmdBarButton btn) {
		btn.dispose();
	}

	
	/**
	 * Convenience method for {@link #createButton(CmdBarGroup, BtnStyle)} with
	 * {@link BtnStyle#LARGE}.
	 * @param parent
	 * @return
	 */
	public CmdBarButton createLargeButton(final CmdBarGroup parent) {
		final CmdBarButton btn = new CmdBarButton(parent, BtnStyle.LARGE);
		final GridData layoutData = createLargeButtonLayout();
		btn.getBtn().setLayoutData(layoutData);
		return btn;
	}

	/**
	 * Convenience method for {@link #createButton(CmdBarGroup, BtnStyle)} with
	 * {@link BtnStyle#SMALL}.
	 * @param parent
	 * @return
	 */
	public CmdBarButton createSmallButton(final CmdBarGroup parent) {
		final CmdBarButton btn = new CmdBarButton(parent, BtnStyle.SMALL);
		btn.getBtn().getParent().layout();
		return btn;
	}
	
	
	CmdBarMenu createMenu(final CmdBarButton button) {
		final CmdBarMenu menu = new CmdBarMenu(button);
		return menu;
	}

	CmdBarGroup createMenuGroup(final CmdBar parent) {
		return new CmdBarGroup(parent, SWT.NONE, "cmdGroupFrameMenu");
	}
	
	GridData createLargeButtonLayout() {
		final GridData layoutData = new GridData(SWT.FILL, SWT.FILL, false, false);
		layoutData.verticalSpan = 2;
		layoutData.heightHint = 60;
		layoutData.minimumWidth = 60;
		return layoutData;
	}

}
