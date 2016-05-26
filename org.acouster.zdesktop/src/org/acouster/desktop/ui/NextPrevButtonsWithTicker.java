package org.acouster.desktop.ui;


import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.acouster.IFunc;
import org.acouster.IFuncVoid;

public class NextPrevButtonsWithTicker extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	private int min, max, index = 0;
	private boolean wrap;
	private JLabel label;

	private IFunc<Integer, String> labelCallback;
	
	public NextPrevButtonsWithTicker(int min, int max, final IFuncVoid<Integer> listener) {
		this ("<--", "-->", min, max, listener, false);
	}
	public NextPrevButtonsWithTicker(String titlePrev, String titleNext, int min, int max, final IFuncVoid<Integer> listener) {
		this (titlePrev, titleNext, min, max, listener, false);
	}
	public NextPrevButtonsWithTicker(String titlePrev, String titleNext, final int min, final int max, final IFuncVoid<Integer> listener, final boolean wrap)
	{
		super(new GridLayout(1, 3));
		this.min = min;
		this.max = max;
		this.index = min;
		this.wrap = wrap;
		add(new JButtonMine(titlePrev, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				index--;
				if (index < min)
					index = NextPrevButtonsWithTicker.this.wrap ? max : min;
				setIndex(index);
				listener.lambda(index);
			}
		}));
		add(label = new JLabel("" + index));
		add(new JButtonMine(titleNext, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				index++;
				if (index > max)
					index = NextPrevButtonsWithTicker.this.wrap ? min : max;
				setIndex(index);
				listener.lambda(index);
			}
		}));
	}
	
	public NextPrevButtonsWithTicker setWrap(boolean wrap)
	{
		this.wrap = wrap;
		return this;
	}
	
	public NextPrevButtonsWithTicker setCustomLabelCallback(IFunc<Integer, String> labelCallback)
	{
		this.labelCallback = labelCallback;
		updateLabel();
		return this;
	}
	
	private void updateLabel()
	{
		if (labelCallback != null)
			label.setText(labelCallback.lambda(index));
		else
			label.setText("" + index);
	}
	
	// note: this will not call the listener.. do we want to tho??
	public void setIndex(int index)
	{
		if (index >= min && index <= max)
		{
			this.index = index;
			updateLabel();
		}
	}
}