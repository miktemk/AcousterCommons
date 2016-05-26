package org.acouster.desktop;

import java.awt.image.ImageObserver;

public class ImageObserverUtil
{
	private static ImageObserver instance;
	public static void setInstance(ImageObserver o)
	{
		instance = o;
	}
	public static ImageObserver instance()
	{
		return instance;
	}
	
}
