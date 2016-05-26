package org.acouster.xml.AnimatedStroke;

import org.simpleframework.xml.Attribute;

public class XmlAnimatedStrokeFrame
{
	@Attribute
	public long time;
	@Attribute
	public float x, y;
	@Attribute
	public int width;
	@Attribute
	public boolean isNew;
	
	public XmlAnimatedStrokeFrame() {
		this(0, 0, 0, 0, false);
	}
	public XmlAnimatedStrokeFrame(long time, float x, float y, int width, boolean isNew) {
		super();
		this.time = time;
		this.x = x;
		this.y = y;
		this.width = width;
		this.isNew = isNew;
	}
	
}
