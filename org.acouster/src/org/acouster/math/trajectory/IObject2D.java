package org.acouster.math.trajectory;

import org.acouster.math.Vector2D;

public interface IObject2D
{
    public boolean checkCollision(Vector2D v);
    public void getCenter(Vector2D fillMe);
    // TODO: why do we need getSize?
    //public void getSize(Vector2D fillMe);
}
