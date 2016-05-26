package org.acouster.eyes.desktop;

import org.acouster.Game;
import org.acouster.GameContext;
import org.acouster.IFuncVoid;
import org.acouster.IFuncVoidSolo;
import org.acouster.SimpleTicker;
import org.acouster.context.ContextMouseEvent;
import org.acouster.context.desktop.DesktopResourceContext;
import org.acouster.desktop.ui.EmulatorWindow;
import org.acouster.eyefitness.bubbles.BubblePopVisual_btnGo;
import org.acouster.graphics.WorldLayer;
import org.acouster.graphics.anim.BubblePopVisual;
import org.acouster.graphics.anim.FullScreenSolidColor;
import org.acouster.logic.HtmlPositioner;
import org.acouster.logic.Sprite2DHtmlPositioned;

public class _Test_BubbleImages extends Game
{
	private static final int LAYER_LEVEL_BG = 1;
	private static final int LAYER_LEVEL_1 = 2;
	private static final double POP_DELAY = 0.2;
	private static final int XMARGIN = 20;
	
	private WorldLayer layerBg, layer1;
	private FullScreenSolidColor bg;
	private SimpleTicker popTrigger;
	private BubblePopVisual bubbly;
	private Sprite2DHtmlPositioned positioner;
	
	/** for tests and such */
	public _Test_BubbleImages(GameContext gameContext)
	{
		super(gameContext);
		
		// layers
		addLayer(layerBg = new WorldLayer(LAYER_LEVEL_BG));
		addLayer(layer1 = new WorldLayer(LAYER_LEVEL_1));
		
		layerBg.addRenderable(bg = new FullScreenSolidColor(0xFFFFFF));
		
		layer1.addRenderable(bubbly = new BubblePopVisual_btnGo(positioner = new Sprite2DHtmlPositioned(
				new HtmlPositioner(0, 0).centerX().centerY())));
		addSprite(positioner);
		
		popTrigger = new SimpleTicker(POP_DELAY, false, new IFuncVoidSolo() {
			@Override
			public void lambda() {
				bubbly.pop(BubblePopVisual.POP_ACTION_FILL, BubblePopVisual.POP_MODE_RANDOM_UP, BubblePopVisual.RAIN_MODE_RANDOM_STEADY);
			}
		});
		bubbly.setFuncOnFinished(new IFuncVoid<BubblePopVisual>() {
			@Override
			public void lambda(BubblePopVisual value) {
				sendEventToContext("GO");
			}
		});
	}
	
	@Override
	public void incrementCharacters(int width, int height)
	{
		super.incrementCharacters(width, height);
		popTrigger.increment();
		if (isContextDimensionsChanged())
		{
			bubbly.setWidthKeepAspectRatio(Math.min(width - 2*XMARGIN, bubbly.getImageWidth()));
		}
	}
	
	@Override
	public void mouseReleased(ContextMouseEvent e)
	{
		super.mouseReleased(e);
		timestamp0 = System.currentTimeMillis();
		bubbly.reset();
		popTrigger.reset();
	}
	
	

	
	
	
	
	
	// =================== copy up to here ========================
	
	public static void main(String[] args) {
		new DesktopResourceContext().makeInstance();
		DesktopResourceContext.myInstance().setAssetPrefix("../zterminal.EyeFitness/assets");
		DesktopResourceContext.myInstance().setBitmapPrefix("../zterminal.EyeFitness/res/drawable-hdpi");
		//DesktopResourceContext.myInstance().setExternalDir("C:\\Users\\miktemk\\Desktop");
		//DesktopResourceContext.myInstance().setExternalDir("C:\\Users\\mtemkine.TFO\\Desktop");
		new EmulatorWindow() {
			@Override
			public void onInit() {
				setGame(new _Test_BubbleImages(this));
				// ============ for debug/recording =================
				//beginRecording();
				//setHandVisible(true);
				//setDebugVisible(false);
			}
		};
	}
}

