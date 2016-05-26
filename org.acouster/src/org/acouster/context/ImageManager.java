package org.acouster.context;

import java.util.HashMap;

import org.acouster.DebugUtil;
import org.acouster.util.HashMapUtils;
import org.acouster.util.StringUtils;

public class ImageManager
{
	private HashMap<String, ContextBitmap> cacheResource;
	private HashMap<String, ContextBitmap> cacheFile;
	private int bufferSize;
	public ImageManager()
	{
		this(10);
	}
	public ImageManager(int bufferSize)
	{
		this.bufferSize = bufferSize;
		cacheResource = new HashMap<String, ContextBitmap>();
		cacheFile = new HashMap<String, ContextBitmap>();
	}
	
	public ContextBitmap loadFromResource(String resourceName) {
		return loadFromResource(resourceName, false);
	}
	public ContextBitmap loadFromResource(String resourceName, boolean preciseSize)
	{
		if (cacheResource.containsKey(resourceName))
			return cacheResource.get(resourceName);
		ContextBitmap newGuy = ResourceContext.instance().LoadBitmap(resourceName, preciseSize);
		cache(cacheResource, resourceName, newGuy);
		return newGuy;
	}
	
	public ContextBitmap loadFromFile(String filename)
	{
		if (cacheFile.containsKey(filename))
			return cacheFile.get(filename);
		ContextBitmap newGuy = ResourceContext.instance().LoadBitmapFromFile(filename);
		cache(cacheFile, filename, newGuy);
		return newGuy;
	}
	public void disposeOf(ContextBitmap bmp)
	{
		bmp.dispose();
		uncache(bmp);
	}
	public void uncache(ContextBitmap bmp)
	{
		if (bmp == null)
			return;
		if (cacheFile.containsValue(bmp))
			HashMapUtils.removeValue(cacheFile, bmp);
		if (cacheResource.containsValue(bmp))
			HashMapUtils.removeValue(cacheResource, bmp);
	}
	
	public void disposeAllImages()
	{
		//TODO: this
		//DebugUtil.sss("ImageManager.disposeAllImages called");
		disposeAll(cacheResource);
		disposeAll(cacheFile);
	}
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("------ ImageManager Dump ------\n");
		sb.append("cacheResource:\n");
		for (String key : cacheResource.keySet())
			sb.append(" - " + key + "\n");
		sb.append("cacheFile:\n");
		for (String key : cacheFile.keySet())
			sb.append(" - " + key + "\n");
		return sb.toString();
	}
	
	
	// privates
	private void cache(HashMap<String, ContextBitmap> map, String resourceName, ContextBitmap img)
	{
		if (bufferSize > 0 && map.size() >= bufferSize)
			map.remove(map.values().iterator().next());
		map.put(resourceName, img);
	}
	private void disposeAll(HashMap<String, ContextBitmap> map)
	{
		for (String key : map.keySet())
		{
			DebugUtil.sss("gggeyy: " + key + " " + map.containsKey(key));
			ContextBitmap bmp = map.get(key);
			if (bmp != null)
				bmp.dispose();
		}
		map.clear();
	}

	
	
	// singleton
	protected static ImageManager instance;
	public static ImageManager instance()
	{
		//if (instance == null)
		//	instance = new ImageManager();
		return instance;
	}
	public void makeInstance()
	{
		instance = this;
	}
	/** A shorthand for ImageManager.instance().loadFromResource(...) */
	public static ContextBitmap loadR(String resourceName) {
		return instance.loadFromResource(resourceName);
	}
	public static ContextBitmap loadR(String resourceName, boolean preciseSize)
	{
		return instance.loadFromResource(resourceName, preciseSize);
	}
	/** A shorthand for ImageManager.instance().loadFromFile(...) */
	public static ContextBitmap loadF(String filename)
	{
		return instance.loadFromFile(filename);
	}
	/** A shorthand for ImageManager.instance().disposeOf(...) or u can use bitmap's dispose which also clears the cache here in ImageManager */
	public static void dispose(ContextBitmap bmp)
	{
		instance.disposeOf(bmp);
	}
}
