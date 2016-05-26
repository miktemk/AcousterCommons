package org.acouster.data.GraphLogic;

import java.util.List;
import java.util.Vector;

import org.acouster.GameEvent;

public class FsmBranch
{
	private String func;
	private double value;
	//private String output;
	private String destination;
	private List<GameEvent> events;
	
	public FsmBranch() {
		this("", 0, "", "");
	}
	public FsmBranch(String func, double value, String output, String destination) {
		super();
		events = new Vector<GameEvent>();
		this.func = func;
		this.value = value;
		//this.output = output;
		this.destination = destination;
	}
	
	public String getFunc() {
		return func;
	}
	public double getValue() {
		return value;
	}
//	public String getOutput() {
//		return output;
//	}
	public String getDestination() {
		return destination;
	}
	public List<GameEvent> getEvents() {
		return events;
	}

	//================ logic ========================
	
	public static final String FUNC_NodeVisit = "node_visit";
	public static final String FUNC_NodeNewVisit = "node_new_visit";
	public static final String FUNC_EdgeVisit = "edge_visit";
	public static final String FUNC_EdgeNewVisit = "edge_new_visit";
	public static final String FUNC_Prob = "prob";	
	
	private IFsmRoot iGraph;
	private IFsmNode iNode;
	private IFsmEdge iEdge;
	
	public void addGameEvent(String target, String body)
	{
		events.add(new GameEvent(target, body));
	}
	public void compile(IFsmRoot iGraph, IFsmNode iNode, IFsmEdge iEdge)
	{
		this.iGraph = iGraph;
		this.iNode = iNode;
		this.iEdge = iEdge;
		//System.out.println("func: " + func);
	}
	public double executeBranchFunction()
	{
		if (FUNC_NodeVisit.equals(getFunc()))
		{
			//System.out.println(iNode.getVisitCount() + " : " + value);
			if (iNode.getVisitCount() <= getValue())
				return 1;
			else
				return 0;
		}
		else if (FUNC_NodeNewVisit.equals(getFunc()))
		{
			if (iNode.getNewVisitCount() <= getValue())
				return 1;
			else
				return 0;
		}
		else if (FUNC_EdgeVisit.equals(getFunc()))
		{
			// TODO: this
		}
		else if (FUNC_EdgeNewVisit.equals(getFunc()))
		{
			// TODO: this			
		}
		else if (FUNC_Prob.equals(getFunc()))
			return getValue();
		
		return 0;
	}
	
}
