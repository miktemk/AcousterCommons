package org.acouster.xml.AnimatedStroke;

import java.util.List;
import java.util.Vector;

import org.acouster.graphics.anim.AnimatedStroke;
import org.acouster.graphics.anim.AnimatedStroke.StrokePoint;
import org.acouster.util.StringUtils;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name="stroke")
public class XmlAnimatedStroke
{
	public static final String MENU_XML_FILENAME = "_menu.xml";
	
	@Attribute
	private String rgb;
	@ElementList(inline=true, entry="frame")
	public List<XmlAnimatedStrokeFrame> frames;
	
	public int getRgb() {
		return StringUtils.parseHexInt(rgb);
	}
	public void setRgb(int rgbz) {
		rgb = StringUtils.intToHexPad(rgbz);
	}
	
	public XmlAnimatedStroke()
	{
		frames = new Vector<XmlAnimatedStrokeFrame>();
	}
	
	//------- logic -----
	public void addEntry(XmlAnimatedStrokeFrame item)
	{
		frames.add(item);
	}
	
	public AnimatedStroke toAnimatedStroke()
	{
		AnimatedStroke s = new AnimatedStroke(getRgb());
		for (XmlAnimatedStrokeFrame frame : frames)
			s.append(frame.time, frame.x, frame.y, frame.width, frame.isNew);
		return s;
	}
	
	public static XmlAnimatedStroke fromAnimatedStroke(AnimatedStroke other)
	{
		XmlAnimatedStroke x = new XmlAnimatedStroke();
		x.setRgb(other.getRgb());
		for (StrokePoint point : other.getPoints())
			x.addEntry(new XmlAnimatedStrokeFrame(point.time, point.x, point.y, point.width, point.isNew));
		return x;
	}
	
}
