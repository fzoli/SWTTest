package org.dyndns.fzoli.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

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
	
	public static boolean isRemove(Event event) {
		return SWT.DEL == event.character || SWT.BS == event.character;
	}
	
	public static String getText(Event event) {
		return getText(event, true, true);
	}
	
	public static String getText(Event event, boolean subBefore, boolean subAfter) {
		String text = ((Text) event.widget).getText();
		if (isRemove(event)) {
			text = (subBefore ? text.substring(0, event.start) : "") + (subAfter ? text.substring(event.start + 1) : "");
		}
		else if (event.character != '\0' && event.character != '\r' && event.character != '\t') {
			text = (subBefore ? text.substring(0, event.start) : "") + event.character + (subAfter ? text.substring(event.start) : "");
		}
		return text;
	}
	
}
