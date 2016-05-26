package org.acouster.graphics.anim;

import org.acouster.context.ContextBitmap;
import org.acouster.context.ContextGraphics;
import org.acouster.graphics.BitmapWithTransform;
import org.acouster.graphics.RenderableObject;
import org.acouster.util.MathUtils;

public class FullScreenImage extends RenderableObject {

	protected ContextBitmap[] images;
	protected double angle;
	protected int width, height;
	protected BitmapWithTransform image;
	protected boolean imageLandscape;
	protected int fitOrientationRotation;
	
	public FullScreenImage(BitmapWithTransform image)
	{
		this(image, BitmapWithTransform.ROTATION_4_WAY_0);
	}
	/** fitOrientationRotation - BitmapWithTransform.ROTATION_4_WAY_90 or BitmapWithTransform.ROTATION_4_WAY_270 */
	public FullScreenImage(BitmapWithTransform image, int fitOrientationRotation)
	{
		super();
		this.image = image;
		this.fitOrientationRotation = fitOrientationRotation;
		imageLandscape = image.getBitmap().getWidth() >= image.getBitmap().getHeight();
	}

	@Override
	public void render(ContextGraphics g, int w, int h)
	{
		if (!visible)
			return;
		//g.enableImageAntialiasing(true);
		// the following if: user wants reorientation (90 or 270) AND landscapeness does not match
		if ((fitOrientationRotation != BitmapWithTransform.ROTATION_4_WAY_0) && (w >= h) != imageLandscape) {
			// rotate 90 degrees
			//image.setRotation4way(fitOrientationRotation);
			// TODO: find a better way to render this, maybe there is a better rotation function
			int xOffset = 0;
			int yOffset = 0;
			if (fitOrientationRotation == BitmapWithTransform.ROTATION_4_WAY_90)
				xOffset = w;
			else if (fitOrientationRotation == BitmapWithTransform.ROTATION_4_WAY_270)
				yOffset = h;
			g.drawImage(image.getBitmap(), xOffset, yOffset, h, w, MathUtils.angleToRadians(fitOrientationRotation), false);
		} else {
			g.drawImage(image.getBitmap(), 0, 0, w, h);
		}
		//g.enableImageAntialiasing(false);
		
	}
}
