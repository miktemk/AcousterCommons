package org.acouster.proj;

import org.acouster.Game;
import org.acouster.GameContext;
import org.acouster.IFuncVoidSolo;
import org.acouster.SimpleTicker;
import org.acouster.context.ImageManager;
import org.acouster.graphics.BitmapWithTransform;
import org.acouster.graphics.WorldLayer;
import org.acouster.graphics.anim.FullScreenImage;
import org.acouster.graphics.anim.PrettyNumericVisual;
import org.acouster.graphics.ui.UIActionEngine;
import org.acouster.logic.Sprite2D;

public class MainGame extends Game
{
	private static final int LAYER_LEVEL_BG = 1;
	private static final int LAYER_LEVEL_MAIN = 2;
	
	private WorldLayer layerBg;
	private WorldLayer layerMain;
	private UIActionEngine uiActions;
	private FullScreenImage bgImage;
	private PrettyNumericVisual pointsVisual;
	private SimpleTicker simpleTicker;
	private int nPoints;
	

	public MainGame(GameContext gameContext)
	{
		super(gameContext);
		
		// layers
		addLayer(layerBg = new WorldLayer(LAYER_LEVEL_BG));
		addLayer(layerMain = new WorldLayer(LAYER_LEVEL_MAIN));
		addActionEngine(uiActions = new UIActionEngine(this));
		
		// background
		layerBg.addRenderable(bgImage = new FullScreenImage(new BitmapWithTransform(ImageManager.instance().loadFromResource("bg.jpg"), false, false)));
		
		// ticker
		layerMain.addRenderable(pointsVisual = new PrettyNumericVisual(new Sprite2D("", 300, 30), nPoints));
		simpleTicker = new SimpleTicker(0.2, true, new IFuncVoidSolo() {
			@Override
			public void lambda() {
				incPoints();
			}

		});
		
		resetPoints();
	}
	
	@Override
	public void incrementCharacters(int width, int height)
	{
		super.incrementCharacters(width, height);
		if (isContextDimensionsChanged())
		{
			// bump shit here... althought with the new API it is hardly necessary
		}
		simpleTicker.increment();
	}
	
	// --------- privates ------------
	private void resetPoints()
	{
		nPoints = 0;
		pointsVisual.setValue(nPoints);
	}
	private void incPoints()
	{
		nPoints++;
		pointsVisual.setValue(nPoints);
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
