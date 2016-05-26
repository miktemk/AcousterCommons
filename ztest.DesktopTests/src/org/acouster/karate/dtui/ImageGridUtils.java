package org.acouster.karate.dtui;

import java.awt.image.BufferedImage;

import org.acouster.context.desktop.ContextBitmap_BufferedImage;
import org.acouster.context.desktop.DesktopContextGraphics;

public class ImageGridUtils
{
	public static void drawImageTuple(DesktopContextGraphics ggg, IImageGridItem img, int angle, int w, int h)
	{
		BufferedImage bi = img.getBi(angle);
		boolean flip = img.getIsFlipped(angle);
		if (flip)
			ggg.drawImage(ContextBitmap_BufferedImage.ofBi(bi), 0, 0, w, h);
		else
			ggg.drawImage(ContextBitmap_BufferedImage.ofBi(bi), 0, 0, w, h, true, false);
	}
}


