package org.acouster.math;

/** Adds delta (Also a Vector2D) to Vector2D */
public class Transform2D extends Vector2D
{
	protected Vector2D delta;

	public Transform2D(double x, double y) 
	{
		super(x, y);
		delta = new Vector2D();
	}

	public Vector2D getDelta()
	{
		return delta;
	}
	public void setDelta(double x, double y)
	{
		delta.set(x, y);
	}
	public void inc()
	{
		this.inc(delta);
	}
	
	
}
