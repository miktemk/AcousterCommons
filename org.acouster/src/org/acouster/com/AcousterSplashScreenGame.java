package org.acouster.com;

import org.acouster.Game;
import org.acouster.GameContext;
import org.acouster.IFuncVoid;
import org.acouster.IFuncVoidSolo;
import org.acouster.SimpleTicker;
import org.acouster.context.ContextMouseEvent;
import org.acouster.graphics.BitmapWithTransform;
import org.acouster.graphics.WorldLayer;
import org.acouster.graphics.anim.BubblePopVisual;
import org.acouster.graphics.anim.DubugRectRenderable;
import org.acouster.graphics.anim.FullScreenSolidColor;
import org.acouster.graphics.anim.ScreenCenteredImage;
import org.acouster.logic.HtmlPositioner;
import org.acouster.logic.Sprite2DHtmlPositioned;
import org.acouster.util.MathUtils;

public class AcousterSplashScreenGame extends Game
{
	private static final int LAYER_LEVEL_BG = 1;
	private static final int LAYER_LEVEL_1 = 2;
	private static final double POP_DELAY = 1.0;
	private static final int XMARGIN = 20;
	
	private WorldLayer layerBg, layer1;
	private FullScreenSolidColor bg;
	private SimpleTicker popTrigger;
	private BubblePopVisual bubbly;
	private Sprite2DHtmlPositioned positioner;
	private ScreenCenteredImage msgLoading;
	
	/** for tests and such */
	public AcousterSplashScreenGame(GameContext gameContext)
	{
		super(gameContext);
		
		// layers
		addLayer(layerBg = new WorldLayer(LAYER_LEVEL_BG));
		addLayer(layer1 = new WorldLayer(LAYER_LEVEL_1));
		
		layerBg.addRenderable(bg = new FullScreenSolidColor(0x00));
		
		layer1.addRenderable(bubbly = new BubblePopVisual_acouster1(addSprite(positioner = new Sprite2DHtmlPositioned(
				new HtmlPositioner(0, 0).centerX().centerY()))));
		layer1.addRenderable(msgLoading = new ScreenCenteredImage(new BitmapWithTransform(loadImage(Constants.Filenames.msg_loading), false, false)));
		msgLoading.setVisible(false);
		
		//layer1.addRenderable(new DubugRectRenderable(addSprite(new Sprite2DHtmlPositioned(
		//		new HtmlPositioner(0, 0).centerX().centerY())), 720, 480));
		
		popTrigger = new SimpleTicker(POP_DELAY, false, new IFuncVoidSolo() {
			@Override
			public void lambda() {
				bubbly.pop(BubblePopVisual.POP_ACTION_POP, BubblePopVisual.POP_MODE_RANDOM_UP, BubblePopVisual.RAIN_MODE_RANDOM_STEADY);
			}
		});
		bubbly.setFuncOnFinished(new IFuncVoid<BubblePopVisual>() {
			@Override
			public void lambda(BubblePopVisual value) {
				msgLoading.setVisible(true);
				sendEventToContext(MESSAGE_GO);
			}
		});
	}
	
	@Override
	public void incrementCharacters(int width, int height)
	{
		super.incrementCharacters(width, height);
		
		popTrigger.increment();
		if ((System.currentTimeMillis() - timestamp0) < 1000 * POP_DELAY)
		{
			int value = (int)(255 * (System.currentTimeMillis() - timestamp0) / (1000 * POP_DELAY));
			if (value > 255)
				value = 255;
			bg.setColor(MathUtils.getRgb(value, value, Math.min(2*value, 255)));
		}
		else
		{
			bg.setColor(0xFFFFFF);
		}
		
		if (isContextDimensionsChanged())
		{
			bubbly.setWidthKeepAspectRatio(Math.min(width - 2*XMARGIN, bubbly.getImageWidth()));
		}
	}
	
	// TODO: !!!
//	@Override
//	public void mouseReleased(ContextMouseEvent e)
//	{
//		super.mouseReleased(e);
//		timestamp0 = System.currentTimeMillis();
//		bubbly.reset();
//		popTrigger.reset();
//		msgLoading.setVisible(false);
//	}
}

