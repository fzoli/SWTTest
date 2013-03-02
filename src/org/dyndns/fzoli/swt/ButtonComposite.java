package org.dyndns.fzoli.swt;

import java.util.Arrays;
import java.util.List;
import org.dyndns.fzoli.util.UnremovableList;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class ButtonComposite extends Composite {

	private final int EXTRA_SIZE;
	
	private final List<Button> BTNS;
	
	private final List<GridData> DATAS;
	
	private final List<String> TEXTS;
	
	private final Listener LST_RESIZE = new Listener() {
		
		@Override
		public void handleEvent(Event event) {
			for (int i = 0; i < BTNS.size(); i++) {
				Button bt = BTNS.get(i);
				String oldText = TEXTS.get(i);
				String newText = bt.getText();
				if (oldText == null || !oldText.equals(newText)) {
					TEXTS.set(i, newText);
					resizeButtons();
					break;
				}
			}
		}
		
	};
	
	private final Listener LST_DEMAXIMIZE = new Listener() {
		
		@Override
		public void handleEvent(Event event) {
			if (resizeDelay && !getParent().getMaximized()) {
				resizeDelay = false;
				resizeShell();
			}
		}
		
	};
	
	private boolean resizeDelay = false;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ButtonComposite(Shell parent, int style, String leftText, String centerText, String rightText) {
		this(parent, style, 10, leftText, centerText, rightText);
	}
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ButtonComposite(Shell parent, int style, int extraSize, String leftText, String centerText, String rightText) {
		super(parent, style);
		EXTRA_SIZE = extraSize;
		
		GridLayout layout = new GridLayout(3, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		setLayout(layout);
		
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
		
		BTNS = new UnremovableList<Button>(Arrays.asList(btnLeft, btnCenter, btnRight));
		DATAS = Arrays.asList(gridDataLeft, gridDataCenter, gridDataRight);
		TEXTS = Arrays.asList(leftText, centerText, rightText);
		resizeButtons();
		
		btnLeft.addListener(SWT.Paint, LST_RESIZE);
		btnCenter.addListener(SWT.Paint, LST_RESIZE);
		btnRight.addListener(SWT.Paint, LST_RESIZE);
		
		getParent().addListener(SWT.Resize, LST_DEMAXIMIZE);
	}
	
	public List<? extends Button> getButtonList() {
		return BTNS;
	}
	
	@Override
	public Shell getParent() {
		return (Shell) super.getParent();
	}
	
	private void resizeButtons() {
		for (GridData data : DATAS) {
			data.widthHint = SWT.DEFAULT;
		}
		
		int width = 0;
		boolean[] hasTexts = new boolean[BTNS.size()];
		for (int i = 0; i < BTNS.size(); i++) {
			Button btn = BTNS.get(i);
			boolean hasText = !btn.getText().trim().isEmpty();
			btn.setVisible(hasText);
			hasTexts[i] = hasText;
			if (!hasText) continue;
			Point s = btn.computeSize(SWT.DEFAULT, SWT.DEFAULT);
			if (width < s.x) width = s.x;
		}
		
		for (int i = 0; i < BTNS.size(); i++) {
			DATAS.get(i).widthHint = hasTexts[i] ? width + EXTRA_SIZE : 0;
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
