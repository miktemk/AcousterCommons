package org.acouster.util;

import java.lang.reflect.Array;

/** Pop is not implemented!*/
public class CircularQueue<T>
{
	private T[] queue;
	private int curIndex, size;
	
	public CircularQueue(Class<T> clazz, int size)
	{
		queue = (T[])Array.newInstance(clazz, size);
		curIndex = this.size = 0;
	}
	
	public int size() {
		return size;
	}
	
	public void push(T obj)
	{
		queue[curIndex] = obj;
		curIndex++;
		curIndex %= queue.length;
		if (size < queue.length)
			size++;
	}

	public T[] getArray() {
		return queue;
	}
}
