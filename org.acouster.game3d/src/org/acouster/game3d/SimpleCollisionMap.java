package org.acouster.game3d;

import org.acouster.game3d.math.GameMath3D;

public class SimpleCollisionMap extends CollisionMap
{
	public SimpleCollisionMap()
	{
		super();
	}
	public void computeNextCollisions()
	{
		for (Sprite3D missile : missiles)
		{
			for (Sprite3D sprite : sprites)
			{
				if (missile == sprite)
					continue;
				// TODO: implement theyWillBeTooClose where u take FUTURE x, y into account
				//if (theyWillBeTooClose(missile.getSprite(), sprite.getSprite()))
				if (theyAreTooClose(missile, sprite))
					missile.setNextCollision(sprite);
			}
		}
	}
	private boolean theyAreTooClose(Sprite3D sprite, Sprite3D sprite2)
	{
		if (GameMath3D.distanceSquared(sprite.x, sprite.y, sprite.z, sprite2.x, sprite2.y, sprite2.z) < GameMath3D.sqr(sprite.radius + sprite2.radius))
			return true;
		return false;
	}
	
}