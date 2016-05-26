package org.acouster.logic;

import org.acouster.graphics.RenderableObject2D;

/** Contract with {@link RenderableObject2D}: gives TOP-LEFT corner position based on renderableDimensionsChanged callback.
 * This callback is triggered by RenderableObject2D.setDefaultDimensions, etc */
public class Sprite2DHtmlPositioned extends Sprite2D
{
	protected HtmlPositioner positioner;
	
	public Sprite2DHtmlPositioned() {
		this(new HtmlPositioner(0, 0), null);
	}
	public Sprite2DHtmlPositioned(HtmlPositioner positioner) {
		this(positioner, null);
	}
	public Sprite2DHtmlPositioned(HtmlPositioner positioner, String name)
	{
		super(name, 0, 0);
		this.positioner = positioner;
	}
	
//	public void setDimensions(int width, int height)
//	{
//		positioner.setWidth(width);
//		positioner.setHeight(height);
//	}
	
	public HtmlPositioner getPositioner() {
		return positioner;
	}
	

	//-------------- logic ----------------
	@Override
	public void increment(int width, int height)
	{
		super.increment(width, height);
		transform.setX(positioner.getCacheX());
		transform.setY(positioner.getCacheY());
	}
	
	@Override
	public void dimensionsChanged(int width, int height)
	{
		positioner.updateCache(width, height);
	}
	
	@Override
	public void renderableDimensionsChanged(int width, int height)
	{
		positioner.setObjectDimensions(width, height);
	}
	
}
