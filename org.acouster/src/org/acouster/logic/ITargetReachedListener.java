package org.acouster.logic;

public interface ITargetReachedListener<TMover, TTarget>
{
	/** Called when mover reaches target. Called typically by the sprite.
	 * @param state - State is that of the mover which will let the implementing listener
	 * know what context this event happened in. */
	void objectReachedTarget(TMover mover, TTarget target, int state);
}
