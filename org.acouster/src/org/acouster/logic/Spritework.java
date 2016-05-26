package org.acouster.logic;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.acouster.GameEvent;
import org.acouster.util.GameUtils;


public class Spritework
{
	protected List<Sprite> sprites, spritesToAdd;
	protected Map<String, Sprite> nameMap;
	private boolean needAdd; 
	
	public Spritework()
	{
		sprites = Collections.synchronizedList(new Vector<Sprite>());
		spritesToAdd = Collections.synchronizedList(new Vector<Sprite>());
		nameMap = Collections.synchronizedMap(new HashMap<String, Sprite>());
		needAdd = false;
	}
	
	/** thread/concurrency-safe */
	public void addSprite(Sprite sprite)
	{
		// TODO: if Collections.synchronizedList still fails us
		//synchronized (spritesToAdd) { spritesToAdd.add(sprite); }
		
		spritesToAdd.add(sprite);
		needAdd = true;
	}
	/** called from increment when needAdd is true */
	private void addSpriteInner(Sprite sprite)
	{
		// TODO: if Collections.synchronizedList still fails us
		//synchronized (sprites) { sprites.add(sprite); }
		
		sprites.add(sprite);
		String key = sprite.getId();
		if ((key != null) && (key != "") && !nameMap.containsKey(key))
			nameMap.put(key, sprite);
	}
	/** thread/concurrency-safe */
	public void removeSprite(Sprite sprite)
	{
		sprite.expire();
		String key = sprite.getId();
		if ((key != null) && (key != "") && !nameMap.containsKey(key))
			nameMap.remove(key);
	}
	/** thread/concurrency-safe */
	public void removeSprite(String key)
	{
		if (nameMap.containsKey(key))
		{
			Sprite sprite = nameMap.get(key);
			sprite.expire();
			nameMap.remove(key);
		}
	}
	/** typically called for statistics */
	public int getNSprites() {
		return sprites.size();
	}
	
	public void increment(int width, int height)
	{
		if (needAdd)
		{
			synchronized (spritesToAdd)
			{
				for (Sprite s : spritesToAdd)
					addSpriteInner(s);
			}
			spritesToAdd.clear();
			needAdd = false;
		}
		GameUtils.Increment(sprites, width, height);
	}
	
	public void handleActionMessage(GameEvent message)
	{
		Sprite value = nameMap.get(message.getTarget());
		value.handleActionMessage(message);
	}

	public void dispose() {}

	public void dimensionsChanged(int width, int height)
	{
		// TODO: check for concurrency issues
		for (Sprite s : sprites)
			s.dimensionsChanged(width, height);
	}
}
