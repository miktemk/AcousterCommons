package org.acouster.android.ui;

import org.acouster.Acouster;
import org.acouster.util.StringUtils;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

public class PreferenceFragmentAcouster extends PreferenceFragment
{
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	
	/** call after setting content view XML */
	protected void setupCommonAcousterUI(final String appIdPro)
	{
		// About... go to pro version on app store
		Preference prefBuy = findPreference(Acouster.Android.OptKey_aboutBuyPro);
		if (prefBuy != null && !StringUtils.isNullOrEmpty(appIdPro)) {
			prefBuy.setOnPreferenceClickListener(new OnPreferenceClickListener() {
				@Override
				public boolean onPreferenceClick(Preference preference) {
					Intent browse = new Intent( Intent.ACTION_VIEW , Uri.parse( "market://details?id=" + appIdPro) );
					startActivity(browse);
					return true;
				}
			});
		}
		
		// About... go to website link
		Preference prefSite = findPreference(Acouster.Android.OptKey_aboutAcouster);
		if (prefSite != null) {
			prefSite.setOnPreferenceClickListener(new OnPreferenceClickListener() {
				@Override
				public boolean onPreferenceClick(Preference preference) {
					Intent browse = new Intent( Intent.ACTION_VIEW , Uri.parse( Acouster.ACOUSTER_URL ) );
					startActivity(browse);
					return true;
				}
			});
		}
	}

	// ======================================

	protected SharedPreferences DSP() {
		return PreferenceManager.getDefaultSharedPreferences(getActivity());
	}
}