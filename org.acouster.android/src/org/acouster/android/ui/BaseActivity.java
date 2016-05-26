package org.acouster.android.ui;

import java.io.IOException;
import java.util.Locale;

import org.acouster.DebugUtil;
import org.acouster.util.HashMapMulti;
import org.acouster.util.StringUtils;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.Camera.Parameters;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

public class BaseActivity extends Activity implements SensorEventListener, OnInitListener
{
	protected PowerManager mPowerManager;
	protected WakeLock mWakeLock;
	protected SensorManager mSensorManager;
	protected WindowManager mWindowManager;
	protected Display mDisplay;
	protected TextToSpeech mTts;
	protected boolean ttsInitialized = false;
	protected boolean showAds = false;
	protected boolean flashlightEnabled = false;
	protected String adUnitId;
	protected AudioManager audioManager;
	protected SoundPool soundPool;
	protected SurfaceHolder camcamPreviewHolder;
	protected CameraMessyCode camcam;
	
	//=================== init ==================================
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onTimeForRequestingFeatures();
    }
    
    /** Override this to request any features such as fullscreen */
    public void onTimeForRequestingFeatures() {}
    
    //=================== protected helpers ==================================
    protected final String getExtraString(String key) {
        return getExtraString(key, null);
	}
    protected final String getExtraString(String key, String def) {
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        	return extras.getString(key);
		return def;
	}
    protected final int getExtraInt(String key) {
        return getExtraInt(key, 0);
	}
    protected final int getExtraInt(String key, int def) {
		Bundle extras = getIntent().getExtras();
		if (extras != null)
			return extras.getInt(key);
 		return def;
	}
    protected final boolean getExtraBoolean(String key) {
        return getExtraBoolean(key, false);
	}
    protected final boolean getExtraBoolean(String key, boolean def) {
		Bundle extras = getIntent().getExtras();
		if (extras != null)
			return extras.getBoolean(key);
 		return def;
	}
    
    //=================== textToSpeech ==================================
    public void activateTts()
    {
    	// tts
		mTts = new TextToSpeech(this, this);
    }
    /** called by TTS */
    @Override
	public void onInit(int status)
    {
    	if (mTts == null)
    		return;
    	
		// status can be either TextToSpeech.SUCCESS or TextToSpeech.ERROR.
    	Log.e(DebugUtil.TAG, "init tts service => ");
        if (status == TextToSpeech.SUCCESS) {
        	int result = mTts.setLanguage(Locale.US);
            // Try this someday for some interesting results.
        	Log.e(DebugUtil.TAG, "tts setting language => " + result);
            if (result == TextToSpeech.LANG_MISSING_DATA ||
                result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e(DebugUtil.TAG, "Language is not available.");
            } else {
            	// success!
            	//mTts.speak("hello I am an engine whoof", TextToSpeech.QUEUE_FLUSH, null);
            	speakText("hello I am an engine whoof");
            	ttsInitialized = true;
            }
        } else {
            // Initialization failed.
            Log.e(DebugUtil.TAG, "Could not initialize TextToSpeech.");
        }
	}
    public boolean isTtsInitialized() {
    	return ttsInitialized;
    }
    public void speakText(String text)
    {
    	if (ttsInitialized)
    	{
	    	//Log.v(DebugUtil.TAG, "ttssssssing: " + text);
	        mTts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    	}
    }
    
    //=================== ads ==================================
    /** called from onTimeForRequestingFeatures() */
    public void showAds(String adUnitId) {
    	showAds = true;
    	this.adUnitId = adUnitId;
    }
    
    //=================== flashlight ==================================
    /** called from onTimeForRequestingFeatures()
     * Note: at this moment cannot be used in conjunction with showAds()...
     * look at the if statement in GameActivity's onCreate */
    public void enableFlashlight() {
    	flashlightEnabled = true;
    }
    protected void setUpCamera(SurfaceView surface) {
    	if (camcam != null)
    		return;
    	camcamPreviewHolder = surface.getHolder();
    	camcam = new CameraMessyCode(camcamPreviewHolder);
    }
    /** called from onCreate() */
    public void turnFlashlightOn() {
    	if (!flashlightEnabled)
    		throw new RuntimeException("Please call enableFlashlight()");
    	if (camcamPreviewHolder == null)
    		throw new RuntimeException("camcamPreviewHolder not initialized!");
    	camcam.setParamFlash(Parameters.FLASH_MODE_TORCH);
    	camcam.useSmallestSize(true);
    	camcam.startPreview();
    }
    public void turnFlashlightOff() {
    	if (!flashlightEnabled)
    		throw new RuntimeException("Please call enableFlashlight()");
    	if (camcamPreviewHolder == null)
    		throw new RuntimeException("camcamPreviewHolder not initialized!");
    	if (camcam == null)
    		return;
    	camcam.stopPreview();
    }
    
    //TODO: move to CameraUtils
    
    
    //=================== camera ==================================
    public void initCamera() {
    	
    }
    
    //=================== wake lock ==================================
    /** Leaves screen on forever */
    public void activateWakeLock()
    {
    	// Get an instance of the PowerManager
        mPowerManager = (PowerManager) getSystemService(POWER_SERVICE);
        // Create a bright wake lock
        mWakeLock = mPowerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, getClass().getName());
    }
    
    // =================== accelerometer ==================================
    public void activateAccelerometer()
    {
    	// Get an instance of the SensorManager
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mDisplay = mWindowManager.getDefaultDisplay();
        //Sensor mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }
    
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {}

	@Override
	public void onSensorChanged(SensorEvent event) {
		
		//Log.v(DebugUtil.TAG, "onSensorChanged " + event.sensor.getType());
		
		if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER)
            return;
		
		float mSensorX = 0;
        float mSensorY = 0;
        
        switch (mDisplay.getRotation()) {
	        case Surface.ROTATION_0:
	            mSensorX = event.values[0];
	            mSensorY = event.values[1];
	            break;
	        case Surface.ROTATION_90:
	            mSensorX = -event.values[1];
	            mSensorY = event.values[0];
	            break;
	        case Surface.ROTATION_180:
	            mSensorX = -event.values[0];
	            mSensorY = -event.values[1];
	            break;
	        case Surface.ROTATION_270:
	            mSensorX = event.values[1];
	            mSensorY = -event.values[0];
	            break;
	    }
        
        onAccelerometerCoordsChanged(mSensorX, mSensorY);
	}
	
	// Override this whenever you use accelerometer
	protected void onAccelerometerCoordsChanged(float mSensorX, float mSensorY) {}

	// =================== audio ==================================
	protected int addSoundResource(int resourceId)
	{
		if (soundPool == null)
		{
			audioManager = (AudioManager)getSystemService(AUDIO_SERVICE);
			soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
			
		}
		return soundPool.load(this, resourceId, 1);
	}
	protected int playSound(int id) { return playSound(id, false, 1); }
	protected int playSound(int id, boolean loop, float playback_rate)
	{
		if (audioManager == null)
			return 0;
		if (soundPool == null)
			return 0;
		float curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		float maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		float leftVolume = curVolume/maxVolume;
		float rightVolume = curVolume/maxVolume;
		int priority = 1;
		int no_loop = 0;
		if (loop)
			no_loop = -1;
		return soundPool.play(id, leftVolume, rightVolume, priority, no_loop, playback_rate);
	}
	protected void stopSound(int streamID)
	{
		soundPool.stop(streamID);
	}
	// other helper shit
	protected HashMapMulti<String, Integer> soundMultiMap = null;
	protected int addSoundResourceToMultiMap(String key, int resourceId)
	{
		if (soundMultiMap == null)
			soundMultiMap = new HashMapMulti<String, Integer>();
		int soundId = addSoundResource(resourceId);
		soundMultiMap.put(key, soundId);
		return soundId;
	}
	protected int playSoundFromMultiMap(String key) { return playSoundFromMultiMap(key, false, 1); }
	protected int playSoundFromMultiMap(String key, boolean loop, float playback_rate)
	{
		if (soundMultiMap == null)
			return 0;
		if (!soundMultiMap.containsKey(key))
			return 0;
		int soundId = soundMultiMap.get(key);
		return playSound(soundId, loop, playback_rate);
	}
	
	// -------- music
	private MediaPlayer mpBackground;
	private int mpBackground_streamType = AudioManager.STREAM_MUSIC;
	protected void playBackgroundMusic(int songId, boolean isLooping)
	{
		mpBackground = MediaPlayer.create(this, songId);
		mpBackground.setAudioStreamType(mpBackground_streamType);
		mpBackground.setLooping(isLooping);
		try {
			mpBackground.prepare();
			mpBackground.start();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	protected void playBackgroundMusicUrl(String urlPath, boolean isLooping)
	{
		Uri uri = Uri.parse(urlPath);
		mpBackground = new MediaPlayer();
		try {
			mpBackground.setDataSource(this, uri);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		mpBackground.setAudioStreamType(mpBackground_streamType);
		mpBackground.setLooping(isLooping);
		try {
			mpBackground.prepare();
			mpBackground.start();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	protected void killBackgroundMusic()
	{
		if (mpBackground != null) {
        	mpBackground.stop();
        	mpBackground.release();
        	mpBackground = null;
        }
	}
	/** Used only in alarm clock applications to change to STREAM_ALARM, for instance
	 * The default is and was (and always will be) AudioManager.STREAM_MUSIC */
	protected void setBackgroundMusicStream(int type) {
		mpBackground_streamType = type;
	}
	
	// =================== pause/resume events ==================================
    @Override
    protected void onResume() {
        super.onResume();
        if (mWakeLock != null)
        	mWakeLock.acquire();
        if (mSensorManager != null)
        {
	        Sensor mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
	        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
        }
        if (camcam != null && camcam.shouldBePreviewing())
        	camcam.startPreview();
        if (mpBackground != null) {
        	mpBackground.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // and release our wake-lock
        if (mWakeLock != null)
        	mWakeLock.release();
        if (mSensorManager != null)
        	mSensorManager.unregisterListener(this);
        if (camcam != null)
        	camcam.stopPreview();
        if (mpBackground != null) {
        	mpBackground.pause();
        }
    }
    
    @Override
    public void onDestroy() {
        // kill tts
        if (mTts != null) {
            mTts.stop();
            mTts.shutdown();
            mTts = null;
            ttsInitialized = false;
        }
        // kill soundPool and audioManager
        if (soundPool != null) {
        	soundPool.release();
        	soundPool = null;
        	audioManager = null;
        }
        if (camcam != null) {
        	camcam.release();
        	camcam = null;
        }
        killBackgroundMusic();
        super.onDestroy();
    }

}
