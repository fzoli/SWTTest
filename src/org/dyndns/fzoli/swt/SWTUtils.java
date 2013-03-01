package org.dyndns.fzoli.swt;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class SWTUtils {

	public static void setLocationToCenter(Shell shell) {
		Point p = getCenterPoint(shell);
		shell.setLocation(p.x, p.y);
	}
	
	public static Point getCenterPoint(Shell shell) {
		Rectangle rectShell = shell.getBounds();
		return getCenterPoint(shell, rectShell.width, rectShell.height);
	}
	
	public static Point getCenterPoint(Shell shell, int width, int height) {
		Rectangle rectParent = shell.getParent() == null ? Display.getCurrent().getPrimaryMonitor().getBounds() : shell.getParent().getBounds();
		int x = rectParent.x + (rectParent.width - width) / 2;
		int y = rectParent.y + (rectParent.height - height) / 2;
		return new Point(x,y);
	}
	
}
