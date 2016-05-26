package org.acouster.context;

import org.acouster.util.StringUtils;

public abstract class ContextBitmap
{
	protected boolean isDisposed = false;
	protected String filename;
	
	//public abstract ContextBitmap GetRotatedBitmap(double degrees);
	public abstract boolean isFragment();
	public abstract int getWidth();
	public abstract int getHeight();
	
	public String getFilename()
	{
		return filename;
	}
	public void dispose()
	{
		if (isDisposed)
			return;
		isDisposed  = true;
		disposeInner();
		//ImageManager.instance().uncache(filename); // no need.. image manager does that
	}
	protected abstract void disposeInner();
}
