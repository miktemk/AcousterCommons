package org.acouster.gameTests.glasses;

import org.acouster.ActionEngineAdapter;
import org.acouster.DebugUtil;
import org.acouster.Game;
import org.acouster.GameContext;
import org.acouster.context.ContextGraphics;
import org.acouster.context.ContextMouseEvent;
import org.acouster.graphics.Colorz;
import org.acouster.graphics.WorldLayer;
import org.acouster.graphics.anim.FullScreenImage;
import org.acouster.graphics.anim.FullScreenSolidColor;
import org.acouster.graphics.anim.ImageRenderable;
import org.acouster.graphics.ui.UIActionEngine;
import org.acouster.res.ImageSequence;
import org.acouster.util.GameUtils;


public class MainGame extends Game implements ITorchWorld
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
	
	public MainGame(GameContext gameContext) {
		this(gameContext, new GameConfig());
	}
	public MainGame(GameContext gameContext, GameConfig gameConfig)
	{
		super(gameContext);
		
		// layers
		addLayer(layerBg = new WorldLayer(LAYER_LEVEL_BG));
		addLayer(layerStuff = new WorldLayer(LAYER_LEVEL_BUTTONS));

		//addActionEngine(uiActions = new UIActionEngine(this));
//		addActionEngine(new ActionEngineAdapter(this) {
//			@Override
//			public void mouseReleased(ContextMouseEvent arg0) {
//				playSound(Constants.Sounds.WAHAHA);
//			}
//		});
		
		
		
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
		
		playSound(Constants.Sounds.WAHAHA);
		
		// TODO: remove when offscreen
	}
	
	//---------- Andriod interactiopn -------------
	
	
	public void onAccelerometerChange(float mSensorX, float mSensorY) {
		DebugUtil.sss("grav: " + mSensorX + " : " + mSensorY);
		curTorch.setAcceleration(mSensorX);
	}

	@Override
	public void playSound(String eeek) {
		if (doSounds)
			sendEventToContext(DIRECTIVE_SOUND, eeek);
	}
	
	
	
	
	
	// --------- other shit ------------
	
	// ants example
//	@Override
//	public String getBundleValues()
//	{
//		BundleStruct struct = new BundleStruct();
//		struct.setDifficultyStructValues(logic.getDifficultyStruct());
//		struct.setSkinValues(skin);
//		return JsonSerialiser.serialize(struct);
//	}
//	@Override
//	public void restoreBundleValues(String json)
//	{
//		BundleStruct struct = JsonSerialiser.deserialize(BundleStruct.class, json);
//		//DebugUtil.sss("new misc data set: " + struct);
//		logic.getDifficultyStruct().setBundleData(struct);
//		GameSkin newSkin = SkinFactory.getSkinByConfigId(struct.skinId);
//		// sanity check... please fix the parser!!!
//		if (!StringUtils.isNullOrEmpty(struct.skinBgFilename))
//			newSkin.setBundleValues(struct.skinBgFilename);
//		setSkin(newSkin);
//		reset();
//	}
}
