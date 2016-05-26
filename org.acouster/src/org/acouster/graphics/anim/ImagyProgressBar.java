package org.acouster.graphics.anim;

import org.acouster.context.ContextBitmap;
import org.acouster.context.ContextBitmapFragment;
import org.acouster.context.ContextGraphics;
import org.acouster.context.ImageManager;
import org.acouster.graphics.RenderableObject2D;
import org.acouster.logic.SimpleXyPositioner;
import org.acouster.logic.SimpleXyPositionerRocking;
import org.acouster.logic.Sprite2D;
import org.acouster.util.MathUtils;

/** Usage:
 * <ul>
 *   <!--<li>new ImagyProgressBar(sprite, ImagyProgressBar.ORIENTATION_HORIZONTAL).setDimensions(20, 200)</li>-->
 *   <li>new ImagyProgressBar(sprite, ImagyProgressBar.ORIENTATION_VERTICAL).setImages(imgBg, imgFill)</li>
 * </ul>
 */
public class ImagyProgressBar extends RenderableObject2D
{
	public static final int ORIENTATION_HORIZONTAL = 1;
	public static final int ORIENTATION_VERTICAL = 2;
	
	protected int orientation;
	protected ContextBitmap imgBg;
	protected ContextBitmapFragment imgFill;
	private double progress;
	private int padTop, padBottom;
	
	public ImagyProgressBar(Sprite2D sprite, int orientation) {
		super(sprite);
		this.orientation = orientation;
		// defaults
		padTop = 10;
		padBottom = 10;
		setProgress(0);
	}
	
//	public ImagyProgressBar setDimensions(int w, int h) {
//		setDefaultDimensions(w, h);
//		return this;
//	}
	
	public ImagyProgressBar setImages(ContextBitmap imgBg, ContextBitmap imgFill) {
		this.imgBg = imgBg;
		this.imgFill = new ContextBitmapFragment(imgFill, 0, 0, imgBg.getWidth(), imgBg.getHeight());
		setDefaultDimensions(imgBg.getWidth(), imgBg.getHeight());
		return this;
	}
	
	/** in pixels */
	public ImagyProgressBar setPadding(int padTop, int padBottom) {
		this.padTop = padTop;
		this.padBottom = padBottom;
		return this;
	}
	
	@Override
	public ImagyProgressBar setAlign(int align) {
		super.setAlign(align);
		return this;
	}
	
	/** value 0 - 1.0 */
	public void setProgress(double progress) {
		this.progress = progress;
	}
	
	@Override
	public void increment(int width, int height) {
		super.increment(width, height);
		if (orientation == ORIENTATION_HORIZONTAL) {
			imgFill.srcX = 0;
			imgFill.srcY = 0;
			imgFill.srcW = (progress < 1.0)
					? (int)(progress*(imgBg.getWidth() - padTop - padBottom)) + padTop
					: imgBg.getWidth();
			imgFill.srcH = imgFill.getHeight();
		}
		else {
			int fromBottom = (progress < 1.0)
					? (int)(progress*(imgBg.getHeight() - padTop - padBottom)) + padTop
					: imgBg.getHeight();
			imgFill.srcX = 0;
			imgFill.srcY = imgBg.getHeight() - fromBottom;
			imgFill.srcW = imgFill.getWidth();
			imgFill.srcH = fromBottom;
		}
	}
	
	@Override
	public void render(ContextGraphics g, int w, int h)
	{
		int x = sprite.getIntX();
		int y = sprite.getIntY();
		int xoffset = (align == ALIGN_RIGHT) ? -width : 0;
		g.drawImage(imgBg, x + xoffset, y, width, height);
		g.drawImage(imgFill,
				x + xoffset + imgFill.srcX,
				y + imgFill.srcY,
				imgFill.srcW,
				imgFill.srcH);
	}

}
