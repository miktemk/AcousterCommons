package org.acouster;

public class DebugUtil
{
	public static final String TAG = "========>>>>>>>";
	
	public static void sss(Object o)
	{
		System.out.println("" + o);
	}
	public static void lll(Object o)
	{
		System.out.print("" + o);
	}
	public static void eee(Object s)
	{
		System.err.println("" + s);
	}
	
	public static void coord(int x, int y)
	{
		System.out.println(x + " : " + y);
	}
	public static void coord(double x, double y)
	{
		System.out.println(x + " : " + y);
	}
}