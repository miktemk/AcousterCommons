package org.acouster.desktop.EyeFitness;

import org.acouster.Game;
import org.acouster.GameEvent;
import org.acouster.android.eyefitness.EyeFitnessGame;
import org.acouster.context.desktop.DesktopResourceContext;
import org.acouster.desktop.karate._KarateTest;
import org.acouster.desktop.ui.EmulatorWindow;
import org.acouster.karate.builder.ScheduleBuilder;

public class EyeFitnessTest extends EmulatorWindow
{
	private static final long serialVersionUID = 1L;
	
	public EyeFitnessTest()
	{
		super();
		Game game = new EyeFitnessGame(this);
		//Game game = new KataGame(this);
		//Game game = new MenuGame(this);
		setGame(game);
		//game.handleCustomMessage(new GameEvent("game", ScheduleBuilder.GRAPH_MESSAGE_GO));
	}
	
	
	
	
	public static void main(String[] args) {
		new DesktopResourceContext().makeInstance();
		DesktopResourceContext.myInstance().setAssetPrefix("../zterminal.EyeFitness/assets");
		DesktopResourceContext.myInstance().setBitmapPrefix("../zterminal.EyeFitness/res/drawable-hdpi");
		new EyeFitnessTest();
	}

}