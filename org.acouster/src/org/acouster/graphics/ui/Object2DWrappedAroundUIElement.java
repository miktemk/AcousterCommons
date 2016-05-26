package org.acouster.graphics.ui;

import org.acouster.math.Vector2D;
import org.acouster.math.trajectory.IObject2D;

public class Object2DWrappedAroundUIElement implements IObject2D
{

	protected UIElementBase elem;
	
	public Object2DWrappedAroundUIElement(UIElementBase elem)
	{
		this.elem = elem;
	}

	@Override
	public boolean checkCollision(Vector2D v)
	{
		return elem.checkCollision((int)v.getX(), (int)v.getY());
	}

	@Override
	public void getCenter(Vector2D fillMe)
	{
		fillMe.set(elem.getX(), elem.getY());
	}
}
