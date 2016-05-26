package org.acouster.math;

import org.acouster.util.MathUtils;

/** double x, y and ability to set, increment and rotate this bozgor. */
public class Vector2D
{
    protected double x, y;

    public Vector2D(double x, double y) 
    {
        super();
        this.x = x;
        this.y = y;
    }
    public Vector2D(Vector2D tip, Vector2D base) 
    {
        this(tip.x-base.x, tip.y-base.y);
    }
    public Vector2D(Vector2D cloneMe) 
    {
        this(cloneMe.x, cloneMe.y);
    }
    public Vector2D()
    {
    	this(0, 0);
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public int getIntX() { return (int)x; }
    public int getIntY() { return (int)y; }
    public long getLongX() { return (long)x; }
    public long getLongY() { return (long)y; }
    
    public boolean isZero() { return x == 0.0 && y == 0.0; }

    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
    public void set(double x, double y) 
    {
        this.x = x;
        this.y = y;
    }
    public void set(Vector2D v) 
    {
        x = v.x;
        y = v.y;
    }
   public void setR(double r, double aRadians) 
    {
        this.x = r * Math.cos(aRadians);
        this.y = r * Math.sin(aRadians);;
    }
    
    public void normalaze(double r)
    {
    	if (x == 0 && y == 0)
    		return;
        double l = Math.sqrt(x*x + y*y);
        double scale = r/l;
        x *= scale;
        y *= scale;
    }
    
    public void incX(double dx) 
    {
        x += dx;
    }
    public void incY(double dy) 
    {
        y += dy;
    }
    public void inc(double dx, double dy) 
    {
        x += dx;
        y += dy;
    }
    public void inc(Vector2D v) 
    {
        x += v.x;
        y += v.y;
    }
    public void dec(double dx, double dy) 
    {
        x -= dx;
        y -= dy;
    }
    public void dec(Vector2D v) 
    {
        x -= v.x;
        y -= v.y;
    }
    /**
     * 
     * @param aRadians
     * rotates vector by aRadians radians clockwise
     */
         
    public void rotate(double aRadians)
    {
        double s = Math.sin(aRadians);
        double c = Math.cos(aRadians);
        
        double xn = x * c - y * s;
        double yn = x * s + y * c;
        x = xn;
        y = yn;
    }
    /**
     * 
     * @param v
     * @param aRadians
     */
         
    public void rotate(Vector2D v, double aRadians)
    {
        set(v);
        rotate(aRadians);
    }
    public void setDiff(Vector2D p, Vector2D m)
    {
        x = p.x-m.x;
        y = p.y-m.y;
    }
    public void setSum(Vector2D f, Vector2D s)
    {
        x = f.x+s.x;
        y = f.y+s.y;
    }
    
    public double getDistance(Vector2D v)
    {
        double dx = x-v.x;
        double dy = y-v.y;
        return Math.sqrt(dx*dx+dy*dy);
    }
    /**
     * 
     * @param v
     * @return scalar dot product of this and v 
     */
    public double dot(Vector2D v)
    {
        return MathUtils.dot(x, y, v.x, v.y);
    }
    /**
     * 
     * @param v
     * @return Z component of cross product vector: this X v
     * positive when rotation from this to v is counterclockwise
     */
    public double crossZ(Vector2D v)
    {
        return x*v.y - y*v.x;
    }
    public double getAngle()
    {
		return MathUtils.getSpriteAngle(x, y);
	}
    
    public String toString()
    {
        return " x:"+x+" y:"+y;
    }

    // Recycled singleton
    public static final Vector2D shared = new Vector2D();
	
}
