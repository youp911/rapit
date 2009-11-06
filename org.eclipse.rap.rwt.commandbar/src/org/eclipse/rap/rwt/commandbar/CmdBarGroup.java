package org.eclipse.rap.rwt.commandbar;

import org.eclipse.rwt.lifecycle.WidgetUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class CmdBarGroup {

	private Composite group;
	private Label label;

	public CmdBarGroup(final CmdBar parent, int style) {
		Composite grpContainer = new Composite(parent.getContainer(), SWT.NONE);
		parent.addNewGroup(this);
		grpContainer.setLayoutData(new GridData(GridData.FILL_VERTICAL));
		
		grpContainer.addDisposeListener(new DisposeListener() {
			
			public void widgetDisposed(DisposeEvent event) {
				parent.removeGroup(CmdBarGroup.this);
			}
		});
		
		GridLayout containerLayout = new GridLayout(1, false);
		containerLayout.marginHeight = 0;
		containerLayout.marginWidth = 0;
		containerLayout.verticalSpacing = 0;
		grpContainer.setLayout(containerLayout );
		grpContainer.setData(WidgetUtil.CUSTOM_VARIANT, "cmdGroupFrame");
		
//		group = new Group(grpContainer, style);
		group = new Composite(grpContainer, style);
		group.setData(WidgetUtil.CUSTOM_VARIANT, "cmdGroup");
		group.setLayoutData(new GridData(GridData.FILL_BOTH));
//		bar.setText(text);
		
		label = new Label(grpContainer, SWT.NONE);
		GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
//		layoutData.verticalIndent = 2;
		label.setLayoutData(layoutData);
		label.setData(WidgetUtil.CUSTOM_VARIANT, "cmdGroupLabel");
		
		GridLayout layout = new GridLayout(2, false);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;
		layout.marginLeft = 2;
		layout.marginTop = 2;
		layout.marginRight = 2;
		layout.marginBottom = 2;
		group.setLayout(layout);
	}

	public GridLayout createGridNoMargin(int numColumns) {
		GridLayout layout = new GridLayout(numColumns, false);
		layout.verticalSpacing = 0;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.marginTop = 0;
		layout.marginBottom = 0;
		layout.marginLeft = 0;
		layout.marginRight = 0;
		return layout;
	}
	
	public void add(CmdBarButton button) {
		
	}
	
	public void setText(final String text) {
		label.setText(text);
	}

	Composite getContainer() {
		return group;
	}
}
