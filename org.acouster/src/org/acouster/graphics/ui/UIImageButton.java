package org.acouster.graphics.ui;

import org.acouster.context.ContextBitmap;
import org.acouster.context.ContextGraphics;
import org.acouster.logic.Sprite2D;
import org.acouster.util.StringUtils;

public class UIImageButton extends UIButton
{
	protected ContextBitmap img, imgDown;
	protected int rgbUp = 0x0, rgbDown = 0x0;
	
	public UIImageButton(ContextBitmap img) {
		this(img, img, "", null);
	}
	public UIImageButton(ContextBitmap img, ContextBitmap imgDown)
	{
		this(img, imgDown, "", null);
	}
	public UIImageButton(ContextBitmap img, ContextBitmap imgDown, String text) {
		this(img, imgDown, text, null);
	}
	public UIImageButton(ContextBitmap img, ContextBitmap imgDown, String text, Sprite2D trans)
	{
		super(text, trans);
		this.img = img;
		this.imgDown = imgDown;
		if (img != null)
			setPreferredDimensions(img.getWidth(), img.getHeight());
	}
	
	public UIImageButton setTextColors(int rgbUp, int rgbDown) {
		this.rgbUp = rgbUp;
		this.rgbDown = rgbDown;
		return this;
	}

	@Override
	public void render(ContextGraphics g, int w, int h)
	{
		ContextBitmap imggg = img;
		if (isDown)
			imggg = imgDown;
		if (imggg != null) {
			g.drawImage(imggg, x, y, myWidth, myHeight);
		}
		if (!StringUtils.isNullOrEmpty(text)) {
			g.setColor(isDown ? rgbDown : rgbUp);
			drawTextNiceAndCentered(g, x + myWidth/2, y + myHeight/2, myWidth);
		}
		//g.drawLine(x, y, x, y + myHeight/2);
		//g.drawLine(x, y + myHeight/2, x+200, y + myHeight/2);
	}
}
