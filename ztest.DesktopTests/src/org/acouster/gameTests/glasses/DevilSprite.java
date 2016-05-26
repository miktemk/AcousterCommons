package org.acouster.gameTests.glasses;

import org.acouster.logic.Sprite2D;

//TODO: create a moving sprite class with basic increment control and screen out-of-bounds callbacks
public class DevilSprite extends Sprite2D
{
	private double incX;
	private double incY;
	
	public DevilSprite(
			double x,
			double y,
			double incX,
			double incY)
	{
		super(null, x, y);
		this.incX = incX;
		this.incY = incY;
		transform.setDelta(incX, incY);
	}
	
	@Override
	public void increment(int width, int height)
	{
		super.increment(width, height);
		// TODO: add gravity maybe?
		this.transform.inc();
	}
}
