package org.dyndns.fzoli.test;

import org.dyndns.fzoli.swt.ButtonComposite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TabItem;

public class SettingsWindow {

	protected Shell shell;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			SettingsWindow window = new SettingsWindow();
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
		shell.setSize(450, 300);
		shell.setText("SWT Application");
		shell.setLayout(new GridLayout(1, false));
		
		GridData gdTab = new GridData();
		gdTab.horizontalAlignment = SWT.FILL;
		gdTab.verticalAlignment = SWT.FILL;
		gdTab.grabExcessHorizontalSpace = true;
		gdTab.grabExcessVerticalSpace = true;
		
		TabFolder tabFolder = new TabFolder(shell, SWT.NONE);
		tabFolder.setLayoutData(gdTab);
		
		TabItem tbtm1 = new TabItem(tabFolder, SWT.NONE);
		tbtm1.setText("Item 1");
		
		TabItem tbtm2 = new TabItem(tabFolder, SWT.NONE);
		tbtm2.setText("Item 2");
		
		GridData gdButtons = new GridData();
		gdButtons.horizontalAlignment = SWT.FILL;
		gdTab.grabExcessHorizontalSpace = true;
		
		ButtonComposite cmpButtons = new ButtonComposite(shell, SWT.NONE, "Help", "Cancel", "OK");
		cmpButtons.setLayoutData(gdButtons);
		
	}

}
