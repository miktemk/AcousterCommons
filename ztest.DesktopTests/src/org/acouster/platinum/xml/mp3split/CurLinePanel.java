package org.acouster.platinum.xml.mp3split;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.JPanel;


public class CurLinePanel extends JPanel
{
	private JLabel label;

	public CurLinePanel()
	{
		super(new BorderLayout());
		add(label = new JLabel());
	}
	
	public void setLines(String line1, String line2)
	{
		String text = "<html><h1>" + line1 + "</h1>";
		if (line2 != null)
			text += "<br>" + line2;
		text += "</html>";
		label.setText(text);
	}
}
