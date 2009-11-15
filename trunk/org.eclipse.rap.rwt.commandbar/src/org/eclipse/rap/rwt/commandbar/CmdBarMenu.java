package org.eclipse.rap.rwt.commandbar;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.rap.rwt.commandbar.internal.ICmdBarMenuListener;
import org.eclipse.rwt.lifecycle.WidgetUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Shell;

public class CmdBarMenu {

	private final Shell menu;
	private final CmdBar cmdBar;
	private final Button parentBtn;
	private List<ICmdBarMenuListener> listeners;

	public CmdBarMenu(final CmdBarButton parent) {
		parentBtn = parent.getBtn();
		menu = new Shell(parentBtn.getShell(), SWT.NONE);
		menu.setLayout(new FillLayout());
		menu.setData(WidgetUtil.CUSTOM_VARIANT, "cmdMenuShell");
		
		parentBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				open();
			}
		});
		cmdBar = new CmdBar(menu);
		menu.addShellListener(new ShellAdapter() {
			@Override
			public void shellDeactivated(final ShellEvent e) {
				menu.setVisible(false);
			}
		});
		
		// Bar should have no border so that the border rounded corners are define the shell's shape
		GridLayout layout = new GridLayout(1, false);
		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.marginRight = 2;
		cmdBar.getContainer().setLayout(layout);
		cmdBar.getContainer().setData(WidgetUtil.CUSTOM_VARIANT, null);
	}
	
	public void open() {
		fireBeforeOpen();
		
		menu.pack();
		final Point btnLoc = parentBtn.getDisplay().map(parentBtn, null, parentBtn.getLocation());
		final Point btnSize = parentBtn.getSize();
		final Point menuLoc = new Point(btnLoc.x, btnLoc.y + btnSize.y);
		menu.setLocation(menuLoc);
		menu.open();
	}
	
	public CmdBar getCmdBar() {
		return cmdBar;
	}

	private List<ICmdBarMenuListener> getListeners() {
		if (this.listeners == null) {
			this.listeners = new ArrayList<ICmdBarMenuListener>();
		}
		return listeners;
	}
	
	private void fireBeforeOpen() {
		if (this.listeners != null) {
			final List<ICmdBarMenuListener> allListeners = new ArrayList<ICmdBarMenuListener>(this.listeners);
			for (final ICmdBarMenuListener listener: allListeners) {
				listener.beforeOpen();
			}
		}
	}

	
	public void addCmdBarMenuListener(final ICmdBarMenuListener listener) {
		getListeners().add(listener);
	}
	
	public void removeCmdBarMenuListener(final ICmdBarMenuListener listener) {
		getListeners().remove(listener);
	}

	
	public void close() {
		menu.setVisible(false);
	}
	
}
