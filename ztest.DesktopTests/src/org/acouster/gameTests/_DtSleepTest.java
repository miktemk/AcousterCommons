
package org.acouster.gameTests;

import org.acouster.context.desktop.DesktopResourceContext;
import org.acouster.desktop.ui.EmulatorWindow;
import org.acouster.sleepTimer.GameMain;

public class _DtSleepTest
{
	public static void main(String[] args) {
		new DesktopResourceContext().makeInstance();
		DesktopResourceContext.myInstance().setAssetPrefix( "../zterminal.SleepTimer/assets");
		DesktopResourceContext.myInstance().setBitmapPrefix("../zterminal.SleepTimer/res/drawable-hdpi");
		DesktopResourceContext.myInstance().setExternalDir("C:\\Users\\mtemkine\\Desktop");
		new EmulatorWindow() {
			@Override
			public void onInit() {
				GameMain gggame = new GameMain(this, new String[] { "8:00", "9:00", "10:00" });
	    		setGame(gggame);
			}
		};
	}
}
