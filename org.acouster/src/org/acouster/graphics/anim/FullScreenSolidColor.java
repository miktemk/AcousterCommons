package org.acouster.graphics.anim;

import org.acouster.context.ContextGraphics;
import org.acouster.graphics.BasicGraphics;
import org.acouster.graphics.RenderableObject;

public class FullScreenSolidColor extends RenderableObject
{

	protected int rgb;
	
	public FullScreenSolidColor(int rgb)
	{
		super();
		this.rgb = rgb;
	}

	@Override
	public void render(ContextGraphics g, int w, int h)
	{
		if (!visible)
			return;
		BasicGraphics.paintBackground(g, w, h, rgb);
	}

	public void setColor(int rgb)
	{
		this.rgb = rgb;
	}
}
