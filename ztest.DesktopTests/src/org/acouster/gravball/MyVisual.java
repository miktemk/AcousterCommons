package org.acouster.gravball;

import org.acouster.context.ContextBitmap;
import org.acouster.context.ContextGraphics;
import org.acouster.graphics.RenderableObject2D;

public class MyVisual extends RenderableObject2D
{
	protected ContextBitmap bmp;
	public MyVisual(MySprite sprite, ContextBitmap bmp)
	{
		super(sprite);
		this.bmp = bmp;
	}
	
	@Override
	public void render(ContextGraphics g, int w, int h)
	{
		double x = sprite.getTransform().getX();
		double y = sprite.getTransform().getY();
		g.drawImageCentered(bmp, (int)(x), (int)(y), bmp.getWidth(), bmp.getHeight());
	}
}
