package org.acouster.android.testo;

import java.util.ArrayList;
import java.util.List;

import org.acouster.testo.MainGame;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.widget.Toast;

public class ActivityTest extends org.acouster.android.ui.BaseActivity
{
	private MainGame mainGame;
    
	@Override
    public void onCreate(Bundle savedInstanceState)
	{
    	super.onCreate(savedInstanceState);
    	initShit();
    	
    }
	
	public void onTimeForRequestingFeatures()
	{
		super.onTimeForRequestingFeatures();
		//activateFullscreen();
		//setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		activateWakeLock();
		activateTts();
		//activateAccelerometer();
		//showAds(getResources().getString(R.string.adUnitId));
	}
	
	//=================== core shit ========================
	
	private void initShit()
	{
		//find out whether speech recognition is supported
		PackageManager packManager = getPackageManager();
		List<ResolveInfo> intActivities = packManager.queryIntentActivities(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
		if (intActivities.size() != 0) {
		    //speech recognition is supported - detect user button clicks
		    //speechBtn.setOnClickListener(this);
			listenToSpeech();
		}
		else
		{
		    //speech recognition not supported, disable button and output message
		    //speechBtn.setEnabled(false);
		    Toast.makeText(this, "Oops - Speech recognition not supported!", Toast.LENGTH_LONG).show();
		}
	}
	
	/**
	 * Instruct the app to listen for user speech input
	 */
	private void listenToSpeech() {
	    //start the speech recognition intent passing required data
	    Intent listenIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
	    //indicate package
	    listenIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getClass().getPackage().getName());
	    //message to display while listening
	    listenIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say a word!");
	    //set speech model
	    listenIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
	    //specify number of results to retrieve
	    listenIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 10);
	    //start listening
	    startActivityForResult(listenIntent, VR_REQUEST);
	}
	
	private static final int VR_REQUEST = 999;
	
	/**
	 * onActivityResults handles:
	 *  - retrieving results of speech recognition listening
	 *  - retrieving result of TTS data check
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    //check speech recognition result
	    if (requestCode == VR_REQUEST && resultCode == RESULT_OK)
	    {
	        //store the returned word list as an ArrayList
	        ArrayList<String> suggestedWords = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
	        //set the retrieved list to display in the ListView using an ArrayAdapter
	        //wordList.setAdapter(new ArrayAdapter<String> (this, R.layout.word, suggestedWords));
	        for (String sss : suggestedWords)
	        	System.out.println(sss);
	    }
	    //tss code here
	    //call superclass method
	    super.onActivityResult(requestCode, resultCode, data);
	}

	
	
    @Override
    protected void onResume() {
    	super.onResume();
    }
    
    @Override
    public void onDestroy() {
    	super.onDestroy();
    }
    
}