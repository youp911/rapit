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
	private boolean compact;
	private Composite grpContainer;
	private CmdBarButton compactButton;
	private int uncompactedWidth;
	private int uncompactedHeight;

	public int getUncompactedHeight() {
		return uncompactedHeight;
	}

	public CmdBarGroup(final CmdBar parent, int style) {
		grpContainer = new Composite(parent.getContainer(), SWT.NONE);
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
		
		group = new Composite(grpContainer, style);
		group.setData(WidgetUtil.CUSTOM_VARIANT, "cmdGroup");
		group.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		label = new Label(grpContainer, SWT.NONE);
		GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
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
	
	public String getText() {
		return label.getText();
	}

	Composite getButtonContainer() {
		return group;
	}

	public boolean isCompact() {
		return this.compact;
	}

	public void makeCompact() {
		if (!isCompact()) {
			this.compact = true;
			this.uncompactedWidth = getCurrentWidth();
			this.uncompactedHeight = getCurrentHeight();
			Utils.hideControl(group);
			Utils.hideControl(label);
			
			if (this.compactButton == null) {
				this.compactButton = createCompactButton();
			}
			Utils.showControl(compactButton.getBtn());
			compactButton.getBtn().getParent().layout();
		}
	}

	private CmdBarButton createCompactButton() {
		GridData layoutData;
		CmdBarButton btn = new CmdBarButton(getGrpContainer());
		btn.getBtn().setText(label.getText());
		btn.getBtn().setAlignment(SWT.CENTER);
		layoutData = new GridData(GridData.FILL_BOTH);
		layoutData.minimumWidth = CommandBarFactory.LARGE_BUTTON_MINIMUM_WIDTH;
		layoutData.minimumHeight = getUncompactedHeight() - 5; //TODO: 5 is container GridLayout#horizontalSpacing
		btn.getBtn().setLayoutData(layoutData);
		return btn;
	}

	int getUncompactedWidth() {
		return uncompactedWidth;
	}

	/**
	 * Root widget of this group. Holds the button container ({@link #getButtonContainer()})
	 * and a label.
	 * @return
	 */
	Composite getGrpContainer() {
		return grpContainer;
	}

	public void unmakeCompact() {
		if (isCompact()) {
			this.compact = false;
			Utils.showControl(group);
			Utils.showControl(label);
			
			if (this.compactButton != null) {
				Utils.hideControl(this.compactButton.getBtn());
			}
			group.getParent().layout();
		}
	}

	public int getCurrentWidth() {
		return getGrpContainer().getSize().x;
	}
	
	public int getCurrentHeight() {
		return getGrpContainer().getSize().y;
	}

	@Override
	public String toString() {
		return new StringBuilder(getClass().getName()).append("[").append(getText()).append("]").toString();
	}
	
}
