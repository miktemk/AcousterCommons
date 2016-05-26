package org.acouster.gravball;

import org.acouster.context.ContextGraphics;
import org.acouster.graphics.RenderableObject2D;

public class NewVisual extends RenderableObject2D
{
	public NewVisual()
	{
		super(null);
	}
	
	@Override
	public void render(ContextGraphics g, int w, int h)
	{
		g.setStroke(0xFF0000, 3);
		g.drawLine(10, 10, 30, 50);
	}
	
}
