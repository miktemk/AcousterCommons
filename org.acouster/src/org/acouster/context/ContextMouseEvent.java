package org.acouster.context;

public class ContextMouseEvent
{
	private int x, y, eventType;
	private boolean isProcessed;
	public ContextMouseEvent() {
		super();
		isProcessed = false;
	}
	public ContextMouseEvent(int x, int y, int eventType) {
		super();
		set(x, y, eventType);
	}
	public ContextMouseEvent set(int x, int y, int eventType) {
		this.x = x;
		this.y = y;
		this.eventType = eventType;
		isProcessed = false;
		return this;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getEventType() {
		return eventType;
	}
	public boolean isProcessed() {
		return isProcessed;
	}
	public void setProcessed(boolean isProcessed) {
		this.isProcessed = isProcessed;
	}
	
}
