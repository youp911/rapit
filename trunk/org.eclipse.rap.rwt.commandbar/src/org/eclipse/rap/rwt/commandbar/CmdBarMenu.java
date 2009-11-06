package org.eclipse.rap.rwt.commandbar;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Shell;

public class CmdBarMenu {

	private Shell menu;
	private CmdBar cmdBar;
	private Button parentBtn;

	public CmdBarMenu(CmdBarButton parent) {
		parentBtn = parent.getBtn();
		menu = new Shell(parentBtn.getShell(), SWT.NONE);
		menu.setLayout(new FillLayout());
		// TODO: setStyle data
		
		parentBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				open();
			}
		});
		cmdBar = new CmdBar(menu);
		menu.addShellListener(new ShellAdapter() {
			@Override
			public void shellDeactivated(ShellEvent e) {
				menu.setVisible(false);
			}
		});
	}
	
	public void open() {
		menu.pack();
		Point btnLoc = parentBtn.getDisplay().map(parentBtn, null, parentBtn.getLocation());
		Point btnSize = parentBtn.getSize();
		Point menuLoc = new Point(btnLoc.x, btnLoc.y + btnSize.y + 5);
		menu.setLocation(menuLoc);
		menu.open();
	}
	
	public CmdBar getCmdBar() {
		return cmdBar;
	}
}
