package org.acouster.antsStoned.desktop;

import org.acouster.Game;
import org.acouster.antsStoned.LevelConfig;
import org.acouster.antsStoned.MenuGame;
import org.acouster.context.desktop.DesktopResourceContext;
import org.acouster.desktop.ui.EmulatorWindow;

public class _AntsTest extends EmulatorWindow {
	private static final long serialVersionUID = 1L;
	
	public _AntsTest()
	{
		super();
		
		OptionsStruct options = new OptionsStruct(
//		        LevelConfig.SKIN_ANTS_AND_STONES, // settings.getInt("skin", LevelConfig.SKIN_ANTS_AND_STONES),
//		        //LevelConfig.SKIN_MICE_AND_BUSHES, // settings.getInt("skin", LevelConfig.SKIN_ANTS_AND_STONES),
//                true,
//    			10, //settings.getBoolean("customDifficulty", true),
//    			5, //settings.getBoolean("gameEndsWhenFail", true),
//    			10 //settings.getInt("crawlSpeed", 5),
    	);
    	options.makeInstance();
		OptionsStruct.instance().UpdateLevelConfig();
		OptionsStruct.instance().levelConfig().setDifficultyLevel(LevelConfig.DIFFICULTY_EXPERT_DEBUG);
		OptionsStruct.instance().levelConfig().setDifficultyLevel(LevelConfig.DIFFICULTY_PRO);
		OptionsStruct.instance().levelConfig().setDifficultyLevel(LevelConfig.DIFFICULTY_EXPERT);
		LevelConfig config = OptionsStruct.instance().levelConfig();
		
		//DebugUtil.sss(config.toString());
		
		//Game game = new _Test_NumericVisual(this);
		//Game game = new AcousterSplashScreenGame(this);
		//Game game = new MainGame(this, config);
		Game game = new MenuGame(this);
		//Game game = new _Test_BubbleImages(this);
		setGame(game);
		
		//============ for debug/recording ====================
		//beginRecording();
		//setHandVisible(true);
		//setDebugVisible(false);
	}
	
	
	
	
	public static void main(String[] args) {
		new DesktopResourceContext().makeInstance();
		DesktopResourceContext.myInstance().setAssetPrefix("../zterminal.AntsStoned/assets");
		DesktopResourceContext.myInstance().setBitmapPrefix("../zterminal.AntsStoned/res/drawable-hdpi");
		new _AntsTest();
	}

}
