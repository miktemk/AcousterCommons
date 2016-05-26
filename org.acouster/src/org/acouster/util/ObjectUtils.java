package org.acouster.util;

public class ObjectUtils
{
	public static <T> T randomPick(T t1, T t2)
	{
		if (Math.random() > 0.5)
			return t1;
		return t2;
	}
}
