package org.acouster.desktop.ui;

import java.awt.Color;
import java.awt.Graphics2D;

public class UtilsGraphics
{
	public static void fillBg(Graphics2D g, Color c, int w, int h)
	{
		g.setPaint(c);
		g.fillRect(0,  0, w, h);
	}
	
}
