package org.acouster.game3d.graphics;

import org.acouster.context.ContextGraphics;

public class BasicGraphics
{
    public static void paintBackground(ContextGraphics ggg, int width, int height, int colorRgb)
    {
        ggg.setColor(colorRgb);
        ggg.fillRect(0, 0, width, height);
    }
}
