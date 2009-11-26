package org.eclipse.rap.rwt.commandbar.demo;


import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.rap.rwt.commandbar.CmdBar;
import org.eclipse.rap.rwt.commandbar.CmdBarButton;
import org.eclipse.rap.rwt.commandbar.CmdBarGroup;
import org.eclipse.rap.rwt.commandbar.CommandBarFactory;
import org.eclipse.rap.rwt.commandbar.Utils;
import org.eclipse.rwt.lifecycle.IEntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * This class controls all aspects of the application's execution
 * and is contributed through the plugin.xml.
 */
public class Application implements IEntryPoint {

	public int createUI() {
		final Display display = new Display ();
		final Shell shell = new Shell(display, SWT.DIALOG_TRIM | SWT.RESIZE);
		shell.setText("Resize me");
		
		GridLayout layout = new GridLayout(1, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		shell.setLayout(layout);
		Composite container = new Composite(shell, SWT.NONE /*| SWT.BORDER*/);
		container.setLayout(new FillLayout());
		container.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		createContent(container);
		
		shell.setSize(new Point(400, 180));
		shell.open ();
		while (!shell.isDisposed ()) {
			if (!display.readAndDispatch ()) display.sleep ();
		}
		display.dispose ();
		return 0;
	}

	
	public Control createContent(final Composite parent) {
		CommandBarFactory factory = new CommandBarFactory();
		
		CmdBar bar = factory.createCmdBar(parent);
		
		CmdBarGroup group1 = factory.createGroup(bar);
		group1.setText("commands");
		
		CmdBarButton btn1 = factory.createLargeButton(group1);
		btn1.setImage(Utils.loadImage("img3.png"));
		btn1.setText("Let's go");
		
		CmdBarButton btn2 = factory.createSmallButton(group1);
		btn2.setImage(Utils.loadImage("img1.png"));
		btn2.setText("Let's go");
		
		CmdBarButton btn3 = factory.createSmallButton(group1);
		btn3.setImage(Utils.loadImage("img2.png"));
		btn3.setText("Let's go");

	
		CmdBarGroup group2 = factory.createGroup(bar);
		group2.setText("tasks");
	
		CmdBarButton btn21 = factory.createLargeButton(group2);
		btn21.setImage(Utils.loadImage("img1.png"));
		btn21.setText("Quite a large \ntask to do");
		btn21.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MessageDialog.openInformation(parent.getShell(), "Wise words", "Don’t take life too serious – you won’t get out of it alive!");
			}
		});
		
//		CmdBarMenu menu = factory.createMenu(btn21);
//		
//		CmdBarGroup groupMenu = factory.createGroup(menu.getCmdBar());
//		groupMenu.setText("tasks");
//	
//		CmdBarButton btnMenu = factory.createLargeButton(groupMenu);
//		btnMenu.setImage(Utils.loadImage("img1.png"));
//		btnMenu.setText("Quite a large \ntask to do");
		
		return bar.getControl();
	}
}
