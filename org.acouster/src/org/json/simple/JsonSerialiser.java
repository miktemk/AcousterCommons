package org.json.simple;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;

import org.acouster.DebugUtil;
import org.acouster.IFunc;
import org.acouster.util.StringUtils;

/** A crude imitation of JavascriptSetrializer class in .NET...
 * requires use of @JsonField attribute on your fields that you want to serialize.
 * Currently supports only a flat object... no boobs :P */
public class JsonSerialiser
{

	public static String serialize(Object obj)
	{
		if (obj == null)
			return null;
		if (isWrapperType(obj.getClass()))
			return obj.toString();
		//if (obj.getClass().isAssignableFrom(List<Object>.class).isArray())
		if (obj.getClass().isArray())
		{
			//obj.getClass().cast<Object[]>(obj);
			JSONArray arr = new JSONArray();
			//TODO: arrays and List<?> objects
			return null;
			//for ()
		}
		JSONObject json = new JSONObject();
		Field[] fields = obj.getClass().getFields();
		for (Field field : fields)
		{
			Annotation jsonAnnotation = field.getAnnotation(JsonField.class);
			if (jsonAnnotation == null)
				continue;
			try {
				json.put(field.getName(), serialize(field.get(obj)));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return json.toJSONString();
	}
	
	private static Class<?> classOf(Field field, Object test) throws IllegalArgumentException, IllegalAccessException
	{
		Object oldValue = field.get(test);
		if (oldValue == null)
			return field.getType();
		return oldValue.getClass();
	}
	
//	public static Object deserialize(String str, Class<?> clazz)
//	{
//		Object obj = JSONValue.parse(str);
//		
//		return json.toJSONString();
//	}
	
	public static <T> T deserialize(Class<T> clazz, String str)
	{
		Object obj = JSONValue.parse(str);
		// TODO: check for array and deserialize the array
		JSONObject objJson = (JSONObject)obj;
		try {
			T ttt = clazz.newInstance();
			for (Field field : clazz.getFields())
			{
				Annotation jsonAnnotation = field.getAnnotation(JsonField.class);
				if (jsonAnnotation == null)
					continue;
				Object valueStr = objJson.get(field.getName());
				Class<?> classOf = classOf(field, ttt);
				// TODO: parse this mess
				if (PRIMITIVE_PARSERS.containsKey(classOf))
				{
					Object value = PRIMITIVE_PARSERS.get(classOf).lambda("" + valueStr);
					field.set(ttt, value);
				}
				else
				{
					DebugUtil.sss(classOf);
				}
				// TODO: or else.... multi struct??? aahhhh
			}
			return ttt;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static final HashSet<Class<?>> WRAPPER_TYPES = getWrapperTypes();
	private static final HashMap<Class<?>, IFunc<String, ?>> PRIMITIVE_PARSERS = getPrimitiveParsers();
    public static boolean isWrapperType(Class<?> clazz)
    {
        return WRAPPER_TYPES.contains(clazz);
    }
    private static HashMap<Class<?>, IFunc<String, ?>> getPrimitiveParsers()
    {
    	HashMap<Class<?>, IFunc<String, ?>> ret = new HashMap<Class<?>, IFunc<String, ?>>();
        ret.put(Boolean.class, new IFunc<String, Boolean>() {
			@Override
			public Boolean lambda(String value) {
				return Boolean.parseBoolean(value);
			}
        });
        ret.put(Character.class, new IFunc<String, Character>() {
			@Override
			public Character lambda(String value) {
				if (StringUtils.isNullOrEmpty(value))
					return Character.MIN_VALUE;
				return Character.valueOf(value.charAt(0));
			}
        });
        ret.put(Byte.class, new IFunc<String, Byte>() {
			@Override
			public Byte lambda(String value) {
				return Byte.parseByte(value);
			}
        });
        ret.put(Short.class, new IFunc<String, Short>() {
			@Override
			public Short lambda(String value) {
				return Short.parseShort(value);
			}
        });
        ret.put(Integer.class, new IFunc<String, Integer>() {
			@Override
			public Integer lambda(String value) {
				return Integer.parseInt(value);
			}
        });
        ret.put(Long.class, new IFunc<String, Long>() {
			@Override
			public Long lambda(String value) {
				return Long.parseLong(value);
			}
        });
        ret.put(Float.class, new IFunc<String, Float>() {
			@Override
			public Float lambda(String value) {
				return Float.parseFloat(value);
			}
        });
        ret.put(Double.class, new IFunc<String, Double>() {
			@Override
			public Double lambda(String value) {
				return Double.parseDouble(value);
			}
        });
        ret.put(String.class, new IFunc<String, String>() {
			@Override
			public String lambda(String value) {
				if (value == "null")
					return null;
				return value;
			}
        });
        return ret;
	}

	private static HashSet<Class<?>> getWrapperTypes()
    {
        HashSet<Class<?>> ret = new HashSet<Class<?>>();
        ret.add(Boolean.class);
        ret.add(Character.class);
        ret.add(Byte.class);
        ret.add(Short.class);
        ret.add(Integer.class);
        ret.add(Long.class);
        ret.add(Float.class);
        ret.add(Double.class);
        ret.add(Void.class);
        ret.add(String.class);
        return ret;
    }
	
}