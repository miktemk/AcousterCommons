package org.acouster.android;

import org.acouster.IFuncVoidSolo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.Html;
import android.text.Html.ImageGetter;

public class DialogUtils
{
	public static final String PREFERENCES_HIDDEN = "hiddenFlags";
	public static final String PREFERENCE_EULA_ACCEPTED = "eula.accepted";
	
	/** Show dialog to be shown if they never been shown before and then
	 * they are never shown again... unless asked for via override.
	 * @Returns whether showing dialog was skipped
	 */
	public static boolean showRichTextDialogIfNeverBefore(
			final Activity activity,
			String title,
			String htmlText,
			String okButtonText,
			final String flagVisited,
			final IFuncVoidSolo onOk,
			boolean override)
	{
		return showRichTextDialogIfNeverBefore(
				activity,
				title,
				htmlText,
				okButtonText,
				flagVisited,
				onOk,
				override, null);
	}
	/** Show dialog to be shown if they never been shown before and then
	 * they are never shown again... unless asked for via override.
	 * @Returns whether showing dialog was skipped
	 */
	public static boolean showRichTextDialogIfNeverBefore(
			final Activity activity,
			String title,
			String htmlText,
			String okButtonText,
			final String flagVisited,
			final IFuncVoidSolo onOk,
			boolean override,
			ImageGetter getter)
	{
		if (!override && isAcceptedFlag(activity, flagVisited))
			return true;
		
		final AlertDialog.Builder builder = initDialog(activity, title, htmlText, getter);
		builder.setPositiveButton(okButtonText,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						setAcceptedFlag(activity, flagVisited);
						if (onOk != null)
							onOk.lambda();
					}
				});
		builder.show();
		return false;
	}
	/**
	 * Displays the EULA if necessary. This method should be called from the
	 * onCreate() method of your main Activity.	If the user accepts, the EULA
	 * will never be displayed again.	If the user refuses, the activity will
	 * finish (exit).
	 *
	 * @param activity The Activity to finish if the user rejects the EULA
	 * @Returns whether showing EULA dialog was skipped
	 */
	public static boolean showEulaRequireAcceptance(final Activity activity)
	{
		return showEulaRequireAcceptance(activity, null);
	}
	/**
	 * Displays the EULA if necessary. This method should be called from the
	 * onCreate() method of your main Activity.	If the user accepts, the EULA
	 * will never be displayed again.	If the user refuses, the activity will
	 * finish (exit).
	 *
	 * @param activity The Activity to finish if the user rejects the EULA
	 * @param onAccept called on accept being pressed
	 * @Returns whether showing EULA dialog was skipped
	 */
	public static boolean showEulaRequireAcceptance(final Activity activity, final IFuncVoidSolo onAccept)
	{
		if (isAcceptedFlag(activity, PREFERENCE_EULA_ACCEPTED))
			return true;
		
		final AlertDialog.Builder builder = initEulaDialog(activity);
		builder.setPositiveButton(R.string.accept,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						setAcceptedFlag(activity, PREFERENCE_EULA_ACCEPTED);
						if (onAccept != null)
							onAccept.lambda();
					}
				});
		builder.setNegativeButton(R.string.decline,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						activity.finish();
					}
				});
		builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				activity.finish();
			}
		});
		builder.show();
		return false;
	}

	/**
	 * Display the EULA to the user in an informational context. They won't be
	 * given the choice of accepting or declining the EULA -- we're simply
	 * displaying it for them to read.
	 */
	public static void showEula(Context context) {
		AlertDialog.Builder builder = initEulaDialog(context);
		builder.setPositiveButton(R.string.ok, null);
		builder.show();
	}
	
	private static AlertDialog.Builder initDialog(Context context, String title, String htmlText, ImageGetter getter)
	{
		if (getter == null)
			getter = new ImageGetter() {
				@Override
				public Drawable getDrawable(String source) {
					return null;
				}
			};
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setCancelable(true);
		builder.setTitle(title);
		builder.setMessage(Html.fromHtml(htmlText, getter, null));
		return builder;
	}
	private static AlertDialog.Builder initEulaDialog(Context context)
	{
		return initDialog(context, context.getString(R.string.eula_title), context.getString(R.string.eula_text), null);
	}

	private static boolean isAcceptedFlag(Activity activity, String flag) {
		final SharedPreferences preferences =
				activity.getSharedPreferences(PREFERENCES_HIDDEN, Activity.MODE_PRIVATE);
		return preferences.getBoolean(flag, false);
	}
	private static void setAcceptedFlag(Activity activity, String flag) {
		final SharedPreferences preferences =
				activity.getSharedPreferences(PREFERENCES_HIDDEN, Activity.MODE_PRIVATE);
		SharedPreferences.Editor prefEditor = preferences.edit();
		prefEditor.putBoolean(flag, true);
		prefEditor.commit();
	}

	/**
	 * Display a simple Yes/No dialog that tells user a feature is restricted and
	 * he needs to pay hos money for the real thing! CHICHING!
	 * @param appId - Id (java package) on Google Play eg. org.acouster.android.antsStonedPro
	 */
	public static void showBuyOrDieDialog(final Activity activity, final String appId) {
		AlertDialog.Builder adb = new AlertDialog.Builder(activity);
	    adb.setTitle(R.string.buyDialogTitle);
	    adb.setMessage(R.string.buyDialogMessage);
	    adb.setIcon(android.R.drawable.ic_dialog_alert);
	    adb.setPositiveButton(R.string.gotoMarket, new DialogInterface.OnClickListener() {
	    	public void onClick(DialogInterface dialog, int which) {
	    		Intent browse = new Intent( Intent.ACTION_VIEW , Uri.parse( "market://details?id=" + appId) );
	    		activity.startActivity(browse);
	    	}
	    });
	    adb.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) {
	            //finish();
	      } });
	    adb.show();
	}
}