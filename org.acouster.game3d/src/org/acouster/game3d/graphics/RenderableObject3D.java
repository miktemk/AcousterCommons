package org.acouster.game3d.graphics;

import org.acouster.context.ContextGraphics;
import org.acouster.game3d.math.TransformMatrix3D;
import org.acouster.graphics.RenderableObject;

public abstract class RenderableObject3D extends RenderableObject
{
	protected double distance;
	//TODO: make this guy share a matrix with that of the sprite
	public TransformMatrix3D transform;
	//protected double x, y, z;
	
	public RenderableObject3D()
	{
		transform = new TransformMatrix3D();
	}
//	public TransformMatrix3D getTransform()
//	{
//		return transform;
//	}
	public double getMaxRadius()
	{
		return -1;
	}
	
	
	@Override
	public void render(ContextGraphics g, int w, int h) {
		// obsolete in 3d rendering
	}

	public abstract void render(ContextGraphics g, TransformMatrix3D objectToCam, TransformMatrix3D camToWorld, double focalFactorX, double focalFactorY);
}
