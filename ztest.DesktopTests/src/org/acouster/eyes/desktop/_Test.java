package org.acouster.eyes.desktop;

import org.acouster.Game;
import org.acouster.context.desktop.DesktopResourceContext;
import org.acouster.desktop.ui.EmulatorWindow;
import org.acouster.eyefitness.MenuGame;

public class _Test extends EmulatorWindow {
	private static final long serialVersionUID = 1L;
	
	public _Test()
	{
		super();
		
		//DebugUtil.sss(config.toString());
		
		//Game game = new _Test_NumericVisual(this);
		//Game game = new AcousterSplashScreenGame(this);
		//EyeFitnessGame game = new EyeFitnessGame(this);
		//game.constructExerciseGraph(5);
		Game game = new MenuGame(this);
		//Game game = new _Test_BubbleImages(this);
		
		
		setGame(game);
		
		// ============ for debug/recording =================
		//beginRecording();
		//setHandVisible(true);
		//setDebugVisible(false);
	}
	
	public static void main(String[] args) {
		new DesktopResourceContext().makeInstance();
		DesktopResourceContext.myInstance().setAssetPrefix("../zterminal.EyeFitness/assets");
		DesktopResourceContext.myInstance().setBitmapPrefix("../zterminal.EyeFitness/res/drawable-hdpi");
		new _Test();
	}

}
