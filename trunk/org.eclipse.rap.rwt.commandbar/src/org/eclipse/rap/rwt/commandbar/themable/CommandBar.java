package org.eclipse.rap.rwt.commandbar.themable;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * This class must not be called or referenced by clients. Its only purpose
 * is being a place holder for the contribution at org.eclipse.rap.ui.themeableWidgets
 * to register the custom css.
 * @author Stefan Röck
 * @deprecated
 */
@Deprecated
class CommandBar extends Control {

	public CommandBar(Composite parent, int style) {
		super(parent, style);
	}

}
