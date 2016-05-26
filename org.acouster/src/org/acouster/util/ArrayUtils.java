package org.acouster.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Vector;

public class ArrayUtils
{
	//=================================================
	// Conversion
	public static int[] ToIntArray(Integer[] array)
	{
		int[] result = new int[array.length];
		for (int i = 0; i < array.length; i++)
			result[i] = array[i];
		return result;
	}
	public static Integer[] ToIntegerArray(int[] array)
	{
		Integer[] result = new Integer[array.length];
		for (int i = 0; i < array.length; i++)
			result[i] = array[i];
		return result;
	}
	public static long[] TolongArray(Long[] array)
	{
		long[] result = new long[array.length];
		for (int i = 0; i < array.length; i++)
			result[i] = array[i];
		return result;
	}
	public static Long[] ToLongArray(long[] array)
	{
		Long[] result = new Long[array.length];
		for (int i = 0; i < array.length; i++)
			result[i] = array[i];
		return result;
	}
	public static String[] ToStringArray(Integer[] array)
	{
		String[] result = new String[array.length];
		for (int i = 0; i < array.length; i++)
			result[i] = "" + array[i];
		return result;
	}
	public static <T> String stringify(T[] array) {
		StringBuilder sb = new StringBuilder();
		sb.append('[');
		for (T obj : array) {
			if (sb.length() > 1)
				sb.append(", ");
			sb.append(obj);
		}
		sb.append(']');
		return sb.toString();
	}
	public static <T> ArrayList<T> toArrayList(T[] array) {
		if (array == null)
			return null;
		ArrayList<T> list = new ArrayList<T>();
		for (T elem : array)
			list.add(elem);
		return list;
	}
	public static <T> Vector<T> toVector(T[] array) {
		if (array == null)
			return null;
		Vector<T> list = new Vector<T>();
		for (T elem : array)
			list.add(elem);
		return list;
	}
	
//	public <T extends Number> T[] ToNumericArray(Class<T> clazz, int[] array)
//	{
//		T[] result = (T[])Array.newInstance(clazz, array.length);
//		for (int i = 0; i < array.length; i++)
//			result[i] = array[i];
//		return result;
//	}
	
	//=================================================
	// Manipulation
	
	public static <T> T[] removeAtIndex(Class<T> clazz, T[] arr1, int index)
	{
		if (index < 0 || index >= arr1.length)
			return arr1;
		T[] result = (T[])Array.newInstance(clazz, arr1.length-1);
		for (int i = 0, j = 0; i < arr1.length; i++)
		{
			if (i == index)
				continue;
			result[j] = arr1[i];
			j++;
		}
		return result;
	}
	public static double[] removeAtIndex(double[] arr1, int index)
	{
		if (index < 0 || index >= arr1.length)
			return arr1;
		double[] result = new double[arr1.length-1];
		for (int i = 0, j = 0; i < arr1.length; i++)
		{
			if (i == index)
				continue;
			result[j] = arr1[i];
			j++;
		}
		return result;
	}
	public static <T> T[] union(Class<T> clazz, T[] arr1, T[] arr2)
	{
		T[] result = (T[])Array.newInstance(clazz, arr1.length + arr2.length);
		for (int i = 0; i < arr1.length; i++)
			result[i] = arr1[i];
		for (int i = 0; i < arr2.length; i++)
			result[arr1.length + i] = arr2[i];
		return result;
	}
	/** NOTE: This method no longer requires the class parameter... (and it never did -March 25, 2014) */
	public static <T> int indexOf(T[] arr, T obj) {
		for (int i = 0; i < arr.length; i++)
			if (arr[i] == obj)
				return i;
		return -1;
	}
	/** Same as indexof(), except uses equals() to compare objects if they are not null */
	public static <T> int indexOfEquals(T[] arr, T obj) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == null && obj == null)
				return i;
			if (arr[i] != null && obj != null && obj.equals(arr[i]))
				return i;
		}
		return -1;
	}
	
	public static void initArray(int[] outResult, int value) {
		initArray(outResult, value, outResult.length);
	}
	/** initializes the first "length" elements. Does not handle index out of bounds! */
	public static void initArray(int[] outResult, int value, int length)
	{
		for (int i = 0; i < length; i++)
			outResult[i] = value;
	}
	public static void initArray(double[] outResult, double value) {
		initArray(outResult, value, outResult.length);
	}
	/** initializes the first "length" elements. Does not handle index out of bounds! */
	public static void initArray(double[] outResult, double value, int length)
	{
		for (int i = 0; i < length; i++)
			outResult[i] = value;
	}
	
	//=================================================
	// Aggregate functions
	
	public static long sum(Long[] array) { return sum(array, array.length); }
	public static long sum(Long[] array, int size) {
		long total = 0;
		for (int i = 0; i < size; i++)
			total += array[i];
		return total;
	}
	public static long sum(long[] array) { return sum(array, array.length); }
	public static long sum(long[] array, int size) {
		long total = 0;
		for (int i = 0; i < size; i++)
			total += array[i];
		return total;
	}
	public static long sum(Integer[] array) { return sum(array, array.length); }
	public static long sum(Integer[] array, int size) {
		long total = 0;
		for (int i = 0; i < size; i++)
			total += array[i];
		return total;
	}
	public static long sum(int[] array) { return sum(array, array.length); }
	public static long sum(int[] array, int size) {
		long total = 0;
		for (int i = 0; i < size; i++)
			total += array[i];
		return total;
	}
}
