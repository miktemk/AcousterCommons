package org.acouster.desktop.ui;

import java.awt.BorderLayout;
import java.text.DecimalFormat;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.acouster.IFuncVoid;

public class JSliderMine extends JPanel
{
	protected final int SLIDER_MAX = 100;
	public JSliderMine(final String title, final double min, final double max, double curValue, final IFuncVoid<Double> func)
	{
		super(new BorderLayout());
		
		final JLabel valueLabel = new JLabel(getLabelText(title, curValue));
		final JSlider slider = new JSlider(0, SLIDER_MAX, (int)Math.round(100 * (curValue-min) / (max-min)));
		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent event) {
				double value = min + (max-min) * slider.getValue() / SLIDER_MAX;
				valueLabel.setText(getLabelText(title, value));
				func.lambda(value);
			}
		});
		
		add(valueLabel, BorderLayout.WEST);
		add(slider, BorderLayout.CENTER);
	}
	
	private String getLabelText(String title, double value)
	{
		return title + ": " + new DecimalFormat("#.####").format(value);
	}
	
}
