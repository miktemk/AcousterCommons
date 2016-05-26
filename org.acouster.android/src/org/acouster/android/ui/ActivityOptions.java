package org.acouster.android.ui;

import org.acouster.android.R;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.Window;
import android.view.WindowManager;

public class ActivityOptions extends PreferenceActivity
{
	protected int preferenceXml;
	
	public ActivityOptions() {
		this(R.xml.preferences);
	}
	public ActivityOptions(int preferenceXml)
	{
		this.preferenceXml = preferenceXml;
	}
	
	@Override
    protected void onCreate(Bundle savedInstanceState)
	{
		onTimeForRequestingFeatures();
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(preferenceXml);
    }
	
	/** Override this to request any features such as Fullscreen.
	 * Note: fullscreen is enabled by default. To disable write your own ActivityOptions... muhaha
	 */
    public void onTimeForRequestingFeatures() {
    	// Set full screen view
 		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
 		requestWindowFeature(Window.FEATURE_NO_TITLE);
    }
    
}
