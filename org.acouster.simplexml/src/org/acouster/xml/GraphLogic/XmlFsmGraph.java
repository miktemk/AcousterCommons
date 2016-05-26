package org.acouster.xml.GraphLogic;

import java.util.List;

import org.acouster.data.GraphLogic.FsmGraph;
import org.acouster.data.GraphLogic.FsmNode;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;


@Root(name="FsmGraph")
public class XmlFsmGraph extends FsmGraph
{
	@Attribute(name="startnode")
	private String xmlStartnode;
	@ElementList(inline=true, entry="node")
	private List<XmlFsmNode> nodes;
	
//	public XmlFsmGraph() {
//		this("");
//	}
//	public XmlFsmGraph(String startnode) {
//		this.startnode = startnode;
//		nodes = new Vector<XmlFsmNode>();
//	}
	
	public List<? extends FsmNode> getNodes() {
		return nodes;
	}
	public String getStartnode() {
		return xmlStartnode;
	}
	
	//================ covert to data.* ========================

//	@Override
//	public void compile()
//	{
//		return FsmGraphFactory.FromXmlObject(this);
//	}
}
