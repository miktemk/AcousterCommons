package org.acouster.util;

import java.util.List;
import java.util.Vector;

import org.acouster.IFunc;

public class ListUtils
{
	public interface IFuncVoidIndexed<T1>
	{
		void lambda(T1 value, int index);
	}
	
	//=================================================
	// LINQ
	
	public static <T, R> List<R> select(List<? extends T> list, IFunc<T, R> func)
	{
		List<R> result = new Vector<R>();
		for (T obj : list)
			result.add(func.lambda(obj));
		return result;
	}
	public static <T> List<T> where(List<T> list, IFunc<T, Boolean> func, List<T> result)
	{
		for (T obj : list)
		{
			if (func.lambda(obj))
				result.add(obj);
		}
		return result;
	}
	public static <T> List<T> where(List<T> list, IFunc<T, Boolean> func)
	{
		return where(list, func, new Vector<T>());
	}
	public static <T> T firstOrDefault(List<T> list, IFunc<T, Boolean> func)
	{
		for (T obj : list)
		{
			if (func.lambda(obj))
				return obj;
		}
		return null;
	}
	public static <T> boolean any(List<T> list, IFunc<T, Boolean> func)
	{
		for (T obj : list)
		{
			if (func.lambda(obj))
				return true;
		}
		return false;
	}
	//TODO: add function to paginate
	
	//=================================================
	// min/max
	public static <T> T findMinObj(List<T> list, IFunc<T, Double> func)
	{
		return findMinObjAndIndex(list, func).value;
	}
	public static <T> T findMaxObj(List<T> list, IFunc<T, Double> func)
	{
		return findMaxObjAndIndex(list, func).value;	
	}
	public static <T> int findMinIndex(List<T> list, IFunc<T, Double> func)
	{
		return findMinObjAndIndex(list, func).index;
	}
	public static <T> int findMaxIndex(List<T> list, IFunc<T, Double> func)
	{
		return findMaxObjAndIndex(list, func).index;	
	}
	
	private static <T> ListUtils_MinMaxResult<T> findMinObjAndIndex(List<T> list, IFunc<T, Double> func)
	{
		return findMinMaxObj(list, func, new IFunc<ListUtils_MinMaxTuple, Boolean>() {
			@Override
			public Boolean lambda(ListUtils_MinMaxTuple tuple) {
				return tuple.dist < tuple.minDist;
			}
		});
	}
	private static <T> ListUtils_MinMaxResult<T> findMaxObjAndIndex(List<T> list, IFunc<T, Double> func)
	{
		return findMinMaxObj(list, func, new IFunc<ListUtils_MinMaxTuple, Boolean>() {
			@Override
			public Boolean lambda(ListUtils_MinMaxTuple tuple) {
				return tuple.dist > tuple.minDist;
			}
		});
	}
	private static class ListUtils_MinMaxTuple
	{
		public double minDist;
		public double dist;
	}
	private static class ListUtils_MinMaxResult<T>
	{
		public T value;
		public int index;
	}
	private static <T> ListUtils_MinMaxResult<T> findMinMaxObj(List<T> list, IFunc<T, Double> func, IFunc<ListUtils_MinMaxTuple, Boolean> funcDistanceTest)
	{
		ListUtils_MinMaxResult<T> minGuy = new ListUtils_MinMaxResult<T>();
		ListUtils_MinMaxTuple tuple = new ListUtils_MinMaxTuple();
		int i = 0;
		for (T elem : list)
		{
			tuple.dist = func.lambda(elem);
			if (minGuy.value == null || funcDistanceTest.lambda(tuple))
			{
				tuple.minDist = tuple.dist;
				minGuy.value = elem;
				minGuy.index = i;
			}
			i++;
		}
		return minGuy;
	}
	public static <T> void reverseIterate(List<T> list, IFuncVoidIndexed<T> func)
	{
		for (int i = list.size()-1; i >= 0; i--)
		{
			func.lambda(list.get(i), i);
		}
	}
	
	//=================================================
	// other shit
	public static <T> T randomElem(List<T> list)
	{
		return list.get((int)(Math.random() * list.size()));
	}
	public static <T> T randomElem(T[] list)
	{
		return list[(int)(Math.random() * list.length)];
	}
	public static <T> List<T> shuffle(List<T> list) {
		//TODO: find a better, more efficient implementation
		List<T> result = new Vector<T>();
		while (!list.isEmpty()) {
			T elem = randomElem(list);
			list.remove(elem);
			result.add(elem);
		}
		return result;
	}
	
	//=================================================
	// array utils
	// moved to ArrayUtils.java
}
