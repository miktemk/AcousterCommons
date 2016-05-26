package org.acouster.context.desktop;

import org.acouster.Flashable;

public class AnimationThread extends Thread
{
    private int delayTime;
    private Flashable f;
    public AnimationThread(Flashable f)
    {
        this.delayTime = 33;
        this.f = f;
    }
    public AnimationThread(Flashable f, int delayTime)
    {
        this.delayTime = delayTime;
        this.f = f;
    }
    public void run()
    {
        while(true)
        {
            try{ sleep(delayTime); } catch(Exception ex) {}
            if (f != null)
                f.flash();
        }
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