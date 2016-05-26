package org.acouster.simplexml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

public class ObjectFactory {
	
	public static <T> T parseXml(Class<? extends T> clazz, InputStream stream)
	{
		Serializer serializer = new Persister();
		T result = null;
		try {
			result = serializer.read(clazz, stream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static <T> T loadXml(Class<? extends T> clazz, String filename) throws IOException
	{
		File f = new File(filename);
		InputStream is = new FileInputStream(f);
		T result = parseXml(clazz, is);
		is.close();
		return result;
	}
	
	public static <T> void writeXml(Class<? extends T> clazz, T xml, OutputStream stream)
	{
		Serializer serializer = new Persister();
		try {
			serializer.write(xml, stream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static <T> void writeXmlToFile(Class<? extends T> clazz, T xml, String filename) throws IOException
	{
		FileOutputStream stream = new FileOutputStream(filename);
		writeXml(clazz, xml, stream);
		stream.close();
	}
}

