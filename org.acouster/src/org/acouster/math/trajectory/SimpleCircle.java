package org.acouster.math.trajectory;

import org.acouster.math.Vector2D;
import org.acouster.util.MathUtils;

public class SimpleCircle implements ICircle
{
    private double radius;
    private Vector2D center;
    private Vector2D size;
    
    public SimpleCircle(Vector2D center, double radius)
    {
        this.center = center;
        this.radius = radius;
        size = new Vector2D(radius, radius);
    }
    
    @Override
    public boolean checkCollision(Vector2D v)
    {
        return MathUtils.collisionCircle(center.getX(), center.getY(), radius, v.getX(), v.getY());
    }

    @Override
    public void getCenter(Vector2D fillMe)
    {
        fillMe.set(center);
    }

//    @Override
//    public Vector2D getSize()
//    {
//        return size;
//    }

    @Override
    public double getRadius()
    {
        return radius;
    }

}
