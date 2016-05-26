package org.acouster.gravball;

import org.acouster.Game;
import org.acouster.GameContext;
import org.acouster.IFuncVoid;
import org.acouster.context.ContextBitmap;
import org.acouster.context.ContextGraphics;
import org.acouster.context.ImageManager;
import org.acouster.graphics.BitmapWithTransform;
import org.acouster.graphics.WorldLayer;
import org.acouster.graphics.ui.UIActionEngine;
import org.acouster.graphics.ui.UIButton;
import org.acouster.graphics.ui.UIImageButton;
import org.acouster.graphics.ui.UILayoutManagerCentered;
import org.acouster.graphics.utils.FullScreenImage;

public class MenuGame extends Game
{
	private static final int LAYER_LEVEL_BG = 1;
	private static final int LAYER_LEVEL_BUTTONS = 3;
	public static final String OPTION_GO = "goooo";
	public static final String OPTION_OPT = "opttt";
	
	protected WorldLayer layerBg, layerButtons;
	protected UIActionEngine uiActions;
	private UILayoutManagerCentered layout;
	private UIButton btnGo, btnOptions;
	private FullScreenImage bgImage;
	
	
	public MenuGame(GameContext gameContext)
	{
		super(gameContext);
		
		// layers
		addLayer(layerBg = new WorldLayer(LAYER_LEVEL_BG));
		addLayer(layerButtons = new WorldLayer(LAYER_LEVEL_BUTTONS));
		addActionEngine(uiActions = new UIActionEngine(this));
		
		layerBg.addRenderable(bgImage = new FullScreenImage(new BitmapWithTransform(ImageManager.instance().loadFromResource("bg.jpg"), false, false)));
		
		// buttons
		layerButtons.addRenderable(uiActions
				.addUI(btnGo = new UIImageButton(loadImage("btn_go"), loadImage("btn_go_down"))
				.setColor(0xFF0000)
				.setLambda(new IFuncVoid<UIButton>() {
					@Override
					public void lambda(UIButton value) {
						sendEventToContext(OPTION_GO);
					}
				}))
				.setFrame(10, 10, 100, 50));
		layerButtons.addRenderable(uiActions
				.addUI(btnOptions = new UIImageButton(loadImage("btn_opt"), loadImage("btn_opt_down"))
				.setColor(0xFF0000)
				.setLambda(new IFuncVoid<UIButton>() {
					@Override
					public void lambda(UIButton value) {
						sendEventToContext(OPTION_OPT);
					}
				}))
				.setFrame(10, 10, 100, 50));
		
		// layout
		layout = new UILayoutManagerCentered(0.15, 0.05, 0.15);
		layout.addUI(btnGo);
		layout.addUI(btnOptions);
	}
	
	@Override
	public void incrementCharacters(int width, int height) {
		super.incrementCharacters(width, height);
		if (isContextDimensionsChanged())
		{
			// realign the buttons
			layout.rearrange(width, height);
		}
	}
	
	@Override
	public void paintCharacters(ContextGraphics g, int width, int height) {
		super.paintCharacters(g, width, height);
	}
	
	//-------------- privates
	
	private ContextBitmap loadImage(String fname)
	{
		return ImageManager.instance().loadFromResource(fname + ".png");
	}

}