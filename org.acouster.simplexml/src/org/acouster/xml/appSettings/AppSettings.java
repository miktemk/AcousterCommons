package org.acouster.xml.appSettings;

import java.util.List;
import java.util.Vector;

import org.simpleframework.xml.ElementList;

public class AppSettings
{
	@ElementList(inline=true,entry="add")
	List<AppSetting> settings;
	
	public AppSettings()
	{
		settings = new Vector<AppSetting>();
	}
	
	public String getValue(String key)
	{
		for (AppSetting s : settings)
		{
			if (s.key.equals(key))
				return s.value;
		}
		return null;
	}
	public int getValueInt(String key, int def)
	{
		String result = getValue(key);
		if (result == null)
			return def;
		try
		{
			return Integer.parseInt(result.trim());
		}
		catch (Exception ex)
		{
			return def;
		}
	}
	public double getValueDouble(String key, double def)
	{
		String result = getValue(key);
		if (result == null)
			return def;
		try
		{
			return Double.parseDouble(result.trim());
		}
		catch (Exception ex)
		{
			return def;
		}
	}
}
