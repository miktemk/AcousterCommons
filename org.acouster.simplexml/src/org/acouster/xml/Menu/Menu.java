package org.acouster.xml.Menu;

import java.util.List;
import java.util.Vector;

import org.simpleframework.xml.ElementList;

public class Menu
{
	public static final String MENU_XML_FILENAME = "_menu.xml";
	
	@ElementList(inline=true)
	private List<MenuItem> entries;
	
	public Menu()
	{
		entries = new Vector<MenuItem>();
	}

	public List<MenuItem> getEntries() {
		return entries;
	}
	
	//------- logic -----
	public void addEntry(MenuItem item)
	{
		entries.add(item);
	}
	
	
}
