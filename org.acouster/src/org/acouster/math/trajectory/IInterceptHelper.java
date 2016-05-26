package org.acouster.math.trajectory;

import java.util.List;

import org.acouster.math.Vector2D;

/**  */
public interface IInterceptHelper 
{
	List<IObject2D> getCandidates(Vector2D pos);
	/** Returns first collision object. */
    IObject2D checkCollision(Vector2D v);
}
