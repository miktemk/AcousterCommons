package org.acouster.android;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;

public class ActivityUtils
{
	/** prefBundle/prefName is the flag in settings that gets set
	 * the first time intentPrefable is launched.
	 * Otherwise we launch intentOtherwise
	 * Use override to launch intentPrefable anyway */
	public static void startIntentIfNotVisitedByPref(
			Activity parent,
			Intent intentPrefable,
			Intent intentOtherwise,
			String prefBundle,
			String prefName,
			boolean override
			)
	{
		// http://stackoverflow.com/questions/9550039/how-to-make-a-do-not-ask-me-again-dialog-pop-up-box-android
		// Check whether the user has already accepted
		SharedPreferences settings = parent.getSharedPreferences(prefBundle, Activity.MODE_PRIVATE);
		boolean visited = settings.getBoolean(prefName, false);
		if( visited && !override )
		{
			//Intent intent = new Intent(parent, intentOtherwise);
			parent.startActivity(intentOtherwise);
		}
		else
		{
			// We need an Editor object to make preference changes.
			// All objects are from android.context.Context
			SharedPreferences.Editor editor = settings.edit();
			editor.putBoolean(prefName, true);
			// Commit the edits!
			editor.commit();
			
			//Intent intent = new Intent(parent, intentClass);
			parent.startActivity(intentPrefable);
		}
	}
}
