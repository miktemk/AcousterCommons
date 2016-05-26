package org.acouster.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Vector;

/** Utility functions for reflection */
public class ClassUtils
{
	public static String[] fieldsAnnotated(Class<?> type, Class<? extends Annotation> annotation)
	{
		Vector<String> names = new Vector<String>();
		Field[] fields = type.getFields();
		for (Field field : fields)
		{
			Annotation jsonAnnotation = field.getAnnotation(annotation);
			if (jsonAnnotation == null)
				continue;
			names.add(field.getName());
		}
		return names.toArray(new String[names.size()]);
	}
	
}