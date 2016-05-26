package org.acouster.desktop.ui;

import javax.swing.ButtonGroup;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class JToggleButtonMine<T> extends JToggleButton
{
	public interface IToolSetter<Ttool>
	{
		void setTool(Ttool tool);
	}
	
	public JToggleButtonMine(final T tool, final IToolSetter<T> setter) {
		this("", tool, setter);
	}
	public JToggleButtonMine(String text, final T tool, final IToolSetter<T> setter)
	{
		super(text);
//		addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				setter.setTool(tool);
//			}
//		});
		addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				if (JToggleButtonMine.this.isSelected())
				{
					setter.setTool(tool);
				}
			}
		});
	}
	
	public JToggleButtonMine<T> addToButtonGroup(ButtonGroup g)
	{
		g.add(this);
		return this;
	}
	
}
