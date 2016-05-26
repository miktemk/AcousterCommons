package org.acouster.util;

/** statistical functions such as distributions, etc */
public class StatUtils
{
	public static void randomDistribution(int[] outResult, int total)
	{
		if (outResult == null)
			return;
		if (outResult.length == 0)
			return;
		ArrayUtils.initArray(outResult, 0);
		for (int i = 0; i < total; i++)
			outResult[MathUtils.random(outResult.length)]++;
	}
	/** returns at least [1,1,1,1,1...]
	 * if total < out */
	public static void randomDistributionNo0s(int[] outResult, int total)
	{
		if (outResult == null)
			return;
		if (outResult.length == 0)
			return;
		ArrayUtils.initArray(outResult, 0);
		ArrayUtils.initArray(outResult, 1, Math.min(total, outResult.length));
		for (int i = 0; i < total-outResult.length; i++)
			outResult[MathUtils.random(outResult.length)]++;
	}

	
}
