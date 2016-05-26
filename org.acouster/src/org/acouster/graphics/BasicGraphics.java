package org.acouster.graphics;

import org.acouster.context.ContextGraphics;

public class BasicGraphics
{
    public static void paintBackground(ContextGraphics g, int width, int height, int colorRgb)
    {
        g.setColor(colorRgb);
        g.setFill(colorRgb);
        g.fillRect(0, 0, width, height);
    }
    
    /** debug lines */
    public static void drawCrosshair(ContextGraphics g, int x, int y)
    {
    	//g.setColor(0x000000);
    	g.drawLine(x, y-100, x, y+100);
    	g.drawLine(x-100, y, x+100, y);
    }
    

}
