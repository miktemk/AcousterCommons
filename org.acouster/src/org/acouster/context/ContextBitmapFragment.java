package org.acouster.context;

public class ContextBitmapFragment extends ContextBitmap
{
	public int srcX, srcY, srcW, srcH;
	public ContextBitmap src;
	
	public ContextBitmapFragment(ContextBitmap src, int srcX, int srcY, int srcW, int srcH) {
		super();
		this.src = src;
		this.srcX = srcX;
		this.srcY = srcY;
		this.srcW = srcW;
		this.srcH = srcH;
	}
	public int getWidth() {
		return srcW;
	}
	public int getHeight() {
		return srcH;
	}
	public boolean isFragment() {
		return true;
	}
	
	protected void disposeInner() {}
	
	public String toString_rect() {
		return String.format("(%d, %d), %d, x %d", srcX, srcY, srcW, srcH);
	}
}
