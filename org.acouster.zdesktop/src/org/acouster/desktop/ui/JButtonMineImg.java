package org.acouster.desktop.ui;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class JButtonMineImg extends JButtonMine {

	public JButtonMineImg(String text, final ActionListener actionListener) {
		this (text, actionListener, false);
	}
	public JButtonMineImg(String text, final ActionListener actionListener, boolean anotherThread)
	{
		super(text, actionListener, anotherThread);
		try {
			ImageIcon icon = new ImageIcon(ImageIO.read(new File(text)));
			setIcon(icon);
			setText("");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
}
