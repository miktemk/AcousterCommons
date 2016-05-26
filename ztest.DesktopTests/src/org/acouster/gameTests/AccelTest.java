package org.acouster.gameTests;

import org.acouster.Game;
import org.acouster.GameContext;
import org.acouster.context.ContextGraphics;
import org.acouster.context.desktop.DesktopResourceContext;
import org.acouster.desktop.ui.EmulatorWindow;
import org.acouster.graphics.BasicGraphics;
import org.acouster.graphics.Colorz;

public class AccelTest extends Game
{
	private float accelX, accelY;


	public AccelTest(GameContext gameContext)
	{
		super(gameContext);
		
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
		
		BasicGraphics.paintBackground(g, width, height, Colorz.WHITE);
		g.setColor(Colorz.BLACK);
		g.drawLine(300, 200, 300 + accelX, 200 + accelY);
	}

	//---------- Andriod interactiopn -------------
	
	public void onAccelerometerChange(float mSensorX, float mSensorY) {
		accelX = mSensorX;
		accelY = mSensorY;
		System.out.println("accel changed: " + mSensorX + " : " + mSensorY);
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
	    		setGame(new AccelTest(this));
	    		setGravityEnabled(true);
			}
		};
	}
}
