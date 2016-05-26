package useless;
//package org.acouster.game3d.graphics;
//
//import org.acouster.context.ContextBitmap;
//import org.acouster.context.ResourceContext;
//
//public class ImageSequence
//{
//	protected ContextBitmap[] images;
//
//	public ImageSequence(ContextBitmap[] images)
//	{
//		super();
//		this.images = images;
//	}
//	
//	/**
//	 * @param filenameBase
//	 * would be "pilot" if the files are "pilot1.dat", "pilot2.dat", etc
//	 * @param startIndex
//	 * @param nFrames
//	 * @param extension
//	 * would be ".dat" if the files are "pilot1.dat", "pilot2.dat"
//	 * i.e. must begin with a dot (.) we don't put one in!
//	 */
//	public ImageSequence(String filenameBase, int startIndex, int nFrames, String extension)
//	{
//		images = new ContextBitmap[nFrames];
//		for (int i = 0; i < nFrames; i++)
//		{
//			String filename = filenameBase + (startIndex+i) + extension;
//			images[i] =  ResourceContext.instance().LoadBitmap(filename);
//		}
//	}
//	
//	public ContextBitmap[] getImages()
//	{
//		return images;
//	}
//	public ContextBitmap getImage(int i)
//	{
//		return images[i];
//	}
//	public int nImages()
//	{
//		return images.length;
//	}
//	
//	public void addImage(String filename)
//	{
//		ContextBitmap[] images2 = new ContextBitmap[images.length + 1];
//		for (int i = 0; i < images.length; i++)
//		{
//			images2[i] = images[i];
//		}
//		images2[images.length] = ResourceContext.instance().LoadBitmap(filename);
//		images = images2;
//	}
//}
