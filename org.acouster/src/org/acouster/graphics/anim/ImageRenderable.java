package org.acouster.graphics.anim;

import org.acouster.GameEvent;
import org.acouster.GraphTicker;
import org.acouster.INavigableGraph;
import org.acouster.INodeGetter;
import org.acouster.context.ContextBitmap;
import org.acouster.context.ContextGraphics;
import org.acouster.graphics.BitmapWithTransform;
import org.acouster.graphics.RenderableObject2D;
import org.acouster.logic.Sprite2D;

/**
 * Initialize with any Sprite2D function in GameUtils and any possible
 * INavigableGraph extensions found in -.-.res such as ImageSequence
 */
public class ImageRenderable extends AbstractImageRenderable
{
	protected GraphTicker<BitmapWithTransform> ticker;
	public ImageRenderable(Sprite2D trans, INavigableGraph<BitmapWithTransform> images) {
		this(trans, images, 1);
	}
	public ImageRenderable(Sprite2D trans, INavigableGraph<BitmapWithTransform> images, double scale) {
		super(trans, images, scale);
		ticker = new GraphTicker<BitmapWithTransform>(images);
	}
	
	@Override
	public void increment(int width, int height) {
		ticker.increment();
	}
	
	@Override
	public void handleMessage(GameEvent event)
	{
		ticker.gotoNode(event.getBody());
	}

}
