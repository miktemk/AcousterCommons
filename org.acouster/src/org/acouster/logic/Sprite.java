package org.acouster.logic;

import org.acouster.GameEvent;
import org.acouster.Incrementable;
import org.acouster.graphics.RenderableObject;

public class Sprite implements Incrementable
{
	protected int prevWidth, prevHeight;
	protected String id;
	protected RenderableObject renderable;
	protected boolean expired = false;
	
	public Sprite(String id)
	{
		this.id = id;
	}

	// getters/setters
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	// Incrementable
	public void increment(int width, int height) {}
	public void dimensionsChanged(int width, int height) {
		prevWidth = width;
		prevHeight = height;
	}
	
	public boolean isExpired() {
		return expired;
	}
	public void expire() {
		expired = true;
	}
	
	// communication
	public void handleActionMessage(GameEvent message)
	{
		//TODO: state machine
		// ........ state machine????????
		
	}
	
	public void setRenderable(RenderableObject renderable)
	{
		this.renderable = renderable;
	}
	public RenderableObject getRenderable() {
		return renderable;
	}
	public void hide() {
		if (renderable != null)
			renderable.setVisible(false);
	}
	public void show() {
		//if (renderable != null)
			renderable.setVisible(true);
	}
	public void sendMessageToRenderable(GameEvent e)
	{
		if (renderable != null)
			renderable.handleMessage(e);
	}

}
