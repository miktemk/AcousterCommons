package org.acouster.game3d.graphics;

import org.acouster.game3d.math.TransformMatrix3D;
import org.acouster.graphics.BitmapWithTransform;

public class BillBoardSimple extends BillBoardBase
{
	protected BitmapWithTransform img;
	
	public BillBoardSimple(BitmapWithTransform img) {
		this(1, 100, img);
	}
	public BillBoardSimple(double size, double pixelSize, BitmapWithTransform img)
	{
		super(size, pixelSize);
		this.img = img;
	}
	
	public BitmapWithTransform getImage()
	{
		return img;
	}
	
	@Override
	protected BitmapWithTransform getCurrentImage(TransformMatrix3D objectToCam, TransformMatrix3D camToWorld)
	{
		return img;
	}
	@Override
	public void dispose() {}
}
