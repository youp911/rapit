package org.eclipse.rap.rwt.commandbar;


import org.eclipse.rwt.lifecycle.IEntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
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
		
		shell.setLayout(new GridLayout(1, false));
		createContent(shell);
		
		shell.setSize(new Point(400, 180));
		shell.open ();
		while (!shell.isDisposed ()) {
			if (!display.readAndDispatch ()) display.sleep ();
		}
		display.dispose ();
		return 0;
	}

	
	public void createContent(Composite parent) {
		CommandBarFactory factory = new CommandBarFactory();
		
		CmdBar bar = factory.createCmdBar(parent);
		bar.getControl().setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		CmdBarGroup group1 = factory.createGroup(bar);
		group1.setText("commands");
		
		CmdBarButton btn1 = factory.createLargeButton(group1);
		btn1.getBtn().setImage(Utils.loadImage("img3.png"));
		btn1.getBtn().setText("Let's go");
		
		CmdBarButton btn2 = factory.createSmallButton(group1);
		btn2.getBtn().setImage(Utils.loadImage("img1.png"));
		btn2.getBtn().setText("Let's go");
		
		CmdBarButton btn3 = factory.createSmallButton(group1);
		btn3.getBtn().setImage(Utils.loadImage("img2.png"));
		btn3.getBtn().setText("Let's go");

	
		CmdBarGroup group2 = factory.createGroup(bar);
		group2.setText("tasks");
	
		CmdBarButton btn21 = factory.createLargeButton(group2);
		btn21.getBtn().setImage(Utils.loadImage("img1.png"));
		btn21.getBtn().setText("Quite a large \ntask to do");
		
		CmdBarMenu menu = factory.createMenu(btn21);
		
		CmdBarGroup groupMenu = factory.createGroup(menu.getCmdBar());
		groupMenu.setText("tasks");
	
		CmdBarButton btnMenu = factory.createLargeButton(groupMenu);
		btnMenu.getBtn().setImage(Utils.loadImage("img1.png"));
		btnMenu.getBtn().setText("Quite a large \ntask to do");
	}
}
