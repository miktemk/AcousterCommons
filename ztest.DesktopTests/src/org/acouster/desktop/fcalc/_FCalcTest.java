package org.acouster.desktop.fcalc;

import org.acouster.Game;
import org.acouster.GameEvent;
import org.acouster.context.desktop.DesktopResourceContext;
import org.acouster.desktop.ui.EmulatorWindow;
import org.acouster.fcalc.MenuGame;

public class _FCalcTest extends EmulatorWindow {
	private static final long serialVersionUID = 1L;
	
	public _FCalcTest()
	{
		super();
		Game game = new MenuGame(this);
		//Game game = new KataGame(this);
		//Game game = new MenuGame(this);
		setGame(game);
	}
	
	
	
	
	public static void main(String[] args) {
		new DesktopResourceContext().makeInstance();
		DesktopResourceContext.myInstance().setAssetPrefix("../zterminal.FCalc/assets");
		DesktopResourceContext.myInstance().setBitmapPrefix("../zterminal.FCalc/res/drawable-hdpi");
		new _FCalcTest();
	}

}
