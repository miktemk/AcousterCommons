package org.acouster.util;

import java.util.List;
import java.util.Vector;

import org.acouster.Incrementable;
import org.acouster.logic.HtmlPositioner;
import org.acouster.logic.Sprite2DHtmlPositioned;

public class GameUtils
{
	private static List<Incrementable> incrementablesToDelete = new Vector<Incrementable>();
	public static void Increment(List<? extends Incrementable> list, int width, int height)
	{
		synchronized(list)
		{
			boolean needDelete = false;
			for (Incrementable i : list)
			{
				i.increment(width, height);
				if (i.isExpired())
				{
					needDelete = true;
					incrementablesToDelete.add(i);
				}
			}
			if (needDelete)
			{
				for (Incrementable i : incrementablesToDelete)
					list.remove(i);
				incrementablesToDelete.clear();
			}
		}
	}
	
	public static Sprite2DHtmlPositioned makeCenteredSprite()
	{
		return new Sprite2DHtmlPositioned(
				new HtmlPositioner(0, 0).centerX().centerY());
	}
}
