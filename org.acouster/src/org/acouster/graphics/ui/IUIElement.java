package org.acouster.graphics.ui;

import org.acouster.context.ContextMouseEvent;

public interface IUIElement
{
    boolean checkCollision(int mx, int my);
	void mousePressed(ContextMouseEvent e);
	void mouseDragged(ContextMouseEvent e);
	void mouseReleased(ContextMouseEvent e);
	boolean isVisible();

}
