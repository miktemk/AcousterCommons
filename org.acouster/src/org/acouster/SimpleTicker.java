package org.acouster;

public class SimpleTicker extends BasicTicker
{
	protected double delay;
	protected boolean loop;
	protected IFuncVoidSolo onTimeout;
	
	public SimpleTicker(double delay, boolean loop, IFuncVoidSolo onTimeout)
	{
		this.delay = delay;
		this.loop = loop;
		this.onTimeout = onTimeout;
	}

	public void setDelay(double delay) {
		this.delay = delay;
	}
	public boolean isLoop() {
		return loop;
	}
	public void setLoop(boolean loop) {
		this.loop = loop;
	}
	
	@Override
	public double getCurrentDelay() {
		return delay;
	}
	@Override
	public void onTimeout() {
		
		onTimeout.lambda();
		if (loop)
			reset();
	}
}
