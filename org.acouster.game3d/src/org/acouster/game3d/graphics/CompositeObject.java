package org.acouster.game3d.graphics;

import java.util.Vector;

import org.acouster.context.ContextGraphics;
import org.acouster.game3d.math.TransformMatrix3D;


public class CompositeObject extends RenderableObject3D
{
	public class CompositeElement
	{
		public double x, y, z;
		public RenderableObject3D object;
		public CompositeElement(double x, double y, double z, RenderableObject3D object)
		{
			super();
			this.x = x;
			this.y = y;
			this.z = z;
			this.object = object;
		}
	}
	
	protected Vector<CompositeElement> elements;
	public CompositeObject()
	{
		elements = new Vector<CompositeElement>();
	}
	
	public void addElement(RenderableObject3D obj, double x, double y, double z)
	{
		elements.add(new CompositeElement(x, y, z, obj));
	}
	
	@Override
	public void increment(int width, int height)
	{
		for (CompositeElement elem : elements)
			elem.object.increment(width, height);
	}
	@Override
	public void render(ContextGraphics g, TransformMatrix3D objectToCam, TransformMatrix3D camToWorld, double focalFactorX, double focalFactorY)
	{
		// TODO: this is unfinished
		for (CompositeElement elem : elements)
			elem.object.render(g, objectToCam, camToWorld, focalFactorX, focalFactorY);
	}

}
