package org.acouster.android;

import android.graphics.Rect;

public class RectRecyclable
{
	protected Rect rect;
	public RectRecyclable()
	{
		rect = new Rect();
	}
	public Rect set(int left, int top, int right, int bottom)
	{
		rect.set(left, top, right, bottom);
		return rect;
	}
	public Rect set(Rect recttt)
	{
		rect.set(recttt);
		return rect;
	}
	public Rect get()
	{
		return rect;
	}
	
	// singleton
//	protected static RectRecyclable instance;
//	public static RectRecyclable instance()
//	{
//		if (instance == null)
//			instance = new RectRecyclable();
//		return instance;
//	}
}
