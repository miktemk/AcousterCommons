package org.acouster.desktop.ui;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class LeftToolbar extends JPanel
{
	protected JProgressBar progressBar;
	protected JLabel myLabel;
	public LeftToolbar()
	{
		super(new VerticalLayout(5, VerticalLayout.RIGHT));
	}
	
	// progress bar
	public void addMyLabel()
	{
		add(myLabel = new JLabel());
	}
	public void setLabelText(String text)
	{
		if (myLabel == null)
			return;
		myLabel.setText(text);
	}
		
	// progress bar
	public void addMyProgressBar()
	{
		add(progressBar = new JProgressBar());
	}
	public void setProgress(double outOf1)
	{
		if (progressBar == null)
			return;
		progressBar.setValue((int)(100 * outOf1));
	}
}
