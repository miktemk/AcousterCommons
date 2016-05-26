package org.acouster.desktop.karate;

import org.acouster.Game;
import org.acouster.GameEvent;
import org.acouster.context.desktop.DesktopResourceContext;
import org.acouster.desktop.ui.EmulatorWindow;
import org.acouster.karate.KarateInstructionsGame;
import org.acouster.karate.builder.ScheduleBuilder;

public class _KarateTest extends EmulatorWindow {
	private static final long serialVersionUID = 1L;
	
	public _KarateTest()
	{
		super();
		Game game = new KarateInstructionsGame(this, true);
		//Game game = new KataGame(this);
		//Game game = new MenuGame(this);
		setGame(game);
		game.handleCustomMessage(new GameEvent("game", ScheduleBuilder.GRAPH_MESSAGE_GO));
	}
	
	
	
	
	public static void main(String[] args) {
		new DesktopResourceContext().makeInstance();
		DesktopResourceContext.myInstance().setAssetPrefix("../zterminal.karate/assets");
		DesktopResourceContext.myInstance().setBitmapPrefix("../_res/karate");
		new _KarateTest();
	}

}
