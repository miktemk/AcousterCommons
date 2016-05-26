package org.acouster.math;

public class Rect2D
{
	public int x, y, w, h;

	public Rect2D() {
		this(0, 0, 0, 0);
	}
	public Rect2D(int x, int y, int w, int h) {
		super();
		set(x, y, w, h);
	}

	public void set(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
}
