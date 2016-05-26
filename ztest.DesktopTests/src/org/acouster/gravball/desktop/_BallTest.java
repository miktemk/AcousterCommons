package org.acouster.gravball.desktop;

import org.acouster.Game;
import org.acouster.context.desktop.DesktopResourceContext;
import org.acouster.desktop.ui.EmulatorWindow;
import org.acouster.gravball.MainGame;

public class _BallTest extends EmulatorWindow {
	private static final long serialVersionUID = 1L;
	
	public _BallTest()
	{
		super();
		Game game = new MainGame(this);
		//Game game = new KataGame(this);
		//Game game = new MenuGame(this);
		setGame(game);
	}
	
	
	
	
	public static void main(String[] args) {
		new DesktopResourceContext().makeInstance();
		DesktopResourceContext.myInstance().setAssetPrefix("../zterminal.AntsStoned/assets");
		DesktopResourceContext.myInstance().setBitmapPrefix("../zterminal.AntsStoned/res/drawable-hdpi");
		new _BallTest();
	}

}
