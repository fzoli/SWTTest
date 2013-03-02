package org.dyndns.fzoli.test;

import org.dyndns.fzoli.swt.ButtonComposite;
import org.dyndns.fzoli.swt.SWTUtils;
import org.dyndns.fzoli.test.res.R;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TabItem;

public class ConfigEditorWindow {

	protected Shell shell;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ConfigEditorWindow window = new ConfigEditorWindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setText("Connection settings");
		shell.setLayout(new GridLayout(1, false));
		
		GridData gdTab = new GridData();
		gdTab.horizontalAlignment = SWT.FILL;
		gdTab.verticalAlignment = SWT.FILL;
		gdTab.grabExcessHorizontalSpace = true;
		gdTab.grabExcessVerticalSpace = true;
		
		TabFolder tabFolder = new TabFolder(shell, SWT.NONE);
		tabFolder.setLayoutData(gdTab);
		
		Composite composite1 = new Composite(tabFolder, SWT.NONE);
		composite1.setLayout(new GridLayout(2, false));
		
		TabItem tbtm1 = new TabItem(tabFolder, SWT.NO_FOCUS);
		tbtm1.setControl(composite1);
		tbtm1.setText("Path");
		
		GridData gdMessage1 = new GridData();
		gdMessage1.horizontalSpan = 2;
		gdMessage1.horizontalAlignment = SWT.FILL;
		gdMessage1.grabExcessHorizontalSpace = true;
		gdMessage1.grabExcessVerticalSpace = true;
		gdMessage1.widthHint = 250;
		
		Label lblMessage1 = new Label(composite1, SWT.WRAP);
		lblMessage1.setLayoutData(gdMessage1);
		lblMessage1.setText("In this tab you can set the path of the Bridge connection.");
		Label lblAddress = new Label(composite1, SWT.NONE);
		lblAddress.setText("Server host:");
		
		GridData gdAddress = new GridData();
		gdAddress.horizontalAlignment = SWT.FILL;
		gdAddress.grabExcessVerticalSpace = true;
		
		Text txtAddress = new Text(composite1, SWT.BORDER);
		txtAddress.setLayoutData(gdAddress);
		
		Label lblPort = new Label(composite1, SWT.NONE);
		lblPort.setText("Server port:");
		
		GridData gdPort = new GridData();
		gdPort.horizontalAlignment = SWT.FILL;
		gdPort.grabExcessVerticalSpace = true;
		
		Text txtPort = new Text(composite1, SWT.BORDER);
		txtPort.setLayoutData(gdPort);
		
		Composite cmpFiller = new Composite(tabFolder, SWT.NONE);
		cmpFiller.setLayout(new GridLayout(1, false));
		
		GridData gdFiller = new GridData();
		gdFiller.minimumHeight = 185;
		gdFiller.horizontalAlignment = SWT.FILL;
		gdFiller.verticalAlignment = SWT.FILL;
		gdFiller.grabExcessHorizontalSpace = true;
		gdFiller.grabExcessVerticalSpace = true;
		
		new Text(cmpFiller, SWT.MULTI | SWT.V_SCROLL | SWT.WRAP | SWT.BORDER).setLayoutData(gdFiller);
		
		TabItem tbtm2 = new TabItem(tabFolder, SWT.NO_FOCUS);
		tbtm2.setControl(cmpFiller);
		tbtm2.setText("Input");
		
		GridData gdButtons = new GridData();
		gdButtons.horizontalAlignment = SWT.FILL;
		
		ButtonComposite cmpButtons = new ButtonComposite(shell, SWT.NONE, "Help", "Cancel", "OK");
		cmpButtons.setLayoutData(gdButtons);
		
		cmpButtons.getButtonList().get(1).addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				shell.dispose();
			}
			
		});
		
		shell.pack();
		SWTUtils.setLocationToCenter(shell);
		shell.setImage(R.getIconImage());
	}

}
