package org.acouster.android.ui;

import org.acouster.DebugUtil;
import org.acouster.Game;
import org.acouster.GameContext;
import org.acouster.GameEvent;
import org.acouster.android.R;
import org.acouster.android.ui.AndroidGamePanel;

import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

import com.google.ads.AdRequest;
import com.google.ads.AdView;

public class GameActivity extends BaseActivity implements GameContext
{
	protected AndroidGamePanel gamePanel;
	protected Game game;
	private boolean threadActivityControlFreakKilled = false;
	protected Bundle bundle;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        
        this.bundle = bundle;
        
        if (showAds)
        {
        	Log.v(DebugUtil.TAG, "GameActivity: showAds");
        	setContentView(R.layout.game_panel_ads);
        	AdView adView = (AdView)findViewById(R.id.adView);
        	adView.loadAd(new AdRequest());
        	gamePanel = (AndroidGamePanel)findViewById(R.id.gamePanel);
        	gamePanel.requestFocus();
        }
        else if (flashlightEnabled)
        {
        	Log.v(DebugUtil.TAG, "GameActivity: flashlightEnabled");
        	setContentView(R.layout.game_panel_flashlight);

        	// we need to set cam surface so that flashlight cam can use it
        	SurfaceView surface = (SurfaceView)findViewById(R.id.preview);
        	setUpCamera(surface);
        	
        	gamePanel = (AndroidGamePanel)findViewById(R.id.gamePanel);
        	gamePanel.requestFocus();
        }
        else
        {
        	Log.v(DebugUtil.TAG, "GameActivity: default new GamePanel");
	        gamePanel = new AndroidGamePanel(this);
	        setContentView(gamePanel);
	        gamePanel.requestFocus();
        }
    }
    
    /** word of advice: never put shit on production that calls this function... */
    protected void displayHints() {
    	gamePanel.displayHints();
    }
    
    /** called before setGame if you want the game to take manual control of the threading */
    public void manualThreadControl()
    {
    	gamePanel.setUseThread(false);
    }
    
    /** call manualThreadControl before this IF(!!!!!) you want the Game to control the thread instead of thread running all the time */
    public void setGame(Game game)
    {
    	this.game = game;
        gamePanel.setGame(game);
        game.setContext(this);
        recoverLostSettings(bundle);
    }
    
    public void activateFullscreen()
    {
		// Set full screen view
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
    }
    protected void setThreadActivityControlFreakEnabled(boolean flag)
	{
		threadActivityControlFreakKilled = !flag;
	}
    
    @Override
    protected void onAccelerometerCoordsChanged(float mSensorX, float mSensorY) {
    	//Log.v(DebugUtil.TAG, "onAccelerometerCoordsChanged");
    	super.onAccelerometerCoordsChanged(mSensorX, mSensorY);
    	if (game != null)
    		game.onAccelerometerChange(mSensorX, mSensorY);
    }
    
    // =================== pause/resume/destroy events ==================================
	
//    @Override
//    protected void onStart() {
//    	super.onStart();
//    	Log.v(DebugUtil.TAG, "onStart thread " + this);
//    }
//    protected void onStop() {
//    	super.onStart();
//    	Log.v(DebugUtil.TAG, "onStop thread " + this);
//    }
//    @Override
//    protected void onRestart() {
//        super.onResume();
//        Log.v(DebugUtil.TAG, "onRestart thread " + this);
//    }
    
    @Override
    protected void onResume() {
        super.onResume();
        Log.v(DebugUtil.TAG, "onResume thread " + this);
        gamePanel.startThread();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(DebugUtil.TAG, "onPause thread " + this);
        if (!threadActivityControlFreakKilled)
        	gamePanel.pauseThread();
    }
    
    @Override
    public void onDestroy() {
    	super.onDestroy();
    	Log.v(DebugUtil.TAG, "onDestroy thread " + this);
        // Don't forget to shutdown!
    	gamePanel.killThread();
    	if (game != null)
    		game.dispose();
    }
    
    @Override
    public void onBackPressed() {
    	if (game.backButtonPressed())
    		super.onBackPressed();
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
    	super.onSaveInstanceState(outState);
    	//Log.v("======>", "onSaveInstanceState");
    	if (game != null)
    	{
    		outState.putIntArray(Game.BUNDLE_KEY_SCREEN_STACK, game.getInnerScreenStack());
    		outState.putString(Game.BUNDLE_KEY_GAME_JSON, game.getBundleValues());
    	}
    }
    
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
    	super.onRestoreInstanceState(savedInstanceState);
    	//Log.v("======>", "onRestoreInstanceState");
    	recoverLostSettings(savedInstanceState);
    }
    
    private void recoverLostSettings(Bundle bundle)
    {
    	if (bundle == null)
    		return;
    	if (game == null)
    		return;
    	if (bundle.containsKey(Game.BUNDLE_KEY_SCREEN_STACK))
    		game.setInnerScreenStack(bundle.getIntArray(Game.BUNDLE_KEY_SCREEN_STACK));
    	if (bundle.containsKey(Game.BUNDLE_KEY_GAME_JSON))
    		game.restoreBundleValues(bundle.getString(Game.BUNDLE_KEY_GAME_JSON));
    }
    
    // =================== GameContext methods ==================================
	
	@Override
	public int getContextHeight() {
		return gamePanel.getHeight();
	}
	@Override
	public int getContextWidth() {
		return gamePanel.getWidth();
	}
	@Override
	public int getContextDiagonal() {
		return gamePanel.getHeight() + gamePanel.getWidth();
	}
	@Override
	public void handleMessage(GameEvent message) {
		if (message.is(Game.DIRECTIVE_KILL_ACTIVITY))
			killActivity();
	}
	@Override
	public void triggerRepaint() {
		gamePanel.triggerRepaint();
	}
	@Override
	public void killActivity() {
		finish();
	}
	@Override
	public void forceStartThread() {
		gamePanel.startThread_force();
	}
	@Override
	public void forceStopThread() {
		gamePanel.pauseThread();
	}
}
