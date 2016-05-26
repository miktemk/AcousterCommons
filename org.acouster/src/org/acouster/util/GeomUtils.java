package org.acouster.util;

import org.acouster.math.Vector2D;

public class GeomUtils 
{

	public static boolean areCirclesOverlaped(double x1, double y1, double r1,
		                                      double x2, double y2, double r2
			                                 )
	{
		double dr = r1+r2;
		double dx = Math.abs(x1-x2);
		if(dx > dr) return false;
		double dy = Math.abs(y1-y2);
		if(dy > dr) return false;
		
		return MathUtils.collisionCircle(x1, y1, dr, x2, y2);
		
	}
    public static boolean areCirclesOverlaped(double x1, double y1,
                                              double x2, double y2, double dr
                                             )
    {
        double dx = Math.abs(x1-x2);
        if(dx > dr) return false;
        double dy = Math.abs(y1-y2);
        if(dy > dr) return false;
        
        return MathUtils.collisionCircle(x1, y1, dr, x2, y2);
    
    }
    public static void pickRandomPlaceAlongBorder(Vector2D fillMe, double width, double height, double margin)
	{
		double tx, ty;
	    double w = width + margin;
	    double h = height + margin;
	    double rw = MathUtils.randomDouble(-margin, w);
	    double rh = MathUtils.randomDouble(-margin, h);
	    switch(MathUtils.randomInt(0, 4))
	    {
	        case 0: tx = -margin; ty = rh; break;
	        case 1: tx = w; ty = rh; break;  
            case 2: tx = rw; ty = -margin; break;
            default: tx = rw; ty = h; break;  
	    }
	    fillMe.set(tx, ty);
	}
}
