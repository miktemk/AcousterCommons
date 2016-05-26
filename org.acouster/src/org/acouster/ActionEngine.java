package org.acouster;

import org.acouster.context.ContextKeyEvent;
import org.acouster.context.ContextMouseEvent;

public abstract class ActionEngine
{
	protected Game game;
	protected GameEvent recycledMessage;
	protected boolean isActive;
	
	public ActionEngine(Game game) {
		this.game = game;
		isActive = true;
	}
	
	protected void sendRecycledMessage(String target, String body) {
		if (recycledMessage == null)
			recycledMessage = new GameEvent();
		recycledMessage.setTarget(target);
		recycledMessage.setBody(body);
		game.handleActionMessage(recycledMessage);
	}
	
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	// abstract methods
	
	public abstract void keyPressed(ContextKeyEvent arg0);
    public abstract void keyReleased(ContextKeyEvent arg0);
    public abstract void keyTyped(ContextKeyEvent arg0);
    
    public abstract void mouseClicked(ContextMouseEvent arg0);
    public abstract void mouseEntered(ContextMouseEvent arg0);
    public abstract void mouseExited(ContextMouseEvent arg0);
    public abstract void mousePressed(ContextMouseEvent arg0);
    public abstract void mouseReleased(ContextMouseEvent arg0);
    
    public abstract void mouseDragged(ContextMouseEvent arg0);
    public abstract void mouseMoved(ContextMouseEvent arg0);
}
