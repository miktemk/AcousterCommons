package org.acouster.karate.dtui;

import java.awt.image.BufferedImage;

public interface IImageGridItem
{
	BufferedImage getBi(int angle);
	boolean getIsFlipped(int angle);
}
