package org.acouster.desktop.ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class NextPrevButtons extends JPanel
{
	public interface INextPrevListener
	{
		void onNext();
		void onPrev();
	}
	
	
	private static final long serialVersionUID = 1L;
	
	public NextPrevButtons(final INextPrevListener listener)
	{
		this ("<--", "-->", listener);
	}
	public NextPrevButtons(String titlePrev, String titleNext, final INextPrevListener listener)
	{
		super(new GridLayout(1, 2));
		add(new JButtonMine(titlePrev, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				listener.onPrev();
			}
		}));
		add(new JButtonMine(titleNext, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				listener.onNext();
			}
		}));
	}
}

