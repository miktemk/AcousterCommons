package org.acouster.graphics.ui;

public interface ScreenUIButtonListener
{
	public abstract void actionDown(Object source);
	public abstract void actionUp(Object source, boolean withinBounds);
	public abstract void actionScratch(Object source, int x, int y);
}
