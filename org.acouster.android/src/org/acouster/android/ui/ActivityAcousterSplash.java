package org.acouster.android.ui;

import org.acouster.android.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
 
/**
 * Splash screen activity
 */
public class ActivityAcousterSplash extends Activity
{
	// used to know if the back button was pressed in the splash screen activity and avoid opening the next activity
	private boolean mIsBackButtonPressed;
	//private static final int SPLASH_DURATION = 2000; // 2 seconds
	
	protected Class<?> activityRoot;

	public ActivityAcousterSplash(Class<?> activityRoot)
	{
		this.activityRoot = activityRoot;
	}
 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
 
		setContentView(R.layout.splash_screen);
 
		Handler handler = new Handler();
 
		// run a thread after 2 seconds to start the home screen
		handler.postDelayed(new Runnable() {
 
			@Override
			public void run() {
 
				// make sure we close the splash screen so the user won't come back when it presses back key
 
				finish();
				 
				if (!mIsBackButtonPressed) {
					// start the home screen if the back button wasn't pressed already 
					Intent intent = new Intent(ActivityAcousterSplash.this, activityRoot);
					ActivityAcousterSplash.this.startActivity(intent);
			    }
				 
			}
 
		}, getResources().getInteger(R.integer.splashDurationMillis)); // time in milliseconds (1 second = 1000 milliseconds) until the run() method will be called
 
	}
	
	@Override
	public void onBackPressed() {
		// set the flag to true so the next activity won't start up
		mIsBackButtonPressed = true;
		super.onBackPressed();
	}
}

