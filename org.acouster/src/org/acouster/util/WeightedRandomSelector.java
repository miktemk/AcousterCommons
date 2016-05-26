package org.acouster.util;

import java.util.Vector;

import org.acouster.IFunc;

public class WeightedRandomSelector<T>
{
	private class WeightedRandomSelector_Tuple<TT>
	{
		public TT obj;
		public double weight;
		WeightedRandomSelector_Tuple(TT obj, double weight)
		{
			this.obj = obj;
			this.weight = weight;
		}
	}
	
	private Vector<WeightedRandomSelector_Tuple<T>> list;
	private double total;
	public WeightedRandomSelector()
	{
		list = new Vector<WeightedRandomSelector_Tuple<T>>();
		total = 0;
	}
	
	public void add(T obj, double weight)
	{
		list.add(new WeightedRandomSelector_Tuple<T>(obj, weight));
		total += weight;
	}
	public void changeWeight(T obj, double weight)
	{
		changeWeight(obj, weight, false);
	}
	public void changeWeight(T obj, double weight, boolean removeIf0)
	{
		if (removeIf0 && weight <= 0)
		{
			remove(obj);
			return;
		}
		if (weight < 0)
			weight = 0;
		for (WeightedRandomSelector_Tuple<T> tuple : list)
		{
			if (tuple.obj == obj)
			{
				double delta = weight - tuple.weight;
				tuple.weight += delta;
				total += delta;
				break;
			}
		}
	}
	public boolean remove(final T obj)
	{
		WeightedRandomSelector_Tuple<T> badApple = ListUtils.firstOrDefault(list, new IFunc<WeightedRandomSelector_Tuple<T>, Boolean>() {
			@Override
			public Boolean lambda(WeightedRandomSelector_Tuple<T> value) {
				return value.obj == obj;
			};
		});
		if (badApple != null)
		{
			list.remove(badApple);
			return true;
		}
		return false;
	}
	
	public T get()
	{
		if (total == 0)
			return null;
		if (list.size() == 0)
			return null;
		double num = Math.random() * total;
		double i = 0;
		for (WeightedRandomSelector_Tuple<T> elem : list)
		{
			i += elem.weight;
			if (i > num)
				return elem.obj;
		}
		return list.lastElement().obj;
	}
	public T get(IFunc<T, Boolean> filter)
	{
		if (total == 0)
			return null;
		double subTotal = 0;
		for (WeightedRandomSelector_Tuple<T> elem : list)
			if (filter.lambda(elem.obj))
				subTotal += elem.weight;
		if (subTotal == 0)
			return null;
		double num = Math.random() * subTotal;
		double i = 0;
		for (WeightedRandomSelector_Tuple<T> elem : list)
		{
			if (!filter.lambda(elem.obj))
				continue;
			i += elem.weight;
			if (i > num)
				return elem.obj;
		}
		return list.lastElement().obj;
	}

	public int size()
	{
		return list.size();
	}

	public Object toString(String prefix)
	{
		StringBuilder sb = new StringBuilder();
		for (WeightedRandomSelector_Tuple<T> elem : list)
			sb.append(prefix + elem.weight + ": " + elem.obj + "\n");
		return sb.toString();
	}
}
