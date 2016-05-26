package org.acouster.gameTests;

import org.acouster.Game;
import org.acouster.GameContext;
import org.acouster.context.ContextGraphics;
import org.acouster.context.ImageManager;
import org.acouster.context.desktop.DesktopResourceContext;
import org.acouster.desktop.ui.EmulatorWindow;
import org.acouster.graphics.BitmapWithTransform;
import org.acouster.graphics.WorldLayer;
import org.acouster.graphics.anim.FullScreenImage;
import org.acouster.graphics.ui.UIActionEngine;
import org.acouster.graphics.ui.UILayoutManagerGrid;

public class _PasteGameCodeHere extends Game
{
	private static final int LAYER_LEVEL_BG = 1;
	private static final int LAYER_LEVEL_BUTTONS = 3;
	public static final String OPTION_GO = "goooo";
	public static final String OPTION_OPT = "opttt";
	
	protected WorldLayer layerBg, layerButtons;
	protected UIActionEngine uiActions;
	private UILayoutManagerGrid layout;
	private FullScreenImage bgImage;
	
	
	public _PasteGameCodeHere(GameContext gameContext)
	{
		super(gameContext);
		
		// layers
		addLayer(layerBg = new WorldLayer(LAYER_LEVEL_BG));
		addLayer(layerButtons = new WorldLayer(LAYER_LEVEL_BUTTONS));
		addActionEngine(uiActions = new UIActionEngine(this));
		
		layerBg.addRenderable(bgImage = new FullScreenImage(new BitmapWithTransform(ImageManager.instance().loadFromResource("bg.jpg"), false, false)));

	}
	
	@Override
	public void incrementCharacters(int width, int height) {
		super.incrementCharacters(width, height);
	}
	
	@Override
	public void paintCharacters(ContextGraphics g, int width, int height) {
		super.paintCharacters(g, width, height);
	}
	
	
	
	
	// =================== copy up to here ========================
	
	
	// guitar main
	
	public static void main(String[] args) {
		new DesktopResourceContext().makeInstance();
		DesktopResourceContext.myInstance().setAssetPrefix("../zterminal.SplashCalc/assets");
		DesktopResourceContext.myInstance().setBitmapPrefix("../zterminal.SplashCalc/res/drawable-hdpi");
		DesktopResourceContext.myInstance().setExternalDir("C:\\Users\\miktemk\\Desktop");
		//DesktopResourceContext.myInstance().setExternalDir("C:\\Users\\mtemkine.TFO\\Desktop");
		new EmulatorWindow() {
			@Override
			public void onInit() {
	    		setGame(new _PasteGameCodeHere(this));
			}
		};
	}
	
}
