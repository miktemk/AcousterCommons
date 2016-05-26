package org.acouster.desktop.ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class JButtonMine extends JButton {

	public JButtonMine(String text, final ActionListener actionListener) {
		this (text, actionListener, false);
	}
	public JButtonMine(String text, final ActionListener actionListener, boolean anotherThread)
	{
		super(text);
		ActionListener aaa = actionListener;
		if (anotherThread)
		{
			aaa = new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e)
				{
					new Thread(new Runnable() {
						public void run() {
							actionListener.actionPerformed(e);
						}
					}).start();
				}
			};
		}
		addActionListener(aaa);
	}
	
	public JButtonMine setMySize(int w, int h)
	{
		setPreferredSize(new Dimension(w, h));
		return this;
	}
	
	public JButtonMine setMyToolTip(String tooltip)
	{
		setToolTipText(tooltip);
		return this;
	}
}
