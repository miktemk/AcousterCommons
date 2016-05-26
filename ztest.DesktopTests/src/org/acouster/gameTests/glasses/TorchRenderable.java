package org.acouster.gameTests.glasses;

import org.acouster.context.ContextGraphics;
import org.acouster.graphics.anim.ImageRenderable;
import org.acouster.graphics.anim.ImageRenderableSingle;
import org.acouster.logic.Sprite2D;
import org.acouster.res.ImageSequence;

/**
 * Initialize with any Sprite2D function in GameUtils and any possible
 * INavigableGraph extensions found in -.-.res such as ImageSequence
 */
public class TorchRenderable extends AbstractTorchRenderable
{
	private ImageRenderable flame;
	private ImageRenderableSingle handle;
	
	public TorchRenderable(Sprite2D trans, ITorchWorld world)
	{
		super(trans, world);
//		flame = new ImageRenderable(
//				trans,
//				new ImageSequence("torch_flame_1_{num}.png", 8, 0.1), 0.5);
		flame = new ImageRenderable(
				trans,
				new ImageSequence("torch_flame_2_{num}.png", 7, 0.07), 0.5);
		handle = new ImageRenderableSingle(trans, "torch_handle_brown.png", 0.5);
		flame.setVerticalAlign(ImageRenderable.VERTICAL_ALIGN_BOTTOM);
		handle.setVerticalAlign(ImageRenderable.VERTICAL_ALIGN_TOP);
	}
	
	@Override
	public void render(ContextGraphics g, int w, int h)
	{
		handle.render(g, w, h);
		flame.render(g, w, h);
	}
	
	@Override
	public void increment(int width, int height) {
		super.increment(width, height);
		flame.increment(width, height);
	}
	
	@Override
	public void vroomed() {
		super.vroomed();
		world.playSound(Constants.Sounds.VROOM);
	}
	
}
