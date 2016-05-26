package org.acouster.platinum.xml;

import java.util.List;
import java.util.Vector;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class Lesson {

	@Element(name="header")
	public LessonHeader header;
	@ElementList(name="lines", entry="line")
	private List<LessonLine> lines;
	
	public Lesson()
	{
		lines = new Vector<LessonLine>();
	}
	
	public LessonHeader getHeader() {
		return header;
	}
	public List<LessonLine> getLines() {
		return lines;
	}
	
	public void addLine(LessonLine line) {
		lines.add(line);
	}
}