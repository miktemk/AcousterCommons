package org.acouster.util;

import org.acouster.math.MathConstants;


public class MathUtils
{
	public static double sqr(double x)
	{
		return x*x;
	}

	// ====================== distance functions ============================
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
	
	// ====================== vector functions ============================
	public static double normalizeVectorX(double x, double y)
	{
		if (x == 0 && y == 0)
			return 0;
		return x / Math.sqrt(x*x + y*y);
	}
	public static double normalizeVectorY(double x, double y)
	{
		if (x == 0 && y == 0)
			return 0;
		return y / Math.sqrt(x*x + y*y);
	}
	public static double rotateVectorX(double x, double y, double aRadians)
	{
		return x * Math.cos(aRadians) - y * Math.sin(aRadians);
	}
	public static double rotateVectorY(double x, double y, double aRadians)
	{
		return x * Math.sin(aRadians) + y * Math.cos(aRadians);
	}
	public static double vectorDifferenceNormalized(double v1x, double v1y, double v2x, double v2y) {
		double length1 = distance(0, 0, v1x, v1y);
		double length2 = distance(0, 0, v2x, v2y);
		return distance(v1x/length1, v1y/length1, v2x/length2, v2y/length2);
	}
	public static double dot(double x1, double y1, double x2, double y2)
	{
		return x1*x2 + y1*y2;
	}
	public static double dot(double x1, double y1, double z1, double x2, double y2, double z2)
	{
		return x1*x2 + y1*y2 + z1*z2;
	}
	public static double rpos2d(
			double ax, double ay,
			double bx, double by,
			double cx, double cy
			)
	{
		double ab2 = MathUtils.distanceSquared(ax, ay, bx, by);
		if (ab2 == 0)
			return 0;
		return MathUtils.dot(cx-ax, cy-ay, bx-ax, by-ay) / ab2;
	}
	public static double rpos3d(
			double ax, double ay, double az,
			double bx, double by, double bz,
			double cx, double cy, double cz
			)
	{
		double ab2 = MathUtils.distanceSquared(ax, ay, az, bx, by, bz);
		if (ab2 == 0)
			return 0;
		return MathUtils.dot(cx-ax, cy-ay, cz-az, bx-ax, by-ay, bz-az) / ab2;
	}
	
	// ====================== angle functions ============================
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
	public static double getSpriteAngle(double vx, double vy)
	{
		//return Math.atan2(-vx, vz);
		return Math.atan2(vy, vx);
	}
	public static double convAn(double a )
	{
		return MathUtils.atan2(Math.cos(a), Math.sin(a));
	}
	public static double angleWithin360(double angle) {
		return wrap(angle, 360, 100);
	}
	public static double angleWithin2Pi(double angle) {
		return wrap(angle, MathConstants.PI2, 10000);
	}
	public static double angleDifference(double a, double b)
	{
		return MathUtils.convAn(a-b);
	}
	public static double angleToRadians(double aDegrees)
	{
		return aDegrees * MathConstants.PI_deg2rad;
	}
	public static double angleToDegrees(double aRadians)
	{
		return aRadians * MathConstants.PI_rad2deg;
	}
	public static double absDiff(double a1, double a2 )
	{
		double  d ;

		d = Math.abs(a1-a2);
		if (d > Math.PI)
			d = MathConstants.PI2 - d;
		return d;
	}

	// ====================== geometry functions ============================
	/** test for (px,py) inside (or on boundary of) rect (x,y,w,h) */
	public static boolean collisionRect(double x, double y, double w, double h, double px, double py)
	{
		if ((px >= x) && (px <= x + w) && (py >= y) && (py <= y + h))
			return true;
		return false;
	}
	/** test for (px,py) in circle of radius r at center (x,y) */
	public static boolean collisionCircle(double x, double y, double r, double px, double py)
	{
		return distance(x, y, px, py) <= r;
	}
	/** test for circles overlapping (x1,y1) of radius r1 and (x2,y2) of radius r2 */
	public static boolean collisionCircles2(double x1, double y1, double r1,
                                           double x2, double y2, double r2)
    {
        return distance(x1, y1, x2, y2) <= r1+r2;
    }
	
	// ====================== arithmetic functions ============================
	public static int DiffSign(double a1, double a2 )
	{
		final double delta = 0.01;

	    if(MathUtils.absDiff(a1+delta, a2) < MathUtils.absDiff(a1, a2) )
	    	return -1;
	    else
	    	return 1;
	}
	public static int sign(double x)
	{
		return (x > 0) ? 1 : -1;
	}
	public static double weightedAverage(double x1, double x2, double position)
	{
		return x1 + (x2-x1) * position;
	}
	public static double invertWeight(double w, double outOf)
	{
		if (w == 0)
			return 1;
		return outOf / w / outOf;
	}
	/** A more sensible % function for doubles and with kkk precision (about 1000 OK) */
	public static double wrap(double x, double max, int kkk)
	{
		while (x < 0)
			x += max * ((int)Math.abs(x / max) + 1);
		int angleInt = (int)(x * kkk);
		angleInt %= max * kkk;
		return (double)angleInt / kkk;
	}
	public static double snap(double x, double tick)
	{
		return tick * Math.round(x/tick);
	}
	public static double snap(double x, double tick, int loopSize)
	{
		return tick * (Math.round(x/tick) % loopSize);
	}
	public static double max(double[] values)
	{
		if (values.length == 0)
			return 0;
		double max = values[0];
		for (int i = 1; i < values.length; i++)
		{
			if (max < values[i])
				max = values[i];
		}
		return max;
	}
	public static double min(double[] values)
	{
		if (values.length == 0)
			return 0;
		double min = values[0];
		for (int i = 1; i < values.length; i++)
		{
			if (min > values[i])
				min = values[i];
		}
		return min;
	}
	public static int maxIndex(double[] values)
	{
		if (values.length == 0)
			return -1;
		double max = values[0];
		int index = 0;
		for (int i = 1; i < values.length; i++)
		{
			if (max < values[i])
			{
				max = values[i];
				index = i;
			}
		}
		return index;
	}
	public static int minIndex(double[] values)
	{
		if (values.length == 0)
			return -1;
		double min = values[0];
		int index = 0;
		for (int i = 1; i < values.length; i++)
		{
			if (min > values[i])
			{
				min = values[i];
				index = i;
			}
		}
		return index;
	}
	public static double max3(double x1, double x2, double x3) {
		return Math.max(x1, Math.max(x2, x3));
	}
	public static double max4(double x1, double x2, double x3, double x4) {
		return Math.max(Math.max(x1, x2), Math.max(x3, x4));
	}
	public static double sum(double[] values)
	{
		double total = 0;
		for (int i = 0; i < values.length; i++)
			total += values[i];
		return total;
	}
	/** returns lowest power of 10 that is greater than this number, i.e. 1 for 0-9, 2 for 10-99, 3 for 100-999, etc */
	public static int intOrder(long value) {
		value = Math.abs(value);
		// TODO: the following approach is hacky as hell... big long values will totally fail here
		if (value < 10)
			return 1;
		if (value < 100)
			return 2;
		if (value < 1000)
			return 3;
		if (value < 10000)
			return 4;
		if (value < 100000)
			return 5;
		if (value < 1000000)
			return 6;
		if (value < 10000000)
			return 7;
		if (value < 100000000)
			return 8;
		if (value < 1000000000)
			return 9;
		return 10;
	}
	
	// ====================== random functions ============================
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
	/** gives an integer in range [min..max] INCLUSIVE!!! AKA max-min+1 possible values */
	public static int randomInt(int min, int max)
	{
		return random(max-min+1) + min;
	}
	public static int randColor()
	{
		return 0xFF000000 | random(0xFFFFFF);
	}
	public static boolean randBoolean()
	{
		return Math.random() > 0.5;
	}
	public static boolean randBoolean(double probTrue)
	{
		return Math.random() < probTrue;
	}
	/** returns 1 or -1 */
	public static int randSign() {
		return 2*random(2) - 1;
	}
	
	// ====================== color functions ============================
	
	public static int getRgb(int r, int g, int b) { return getRgb(0xFF, r, g, b); }
	public static int getRgb(int a, int r, int g, int b)
	{
		return ((a & 0x0ff) << 24)
			| ((r & 0x0ff) << 16)
			| ((g & 0x0ff) << 8)
			| (b & 0x0ff);
	}
	public static short getR(int rgb) {
		return (short)((rgb >> 16) & 0x0ff);
	}
	public static short getG(int rgb) {
		return (short)((rgb >> 8) & 0x0ff);
	}
	public static short getB(int rgb) {
		return (short)(rgb & 0x0ff);
	}

	
}
