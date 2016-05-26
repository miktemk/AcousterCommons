package org.acouster;

import org.acouster.context.ContextKeyEvent;
import org.acouster.context.ContextMouseEvent;

public class ActionEngineAdapter extends ActionEngine
{
	public ActionEngineAdapter(Game game) {
		super(game);
	}
	
	public void keyPressed(ContextKeyEvent arg0) {}
    public void keyReleased(ContextKeyEvent arg0) {}
    public void keyTyped(ContextKeyEvent arg0) {}
    
    public void mouseClicked(ContextMouseEvent arg0) {}
    public void mouseEntered(ContextMouseEvent arg0) {}
    public void mouseExited(ContextMouseEvent arg0) {}
    public void mousePressed(ContextMouseEvent arg0) {}
    public void mouseReleased(ContextMouseEvent arg0) {}
    
    public void mouseDragged(ContextMouseEvent arg0) {}
    public void mouseMoved(ContextMouseEvent arg0) {}
}
