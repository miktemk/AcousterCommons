package org.acouster.simplexml;

import java.util.List;

import org.acouster.xml.common.XmlValueString;

public class XmlUtils
{
	public static String[] XmlValueStringListToArray(List<XmlValueString> list)
	{
		String[] result = new String[list.size()];
		int i = 0;
		for (XmlValueString entry : list)
		{
			result[i] = entry.value;
			i++;
		}
		
		return result;
	}
}
