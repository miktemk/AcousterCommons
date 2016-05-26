package org.acouster.context.desktop;

import java.awt.image.BufferedImage;

import org.acouster.context.ContextBitmap;

public class ContextBitmap_BufferedImage extends ContextBitmap {
	
	private BufferedImage img;
	public ContextBitmap_BufferedImage(BufferedImage img) {
		super();
		this.img = img;
	}

	public BufferedImage getImg() {
		return img;
	}
	public void setImg(BufferedImage img) {
		this.img = img;
	}

	@Override
	public int getWidth() {
		return img.getWidth();
	}
	@Override
	public int getHeight() {
		return img.getHeight();
	}
	@Override
	public boolean isFragment() {
		return false;
	}

	@Override
	protected void disposeInner() {
		// TODO Auto-generated method stub
		
	}

	
	//================= static shit ================
	
	private static ContextBitmap_BufferedImage instance = new ContextBitmap_BufferedImage(null);
	public static ContextBitmap_BufferedImage ofBi(BufferedImage bi)
	{
		instance.setImg(bi);
		return instance;
	}
}
