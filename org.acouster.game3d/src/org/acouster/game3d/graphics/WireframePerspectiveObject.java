package org.acouster.game3d.graphics;

import org.acouster.context.ContextGraphics;
import org.acouster.game3d.geometry.Line3D;
import org.acouster.game3d.geometry.Point2D;
import org.acouster.game3d.geometry.Shape3D;
import org.acouster.game3d.math.TransformMatrix3D;

public class WireframePerspectiveObject extends RenderableObject3D
{
	public static final double GEOMETRY_SCALE_FACTOR = 0.02;
	
	protected WireframeData data;
	protected int colorRgb;

	public WireframePerspectiveObject(WireframeData data)
	{
		this(data, 0x00FFFF);
	}
	
	public WireframePerspectiveObject(WireframeData data, int colorRgb)
	{
		super();
		this.data = data;
		setColor(colorRgb);
	}

	public WireframeData getData()
	{
		return data;
	}
	public void setData(WireframeData data)
	{
		this.data = data;
	}
	
	public int getColor()
	{
		return colorRgb;
	}
	public void setColor(int colorRgb)
	{
		this.colorRgb = colorRgb;
	}
	
	public double getMaxRadius()
	{
		return data.getMaxRadius();
	}

	@Override
	public void render(ContextGraphics g, TransformMatrix3D objectToCam, TransformMatrix3D camToWorld, double focalFactorX, double focalFactorY)
	{
		Shape3D[] shapes = data.getShapes();
		
		if (shapes == null)
			return;
		
		g.setColor(colorRgb);
		
		Point2D[] screenPoints = data.transformPoints(objectToCam, focalFactorX, focalFactorY);
		
		for (int i = 0; i < shapes.length; i++)
		{
			Line3D line3D = (Line3D)shapes[i];

			Point2D p1 = screenPoints[line3D.p1Index];
			Point2D p2 = screenPoints[line3D.p2Index];
			g.drawLine(p1.x, p1.y, p2.x, p2.y);
		}
	}
}
