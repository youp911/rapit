package org.eclipse.rap.rwt.commandbar;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.rap.rwt.commandbar.internal.FloatingImageLabel;
import org.eclipse.rap.rwt.commandbar.internal.ICmdBarMenuListener;
import org.eclipse.rap.rwt.commandbar.internal.Utils;
import org.eclipse.rwt.lifecycle.WidgetUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

public class CmdBarGroup {

	private Composite group;
	private Label label;
	private boolean compact;
	private Composite grpContainer;
	private CmdBarButton compactButton;
	private int uncompactedWidth;
	private int uncompactedHeight;

	private List<CmdBarButton> buttons;
	private boolean buttonListHasChanged = false;
	private FloatingImageLabel compactArrowLbl;
	
	private static int DEFAULT_COLUMN_COUNT = 2;
	
	private final GridLayout groupGridLayout;
	
	CmdBarGroup(final CmdBar parent, int style, final String customVariant) {
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
		grpContainer.setData(WidgetUtil.CUSTOM_VARIANT, customVariant);
		
		group = new Composite(grpContainer, style);
		group.setData(WidgetUtil.CUSTOM_VARIANT, "transparent");
		group.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		label = new Label(grpContainer, SWT.NONE);
		GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
		label.setLayoutData(layoutData);
		label.setAlignment(SWT.CENTER);
		label.setData(WidgetUtil.CUSTOM_VARIANT, "cmdGroupLabel");
		
		groupGridLayout = new GridLayout(DEFAULT_COLUMN_COUNT, false);
		groupGridLayout.marginHeight = 0;
		groupGridLayout.marginWidth = 0;
		groupGridLayout.horizontalSpacing = 0;
		groupGridLayout.verticalSpacing = 0;
		groupGridLayout.marginLeft = 2;
		groupGridLayout.marginTop = 2;
		groupGridLayout.marginRight = 2;
		groupGridLayout.marginBottom = 2;
		group.setLayout(groupGridLayout);
	}

	public void dispose() {
		this.grpContainer.dispose();
	}

	public void setText(final String text) {
		if (!label.getText().equals(text)) {
			label.setText(text);
			// Update commandbar, because width of group might have changed.
			updateLayout();
		}
	}
	
	public String getText() {
		return label.getText();
	}

	Composite getButtonContainer() {
		return group;
	}

	boolean isCompact() {
		return this.compact;
	}

	void makeCompact() {
		if (!isCompact()) {
			this.compact = true;
			this.uncompactedWidth = getCurrentWidth();
			this.uncompactedHeight = getCurrentHeight();
			Utils.hideControl(group);
			Utils.hideControl(label);
			
			if (this.compactButton == null) {
				this.compactButton = createCompactButton();
				// Create a new label that contains the arrow down icon
				this.compactArrowLbl = new FloatingImageLabel(getGrpContainer(), compactButton.getBtn(), SWT.NONE);
				compactArrowLbl.getLabel().setImage(Utils.loadImage("arrowDown.png"));
			}
			Utils.showControl(compactButton.getBtn());
			compactArrowLbl.getLabel().setVisible(true);
			compactButton.getBtn().getParent().layout();
		}
	}

	private CmdBarButton createCompactButton() {
		GridData layoutData;
		CmdBarButton btn = new CmdBarButton(getGrpContainer());
		btn.setText(label.getText());
//		btn.getBtn().setAlignment(SWT.CENTER);
		// Override default variant (no border needed)
		btn.getBtn().setData(WidgetUtil.CUSTOM_VARIANT, "cmdCompactBtn");
		
		layoutData = new GridData(GridData.FILL_BOTH);
		layoutData.minimumWidth = CommandBarFactory.LARGE_BUTTON_MINIMUM_WIDTH;
		layoutData.minimumHeight = getUncompactedHeight() - 5; //TODO: 5 is container GridLayout#horizontalSpacing
		btn.getBtn().setLayoutData(layoutData);
		
		// Attach menu
		CommandBarFactory factory = new CommandBarFactory();
		final CmdBarMenu menu = factory.createMenu(btn);
		final CmdBarGroup menuGroup = factory.createMenuGroup(menu.getCmdBar());
		menu.addCmdBarMenuListener(new ICmdBarMenuListener() {
			
			public void beforeOpen() {
				popuplateMenuBeforeOpen(menu, menuGroup);
			}
		});
		return btn;
	}

	
	protected void popuplateMenuBeforeOpen(final CmdBarMenu menu, final CmdBarGroup menuGroup) {
		
		if (buttonListHasChanged) {
			buttonListHasChanged = false;
			// TODO: [sr] impl more sophisticated change detection
			
			// Erase all old content
			menuGroup.removeAllButtons();
			
			final SelectionListener closeListener = new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					menu.close();
				}
			};
			
			// Copy base label text
			menuGroup.setText(getText());
			
			// Copy content to menu
			CommandBarFactory factory = new CommandBarFactory();
			for (CmdBarButton button: getButtons()) {
				CmdBarButton menuBtn = factory.createButton(menuGroup, button.getStyle());
				menuBtn.setImage(button.getImage());
				menuBtn.setText(button.getText());
				
				// Add selection listener to every button to ensure menu is closed on click
				menuBtn.getBtn().addSelectionListener(closeListener);
				
				// Copy selection Listeners
				List<SelectionListener> listeners = button.getSelectionListeners();
				if (listeners != null) {
					for (SelectionListener listener: listeners) {
						menuBtn.addSelectionListener(listener);
					}
				}
				
			}
		}
		
	}

	private void removeAllButtons() {
		Control[] controls = getButtonContainer().getChildren();
		for (Control ctrl: controls) {
			ctrl.dispose();
		}
		assert getButtons().size() == 0;
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

	void unmakeCompact() {
		if (isCompact()) {
			this.compact = false;
			Utils.showControl(group);
			Utils.showControl(label);
			
			if (this.compactButton != null) {
				Utils.hideControl(this.compactButton.getBtn());
				compactArrowLbl.getLabel().setVisible(false);
			}
			group.getParent().layout();
		}
	}
	protected List<CmdBarButton> getButtons() {
		if (buttons == null) {
			buttons = new ArrayList<CmdBarButton>();
		}
		return buttons;
	}

	int getUncompactedHeight() {
		return uncompactedHeight;
	}

	int getCurrentWidth() {
		return getGrpContainer().getSize().x;
	}
	
	int getCurrentHeight() {
		return getGrpContainer().getSize().y;
	}

	@Override
	public String toString() {
		return new StringBuilder(getClass().getName()).append("[").append(getText()).append("]").toString();
	}

	void addNewButton(CmdBarButton cmdBarButton) {
		getButtons().add(cmdBarButton);
		buttonListHasChanged = true;
		getGrpContainer().getParent().layout(true, true);
	}

	void removeButton(CmdBarButton cmdBarButton) {
		getButtons().remove(cmdBarButton);
		buttonListHasChanged = true;
	}

	/**
	 * Forces an layout update because the content of the group has changed which
	 * potentially has an impact on the group's size within the commandbar.
	 */
	void updateLayout() {
		// Update commandbar's layout
		getGrpContainer().getParent().getParent().layout(true, true);
	}

	public void setColumnCount(int columnCount) {
		if (this.groupGridLayout.numColumns != columnCount) {
			this.groupGridLayout.numColumns = columnCount;
			updateLayout();
		}
	}
	
	public int getColumnCount() {
		return this.groupGridLayout.numColumns;
	}
	
}
