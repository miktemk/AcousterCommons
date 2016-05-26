package org.acouster.gameTests.other;

import org.acouster.Game;
import org.acouster.GameContext;
import org.acouster.GameEvent;
import org.acouster.IFuncVoidSolo;
import org.acouster.SimpleTicker;
import org.acouster.context.ContextBitmap;
import org.acouster.context.ContextMouseEvent;
import org.acouster.context.desktop.DesktopResourceContext;
import org.acouster.desktop.ui.EmulatorWindow;
import org.acouster.graphics.WorldLayer;
import org.acouster.graphics.anim.FullScreenSolidColor;
import org.acouster.graphics.anim.ParticleEffectSmoke;
import org.acouster.graphics.anim.PrettyNumericVisual;
import org.acouster.logic.Sprite2D;

public class _Test_NumericVisual extends Game
{
	private static final int LAYER_LEVEL_BG = 1;
	private static final int LAYER_LEVEL_1 = 2;
	
	private WorldLayer layerBg, layer1;
	private SimpleTicker simpleTicker;
	private PrettyNumericVisual pointsVisual;
	private int nPoints = 0;
	
	// shared resources
	private ContextBitmap[] imgSmoke;

	/** for tests and such */
	public _Test_NumericVisual(GameContext gameContext)
	{
		super(gameContext);
		
		// layers
		addLayer(layerBg = new WorldLayer(LAYER_LEVEL_BG));
		addLayer(layer1 = new WorldLayer(LAYER_LEVEL_1));
		
		layerBg.addRenderable(new FullScreenSolidColor(0xFFFFFF));
		
		layer1.addRenderable(pointsVisual = new PrettyNumericVisual(new Sprite2D("", 300, 30), nPoints));
		simpleTicker = new SimpleTicker(0.2, true, new IFuncVoidSolo() {
			@Override
			public void lambda() {
				incPoints();
			}
		});
		
		resetPoints();
		
//		imgSmoke = new ContextBitmap[] {
//			ImageManager.instance().loadFromResource(Constants.Filenames.smoke1),
//			ImageManager.instance().loadFromResource(Constants.Filenames.smoke2),
//		};
	}
	
	@Override
	public void handleCustomMessage(GameEvent message)
	{
		
	}
	
//	@Override
//	public void paintCharacters(ContextGraphics g, int width, int height)
//	{
//		super.paintCharacters(g, width, height);
//		
//		// DEBUG: rotation test
////		g.setColor(0x000000);
////		BasicGraphics.drawCrosshair(g, 200, 100);
////		g.setColor(0xFF0000);
////		BasicGraphics.drawCrosshair(g, mouseCurX, mouseCurY);
////		g.drawImage(imgTest, (int)200, (int)100, imgTest.getWidth(), imgTest.getHeight(),
////				false, false, angleTest, mouseCurX, mouseCurY);
////		angleTest += 0.05;
//	}
	
	@Override
	public void incrementCharacters(int width, int height)
	{
		super.incrementCharacters(width, height);
		if (isContextDimensionsChanged())
		{
			
		}
		simpleTicker.increment();
	}

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
	
	@Override
	public void mouseReleased(ContextMouseEvent e)
	{
		super.mouseClicked(e);
		//DebugUtil.sss("click");
		//layer1.addRenderable(new ParticleEffect(e.getX(), e.getY()));
		
		//ParticleEffectSmoke ppp = new ParticleEffectSmoke(imgSmoke, e.getX(), e.getY(), ParticleEffectSmoke.USE_IMAGE_DIMENSIONS, ParticleEffectSmoke.USE_IMAGE_DIMENSIONS);
		//ppp.init();
		//layer1.addRenderable(ppp);
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
				setGame(new _Test_NumericVisual(this));
				// ============ for debug/recording =================
				//beginRecording();
				//setHandVisible(true);
				//setDebugVisible(false);
			}
		};
	}
}
