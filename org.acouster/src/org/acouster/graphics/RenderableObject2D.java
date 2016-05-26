package org.acouster.graphics;

import org.acouster.context.ContextGraphics;
import org.acouster.logic.Sprite2D;

public abstract class RenderableObject2D extends RenderableObject
{
	public static final int ALIGN_LEFT = 1;
	public static final int ALIGN_RIGHT = 2;
	
	protected Sprite2D sprite;
	/** on-screen width and height */
	protected int width, height;
	/** original on-screen width and height...
	 * SAFEGUARD: these values are initially set to 1 to avoid /0 */
	protected int origWidth=1, origHeight=1;
	/** width/height... SAFEGUARD: this value is initially set to 1 to avoid /0 */
	protected double aspectRatio=1;
	/** width/origWidth or height/origHeight */
	protected double resizeFactor;
	protected boolean preserveWithImageMaxSize = false;
	
	protected int align = ALIGN_LEFT;
	
	public RenderableObject2D(Sprite2D trans)
	{
		sprite = trans;
	}
	public Sprite2D getSprite() {
		return sprite;
	}
	
	// TODO: see this causes 6 or 7 errors? move dimensions to sprite2D
	/** Fills width, height, aspectRatio. Called by subclasses usually passing dimensions of their images */
	protected void setDefaultDimensions(int width, int height)
	{
		this.origWidth = this.width = width;
		this.origHeight = this.height = height;
		aspectRatio = (double)width/height;
		onDimensionsChanged(getResultWidth(), getResultHeight());
		if (sprite != null)
			sprite.renderableDimensionsChanged(width, height);
	}
	/** Make sure your class calls setDefaultDimensions before this is called */
	public void setWidthKeepAspectRatio(int width)
	{
		this.width = width;
		height = (int)(width / aspectRatio);
		resizeFactor = (double)width/origWidth;
		onDimensionsChanged(getResultWidth(), getResultHeight());
		if (sprite != null)
			sprite.renderableDimensionsChanged(getResultWidth(), getResultHeight());
	}
	/** Make sure your class calls setDefaultDimensions before this is called */
	public void setHeightKeepAspectRatio(int height)
	{
		this.height = height;
		width = (int)(height * aspectRatio);
		resizeFactor = (double)height/origHeight;
		onDimensionsChanged(getResultWidth(), getResultHeight());
		if (sprite != null)
			sprite.renderableDimensionsChanged(getResultWidth(), getResultHeight());
	}
	public void setPreferredDimensions(int width, int height) {
		setDefaultDimensions(width, height);
	}
	/** Called when dimensions change. Override to set sub-dimensions within the renderable */
	protected void onDimensionsChanged(int w, int h) {}
	
	public int getResultWidth()
	{
		if (preserveWithImageMaxSize && width > origWidth)
			return origWidth;
		return width;
	}
	public int getResultHeight()
	{
		if (preserveWithImageMaxSize && height > origHeight)
			return origHeight;
		return height;
	}
	
	public int getAlign() {
		return align;
	}
	public RenderableObject2D setAlign(int align) {
		this.align = align;
		return this;
	}
	
	//=============== misc helpers ===========================
	protected void debug_renderSpriteCrosshair(ContextGraphics g)
	{
		if (sprite != null)
		{
			g.setColor(0x000000);
			BasicGraphics.drawCrosshair(g, sprite.getTransform().getIntX(), sprite.getTransform().getIntY());
		}
	}
}
