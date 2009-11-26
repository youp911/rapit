package org.eclipse.rap.rwt.commandbar.internal;

import java.io.InputStream;

import org.eclipse.rwt.graphics.Graphics;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Control;

public class Utils {

	public static void hideControl(final Control ctrl) {
		if (ctrl.getLayoutData() instanceof GridData) {
			GridData layoutData = (GridData) ctrl.getLayoutData();
			layoutData.exclude = true;
			ctrl.setVisible(false);
		}
	}
	
	public static void showControl(final Control ctrl) {
		if (ctrl.getLayoutData() instanceof GridData) {
			GridData layoutData = (GridData) ctrl.getLayoutData();
			layoutData.exclude = false;
			ctrl.setVisible(true);
		}
	}

	public static Image loadImage(final String name) {
		final InputStream imageStream = Utils.class.getClassLoader().getResourceAsStream("/icons/" + name);
		if (imageStream != null) {
			return Graphics.getImage(name, imageStream);
		}
		return null;
	}
	
}
