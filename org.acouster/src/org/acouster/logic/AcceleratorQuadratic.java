package org.acouster.logic;

/** A utility to provide smooth acceleration for our Sprites (just call increment!)  */
public class AcceleratorQuadratic
{
	protected double delta;
	protected AccelCurve accel1, accel2, curAccel;
	protected boolean accel2Waiting;
	
	public AcceleratorQuadratic()
	{
		delta = 0;
		accel1 = new AccelCurve();
		accel2 = new AccelCurve();
		curAccel = null;
		accel2Waiting = false;
	}
	
	/** Accelerates from current delta to deltaPeak in nFramesUp and then back down in nFramesDown  */
	public void accelTo(double deltaTarget, int nFrames) {
		accel1.set(nFrames, delta, deltaTarget);
		curAccel = accel1;
		
	}
	
	/** Accelerates from current delta to deltaPeak in nFramesUp and then back down in nFramesDown  */
	public void accelAndDescel(double deltaPeak, int nFramesUp, int nFramesDown) {
		accel1.set(nFramesUp, delta, deltaPeak);
		accel2.set(nFramesDown, deltaPeak, delta);
		curAccel = accel1;
		accel2Waiting = true;
	}
	
	public void increment() {
		if (curAccel == null)
			return;
		curAccel.increment();
		if (curAccel.done)
		{
			if ((curAccel == accel1) && accel2Waiting) {
				curAccel = accel2;
				accel2Waiting = false;
			}
			else {
				curAccel = null;
			}
		}
	}
	
	public double getDelta() {
		return delta;
	}
	
	/** Sets the delta and kills any active acceleration routines */
	public void setDelta(double delta) {
		this.delta = delta;
		curAccel = null;
		accel2Waiting = false;
	}
	
	public void reset() {
		setDelta(0);
	}
	
	//==========================================================
	
	private class AccelCurve
	{
		protected int t, nFrames;
		protected double target, accel;
		protected boolean done;
		
		public AccelCurve()
		{
			done = true;
		}
		
		public void set(int nFrames, double from, double to) {
			if (nFrames == 0) {
				done = true;
				delta = to;
			}
			else {
				this.nFrames = nFrames;
				this.target = to;
				this.accel = (to-from)/nFrames;
				done = false;
			}
		}
		public void increment() {
			if (done)
				return;
			t++;
			delta += accel;
			if (t > nFrames) {
				delta = target;
				done = true;
			}
		}
	}

}
