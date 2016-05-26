package org.acouster.gravball;

import org.acouster.Game;
import org.acouster.GameContext;
import org.acouster.context.ContextBitmap;
import org.acouster.context.ImageManager;
import org.acouster.graphics.BitmapWithTransform;
import org.acouster.graphics.WorldLayer;
import org.acouster.graphics.ui.UIActionEngine;
import org.acouster.graphics.utils.FullScreenImage;

public class MainGame extends Game
{
	private static final int LAYER_LEVEL_BG = 1;
	private static final int LAYER_LEVEL_MAIN = 2;
	
	private WorldLayer layerBg;
	private WorldLayer layerMain;
	private UIActionEngine uiActions;
	private FullScreenImage bgImage;
	private MySprite mySprite;
	private MyVisual myVisual;
	private NewVisual newVisual;
	

	public MainGame(GameContext gameContext)
	{
		super(gameContext);
		
		// layers
		addLayer(layerBg = new WorldLayer(LAYER_LEVEL_BG));
		addLayer(layerMain = new WorldLayer(LAYER_LEVEL_MAIN));
		addActionEngine(uiActions = new UIActionEngine(this));
		
		// background
		layerBg.addRenderable(bgImage = new FullScreenImage(new BitmapWithTransform(ImageManager.instance().loadFromResource("bg.jpg"), false, false)));
		
		// eyes
		addSprite(mySprite = new MySprite("boob", 280, 200));
		layerMain.addRenderable(myVisual = new MyVisual(mySprite, ImageManager.instance().loadFromResource("boob.png")));
		layerMain.addRenderable(newVisual = new NewVisual());
		
	}
	
	
	
}
