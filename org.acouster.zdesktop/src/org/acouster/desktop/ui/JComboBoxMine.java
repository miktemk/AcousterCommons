package org.acouster.desktop.ui;

import java.awt.event.ItemListener;

import javax.swing.JComboBox;

public class JComboBoxMine extends JComboBox
{
	public JComboBoxMine(Object[] list, ItemListener aListener)
	{
		super(list);
		addItemListener(aListener);
	}
}
