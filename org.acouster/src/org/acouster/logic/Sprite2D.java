package org.acouster.logic;

import org.acouster.math.Transform2D;


public class Sprite2D extends Sprite {

	protected Transform2D transform;
	
	public Sprite2D()
	{
		this(null, 0, 0);
	}
	public Sprite2D(String name, double x, double y)
	{
		super(name);
		transform = new Transform2D(x, y);
	}

	// --------------- getters/setters ---------------
	public Transform2D getTransform() {
		return transform;
	}
	public double getX() { return transform.getX(); }
	public double getY() { return transform.getY(); }
	public int getIntX() { return transform.getIntX(); }
	public int getIntY() { return transform.getIntY(); }

	/** TODO: do we want to keep dimensions with RenderableObject2D and coordinates with Sprite2D or do we wanna be more consistent???
	 * I mean, it sounds like a lame proposal now, but consider adding dimensions (w,h) to Sprite2D instead of keeping them with RenderableObject2D
	 * consequently this function will no longer be called by renderables... and in the main increment() call (of Game) we will call
	 * setNewObjectDimensions(w,h) on this sprite instead...
	 * consequently also Renderable will render based on width and height provided by the Sprite2D it will come to so dearly cherish...
	 * That's right I'll make you */
	public void renderableDimensionsChanged(int width, int height) {}
	
	//-------------- logic ----------------
	//public void increment(int width, int height) {}
}
