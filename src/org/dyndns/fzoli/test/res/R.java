package org.dyndns.fzoli.test.res;

import org.eclipse.swt.graphics.Image;
import org.eclipse.wb.swt.SWTResourceManager;

public class R {

	public static Image getIconImage() {
		return SWTResourceManager.getImage(R.class, "icon.png");
	}
	
	public static void dispose() {
		SWTResourceManager.dispose();
	}
	
}
