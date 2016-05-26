package org.acouster.game3d.math;

public class VectorXYZ
{
	public float x, y, z, w;
	public VectorXYZ(double x, double y, double z)
	{
		this(x, y, z, 1);
	}
	public VectorXYZ(double x, double y, double z, double w)
	{
		super();
		this.x = (float)x;
		this.y = (float)y;
		this.z = (float)z;
		this.w = (float)w;
	}
	
	public void set(double x, double y, double z, double w)
	{
		this.x = (float)x;
		this.y = (float)y;
		this.z = (float)z;
		this.w = (float)w;
	}
	public void set(double x, double y, double z)
	{
		this.x = (float)x;
		this.y = (float)y;
		this.z = (float)z;
		this.w = 1;
	}
	public void reset()
	{
		this.x = 0;
		this.y = 0;
		this.z = 0;
		this.w = 1;
	}
	public double dot(double x, double y, double z, double w)
	{
		return this.x*x + this.y*y + this.z*z + this.w*w;
	}
	
	public String toString()
	{
		return "("+x+", "+y+", "+z+")";
	}

	public void scale(double factor)
	{
		this.x *= factor;
		this.y *= factor;
		this.z *= factor;
	}
}
