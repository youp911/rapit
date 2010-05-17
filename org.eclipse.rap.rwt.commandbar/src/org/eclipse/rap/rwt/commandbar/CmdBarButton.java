package org.eclipse.rap.rwt.commandbar;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.rwt.lifecycle.WidgetUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class CmdBarButton {

	public static enum BtnStyle {
		LARGE, SMALL
	}

	private final Button btn;
	private BtnStyle style;
	private CmdBarGroup parentGroup;

	Button getBtn() {
		return btn;
	}

	CmdBarButton(final CmdBarGroup parent, final BtnStyle style) {
		this(parent.getButtonContainer());
		this.style = style;
		this.parentGroup = parent;
		parent.addNewButton(this);
		getBtn().addDisposeListener(new DisposeListener() {

			public void widgetDisposed(final DisposeEvent event) {
				parent.removeButton(CmdBarButton.this);
			}
		});
	}

	CmdBarButton(final Composite parent) {
		btn = new Button(parent, SWT.PUSH);
		btn.setData(WidgetUtil.CUSTOM_VARIANT, "cmdSmall");
		btn.setAlignment(SWT.TOP);
	}

	public void setImage(final Image image) {
		getBtn().setImage(image);
		getParentGroup().updateLayout();
	}

	public Image getImage() {
		return getBtn().getImage();
	}

	public void setText(final String text) {
		if (!getBtn().getText().equals(text)) {
			getBtn().setText(text);
			if (getParentGroup() != null) {
				getParentGroup().updateLayout();
			}
		}
	}

	public String getText() {
		return getBtn().getText();
	}

	public BtnStyle getStyle() {
		return style;
	}

	public void addSelectionListener(final SelectionListener listener) {
		getBtn().addSelectionListener(listener);
	}

	public void removeSelectionListener(final SelectionListener listener) {
		getBtn().removeSelectionListener(listener);
	}

	public List<SelectionListener> getSelectionListeners() {
		final List<SelectionListener> list = new ArrayList<SelectionListener>();
		final Object[] listeners = SelectionEvent.getListeners(getBtn());

		if (listeners != null) {
			for (final Object obj : listeners) {
				list.add((SelectionListener) obj);
			}
		}
		return list;
	}
	
	@Override
	public String toString() {
		return new StringBuilder(getClass().getName()).append("[").append(getText()).append("]").toString();
	}

	public void dispose() {
		getBtn().dispose();
		getParentGroup().updateLayout();
	}


	public CmdBarGroup getParentGroup() {
		return parentGroup;
	}
	
}
