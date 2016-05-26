package org.acouster;

public abstract class BasicTicker
{
	protected long startTick, pausedWhen;
	protected boolean isPaused, endReached;
	
	public BasicTicker()
	{
		reset();
	}

	public abstract double getCurrentDelay();
	public abstract void onTimeout();

	public void pause()
	{
		isPaused = true;
		
	}
	public void setPaused(boolean p)
	{
		// only react to value changes
		if (p == isPaused)
			return;
		
		isPaused = p;
		if (isPaused)
			pausedWhen = System.currentTimeMillis();
		else
			startTick += (System.currentTimeMillis() - pausedWhen);
	}
	public boolean isPaused() { return isPaused; }
	
	public void increment()
	{
		if (isPaused)
			return;
		if (endReached)
			return;

		//debugPrint();
		long elapse = System.currentTimeMillis() - startTick;
		double curDelay = 1000 * getCurrentDelay();

		if (curDelay == 0)
			curProgress = 0;
		else
			curProgress = elapse / curDelay;
		if (elapse > curDelay)
		{
			curProgress = 0; //do we want 0 or 1 here? is progress to the current node or do we want
			endReached = true;
			onTimeout();
		}
	}
	
	protected double curProgress = 0;
	public double getCurrentProgress() {
		return curProgress;
	}
	
	public void reset()
	{
		startTick = System.currentTimeMillis();
		curProgress = 0;
		endReached = false;
	}
	
	public void waitUntilReset()
	{
		endReached = true;
	}
	
	public void debugPrint() { debugPrint(""); }
	public void debugPrint(String prefix)
	{
		System.out.println(prefix + (System.currentTimeMillis() - startTick) + " --> " + 1000*getCurrentDelay());
	}
}
