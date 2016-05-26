package org.acouster.graphics.anim;

import org.acouster.INodeGetter;
import org.acouster.context.ContextBitmap;
import org.acouster.context.ContextGraphics;
import org.acouster.graphics.BitmapWithTransform;
import org.acouster.graphics.RenderableObject2D;
import org.acouster.logic.Sprite2D;

public class AbstractImageRenderable extends RenderableObject2D
{
	public static final int HORIZONTAL_ALIGN_CENTER = 0;
	public static final int HORIZONTAL_ALIGN_LEFT = 1;
	public static final int HORIZONTAL_ALIGN_RIGHT = 2;
	public static final int VERTICAL_ALIGN_CENTER = 0;
	public static final int VERTICAL_ALIGN_TOP = 1;
	public static final int VERTICAL_ALIGN_BOTTOM = 2;

	protected double scale;
	protected INodeGetter<BitmapWithTransform> images;
	protected BitmapWithTransform curBmp;
	protected ContextBitmap curCi;
	protected int imgX, imgY, imgW, imgH;
	protected int hAlign = HORIZONTAL_ALIGN_CENTER,
			vAlign = VERTICAL_ALIGN_CENTER;
	
	public AbstractImageRenderable(Sprite2D trans, INodeGetter<BitmapWithTransform> images) {
		this(trans, images, 1);
	}
	public AbstractImageRenderable(Sprite2D trans, INodeGetter<BitmapWithTransform> images, double scale)
	{
		super(trans);
		this.images = images;
		this.scale = scale;
	}
	
	public double getScale() {
		return scale;
	}
	public void setScale(double scale) {
		this.scale = scale;
	}
	public int getHorizontalAlign() {
		return hAlign;
	}
	/** one of ImageRenderable.HORIZONTAL_ALIGN_* */
	public void setHorizontalAlign(int hAlign) {
		this.hAlign = hAlign;
	}
	public int getVerticalAlign() {
		return vAlign;
	}
	/** one of ImageRenderable.VERTICAL_ALIGN_* */
	public void setVerticalAlign(int vAlign) {
		this.vAlign = vAlign;
	}
	
	@Override
	public void render(ContextGraphics g, int w, int h)
	{
		recomputeCurImageCoords();
		if (curCi != null)
		{
			g.drawImage(curCi, imgX, imgY, imgW, imgH, curBmp.isFlipX(), curBmp.isFlipY());
			//g.setColor(Colorz.BLACK);
			//BasicGraphics.drawCrosshair(g, sprite.getIntX(), sprite.getIntY());
		}
	}
	
	protected void recomputeCurImageCoords()
	{
		curCi = null;
		curBmp = images.getCurrentNode();
		if (curBmp == null)
			return;
		curCi = curBmp.getBitmap();
		if (curCi == null)
			return;
		imgW = (int)(scale*curCi.getWidth());
		imgH = (int)(scale*curCi.getHeight());
		switch (hAlign)
		{
		case HORIZONTAL_ALIGN_CENTER:
			imgX = (int)(sprite.getX() - imgW / 2);
			break;
		case HORIZONTAL_ALIGN_LEFT:
			imgX = (int)(sprite.getX());
			break;
		case HORIZONTAL_ALIGN_RIGHT:
			imgX = (int)(sprite.getX() - imgW);
			break;
		}
		switch (vAlign)
		{
		case VERTICAL_ALIGN_CENTER:
			imgY = (int)(sprite.getY() - imgH / 2);
			break;
		case VERTICAL_ALIGN_TOP:
			imgY = (int)(sprite.getY());
			break;
		case VERTICAL_ALIGN_BOTTOM:
			imgY = (int)(sprite.getY() - imgH);
			break;
		}
	}
	
	public int getWidth() {
		BitmapWithTransform bmp = images.getCurrentNode();
		if (bmp == null)
			return 0;
		ContextBitmap ci = bmp.getBitmap();
		if (ci == null)
			return 0;
		return ci.getWidth();
	}
	public int getHeight() {
		BitmapWithTransform bmp = images.getCurrentNode();
		if (bmp == null)
			return 0;
		ContextBitmap ci = bmp.getBitmap();
		if (ci == null)
			return 0;
		return ci.getHeight();
	}

	public void dispose()
	{
		// TODO: dispose of all nodes hehe
	}
}
