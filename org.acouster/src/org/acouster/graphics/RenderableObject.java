package org.acouster.graphics;

import org.acouster.GameEvent;
import org.acouster.Incrementable;
import org.acouster.context.ContextGraphics;


public abstract class RenderableObject implements Incrementable
{
	protected boolean expired = false;
	protected boolean visible = true;
	protected WorldLayer daddy;
	
	// Incrementable
	public void increment(int width, int height) {}
	public boolean isExpired() {
		return expired;
	}
	protected void expire() {
		expired  = true;
	}
	public boolean isVisible() {
		return visible;
	}
	public RenderableObject setVisible(boolean visible) {
		this.visible = visible;
		return this;
	}
	
	// methods
	public abstract void render(ContextGraphics g, int w, int h);
	public void handleMessage(GameEvent event) {}
	public void dispose() {}
	
	public void setDaddy(WorldLayer daddy) {
		this.daddy = daddy;
	}
	public WorldLayer whoIsYourDaddy() {
		return daddy;
	}
}
