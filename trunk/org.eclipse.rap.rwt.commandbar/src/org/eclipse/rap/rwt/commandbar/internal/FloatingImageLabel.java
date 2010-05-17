package org.eclipse.rap.rwt.commandbar.internal;

import org.eclipse.rwt.lifecycle.WidgetUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

public class FloatingImageLabel  {

	private Control refWidget;
	private Label label;

	/**
	 * Label that calculates its position based on a given reference widget.
	 * @param parent Control to which the label is add. Must have a {@link GridLayout}.
	 * @param referenceWidget Widget to which the floating label calcs its position.
	 * @param style Style flag for {@link Label}.
	 */
	public FloatingImageLabel(Composite parent, Control referenceWidget, int style) {
		this.label = new Label(parent, style);
		this.refWidget = referenceWidget;
		
		GridData layoutData = new GridData(16, 16);
		layoutData.exclude = true;
		getLabel().setLayoutData(layoutData);
		getLabel().moveAbove(referenceWidget);
		// Make background transparent
		getLabel().setData(WidgetUtil.CUSTOM_VARIANT, "transparent");
		
		referenceWidget.addControlListener(new ControlAdapter() {
			@Override
			public void controlMoved(ControlEvent e) {
				updatePosition();
			}
			@Override
			public void controlResized(ControlEvent e) {
				updatePosition();
			}
		});
	}
	
	protected void updatePosition() {
		Rectangle refLocation = getRefWidget().getBounds();
		Point refSize = getRefWidget().getSize();
		Rectangle labelBounds;
		if (getLabel().getImage() != null) {
			labelBounds = getLabel().getImage().getBounds();
		} else {
		  Point lblSize = getLabel().computeSize(SWT.DEFAULT, SWT.DEFAULT);
		  labelBounds = new Rectangle(0, 0, lblSize.x, lblSize.y);
		}
		Point newLocation = new Point(refSize.x / 2 - labelBounds.width / 2, refLocation.height - labelBounds.height - 15);
		getLabel().setBounds(newLocation.x, newLocation.y, labelBounds.width, labelBounds.height);
	}


	public Control getRefWidget() {
		return refWidget;
	}

	public Label getLabel() {
		return label;
	}

}
