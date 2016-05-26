package org.acouster.android.ui;

import org.acouster.Game;
import org.acouster.GameEvent;
import org.acouster.com.AcousterSplashScreenGame;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
 
/**
 * Splash screen activity
 */
public class ActivityAcousterSplashAnim extends GameActivity
{
	// used to know if the back button was pressed in the splash screen activity and avoid opening the next activity
	private boolean mIsBackButtonPressed;
	//private static final int SPLASH_DURATION = 2000; // 2 seconds
	
	protected Class<?> activityRoot;

	public ActivityAcousterSplashAnim(Class<?> activityRoot)
	{
		this.activityRoot = activityRoot;
	}
 
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setThreadActivityControlFreakEnabled(false);
		setGame(game = new AcousterSplashScreenGame(this));
	}
	
	@Override
	public void handleMessage(GameEvent message) {
		super.handleMessage(message);
		if (Game.MESSAGE_GO.equals(message.getBody()))
		{
			//finish();
			if (!mIsBackButtonPressed)
			{
				// start the home screen if the back button wasn't pressed already
				this.finish();
				Intent iii = new Intent(this, activityRoot);
				startActivity(iii);
//				runOnUiThread(new Runnable() {
//					public void run() {
//						do android ui shit!!!!!!!!!!!!!!!
//					}
//				});
			}
		}
	}
	
	@Override
	public void onBackPressed() {
		// set the flag to true so the next activity won't start up
		mIsBackButtonPressed = true;
		super.onBackPressed();
	}
}

