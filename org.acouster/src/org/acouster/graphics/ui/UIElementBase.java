package org.acouster.graphics.ui;

import org.acouster.context.ContextMouseEvent;
import org.acouster.graphics.RenderableObject2D;
import org.acouster.logic.Sprite2D;
import org.acouster.util.MathUtils;

public abstract class UIElementBase extends RenderableObject2D implements IUIElement
{
	public UIElementBase() {
		this(null);
	}
	public UIElementBase(Sprite2D trans) {
		super(trans);
	}
	
	protected int x, y, myWidth, myHeight, prefWidth=0, prefHeight=0;
	
	// builder pattern
	public UIElementBase setFrame(int x, int y, int width, int height)
	{
		this.x = x;
		this.y = y;
		this.myWidth = width;
		this.myHeight = height;
		return this;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getMyWidth() {
		return myWidth;
	}
	public int getMyHeight() {
		return myHeight;
	}

	public UIElementBase setVisible(boolean isVisible) {
		super.setVisible(isVisible);
		return this;
	}
	
	public boolean checkCollision(int mx, int my) {
		return MathUtils.collisionRect(x, y, myWidth, myHeight, mx, my);
	}
	
	// parameters
	public boolean isPreferredDimensionsAvailable() { return (prefWidth != 0 && prefHeight != 0); }
	public int getPreferredWidth() { return prefWidth; }
	public int getPreferredHeight() { return prefHeight; }
	public void setPreferredDimensions(int prefWidth, int prefHeight)
	{
		this.prefWidth = prefWidth;
		this.prefHeight = prefHeight;
	}
	/** @returns prefWidth/prefHeight... or 1 if they have not been initialized */
	public float getPreferredAspectRatio() {
		if (!isPreferredDimensionsAvailable())
			return 1;
		return (float)prefWidth / prefHeight;
	}
	
	// graphics
//	@Override
//	public void render(ContextGraphics g, int w, int h)
//	{
//		//if (!visible)
//		//	return;
//		renderInternal(g);
//	}
//	protected abstract void renderInternal(ContextGraphics g);
	
	// interaction
	public abstract void mousePressed(ContextMouseEvent e);
	public abstract void mouseDragged(ContextMouseEvent e);
	public abstract void mouseReleased(ContextMouseEvent e);
	
}
