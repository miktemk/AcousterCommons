package org.acouster.graphics;

import org.acouster.context.ContextBitmap;

public class BitmapWithTransform
{
	public static final int ROTATION_4_WAY_0 = 0;
	public static final int ROTATION_4_WAY_90 = 90;
	public static final int ROTATION_4_WAY_180 = 180;
	public static final int ROTATION_4_WAY_270 = 270;
	
	private ContextBitmap bmp;
	private boolean flipX, flipY;
	private int rotation4way;
	private boolean isArbitraryAngle;
	private double arbitraryAngle;
	
	public BitmapWithTransform(ContextBitmap bmp, boolean flipX, boolean flipY) {
		this(bmp, flipX, flipY, ROTATION_4_WAY_0);
	}
	public BitmapWithTransform(ContextBitmap bmp, boolean flipX, boolean flipY, int rotation4way) {
				super();
		this.bmp = bmp;
		this.flipX = flipX;
		this.flipY = flipY;
		this.rotation4way = rotation4way;
	}
	
	public ContextBitmap getBitmap() {
		return bmp;
	}
	public boolean isFlipX() {
		return flipX;
	}
	public boolean isFlipY() {
		return flipY;
	}
	public int getRotation4way() {
		return rotation4way;
	}

	public void setBmp(ContextBitmap bmp) {
		this.bmp = bmp;
	}
	public void setFlipX(boolean flipX) {
		this.flipX = flipX;
	}
	public void setFlipY(boolean flipY) {
		this.flipY = flipY;
	}
	public void set(ContextBitmap bmp, boolean flipX, boolean flipY) {
		this.bmp = bmp;
		this.flipX = flipX;
		this.flipY = flipY;
	}
	public void setRotation4way(int rotation4way) {
		this.rotation4way = rotation4way;
	}
	
	public void setArbitraryAngle(double a) {
		isArbitraryAngle = true;
		arbitraryAngle = a;
		if (arbitraryAngle == 0)
			isArbitraryAngle = false;
	}
	public void clearArbitraryAngle() {
		isArbitraryAngle = false;
		arbitraryAngle = 0;
	}
}
