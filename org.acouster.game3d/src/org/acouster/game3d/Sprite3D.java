package org.acouster.game3d;

import org.acouster.game3d.graphics.RenderableObject3D;
import org.acouster.game3d.math.GameMath3D;


public class Sprite3D
{
	public static final int V_MAX_DEFAULT = 10;
	protected double x, y, z;
	protected double ay;
	//protected double bob, tilt; // These are not used at the moment
	protected int life;
	protected int frame = 0;
	
	/**
	 * Increment(s)
	 */
	protected double v;
	protected double vMax;
	/**
	 * Bounds
	 */
	protected double radius;
	protected Sprite3D nextCollisionSprite;
	private RenderableObject3D obj;
	
	public Sprite3D(double x, double y, double z)
	{
		setLocation(x, y, z);
		setVMax(V_MAX_DEFAULT);
		radius = 1.7;
	}
	
	//-------- control and logic -------------------
	public void setV(double v)
	{
		this.v = v;
	}
	public void setVMax(double vMax)
	{
		this.vMax = vMax;
	}
	public void accelerate(double accel)
	{
		this.v += accel;
		if (v > vMax)
			v = vMax;
		if (v < -vMax)
			v = -vMax;
	}
	public void incrementPosition()
	{
		z += v*Math.cos(ay);
		x -= v*Math.sin(ay);
		if (obj != null)
		{
			obj.transform.flyby3DConvention_setLocation(x, y, z);
			obj.transform.flyby3DConvention_setAy(ay);
		}
	}
	public void incrementFrame()
	{
		frame++;
	}
	public void turnAY(double ayIncrement)
	{
		ay += ayIncrement;
	}
	public void turnAround()
	{
		ay += Math.PI;
		v = -v;
	}
	public void decrementLife()
	{
		if (life > 0)
			life--;
	}
	public boolean isDead()
	{
		return (life <= 0);
	}
	public void makeDecision(World3d world, Sprite3D player, Spritework content)
	{
		
	}
	
	//-------- related objects ------------------
	//TODO: change the construct
	public void setObject(RenderableObject3D obj)
	{
		this.obj = obj;
		obj.transform.flyby3DConvention_setLocation(x, y, z);
		obj.transform.flyby3DConvention_setAy(ay);
		double rad2b = obj.getMaxRadius();
		if (rad2b > 0)
			this.radius = obj.getMaxRadius();
	}
	public RenderableObject3D getObject()
	{
		return obj;
	}
	public void setNextCollision(Sprite3D sprite)
	{
		this.nextCollisionSprite = sprite;
	}
	
	//-------- coordinates ----------------------
	public void setLocation(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		if (obj != null)
			obj.transform.flyby3DConvention_setLocation(x, y, z);
	}
	public double getX()
	{
		return x;
	}
	public double getY()
	{
		return y;
	}
	public double getZ()
	{
		return z;
	}
	public void setAy(double ay)
	{
		this.ay = ay;
		if (obj != null)
			obj.transform.flyby3DConvention_setAy(ay);
	}
	public double getAy()
	{
		return ay;
	}
	public int getLife()
	{
		return life;
	}
	public void setLife(int life)
	{
		this.life = life;
	}
	public double getV()
	{
		return v;
	}
	public double getRadius()
	{
		return radius;
	}
	public void setRadius(double radius)
	{
		this.radius = radius;
	}
	
	// -------- math ------------------------------------
	public double getDistance2D(Sprite3D s)
	{
		return GameMath3D.distance(x, z, s.x, s.z);
	}
	public double getDistance3D(Sprite3D s)
	{
		return GameMath3D.distance(x, y, z, s.x, s.y, s.z);
	}
	public double getDistance2DSquared(Sprite3D s)
	{
		return GameMath3D.distanceSquared(x, z, s.x, s.z);
	}
	public double getDistance3DSquared(Sprite3D s)
	{
		return GameMath3D.distanceSquared(x, y, z, s.x, s.y, s.z);
	}
	//TODO: get rid of these: we will use sprites to store the
	//location; only sprites should be shown on the radar
	public double getDistance2D(RenderableObject3D s)
	{
		return GameMath3D.distance(x, z, s.transform.value_3_, s.transform.value_11_);
	}
	public double getDistance3D(RenderableObject3D s)
	{
		return GameMath3D.distance(x, y, z, s.transform.value_3_, s.transform.value_7_, s.transform.value_11_);
	}
	public double getDistance2DSquared(RenderableObject3D s)
	{
		return GameMath3D.distanceSquared(x, z, s.transform.value_3_, s.transform.value_11_);
	}
	public double getDistance3DSquared(RenderableObject3D s)
	{
		return GameMath3D.distanceSquared(x, y, z, s.transform.value_3_, s.transform.value_7_, s.transform.value_11_);
	}
}
