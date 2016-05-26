package org.acouster.game3d.geometry;

import java.util.Iterator;
import java.util.Vector;

import org.acouster.game3d.math.VectorXYZ;


public class Point3DSet
{
	public static final double EQUALITY_THRESHOLD = 0.0;
	//TODO: maybe use a binary tree? Then we need a compile
	
	protected Vector<VectorXYZ> points;
	public Point3DSet()
	{
		points = new Vector<VectorXYZ>();
	}
	public int addPoint(double x, double y, double z)
	{
		n_total++;
		int index = 0;
		for (Iterator<VectorXYZ> i = points.iterator(); i.hasNext();)
		{
			VectorXYZ v = i.next();
			if ((Math.abs(v.x-x) <= EQUALITY_THRESHOLD) && (Math.abs(v.y-y) <= EQUALITY_THRESHOLD) && (Math.abs(v.z-z) <= EQUALITY_THRESHOLD))
			{
				//System.out.println("dx="+(v.x-x)+" ");
				n_redund++;
				return index;
			}
			index++;
		}
		
		//INVARIANT: index = points.size() at this point == index of the newly added point
		points.add(new VectorXYZ(x, y, z, 1));
		return index;
	}
	
	private int n_redund = 0;
	private int n_total = 0;
	public VectorXYZ[] compile()
	{
		//System.out.println("n_unique="+points.size()+" n_redund="+n_redund+" n_total:"+n_total);
		return points.toArray(new VectorXYZ[points.size()]);
	}
	
}
