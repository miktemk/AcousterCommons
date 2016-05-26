package org.acouster.graphics.anim;

import java.util.List;
import java.util.Vector;

import org.acouster.context.ContextGraphics;
import org.acouster.graphics.RenderableObject;

public class AnimatedStroke extends RenderableObject
{
	protected int rgb;
	protected List<StrokePoint> points;
	protected boolean isActive = true, isLoop = false;
	protected long timeWaitAfter = -1;
	protected long d0 = 0, timeMax = 0;
	
	public AnimatedStroke(int rgb)
	{
		this.rgb = rgb;
		points = new Vector<AnimatedStroke.StrokePoint>();
	}
	
	@Override
	public void render(ContextGraphics g, int w, int h)
	{
		if (!visible)
			return;
		if (!isActive)
			return;
		
		//TODO: thickness of the stroke
		g.setColor(rgb);
		
		StrokePoint prev = null;
		long curTime = System.currentTimeMillis();
		for (StrokePoint p : points)
		{
			if (prev == null || p.isNew)
			{
				prev = p;
				continue;
			}
			
			if (d0 + p.time > curTime)
				break;
			
			g.setStroke(rgb, p.width);
			g.drawLine(prev.x * w, prev.y * h, p.x * w, p.y * h);
			prev = p;
		}
		
	}
	@Override
	public void increment(int width, int height) {
		super.increment(width, height);
		if (!isActive)
			return;
		long exiryTime = timeMax;
		if (timeWaitAfter > 0)
			exiryTime += timeWaitAfter;
		long curTime = System.currentTimeMillis();
		if (curTime - d0 > exiryTime)
		{
			if (isLoop)
				playFromNow();
			else if (timeWaitAfter > 0)
				setActive(false);
		}
	}
	
	//---------- interaction
	public int getRgb() {
		return rgb;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public boolean isLoop() {
		return isLoop;
	}
	public void setLoop(boolean isLoop) {
		this.isLoop = isLoop;
	}
	public long getTimeWaitAfter() {
		return timeWaitAfter;
	}
	public void setTimeWaitAfter(long timeWaitAfter) {
		this.timeWaitAfter = timeWaitAfter;
	}

	public void playFromNow()
	{
		setActive(true);
		d0 = System.currentTimeMillis();
	}

	//---------- creation
	public void clear()
	{
		points.clear();
	}
	public void append(long time, float x, float y, int width, boolean isNew)
	{
		points.add(new StrokePoint(time, x, y, width, isNew));
		timeMax = Math.max(timeMax, time);
	}
	public List<StrokePoint> getPoints() {
		return points;
	}
	
	
	
	
	
	public class StrokePoint
	{
		public long time;
		public float x, y;
		public int width;
		public boolean isNew;
		public StrokePoint(long time, float x, float y, int width, boolean isNew) {
			super();
			this.time = time;
			this.x = x;
			this.y = y;
			this.width = width;
			this.isNew = isNew;
		}
	}


	
}
