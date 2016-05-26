package org.acouster.gameTests.glasses;

import org.acouster.context.ContextGraphics;
import org.acouster.graphics.RenderableObject2D;
import org.acouster.logic.Sprite2D;
import org.acouster.util.MathUtils;

public abstract class AbstractTorchRenderable extends RenderableObject2D
{
	private static final double TILT_FACTOR = 0.1;
	private static final double VROOM_THRES = 15;
	private static final int DEVIL_COOL_OFF_FRAMES = 30;
	private static final double PROB_DEVIL = 0.4;
	
	protected double angleTilt;
	protected boolean isVroomingNext, isVrooming;
	protected int vroomCooloffMeter = 0;
	protected ITorchWorld world;

	public AbstractTorchRenderable(Sprite2D trans, ITorchWorld world) {
		super(trans);
		this.world = world;
	}

	public void setAcceleration(double accel)
	{
		angleTilt = accel * TILT_FACTOR;
		// TODO: this is good logic for vroom,
		// but this is bad logic for devils, instead remember that thresh was exceeded and only upon decceleration, fire out the devil
		if (Math.abs(accel) > VROOM_THRES)
		{
			//setVroomEnabled(true);
			if (vroomCooloffMeter == 0) {
				vroomCooloffMeter = DEVIL_COOL_OFF_FRAMES;
				isVroomingNext = true;
				vroomed();
				if (MathUtils.randBoolean(PROB_DEVIL))
					world.addLaughingDevil(30 * MathUtils.sign(-accel), 0);
			}
		}
	}
	/** override when want to release devils on VROOM! */
	public void vroomed() {}
	
	@Override
	public void increment(int width, int height) {
		super.increment(width, height);
		isVrooming = false;
		if (isVroomingNext)
			isVrooming = isVroomingNext;
		if (vroomCooloffMeter > 0)
			vroomCooloffMeter--;
	}
}
