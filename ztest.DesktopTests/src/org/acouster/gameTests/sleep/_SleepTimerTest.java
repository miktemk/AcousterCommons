package org.acouster.gameTests.sleep;

import org.acouster.GameContext;
import org.acouster.IFuncVoid;
import org.acouster.context.ContextBitmap;
import org.acouster.context.ImageManager;
import org.acouster.context.desktop.DesktopResourceContext;
import org.acouster.desktop.ui.EmulatorWindow;
import org.acouster.graphics.ui.UIButton;
import org.acouster.graphics.ui.UIElementBase;
import org.acouster.graphics.ui.UIImageButton;
import org.acouster.graphics.ui.UILayoutManagerCentered2ColumnLandscape;

public class _SleepTimerTest
{
	public static void main(String[] args) {
		new DesktopResourceContext().makeInstance();
		DesktopResourceContext.myInstance().setAssetPrefix("../zterminal.SleepTimer/assets");
		DesktopResourceContext.myInstance().setBitmapPrefix("../zterminal.SleepTimer/res/drawable-hdpi");
		DesktopResourceContext.myInstance().setExternalDir("C:\\Users\\mtemkine\\Desktop");
		//DesktopResourceContext.myInstance().setExternalDir("C:\\Users\\mtemkine.TFO\\Desktop");
		new EmulatorWindow() {
			@Override
			public void onInit() {
	    		setGame(new GameMain(this));
			}
		};
	}
}
