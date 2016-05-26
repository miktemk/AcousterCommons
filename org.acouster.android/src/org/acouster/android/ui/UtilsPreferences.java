package org.acouster.android.ui;

import android.content.SharedPreferences;


public class UtilsPreferences
{
	public static boolean saveArray(SharedPreferences prefs, String[] array, String name) { 
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt(name +"_size", array.length);
		for(int i=0;i<array.length;i++)
			editor.putString(name + "_" + i, array[i]);
		return editor.commit();
	}
	
	public static String[] loadArray(SharedPreferences prefs, String name) {
		int size = prefs.getInt(name + "_size", 0);
		String array[] = new String[size];
		for(int i=0;i<size;i++)
			array[i] = prefs.getString(name + "_" + i, null);
		return array;
	}
}
