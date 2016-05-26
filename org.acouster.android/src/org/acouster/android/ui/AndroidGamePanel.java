package org.acouster.android.ui;

import org.acouster.Game;
import org.acouster.android.AnimationThread;
import org.acouster.android.context.AndroidContextGraphics;
import org.acouster.context.ContextKeyEvent;
import org.acouster.context.ContextMouseEvent;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.KeyEvent;

public class AndroidGamePanel extends SurfaceView
implements
	//GameContext,
	OnTouchListener
	//,KeyEvent.Callback
{
	private Context activity;
	private Game game;
    private AnimationThread thread;
	private int prevKeyCode;
    private AndroidContextGraphics ggg;
    private boolean renderHints = false;
    private boolean useThread = true;
    
    public AndroidGamePanel(Context activity) {
    	super(activity);
    	myInit(activity);
    }
    public AndroidGamePanel(Context activity, AttributeSet attrs) {
		super(activity, attrs);
		myInit(activity);
	}
    public AndroidGamePanel(Context activity, AttributeSet attrs, int defStyle) {
		super(activity, attrs, defStyle);
		myInit(activity);
	}
    
    private void myInit(Context activity)
    {
    	this.activity = activity;
		
		setFocusable(true);
        setFocusableInTouchMode(true);
        this.setOnTouchListener(this);
        prevKeyCode = 0;
        
        // context environment
        ggg = new AndroidContextGraphics();
        createFreshThreadIfNotExists();
    }
    
    // ------------------- thread control -------------------------------
    public void setUseThread(boolean b)
    {
    	useThread = b;
    }
    public AnimationThread getThread() {
		return thread;
	}
    public void startThread() {
    	if (!useThread)
    		return;
    	startThread_force();
	}
    public void startThread_force() {
    	createFreshThreadIfNotExists();
    	thread.startMe();
	}
    public void pauseThread() {
    	thread.stopMe();
	}
    public void killThread() {
    	thread.stopMe();
	}
    // thread helpers
    private void createFreshThreadIfNotExists()
    {
    	if (thread == null || thread.isStopped())
    		thread = new AnimationThread(game);
	}
    
    //---------------------- android overrides --------------------------
	@Override
    public void onDraw(Canvas canvas) {
    	super.onDraw(canvas);
    	
    	if (!useThread)
    	{
    		ggg.setCanvas(canvas);
    		doPaintThings();
    	}
    }
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		if (game != null)
			game.incrementCharacters(w, h);
		triggerRepaint();
		System.out.println("onSizeChanged: " + game);
	}

	// ------------------- touch + kb listener -------------------------------
	public boolean onTouch(View v, MotionEvent e)
	{
		final int action = e.getAction();
	    switch (action) {
		    case MotionEvent.ACTION_DOWN:
		    	game.mousePressed(fakeATouchEvent(e));
		        break;
		    case MotionEvent.ACTION_MOVE:
		    	game.mouseDragged(fakeATouchEvent(e));
		        break;
		    case MotionEvent.ACTION_UP:
		    	game.mouseReleased(fakeATouchEvent(e));
		        break;
	    }
		return true;
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		game.keyPressed(fakeAKeyEvent(keyCode, event));
        return super.onKeyUp(keyCode, event);
	}
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		game.keyReleased(fakeAKeyEvent(keyCode, event));
        return super.onKeyUp(keyCode, event);
	}
	
	// conversion shit
	private ContextKeyEvent _recycledKeyEvent = new ContextKeyEvent();
	private ContextKeyEvent fakeAKeyEvent(int keyCode, KeyEvent event)
    {
    	return _recycledKeyEvent.set(keyCode, 1);
    }
	private ContextMouseEvent _recycledMouseEvent = new ContextMouseEvent();
	private ContextMouseEvent fakeATouchEvent(MotionEvent e)
    {
    	return _recycledMouseEvent.set((int)e.getX(), (int)e.getY(), 0);
    }
	
	//----------------------------------------------------------------
	// methods to be called by surrounding gamecontext
	public void setGame(Game game)
    {
        setGame(game, true);
    }
	public void setGame(Game game, boolean useThread)
    {
        if (game == null)
    		return;
    	this.game = game;
    	createFreshThreadIfNotExists();
    	thread.setFlashable(game);
    }
	public void triggerRepaint() {
		// taken from lunar lander :P
		Canvas c = null;
        try {
        	c = this.getHolder().lockCanvas(null);
        	if (c != null) {
        		//draw(c);
	            synchronized (this) {
	                //onDraw(c);
	            	ggg.setCanvas(c);
	            	doPaintThings();
	            }
            }
        } finally {
            // do this in a finally so that if an exception is thrown
            // during the above, we don't leave the Surface in an
            // inconsistent state
            if (c != null) {
                this.getHolder().unlockCanvasAndPost(c);
            }
        }
        // this throws an exceptin (this never happened before... its so random :P) ... only the original thread can touch subviews
        //invalidate();
	}
	
	private void doPaintThings()
	{
		if (game != null) {
            game.paintCharacters(ggg, getWidth(), getHeight());
            if (renderHints)
            	game.drawHints(ggg, getWidth(), getHeight());
    	}
	}
	
	/** word of advice: never put shit on production that calls this function... */
	public void displayHints() {
		renderHints = true;
	}
}
