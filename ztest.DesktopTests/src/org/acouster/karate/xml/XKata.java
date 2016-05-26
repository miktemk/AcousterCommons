package org.acouster.karate.xml;

import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name="kata")
public class XKata
{
	@ElementList(inline=true, entry="pose")
	public List<XPose> poses;
}

