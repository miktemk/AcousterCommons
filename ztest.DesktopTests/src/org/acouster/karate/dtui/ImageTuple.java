package org.acouster.karate.dtui;

import java.awt.image.BufferedImage;

public class ImageTuple implements IImageGridItem
{
	/** array of 4:
	 *  - 0: 0
	 *  - 1: 90
	 *  - 2: 180
	 *  - 3: 270 */
	public BufferedImage[] images;
	/** if set to true: images[1] == images[3], but need to be flipped... we don't do that shit for u */
	public boolean isSymmetrical;
	
	public String filenamePrefix;
	
	public ImageTuple(BufferedImage[] images, boolean isSymmetrical, String filenamePrefix) {
		super();
		this.images = images;
		this.isSymmetrical = isSymmetrical;
		this.filenamePrefix = filenamePrefix;
	}

	
	// ---------------- IImageGridItem shit ----------------------
	
	@Override
	public BufferedImage getBi(int angle) {
		return images[angle];
	}
	
	@Override
	public boolean getIsFlipped(int angle) {
		return isSymmetrical && (angle == IAngleContext.ANGLE_270);
	}
}
