package org.eclipse.rap.rwt.commandbar;

import org.eclipse.rwt.lifecycle.WidgetUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class CmdBarButton {

	private Button btn;

	public Button getBtn() {
		return btn;
	}

	public CmdBarButton(CmdBarGroup parent) {
		this(parent.getButtonContainer());
	}

	CmdBarButton(Composite parent) {
		btn = new Button(parent, SWT.PUSH);
		btn.setData(WidgetUtil.CUSTOM_VARIANT, "cmdSmall");
		btn.setAlignment(SWT.TOP);
	}
}
