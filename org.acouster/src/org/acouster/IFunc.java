package org.acouster;

public interface IFunc<T1, T2>
{
	public class SameThing<R> implements IFunc<R, R> {
		@Override
		public R lambda(R value) {
			return value;
		}
	}

	T2 lambda(T1 value);
}
