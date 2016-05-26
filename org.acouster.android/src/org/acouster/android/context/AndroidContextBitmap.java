package org.acouster.android.context;

import org.acouster.context.ContextBitmap;

import android.graphics.Bitmap;

public class AndroidContextBitmap extends ContextBitmap {

	private Bitmap bitmap;
	public AndroidContextBitmap(Bitmap bitmap) {
		super();
		this.bitmap = bitmap;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	
	@Override
	public int getWidth() {
		return bitmap.getWidth();
	}
	@Override
	public int getHeight() {
		return bitmap.getHeight();
	}
	@Override
	public boolean isFragment() {
		return false;
	}

	@Override
	protected void disposeInner() {
		bitmap.recycle();
		bitmap = null;
	}

}
