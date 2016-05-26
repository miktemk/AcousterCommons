package org.acouster.graphics.anim;

import org.acouster.context.ContextBitmap;
import org.acouster.context.ContextGraphics;
import org.acouster.graphics.BitmapWithTransform;
import org.acouster.graphics.RenderableObject2D;

public class ScreenCenteredImage extends RenderableObject2D {

	protected ContextBitmap[] images;
	protected double angle;
	protected BitmapWithTransform image;
	
	public ScreenCenteredImage(BitmapWithTransform image)
	{
		super(null);
		this.image = image;
		setDefaultDimensions(image.getBitmap().getWidth(), image.getBitmap().getHeight());
	}
	
	@Override
	public void render(ContextGraphics g, int w, int h)
	{
		if (!visible)
			return;
		g.drawImage(image.getBitmap(), (w-width)/2, (h-height)/2, width, height);
	}
}
