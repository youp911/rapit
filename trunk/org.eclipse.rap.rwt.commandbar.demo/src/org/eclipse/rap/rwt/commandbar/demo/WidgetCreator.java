package org.eclipse.rap.rwt.commandbar.demo;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.rap.rwt.commandbar.CmdBar;
import org.eclipse.rap.rwt.commandbar.CmdBarButton;
import org.eclipse.rap.rwt.commandbar.CmdBarGroup;
import org.eclipse.rap.rwt.commandbar.CommandBarFactory;
import org.eclipse.rap.rwt.commandbar.CmdBarButton.BtnStyle;
import org.eclipse.rwt.graphics.Graphics;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;

public class WidgetCreator {
	private final List<Object> allWidgets = new ArrayList<Object>();
	private Combo widgetSelector;
	private CmdBar cmdBar;
	private CommandBarFactory factory = new CommandBarFactory();
	
	private int widgetCounter = 0;
	private Random random = new Random();
	
	public void createGroup() {
		CmdBarGroup group = getFactory().createGroup(getCmdBar());
		group.setText("Group " + ++widgetCounter);
		addWidget(group);
	}
	
	public void createButton(BtnStyle style) {
		final Object selection = getSelectedWidget();
		if (!(selection instanceof CmdBarGroup)) {
			error("No group selected");
			return;
		}
		
		final CmdBarGroup group = (CmdBarGroup) selection;
		CmdBarButton btn = getFactory().createButton(group, style);
		
		btn.setText("Button " + ++widgetCounter);
		btn.setImage(loadRandomImage());
		
		addWidget(btn);
	}
	

	private void addWidget(Object widget) {
		getAllWidgets().add(widget);
		getWidgetSelector().add(widget.toString());
		getWidgetSelector().select(getWidgetSelector().getItemCount() - 1);
	}

	private void removeWidget(Object widget) {
		int idx = getAllWidgets().indexOf(widget);
		if (idx >= 0) {
			getAllWidgets().remove(idx);
			getWidgetSelector().remove(idx);
		}
		
		if (getWidgetSelector().getItemCount() > 0) {
			getWidgetSelector().select(0);
		} else {
			getWidgetSelector().deselectAll();
		}
	}
	
	
	public void deleteGroup() {
		final Object selection = getSelectedWidget();
		if (!(selection instanceof CmdBarGroup)) {
			error("No group selected");
			return;
		}
		
		final CmdBarGroup group = (CmdBarGroup) selection;
		getFactory().removeGroup(group);
		removeWidget(group);
	}

	private void error(final String msg) {
		MessageDialog.openInformation(Display.getCurrent().getActiveShell(), "Something went wrong", msg);
	}

	

	protected Object getSelectedWidget() {
		final int selectionIndex = getWidgetSelector() != null ? getWidgetSelector()
				.getSelectionIndex()
				: -1;
		Object widget = null;
		if (selectionIndex > -1 && selectionIndex < getAllWidgets().size()) {
			widget = getAllWidgets().get(selectionIndex);
		}
		return widget;
	}

	protected List<Object> getAllWidgets() {
		return allWidgets;
	}

	public void setWidgetSelector(Combo combo) {
		widgetSelector = combo;
	}

	protected Combo getWidgetSelector() {
		return widgetSelector;
	}

	public void setCommandBar(CmdBar cmdBar) {
		this.cmdBar = cmdBar;
	}

	protected CmdBar getCmdBar() {
		return cmdBar;
	}
	protected CommandBarFactory getFactory() {
		return factory;
	}


	public void deleteButton() {
		final Object selection = getSelectedWidget();
		if (!(selection instanceof CmdBarButton)) {
			error("No group selected");
			return;
		}
		
		final CmdBarButton widget = (CmdBarButton) selection;
		getFactory().removeButton(widget);
		removeWidget(widget);
	}

	public static Image loadImage(final String name) {
		final InputStream imageStream = WidgetCreator.class.getClassLoader().getResourceAsStream("/icons/" + name);
		if (imageStream != null) {
			return Graphics.getImage(name, imageStream);
		}
		return null;
	}

	
	private Image loadRandomImage() {
		int idx = random.nextInt(2) + 1;
		return loadImage("img" + idx + ".png");
	}


}
