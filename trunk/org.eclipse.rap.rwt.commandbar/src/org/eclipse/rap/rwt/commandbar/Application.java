package org.eclipse.rap.rwt.commandbar;

import java.io.InputStream;

import org.eclipse.rwt.graphics.Graphics;
import org.eclipse.rwt.lifecycle.IEntryPoint;
import org.eclipse.rwt.lifecycle.WidgetUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/**
 * This class controls all aspects of the application's execution
 * and is contributed through the plugin.xml.
 */
public class Application implements IEntryPoint {

	public int createUI() {
		final Display display = new Display ();
		final Shell shell = new Shell(display, SWT.DIALOG_TRIM | SWT.RESIZE);
		
//		shell.setLayout(new RowLayout(SWT.VERTICAL));
		shell.setLayout(new GridLayout(1, false));
		createContent2(shell);
		
		shell.setSize(new Point(400, 180));
		shell.open ();
		while (!shell.isDisposed ()) {
			if (!display.readAndDispatch ()) display.sleep ();
		}
		display.dispose ();
		return 0;
	}

	
	public void createContent2(Composite parent) {
		CommandBarFactory factory = new CommandBarFactory();
		
		CmdBar bar = factory.createCmdBar(parent);
		bar.getControl().setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		CmdBarGroup group1 = factory.createGroup(bar);
		group1.setText("commands");
		
		CmdBarButton btn1 = factory.createLargeButton(group1);
		btn1.getBtn().setImage(loadImage("img3.png"));
		btn1.getBtn().setText("Let's go");
		
		CmdBarButton btn2 = factory.createSmallButton(group1);
		btn2.getBtn().setImage(loadImage("img1.png"));
		btn2.getBtn().setText("Let's go");
		
		CmdBarButton btn3 = factory.createSmallButton(group1);
		btn3.getBtn().setImage(loadImage("img2.png"));
		btn3.getBtn().setText("Let's go");

	
		CmdBarGroup group2 = factory.createGroup(bar);
		group2.setText("tasks");
	
		CmdBarButton btn21 = factory.createLargeButton(group2);
		btn21.getBtn().setImage(loadImage("img1.png"));
		btn21.getBtn().setText("Quite a large \ntask to do");
		
		CmdBarMenu menu = factory.createMenu(btn21);
		
		CmdBarGroup groupMenu = factory.createGroup(menu.getCmdBar());
		groupMenu.setText("tasks");
	
		CmdBarButton btnMenu = factory.createLargeButton(groupMenu);
		btnMenu.getBtn().setImage(loadImage("img1.png"));
		btnMenu.getBtn().setText("Quite a large \ntask to do");
	}


	private void createContent(final Composite parent) {
		Label lbl = new Label(parent, SWT.NONE);
		lbl.setText("Eine Commandbar:");
		
		Composite commandBar = new Composite(parent, SWT.NONE);
		commandBar.setLayout(new RowLayout(SWT.HORIZONTAL));
		commandBar.setData(WidgetUtil.CUSTOM_VARIANT, "cmdBar");
		
		final Group groupCmds = createGroup(commandBar, SWT.VERTICAL, "Commands");
		
		final Composite btnParent = groupCmds;
		
		createLargeBtn(btnParent, "Let's go", "img3.png");
		
		createSmallBtn(btnParent, "Click me", "img1.png");
		createSmallBtn(btnParent, "Hello World", "img2.png");
	}


	private Group createGroup(final Composite parent, final int style, final String text) {
		final Group bar = new Group(parent, SWT.NONE);
		bar.setData(WidgetUtil.CUSTOM_VARIANT, "cmdGroup");
//		bar.setText(text);
		
		GridLayout layout = new GridLayout(2, false);
		layout.verticalSpacing = 0;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.marginTop = 0;
		layout.marginBottom = 0;
		layout.marginLeft = 0;
		layout.marginRight = 0;
		bar.setLayout(layout);
		return bar;
	}


	private void createSmallBtn(final Composite btnParent, final String text, final String img) {
		final Button btn = new Button(btnParent, SWT.PUSH);
		btn.setText(text);
		btn.setImage(loadImage(img));
		btn.setData(WidgetUtil.CUSTOM_VARIANT, "cmdSmall");
		btn.setAlignment(SWT.LEFT);
	}

	private void createLargeBtn(final Composite btnParent, final String text, final String img) {
		final Button btn = new Button(btnParent, SWT.PUSH);
		btn.setText(text);
		btn.setImage(loadImage(img));
		btn.setData(WidgetUtil.CUSTOM_VARIANT, "cmdSmall");
		btn.setAlignment(SWT.TOP);
		GridData layoutData = new GridData(SWT.FILL, SWT.FILL, false, false);
		layoutData.verticalSpan = 2;
		
		btn.setLayoutData(layoutData);
	}

	private Image loadImage(final String name) {
		final InputStream imageStream = getClass().getClassLoader().getResourceAsStream("/icons/" + name);
		if (imageStream != null) {
			return Graphics.getImage(name, imageStream);
		}
		return null;
	}
}
