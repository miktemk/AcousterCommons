package org.acouster.gameTests;

import org.acouster.Game;
import org.acouster.GameContext;
import org.acouster.context.ContextGraphics;
import org.acouster.context.desktop.DesktopResourceContext;
import org.acouster.desktop.ui.EmulatorWindow;
import org.acouster.gameTests.glasses.AbstractTorchRenderable;
import org.acouster.gameTests.glasses.DevilSprite;
import org.acouster.gameTests.glasses.GameConfig;
import org.acouster.gameTests.glasses.ITorchWorld;
import org.acouster.gameTests.glasses.TorchRenderable;
import org.acouster.graphics.Colorz;
import org.acouster.graphics.WorldLayer;
import org.acouster.graphics.anim.FullScreenImage;
import org.acouster.graphics.anim.FullScreenSolidColor;
import org.acouster.graphics.anim.ImageRenderable;
import org.acouster.graphics.ui.UIActionEngine;
import org.acouster.logic.Sprite2D;
import org.acouster.res.ImageSequence;
import org.acouster.util.GameUtils;
import org.acouster.util.MathUtils;

public class CaveTorchTest extends Game implements ITorchWorld
{
	private static final int LAYER_LEVEL_BG = 1;
	private static final int LAYER_LEVEL_BUTTONS = 3;
	public static final String OPTION_GO = "goooo";
	public static final String OPTION_OPT = "opttt";
	
	protected WorldLayer layerBg, layerStuff;
	protected UIActionEngine uiActions;
	private FullScreenImage bgImage;
	private AbstractTorchRenderable curTorch;
	private boolean doSounds;
	
	public CaveTorchTest(GameContext gameContext) {
		this(gameContext, new GameConfig());
	}
	public CaveTorchTest(GameContext gameContext, GameConfig gameConfig)
	{
		super(gameContext);
		
		// layers
		addLayer(layerBg = new WorldLayer(LAYER_LEVEL_BG));
		addLayer(layerStuff = new WorldLayer(LAYER_LEVEL_BUTTONS));
		addActionEngine(uiActions = new UIActionEngine(this));
		
//		layerBg.addRenderable(bgImage = new FullScreenImage(new BitmapWithTransform(ImageManager.instance().loadFromResource("bg.jpg"), false, false)));
		layerBg.addRenderable(new FullScreenSolidColor(Colorz.BLACK));
		
		layerStuff.addRenderable(curTorch = new TorchRenderable(
				addSprite(GameUtils.makeCenteredSprite()), this));
		
		doSounds = gameConfig.doSounds;
	}
	
	@Override
	public void incrementCharacters(int width, int height) {
		super.incrementCharacters(width, height);
		if (isContextDimensionsChanged())
		{
			// realign the buttons
		}
	}
	
	@Override
	public void paintCharacters(ContextGraphics g, int width, int height) {
		super.paintCharacters(g, width, height);
	}

	//---------- ITorchWorld --------------------
	@Override
	public void addLaughingDevil(double incX, double incY) {
		//System.out.println("TODO: add Laughing devil");
		// TODO: direction is not random... it depends on the force of the shake
		DevilSprite sprite = new DevilSprite(
				gameContext.getContextWidth()/2,
				gameContext.getContextHeight()/2,
				incX,
				incY);
		
		addSprite(sprite);
		ImageRenderable devilRendrable = new ImageRenderable(sprite,
				new ImageSequence("devil_100px_{num}.png", 3, 0.1));
		layerStuff.addRenderable(devilRendrable);
		// TODO: remove when offscreen
	}
	
	//---------- Andriod interactiopn -------------
	
	
	public void onAccelerometerChange(float mSensorX, float mSensorY) {
		curTorch.setAcceleration(mSensorX);
	}

	@Override
	public void playSound(String eeek) {
		if (doSounds)
			sendEventToContext(DIRECTIVE_SOUND, eeek);
	}
	
	
	
	
	
	// =================== copy up to here ========================
	
	
	// guitar main
	
	public static void main(String[] args) {
		new DesktopResourceContext().makeInstance();
		DesktopResourceContext.myInstance().setAssetPrefix("../zterminal.CaveTorch/assets");
		DesktopResourceContext.myInstance().setBitmapPrefix("../zterminal.CaveTorch/res/drawable-hdpi");
		DesktopResourceContext.myInstance().setExternalDir("C:\\Users\\mtemkine\\Desktop");
		//DesktopResourceContext.myInstance().setExternalDir("C:\\Users\\mtemkine.TFO\\Desktop");
		new EmulatorWindow() {
			@Override
			public void onInit() {
	    		setGame(new CaveTorchTest(this));
			}
		};
	}
}
