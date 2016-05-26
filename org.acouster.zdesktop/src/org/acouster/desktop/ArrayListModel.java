package org.acouster.desktop;

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

public class ArrayListModel implements ListModel
{
	Object[] stuff;
	public ArrayListModel(Object[] stuff)
	{
		this.stuff = stuff;
	}
	
	@Override
	public void addListDataListener(ListDataListener l)
	{}

	@Override
	public Object getElementAt(int index)
	{
		if (index < 0 || index >= stuff.length)
			return null;
		return stuff[index];
	}

	@Override
	public int getSize() {
		return stuff.length;
	}

	@Override
	public void removeListDataListener(ListDataListener l)
	{}

}
