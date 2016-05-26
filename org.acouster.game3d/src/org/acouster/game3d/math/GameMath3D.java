package org.acouster.game3d.math;

public class GameMath3D
{
	public static double sqr(double x)
	{
		return x*x;
	}

	public static double distance(double x1, double y1, double x2, double y2)
	{
		return Math.sqrt(sqr(x2 - x1) + sqr(y2 - y1));
	}
	public static double distanceSquared(double x1, double y1, double x2, double y2)
	{
		return sqr(x2 - x1) + sqr(y2 - y1);
	}
	public static double distance(double x1, double y1, double z1, double x2, double y2, double z2)
	{
		return Math.sqrt(sqr(x2 - x1) + sqr(y2 - y1) + sqr(z2 - z1));
	}
	public static double distanceSquared(double x1, double y1, double z1, double x2, double y2, double z2)
	{
		return sqr(x2 - x1) + sqr(y2 - y1) + sqr(z2 - z1);
	}
	
	public static double atan2(double x, double y)
	{
		return Math.atan2(y, x);
		
		//FIXME: Math.atan2 might not do what we wanted here...
//		double  angle ;
//
//		px = x;
//		py = y;
//		if ((x == 0) )
//		{
//			if ((y > 0) ) angle = Math.Math.PI/2;
//			else		 angle = -Math.Math.PI/2;
//		}
//		else if ((x < 0) )	 angle = arctan(y/(x-0.0001));
//		else				 angle = arctan(y/(x+0.0001));
//		if ((x < 0) )
//		{
//			if ((y < 0) ) angle = angle - Math.Math.PI;
//			else		 angle = angle + Math.Math.PI;
//		}
//		Math3D.atan2 = angle;
	}
	public static double getSpriteAngle(double vx, double vz)
	{
		return Math.atan2(-vx, vz);
	}
	
	public static double convAn(double a )
	{
		return GameMath3D.atan2(Math.cos(a), Math.sin(a));
	}
	public static double angleDifference(double a, double b)
	{
		return GameMath3D.convAn(a-b);
	}
	
	public static double absDiff(double a1, double a2 )
	{
		double  d ;

		d = Math.abs(a1-a2);
		if (d > Math.PI)
			d = 2*Math.PI - d;
		return d;
	}

	public static int DiffSign(double a1, double a2 )
	{
		final double delta = 0.01;

	    if(GameMath3D.absDiff(a1+delta, a2) < GameMath3D.absDiff(a1, a2) )
	    	return -1;
	    else
	    	return 1;
	}
	public static int sign(double x)
	{
		return (x > 0) ? 1 : -1;
	}
	
	public static int randSpec(int min, int max )
	{
		int num;
		do
			num = random(max-min+1)+min;
		while(num == 0);
		return num;
	}
	public static int random(long l)
	{
		return (int)(Math.random()*l);
	}
	public static double randomDouble(double min, double max)
	{
		return Math.random() * (max-min) + min;
	}
	public static float randomFloat(double min, double max)
	{
		return (float)randomDouble(min, max);
	}
}
