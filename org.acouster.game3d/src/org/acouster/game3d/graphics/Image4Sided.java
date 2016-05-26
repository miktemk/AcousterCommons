package org.acouster.game3d.graphics;

import org.acouster.context.ContextBitmap;
import org.acouster.context.ImageManager;
import org.acouster.graphics.BitmapWithTransform;
import org.acouster.graphics.BitmapWithTransformQuery;

public class Image4Sided
{
	private String prefix, extension;
	private boolean isSymmetrical;
	//private  images;
	public Image4Sided(String prefix, String extension, boolean isSymmetrical)
	{
		this.prefix = prefix;
		this.extension = extension;
		this.isSymmetrical = isSymmetrical;
	}
	public BitmapWithTransform getBitmap(double angle)
	{
		return getBitmap(angle, false);
	}
	public BitmapWithTransform getBitmap(double angle, boolean isOppositeSide)
	{
		// TODO: recycle some of this shit!
		// en face
		BitmapWithTransformQuery[] images = new BitmapWithTransformQuery[8];
		if (isSymmetrical) //MetadataManager.instance().isSymmetrical(node.getQuery()))
		{
			String bpm0 = getImageFilename(0);
			String bpm90 = getImageFilename(90);
			String bpm180 = getImageFilename(180);
			//String bpm270 = getImageFilename(270);
			images[0] = new BitmapWithTransformQuery(bpm0, false, false);
			images[1] = new BitmapWithTransformQuery(bpm90, false, false);
			images[2] = new BitmapWithTransformQuery(bpm180, false, false);
			images[3] = new BitmapWithTransformQuery(bpm90, true, false);
			images[4] = new BitmapWithTransformQuery(bpm0, true, false);
			images[5] = new BitmapWithTransformQuery(bpm90, false, false);
			images[6] = new BitmapWithTransformQuery(bpm180, true, false);
			images[7] = new BitmapWithTransformQuery(bpm90, true, false);
		}
		else
		{
			String bpm0 = getImageFilename(0);
			String bpm90 = getImageFilename(90);
			String bpm180 = getImageFilename(180);
			String bpm270 = getImageFilename(270);
			images[0] = new BitmapWithTransformQuery(bpm0, false, false);
			images[1] = new BitmapWithTransformQuery(bpm90, false, false);
			images[2] = new BitmapWithTransformQuery(bpm180, false, false);
			images[3] = new BitmapWithTransformQuery(bpm270, false, false);
			images[4] = new BitmapWithTransformQuery(bpm0, true, false);
			images[5] = new BitmapWithTransformQuery(bpm270, true, false);
			images[6] = new BitmapWithTransformQuery(bpm180, true, false);
			images[7] = new BitmapWithTransformQuery(bpm90, true, false);
		}
		
		//if (isOppositeSide)
		//	angle = (360 - angle) % 360;
		int index = (int)(Math.round(angle/90) % 4);
		if (isOppositeSide)
			index += 4;
		ContextBitmap bmp = ImageManager.instance().loadFromResource(images[index].getQuery());
		BitmapWithTransform bmpWTrans = new BitmapWithTransform(bmp, images[index].isFlipX(), images[index].isFlipY());
		return bmpWTrans;
	}
	
	private String getImageFilename(int angle)
	{
		return prefix + "_" + angle + "." + extension;
	}
//	private ContextBitmap loadImage(int angle)
//	{
//		String filename = prefix + "_" + angle + "." + extension;
//		//DebugUtil.sss("requesting: " + filename);
//		return ImageManager.instance().loadFromResource(filename);
//	}
	
	@Override
	public String toString()
	{
		return prefix;
	}
	
//	public ImageNode getImageNode() {
//		return node;
//	}
	
}
