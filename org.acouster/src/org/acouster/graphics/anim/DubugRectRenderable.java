package org.acouster.graphics.anim;

import org.acouster.context.ContextGraphics;
import org.acouster.graphics.RenderableObject2D;
import org.acouster.logic.Sprite2D;

public class DubugRectRenderable extends RenderableObject2D {

	protected int width, height;
	
	public DubugRectRenderable(Sprite2D sss, int width, int height)
	{
		super(sss);
		this.width = width;
		this.height = height;
	}
	
	@Override
	public void render(ContextGraphics g, int w, int h)
	{
		if (!visible)
			return;
		g.setColor(0x0);
		g.drawRect(sprite.getTransform().getIntX()-width/2, sprite.getTransform().getIntY()-height/2, width, height);
	}
}
