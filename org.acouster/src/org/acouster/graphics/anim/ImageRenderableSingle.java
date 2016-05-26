package org.acouster.graphics.anim;

import org.acouster.logic.Sprite2D;
import org.acouster.res.ImageSequence;

public class ImageRenderableSingle extends AbstractImageRenderable
{
	public ImageRenderableSingle(Sprite2D trans, String filename)
	{
		// TODO: SingleImageGetter
		super(trans, new ImageSequence(filename, 1, 0));
	}
	public ImageRenderableSingle(Sprite2D trans, String filename, double scale)
	{
		// TODO: SingleImageGetter instead of ImageSequence(filename, 1, 0)
		super(trans, new ImageSequence(filename, 1, 0), scale);
	}
}
