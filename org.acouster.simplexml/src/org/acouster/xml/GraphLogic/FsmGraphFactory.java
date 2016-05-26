//package org.acouster.xml.GraphLogic;
//
//import org.acouster.data.GraphLogic.*;
//
//public class FsmGraphFactory {
//	
//	public static FsmGraph FromXmlObject(XmlFsmGraph x)
//	{
//		FsmGraph y = new FsmGraph(x.getStartnode());
//		for (XmlFsmNode node : x.getNodes())
//			y.addNode(FromXmlObject_Node(node));
//		return y;
//	}
//	
//	private static FsmNode FromXmlObject_Node(XmlFsmNode x)
//	{
//		FsmNode y = new FsmNode(x.getId());
//		for (XmlFsmEdge edge : x.getEdges())
//			y.addEdge(FromXmlObject_Edge(edge));
//		return y;
//	}
//	
//	private static FsmEdge FromXmlObject_Edge(XmlFsmEdge x)
//	{
//		FsmEdge y = new FsmEdge(x.getCommand());
//		for (XmlFsmBranch b : x.getBranches())
//			y.addBranch(new FsmBranch(b.getFunc(), b.getValue(), b.getOutput(), b.getDestination()));
//		return y;
//	}
//	
//}
