package org.acouster.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public class HashMapMulti<K,V> implements Map<K,V>
{
	private HashMap<K,HashMapMultiTuple<V>> map;
	private List<V> allList;
	
	public HashMapMulti()
	{
		map = new HashMap<K,HashMapMultiTuple<V>>();
		allList = new Vector<V>();
	}
	
	@Override
	public void clear() {
		map.clear();
	}
	@Override
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}
	@Override
	public boolean containsValue(Object value) {
		return allList.contains(value);
	}
	@Override
	public V get(Object key) {
		HashMapMultiTuple<V> tuple = map.get(key);
		if (tuple == null)
			return null;
		int size = tuple.list.size();
		if (size == 1)
			return tuple.first;
		return tuple.list.get(MathUtils.random(size));
	}
	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}
	@Override
	public Set<K> keySet() {
		return map.keySet();
	}
	@Override
	public V put(K key, V value) {
		if (!map.containsKey(key))
		{
			map.put(key, new HashMapMultiTuple<V>(value));
		}
		else
		{
			HashMapMultiTuple<V> tuple = map.get(key);
			tuple.list.add(value);
		}
		return value;
	}
	@Override
	public void putAll(Map<? extends K, ? extends V> other) {
		for (K k : other.keySet())
			this.put(k, other.get(k));
	}
	@Override
	public int size() {
		return allList.size();
	}
	@Override
	public V remove(Object arg0) {
		// TODO hmmmmmm no idea
		return null;
	}
	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		// TODO: wtf
		return null;
	}
	@Override
	public Collection<V> values() {
		// TODO wtf
		return null;
	}
	
	
	
	
	
	
	
	private class HashMapMultiTuple<T>
	{
		public T first;
		public List<T> list;
		public HashMapMultiTuple(T first)
		{
			this.first = first;
			list = new Vector<T>();
			list.add(first);
		}
		
	}
}
