package org.dyndns.fzoli.test;

import org.dyndns.fzoli.swt.SWTUtils;
import org.dyndns.fzoli.test.res.R;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Button;

public class VehicleChooserWindow {

	protected Shell shell;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			VehicleChooserWindow window = new VehicleChooserWindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display.setAppName("Mobile-RC");
		Display.setAppVersion("1.0");
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		R.dispose();
	}

	/**	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(300, 220);
		shell.setMinimumSize(300, 220);
		shell.setText("Járműválasztó");
		shell.setLayout(new GridLayout(1, false));
		shell.setImage(R.getIconImage());
		
		SWTUtils.setLocationToCenter(shell);
		
		GridData gridDataMessage = new GridData();
		gridDataMessage.horizontalAlignment = SWT.CENTER;
		
		Label lblMessage = new Label(shell, SWT.NONE);
		lblMessage.setText("Válasszon járművet a listából.");
		lblMessage.setLayoutData(gridDataMessage);
		
		GridData gridDataList = new GridData();
		gridDataList.horizontalAlignment = SWT.FILL;
		gridDataList.verticalAlignment = SWT.FILL;
		gridDataList.grabExcessHorizontalSpace = true;
		gridDataList.grabExcessVerticalSpace = true;
		
		List list = new List(shell, SWT.BORDER);
		list.setLayoutData(gridDataList);
		
		GridData gridDataSelect = new GridData();
		gridDataSelect.horizontalAlignment = SWT.CENTER;
		
		Button btnSelect = new Button(shell, SWT.NONE);
		btnSelect.setText("Kiválasztás");
		btnSelect.setEnabled(false);
		btnSelect.setLayoutData(gridDataSelect);
	}

}
