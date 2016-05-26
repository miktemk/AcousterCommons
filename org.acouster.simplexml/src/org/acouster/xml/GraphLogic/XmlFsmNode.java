package org.acouster.xml.GraphLogic;

import java.util.List;

import org.acouster.data.GraphLogic.FsmEdge;
import org.acouster.data.GraphLogic.FsmNode;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;

public class XmlFsmNode extends FsmNode
{
	@Attribute(name="id")
	private String xmlId;
	@ElementList(inline=true, entry="edge")
	private List<XmlFsmEdge> xmlOutEdges;
	
	public List<? extends FsmEdge> getEdges() {
		return xmlOutEdges;
	}
	public String getId() {
		return xmlId;
	}
}
