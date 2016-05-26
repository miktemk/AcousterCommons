package org.acouster.data.GraphLogic;

import java.util.List;
import java.util.Vector;


public class FsmNode implements IFsmInputListener, IFsmNode
{
	private String id;
	private List<FsmEdge> outEdges;
	
	public FsmNode() {
		this("");
	}
	public FsmNode(String id) {
		this.id = id;
		outEdges = new Vector<FsmEdge>();
	}
	
	public List<? extends FsmEdge> getEdges() {
		return outEdges;
	}
	public String getId() {
		return id;
	}
	
	//================ logic ========================
	
	private IFsmRoot iGraph;
	private int nodeVisits;
	private int nodeNewVisits;
	
	public void compile(IFsmRoot iGraph)
	{
		this.iGraph = iGraph;
		nodeVisits = 0;
		nodeNewVisits = 0;
		for (FsmEdge edge : getEdges())
		{
			edge.compile(iGraph, this);
		}
	}
	public void handleCommand(String command)
	{
		if (command == null || "".equals(command))
			return;
		for (FsmEdge edge : getEdges())
		{
			if (edge.getCommand().equals(command))
			{
				edge.handleCommand(command);
				return;
			}
		}
		throw new RuntimeException("unable to handle command '" + command + "' from node '" + getId() + "'");
	}
	@Override
	public int getVisitCount() {
		return nodeVisits;
	}
	@Override
	public int getNewVisitCount() {
		return nodeNewVisits;
	}
	@Override
	public void visit(boolean encore) {
		if (encore)
			nodeNewVisits = 0;
		nodeVisits++;
		nodeNewVisits++;
	}
	
	//================ misc ========================
	
	public void addEdge(FsmEdge edge) {
		outEdges.add(edge);
	}
	
}
