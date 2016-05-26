package org.acouster.antsStoned.desktop;

import org.acouster.DebugUtil;
import org.acouster.Game;
import org.acouster.antsStoned.LevelConfig;
import org.acouster.antsStoned.LevelDifficulty;
import org.acouster.antsStoned.MainGame;
import org.acouster.context.desktop.DesktopResourceContext;
import org.acouster.desktop.ui.EmulatorWindow;

public class _AntsDifficultyTest {
	
	public _AntsDifficultyTest()
	{
		super();
		
		LevelDifficulty diff = new LevelDifficulty();
		for (int i = 0; i < 30; i++)
		{
			//DebugUtil.sss("=========== game reset ==============");
			DebugUtil.sss(diff.toStringOneLine());
			diff.userWonBumpDifficultyUp();
		}
	}
	
	
	
	
	public static void main(String[] args) {
		new DesktopResourceContext().makeInstance();
		DesktopResourceContext.myInstance().setAssetPrefix("../zterminal.AntsStoned/assets");
		DesktopResourceContext.myInstance().setBitmapPrefix("../zterminal.AntsStoned/res/drawable-hdpi");
		new _AntsDifficultyTest();
	}

}
