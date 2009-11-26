package org.eclipse.rap.rwt.commandbar.demo;


import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.rap.rwt.commandbar.CmdBar;
import org.eclipse.rap.rwt.commandbar.CmdBarButton;
import org.eclipse.rap.rwt.commandbar.CmdBarGroup;
import org.eclipse.rap.rwt.commandbar.CommandBarFactory;
import org.eclipse.rap.rwt.commandbar.CmdBarButton.BtnStyle;
import org.eclipse.rwt.lifecycle.IEntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/**
 * This class controls all aspects of the application's execution
 * and is contributed through the plugin.xml.
 */
public class Application implements IEntryPoint {

	private CmdBar cmdBar;


	public int createUI() {
		final Display display = new Display ();
		final Shell shell = new Shell(display, SWT.DIALOG_TRIM | SWT.RESIZE);
		shell.setText("Resize me");
		final GridLayout layout = new GridLayout(1, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		shell.setLayout(layout);
		
		final SashForm sashForm = new SashForm(shell, SWT.HORIZONTAL);
		sashForm.setLayoutData(new GridData(GridData.FILL_BOTH));

		final Composite cmdBarContainer = new Composite(sashForm, SWT.BORDER /*| SWT.BORDER*/);
		final GridLayout containerLayout = new GridLayout(1, false);
		containerLayout.marginWidth = 0;
		containerLayout.marginHeight = 0;
		cmdBarContainer.setLayout(containerLayout);
		final Control ctrl = createContent(cmdBarContainer);
		ctrl.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		final Composite controlContainer = new Composite(sashForm, SWT.BORDER);
		controlContainer.setLayout(new FillLayout());
		createControlBar(controlContainer);
		
		sashForm.setWeights(new int[] {60, 40});
		
		shell.setSize(new Point(800, 600));
		shell.setMaximized(true);
		shell.open ();
		while (!shell.isDisposed ()) {
			if (!display.readAndDispatch ()) display.sleep ();
		}
		display.dispose ();
		return 0;
	}

	
	protected Control createControlBar(final Composite parent) {
		final Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(1, true));
		Label lbl;
		Button btn;
		
		final WidgetCreator creator = new WidgetCreator();
		
		lbl = new Label(container, SWT.NONE);
		lbl.setText("Selected Widget");
		lbl.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		final Combo cmbCreateWidgets = new Combo(container, SWT.DROP_DOWN | SWT.READ_ONLY);
		cmbCreateWidgets.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		creator.setWidgetSelector(cmbCreateWidgets);
		creator.setCommandBar(cmdBar);
		
		lbl = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);
		lbl.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		// Create action area
		btn = new Button(container, SWT.PUSH);
		btn.setText("Create Group");
		btn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				creator.createGroup();
			}
		});
		
		btn = new Button(container, SWT.PUSH);
		btn.setText("Delete Group");
		btn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				creator.deleteGroup();
			}
		});
		
		btn = new Button(container, SWT.PUSH);
		btn.setText("Create large Button");
		btn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				creator.createButton(BtnStyle.LARGE);
			}
		});
		
		btn = new Button(container, SWT.PUSH);
		btn.setText("Create small Button");
		btn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				creator.createButton(BtnStyle.SMALL);
			}
		});
		
		btn = new Button(container, SWT.PUSH);
		btn.setText("Delete Button");
		btn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				creator.deleteButton();
			}
		});
		
		
		return container;
	}


	public Control createContent(final Composite parent) {
		final CommandBarFactory factory = new CommandBarFactory();
		
		cmdBar = factory.createCmdBar(parent);
		
		final CmdBarGroup group1 = factory.createGroup(cmdBar);
		group1.setText("commands");
		
		final CmdBarButton btn1 = factory.createLargeButton(group1);
		btn1.setImage(WidgetCreator.loadImage("img3.png"));
		btn1.setText("Let's go");
		
		final CmdBarButton btn2 = factory.createSmallButton(group1);
		btn2.setImage(WidgetCreator.loadImage("img1.png"));
		btn2.setText("Let's go");
		
		final CmdBarButton btn3 = factory.createSmallButton(group1);
		btn3.setImage(WidgetCreator.loadImage("img2.png"));
		btn3.setText("Let's go");

	
		final CmdBarGroup group2 = factory.createGroup(cmdBar);
		group2.setText("tasks");
	
		final CmdBarButton btn21 = factory.createLargeButton(group2);
		btn21.setImage(WidgetCreator.loadImage("img1.png"));
		btn21.setText("Quite a large \ntask to do");
		btn21.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
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
		
		return cmdBar.getControl();
	}
	
	

	
}
