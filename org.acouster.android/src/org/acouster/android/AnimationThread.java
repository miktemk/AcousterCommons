package org.acouster.android;

import org.acouster.DebugUtil;
import org.acouster.Flashable;

public class AnimationThread extends Thread
{
    private int delayTime;
    private boolean isRunning;
    private Flashable flashable;
    public AnimationThread(Flashable f)
    {
    	this(f, 33);
    }
    public AnimationThread(Flashable f, int delayTime)
    {
        this.delayTime = delayTime;
        this.isRunning = false;
        this.flashable = f;
    }
    public void startMe()
    {
    	if (isRunning)
    		return;
		isRunning = true;
    	start();
    }
    public void stopMe()
    {
    	// code from lunar lander
    	boolean retry = true;
    	isRunning = false;
    	while (retry) {
            try {
                join();
                retry = false;
            } catch (InterruptedException e) {}
        }
    	//stop();
    }
    public boolean isStopped() {
		return !isRunning;
	}
    public void run()
    {
        while(isRunning)
        {
        	//DebugUtil.sss("thread running... " + flashable);
            try{ sleep(delayTime); } catch(Exception ex) {}
            
            if (flashable != null)
        		flashable.flash();
        }
    }
    
    public Flashable getFlashable() {
		return flashable;
	}
	public void setFlashable(Flashable flashable) {
		this.flashable = flashable;
	}
	/**
     * Sets the tempo of the beater in bpm
     * @param tempo
     */
    public void setDelay(int delayTime)
    {
        this.delayTime = delayTime;
    }
    public void setFPS(int fps)
    {
        this.delayTime = 1000/fps;
    }
    public int getDelay()
    {
        return delayTime;
    }
}



/////////////// old code /////////////////////////

//public class AnimationThread extends Animation
//{
//    private int delayTime;
//    
//    private Flashable f;
//    public AnimationThread(Flashable f)
//    {
//        this(f, 33);
//    }
//    public AnimationThread(Flashable f, int delayTime)
//    {
//    	this.f = f;
//        this.delayTime = delayTime;
//        setRepeatCount(Animation.INFINITE);
//        setDuration(200);
//    }
//    /**
//     * Sets the tempo of the beater in bpm
//     * @param tempo
//     */
//    public void setDelay(int delayTime)
//    {
//        this.delayTime = delayTime;
//    }
//    public void setFPS(int fps)
//    {
//        this.delayTime = 1000/fps;
//    }
//    public int getDelay()
//    {
//        return delayTime;
//    }
//    
//    public void applyTransformation(float interpolatedTime, Transformation t){
//    	if (f != null)
//            f.flash();
//	}
//}