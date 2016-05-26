package org.acouster.gravball;

import org.acouster.logic.Sprite2D;
import org.acouster.util.MathUtils;

public class MySprite extends Sprite2D {

	public MySprite(String name, double x, double y)
	{
		super(name, x, y);
	}

	@Override
	public void increment() {
		super.increment();
		transform.incX(MathUtils.randomDouble(-6, 6));
		transform.incY(MathUtils.randomDouble(-6, 6));
	}
}
