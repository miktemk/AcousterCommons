package org.acouster.res;

import org.acouster.Graph;
import org.acouster.context.ContextBitmap;
import org.acouster.context.ImageManager;
import org.acouster.graphics.BitmapWithTransform;

public class ImageSequence extends Graph<BitmapWithTransform>
{
	private static final String TEMPLATE_NUM = "{num}";

	public ImageSequence(String filenameTemplate, int nFrames, double delaySec)
	{
		this(filenameTemplate, nFrames, delaySec, false, false);
	}
	public ImageSequence(String filenameTemplate, int nFrames, double delaySec, boolean flipX, boolean flipY)
	{
		for (int i = 0; i < nFrames; i++)
		{
			String filename = filenameTemplate.replace(TEMPLATE_NUM, "" + (i+1));
			ContextBitmap img = ImageManager.loadR(filename);
			this.addNode(new BitmapWithTransform(img, flipX, flipY), delaySec, "" + i);
		}
		//bmps.gotoNode("0");
		this.linkToFirstNode();
		this.gotoFirstNode();
	}
	
}
