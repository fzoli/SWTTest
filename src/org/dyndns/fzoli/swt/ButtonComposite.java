package org.dyndns.fzoli.swt;
import java.util.Arrays;
import java.util.List;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;


public class ButtonComposite extends Composite {

	private List<Button> btns;
	
	private List<GridData> datas;
	
	private List<String> texts;
	
	private Listener resizeListener = new Listener() {
		
		@Override
		public void handleEvent(Event event) {
			Button bt = (Button) event.widget;
			int index = btns.indexOf(bt);
			String newText = bt.getText();
			String oldText = texts.get(index);
			texts.set(index, newText);
			if (oldText != null && !oldText.equals(newText)) {
				resizeButtons();
			}
		}
		
	};
	
	private Listener unmaximizeListener = new Listener() {
		
		@Override
		public void handleEvent(Event event) {
			if (resizeDelay && !getParent().getMaximized()) {
				resizeDelay = false;
				resizeShell();
			}
		}
		
	};
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ButtonComposite(Shell parent, int style, String leftText, String centerText, String rightText) {
		super(parent, style);
		setLayout(new GridLayout(3, false));
		
		GridData gridDataLeft = new GridData();
		gridDataLeft.grabExcessHorizontalSpace = true;
		gridDataLeft.grabExcessVerticalSpace = true;
		
		Button btnLeft = new Button(this, SWT.NONE);
		btnLeft.setLayoutData(gridDataLeft);
		btnLeft.setText(leftText == null ? "" : leftText);
		
		GridData gridDataCenter = new GridData();
		gridDataLeft.grabExcessVerticalSpace = true;
		
		Button btnCenter = new Button(this, SWT.NONE);
		btnCenter.setLayoutData(gridDataCenter);
		btnCenter.setText(centerText == null ? "" : centerText);
		
		GridData gridDataRight = new GridData();
		gridDataLeft.grabExcessVerticalSpace = true;
		
		Button btnRight = new Button(this, SWT.NONE);
		btnRight.setLayoutData(gridDataRight);
		btnRight.setText(rightText == null ? "" : rightText);

		btns = Arrays.asList(btnLeft, btnCenter, btnRight);
		datas = Arrays.asList(gridDataLeft, gridDataCenter, gridDataRight);
		texts = Arrays.asList(leftText, centerText, rightText);
		resizeButtons();
		
		btnLeft.addListener(SWT.Paint, resizeListener);
		btnCenter.addListener(SWT.Paint, resizeListener);
		btnRight.addListener(SWT.Paint, resizeListener);
		
		getParent().addListener(SWT.Resize, unmaximizeListener);
	}
	
	public List<Button> getButtons() {
		return btns;
	}
	
	@Override
	public Shell getParent() {
		return (Shell) super.getParent();
	}
	
	private boolean resizeDelay = false;
	
	private void resizeButtons() {
		for (GridData data : datas) {
			data.widthHint = SWT.DEFAULT;
		}
		
		int width = 0;
		boolean[] hasTexts = new boolean[btns.size()];
		for (int i = 0; i < btns.size(); i++) {
			Button btn = btns.get(i);
			boolean hasText = !btn.getText().trim().isEmpty();
			btn.setVisible(hasText);
			hasTexts[i] = hasText;
			if (!hasText) continue;
			Point s = btn.computeSize(SWT.DEFAULT, SWT.DEFAULT);
			if (width < s.x) width = s.x;
		}
		
		for (int i = 0; i < btns.size(); i++) {
			datas.get(i).widthHint = hasTexts[i] ? width : 0;
		}
		
		layout();
		
		if (!getParent().getMaximized()) {
			resizeShell();
		}
		else {
			resizeDelay = true;
		}
	}

	private void resizeShell() {
		Shell shell = getParent();
		Point oldSize = shell.getSize();
		Point newSize = shell.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		if (oldSize.x < newSize.x) shell.setSize(newSize.x, oldSize.y);

		Point location = shell.getLocation();
		Point oldCenter = SWTUtils.getCenterPoint(shell, oldSize.x, oldSize.y);
		if (Math.abs(location.x - oldCenter.x) < 5 && Math.abs(location.y - oldCenter.y) < 5) {
			SWTUtils.setLocationToCenter(shell);
		}
		
		Point minSize = shell.getMinimumSize();
		shell.setMinimumSize(Math.max(newSize.x, minSize.x), Math.max(newSize.y, minSize.y));
	}
	
}
