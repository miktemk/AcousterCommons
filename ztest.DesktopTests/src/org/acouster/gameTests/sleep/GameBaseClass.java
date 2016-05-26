package org.acouster.gameTests.sleep;

import org.acouster.Game;
import org.acouster.GameContext;
import org.acouster.graphics.Colorz;
import org.acouster.graphics.WorldLayer;
import org.acouster.graphics.anim.FullScreenImage;
import org.acouster.graphics.anim.FullScreenSolidColor;
import org.acouster.graphics.anim.FullScreenSolidColorAnimated;
import org.acouster.graphics.ui.UIActionEngine;
import org.acouster.util.MathUtils;

public class GameBaseClass extends Game
{
	protected static final int LAYER_LEVEL_BG = 1;
	protected static final int LAYER_LEVEL_MAIN = 2;
	
	protected WorldLayer layerBg;
	protected WorldLayer layerButtons;
	protected UIActionEngine uiActions;

	public GameBaseClass(GameContext gameContext)
	{
		super(gameContext);
		
		// layers
		addLayer(layerBg = new WorldLayer(LAYER_LEVEL_BG));
		addLayer(layerButtons = new WorldLayer(LAYER_LEVEL_MAIN));
		addActionEngine(uiActions = new UIActionEngine(this));
		
		// background
//		layerBg.addRenderable(new FullScreenImage(new BitmapWithTransform(ImageManager.instance().loadFromResource("bg.jpg"), false, false)));
//		layerBg.addRenderable(new FullScreenSolidColor(Colorz.WHITE));
		layerBg.addRenderable(new FullScreenSolidColorAnimated(5, new int[] {
				Colorz.WHITE,
				MathUtils.getRgb(255, 200, 200),
				MathUtils.getRgb(200, 180, 255),
				MathUtils.getRgb(150, 180, 255),
				MathUtils.getRgb(180, 150, 255),
				MathUtils.getRgb(180, 180, 255),
				MathUtils.getRgb(200, 180, 255),
				MathUtils.getRgb(255, 220, 200),
		}));
	}
}
