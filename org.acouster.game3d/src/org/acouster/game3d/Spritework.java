package org.acouster.game3d;

import java.util.Iterator;
import java.util.Vector;

import org.acouster.game3d.graphics.RenderableObject3D;


public class Spritework {

	protected World3d world3D;
	protected Vector<Sprite3D> sprites;
	protected Vector<Sprite3D> deleteListSprites;
	protected CollisionMap collisionMap;
	
	public Spritework(World3d world3D)
	{
		this.world3D = world3D;
		
		sprites = new Vector<Sprite3D>();
		deleteListSprites = new Vector<Sprite3D>();
		collisionMap = new SimpleCollisionMap();
	}
	
	public void increment(Sprite3D player)
	{
		computeCollisions();
		incrementSprites(player);
		deleteScheduledSprites();
	}
	
	public void scheduleSpriteForDeletion(Sprite3D obj)
	{
		deleteListSprites.add(obj);
	}
	
	public CollisionMap getCollisionMapObject()
	{
		return collisionMap;
	}
	
	protected void computeCollisions()
	{
		collisionMap.computeNextCollisions();
	}
	
	protected void incrementSprites(Sprite3D player)
	{
		for (Iterator<Sprite3D> i = sprites.iterator(); i.hasNext();)
		{
			Sprite3D sprite = i.next();
			sprite.makeDecision(world3D, player, this);
			sprite.incrementPosition();
			sprite.incrementFrame();
		}
	}
	
	protected void deleteScheduledSprites()
	{
		for (Iterator<Sprite3D> i = deleteListSprites.iterator(); i.hasNext();)
		{
			Sprite3D obj = i.next();
			sprites.remove(obj);
		}
	}

	//------ public --------------------------------------------
	public Vector<Sprite3D> getSprites()
	{
		return sprites;
	}
	public void addSprite(Sprite3D s)
	{
		addSpriteAndRenderable(s, s.getObject());
	}
	public void addCollisionMissile(Sprite3D mmm)
	{
		addSprite(mmm);
		collisionMap.addMissileObject(mmm);
	}
	public void addCollisionSprite(Sprite3D s)
	{
		addSprite(s);
		collisionMap.addSpriteObject(s);
	}
	public void scheduleObjectForDeletion(Sprite3D s)
	{
		this.scheduleSpriteForDeletion(s);
		world3D.scheduleRenderableForDeletion(s.getObject());
		collisionMap.removeObject(s);
	}
	
	//------ private --------------------------------------------
	protected void addSpriteAndRenderable(Sprite3D s, RenderableObject3D o)
	{
		if (o != null)
			world3D.scheduleRenderableForAddition(o, true, false);
		sprites.add(s);
	}
}
