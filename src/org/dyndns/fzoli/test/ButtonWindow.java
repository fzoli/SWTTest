package org.dyndns.fzoli.test;

import java.util.List;
import org.dyndns.fzoli.swt.ButtonComposite;
import org.dyndns.fzoli.swt.SWTUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class ButtonWindow {

	protected Shell shell;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ButtonWindow window = new ButtonWindow();
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
		display.dispose();
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setText("Panel test");
		shell.setLayout(new FillLayout(SWT.VERTICAL | SWT.HORIZONTAL));
		final List<? extends Button> btns = new ButtonComposite(shell, SWT.NONE, "Li-Ion", "NiZn", "NiMh").getButtonList();
		shell.pack();
		SWTUtils.setLocationToCenter(shell);
		btns.get(0).addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				Button bt = btns.get(1);
				bt.setText(bt.getText().equals("NiZn") ? "Nickel Zinc" : "NiZn");
			}
			
		});
		Shell shell2 = new Shell(SWT.SHELL_TRIM & ~SWT.RESIZE);
		shell2.setLayout(new FillLayout());
		shell2.setText("Test dialog");
		Button bt = new Button(shell2, SWT.NONE);
		bt.setText("Click");
		bt.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				Button bt = btns.get(0);
				bt.setText(bt.getText().equals("Li-Ion") ? "Lithium-ion" : "Li-Ion");
			}
			
		});
		shell2.layout();
		shell2.pack();
		shell2.setLocation(shell.getLocation().x, shell.getLocation().y + shell.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		shell2.open();
	}

}
