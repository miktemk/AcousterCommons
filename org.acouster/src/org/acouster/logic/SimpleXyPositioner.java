package org.acouster.logic;

/** posX and posY are 0 - 1.0 of screen size */
public class SimpleXyPositioner extends Sprite2D
{
	protected double posX, posY;
	public SimpleXyPositioner() {
		this(0.5, 0.5);
	}
	public SimpleXyPositioner(double posX, double posY) {
		set(posX, posY);
	}
	
	public SimpleXyPositioner set(double posX, double posY) {
		this.posX = posX;
		this.posY = posY;
		updateTransform();
		return this;
	}
	
	protected void updateTransform() {
		transform.set(prevWidth * posX, prevHeight * posY);
	}
	
	@Override
	public void dimensionsChanged(int width, int height)
	{
		super.dimensionsChanged(width, height);
		updateTransform();
	}
}
