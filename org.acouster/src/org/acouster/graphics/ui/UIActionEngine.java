package org.acouster.graphics.ui;

import java.util.Vector;

import org.acouster.ActionEngine;
import org.acouster.Game;
import org.acouster.context.ContextKeyEvent;
import org.acouster.context.ContextMouseEvent;

public class UIActionEngine extends ActionEngine
{
	protected Vector<IUIElement> elements;
	protected IUIElement hook = null;
	protected boolean flagSetEventProcessedAfterDone;
	
	public UIActionEngine(Game game)
	{
		super(game);
		elements = new Vector<IUIElement>();
	}
	public void setEventProcessedAfterDone(boolean flag) {
		flagSetEventProcessedAfterDone = flag;
	}
	
	// TODO: make it return T... the following overload is temp!!!
	//public T addUI<T>(T extends IUIElement elem)
	public UIElementBase addUI(UIElementBase elem)
	{
		elements.add(elem);
		return elem;
	}
	public void addUI(IUIElement elem) {
		elements.add(elem);
		//return elem;
	}
	public void removeUI(IUIElement elem) {
		elements.remove(elem);
	}
	public void clear() {
		elements.clear();
	}
	
	@Override
	public void keyPressed(ContextKeyEvent arg0) {}
	@Override
	public void keyReleased(ContextKeyEvent arg0) {}
	@Override
	public void keyTyped(ContextKeyEvent arg0) {}
	@Override
	public void mouseClicked(ContextMouseEvent arg0) {}
	@Override
	public void mouseEntered(ContextMouseEvent arg0) {}
	@Override
	public void mouseExited(ContextMouseEvent arg0) {}
	@Override
	public void mouseMoved(ContextMouseEvent arg0) {}
	
	
	@Override
	public void mousePressed(ContextMouseEvent e)
	{
		for (IUIElement elem : elements)
		{
			if (!elem.isVisible())
				continue;
			if (elem.checkCollision(e.getX(), e.getY()))
			{
				hook = elem;
				break;
			}
		}
		if (hook != null) {
			hook.mousePressed(e);
			if (flagSetEventProcessedAfterDone)
				e.setProcessed(true);
		}
	}
	@Override
	public void mouseDragged(ContextMouseEvent e)
	{
		if (hook != null) {
			hook.mouseDragged(e);
			if (flagSetEventProcessedAfterDone)
				e.setProcessed(true);
		}
	}
	@Override
	public void mouseReleased(ContextMouseEvent e)
	{
		if (hook != null) {
			hook.mouseReleased(e);
			if (flagSetEventProcessedAfterDone)
				e.setProcessed(true);
		}
		hook = null;
	}
	

}
