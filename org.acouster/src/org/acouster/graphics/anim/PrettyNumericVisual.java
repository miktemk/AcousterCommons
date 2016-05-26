package org.acouster.graphics.anim;

import org.acouster.context.ContextBitmap;
import org.acouster.context.ContextGraphics;
import org.acouster.context.ImageManager;
import org.acouster.graphics.BasicGraphics;
import org.acouster.graphics.RenderableObject2D;
import org.acouster.logic.Sprite2D;
import org.acouster.res.ComicStrip;
import org.acouster.util.MathUtils;

public class PrettyNumericVisual extends RenderableObject2D
{
	protected int value;
	protected ContextBitmap[] digits;
	
	public PrettyNumericVisual(Sprite2D sprite, int value)
	{
		super(sprite);
		this.value = value;
		ComicStrip strip = new ComicStrip(ImageManager.loadR("prettynun.png"), 1, 13, 13);
		digits = strip.getFrames();
		
//		digits = new ContextBitmap[10];
//		for (int i = 0; i < 10; i++)
//		{
//			digits[i] = ImageManager.loadR("prettynum_" + i + ".png");
//		}
		
		// this assumes all digit images are of same dimensions
		// TODO: box
		setDefaultDimensions(digits[0].getWidth(), digits[0].getHeight());
	}
	
	public int getValue() {
		return value;
	}
	public PrettyNumericVisual setValue(int value) {
		this.value = value;
		return this;
	}

	@Override
	public void render(ContextGraphics g, int w, int h)
	{
		//------- old code
		int reduction10 = value;
		int order = MathUtils.intOrder(value);
		int offset = 1;
		int x = (int)sprite.getTransform().getX();
		int y = (int)sprite.getTransform().getY();
		do
		{
			int curDigit = reduction10 % 10;
			if (align == ALIGN_LEFT)
				g.drawImage(digits[curDigit], x+order*width-width*offset, y, width, height);
			else
				g.drawImage(digits[curDigit], x-width*offset, y, width, height);
			reduction10 /= 10;
			offset++;
		}
		while (reduction10 != 0);
		
		//-------- new code
		// TODO: format string to adjust to number of chars
		//String shit = "" + value;
		
		//BasicGraphics.drawCrosshair(g, x, y);
		
		// DEBUG: test of rotation and different image rendering calls
//		g.setColor(0x000000);
//		BasicGraphics.drawCrosshair(g, 200, 200);
//		aaaa += 0.05;
//		BasicGraphics.drawCrosshair(g, 200, 400);
//		BasicGraphics.drawCrosshair(g, 200, 450);
//		BasicGraphics.drawCrosshair(g, 200, 500);
//		g.drawImageCentered(digits[1], 200, 400, digitWidth, digitHeight, true, false, aaaa);
//		g.drawImageCentered(digits[1], 200, 450, digitWidth, digitHeight, false, true, aaaa);
//		g.drawImageCentered(digits[1], 200, 500, digitWidth, digitHeight, true, true, aaaa);
//		g.drawImage(digits[1], 200, 400, digitWidth, digitHeight, true, false);
//		g.drawImage(digits[1], 200, 450, digitWidth, digitHeight, false, true);
//		g.drawImage(digits[1], 200, 500, digitWidth, digitHeight, true, true);
//		
//		g.drawImageCentered(digits[1], 200, 200, digitWidth, digitHeight);
//		g.drawImageCentered(digits[1], 200, 200, digitWidth, digitHeight, aaaa);
////		g.drawImage(digits[1], 200, 200, digitWidth, digitHeight);
////		g.drawImage(digits[1], 200, 200, digitWidth, digitHeight, aaaa);
	}
	
	private float aaaa = 0;
	
}