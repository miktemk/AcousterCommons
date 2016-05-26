package org.acouster;

public interface IFuncClassed<T1, T2>
//public interface IFunc<T1, T2>
{
	T2 lambda(Class<? extends T2> clazz, T1 value);
}
