package org.acouster.android;

import android.graphics.Rect;
import android.graphics.RectF;

public class RectFRecyclable
{
	protected RectF rect;
	public RectFRecyclable()
	{
		rect = new RectF();
	}
	public RectF set(float left, float top, float right, float bottom)
	{
		rect.set(left, top, right, bottom);
		return rect;
	}
	public RectF set(RectF recttt)
	{
		rect.set(recttt);
		return rect;
	}
	public RectF set(Rect recttt)
	{
		rect.set(recttt);
		return rect;
	}
	public RectF get()
	{
		return rect;
	}
	
	// singleton
//	protected static RectFRecyclable instance;
//	public static RectFRecyclable instance()
//	{
//		if (instance == null)
//			instance = new RectFRecyclable();
//		return instance;
//	}
}
