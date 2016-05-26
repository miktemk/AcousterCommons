package org.acouster.simplexml;

import java.io.InputStream;

import org.acouster.IFuncClassed;

public class ObjectFactoryFunc<T> implements IFuncClassed<InputStream, T> 
{
	@Override
	public T lambda(Class<? extends T> clazz, InputStream stream) {
		return ObjectFactory.parseXml(clazz, stream);
	}
}
