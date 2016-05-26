package org.acouster.game3d;

import java.util.Vector;

public abstract class CollisionMap
{
	protected Sprite3D player;
	protected Vector<Sprite3D> sprites;
	protected Vector<Sprite3D> missiles;
	public CollisionMap()
	{
		sprites = new Vector<Sprite3D>();
		missiles = new Vector<Sprite3D>();
	}
	public abstract void computeNextCollisions();
	public void setPlayerObject(Sprite3D player)
	{
		this.player = player;
	}
	public void addSpriteObject(Sprite3D sprite)
	{
		sprites.add(sprite);
	}
	public void addMissileObject(Sprite3D missile)
	{
		missiles.add(missile);
	}
	public void removeSpriteObject(Sprite3D sprite)
	{
		sprites.remove(sprite);
	}
	public void removeMissileObject(Sprite3D missile)
	{
		missiles.remove(missile);
	}
	public void removeObject(Sprite3D obj)
	{
		if (sprites.contains(obj))
			sprites.remove(obj);
		if (missiles.contains(obj))
			missiles.remove(obj);
	}
}
