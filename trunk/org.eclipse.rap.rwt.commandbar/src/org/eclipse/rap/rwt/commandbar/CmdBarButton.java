package org.eclipse.rap.rwt.commandbar;

import org.eclipse.rwt.lifecycle.WidgetUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;

public class CmdBarButton {

	private Button btn;

	public Button getBtn() {
		return btn;
	}

	public CmdBarButton(CmdBarGroup parent) {
		btn = new Button(parent.getContainer(), SWT.PUSH);
		btn.setData(WidgetUtil.CUSTOM_VARIANT, "cmdSmall");
		btn.setAlignment(SWT.TOP);
//		GridData layoutData = new GridData(SWT.FILL, SWT.FILL, false, false);
//		layoutData.verticalSpan = 2;
		
//		btn.setLayoutData(layoutData);
	}

}
