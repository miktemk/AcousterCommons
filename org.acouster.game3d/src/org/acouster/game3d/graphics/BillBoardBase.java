package org.acouster.game3d.graphics;

import org.acouster.DebugUtil;
import org.acouster.context.ContextBitmap;
import org.acouster.context.ContextGraphics;
import org.acouster.game3d.math.TransformMatrix3D;
import org.acouster.game3d.math.VectorXYZ;
import org.acouster.graphics.BitmapWithTransform;

public abstract class BillBoardBase extends RenderableObject3D
{
	protected double scalingFactor, size, pixelSize;
	
	public BillBoardBase(double pixelSize) {
		this(1, pixelSize);
	}
	public BillBoardBase(double size, double pixelSize)
	{
		this.size = size;
		this.pixelSize = pixelSize;
		this.scalingFactor = size/pixelSize;
	}
	
	//-------------- abtract methods -----------------------
	protected abstract BitmapWithTransform getCurrentImage(TransformMatrix3D objectToCam, TransformMatrix3D camToWorld);
	public abstract void dispose();
	
	@Override
	public void render(ContextGraphics g, TransformMatrix3D objectToCam, TransformMatrix3D camToWorld, double focalFactorX, double focalFactorY)
	{
		BitmapWithTransform bmp = getCurrentImage(objectToCam, camToWorld);
		if (bmp == null)
			return;
		
		ContextBitmap ci = bmp.getBitmap();
		if (ci == null)
			return;
		
		// TODO: recycle this shit
		VectorXYZ camVector = new VectorXYZ(0, 0, 0, 1);
		objectToCam.transformNoW(camVector, camVector);
		int xBase = (int)(focalFactorX * camVector.x / Math.abs(camVector.z));
		int yBase = (int)(-focalFactorY * camVector.y / Math.abs(camVector.z));
		int width2B = (int)(scalingFactor * focalFactorY*ci.getWidth()/camVector.z);
		int height2B = (int)(scalingFactor * focalFactorY*ci.getHeight()/camVector.z);
		g.drawImage(ci, xBase-width2B/2, yBase-height2B, width2B, height2B, bmp.isFlipX(), bmp.isFlipY());
	}

	public void setSize(double size) {
		this.size = size;
	}
	public double getSize() {
		return size;
	}
	public double getApproximateRealSize() {
		return size;
	}
	
	
	
}
