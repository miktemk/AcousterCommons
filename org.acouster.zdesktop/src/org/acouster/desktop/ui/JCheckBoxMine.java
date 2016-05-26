package org.acouster.desktop.ui;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;
import javax.swing.SwingConstants;

import org.acouster.IFuncVoid;

public class JCheckBoxMine extends JCheckBox implements ItemListener
{
	private static final long serialVersionUID = 1L;
	
	protected IFuncVoid<Boolean> lambda;
	public JCheckBoxMine(String text, boolean isChecked, IFuncVoid<Boolean> lambda)
	{
		super(text);
		setSelected(isChecked);
		this.lambda = lambda;
		addItemListener(this);
		withAlign(SwingConstants.LEFT);
	}
	
	/**
	 * @param align - SwingConstants.LEFT, SwingConstants.RIGHT
	 */
	public JCheckBoxMine withAlign(int align) {
		setHorizontalTextPosition(align);
		return this;
	}
	public JCheckBoxMine withTooltip(String tooltip) {
		setToolTipText(tooltip);
		return this;
	}

	@Override
	public void itemStateChanged(ItemEvent e)
	{
		if (lambda != null)
		{
			lambda.lambda(isSelected());
		}		
	}
}
