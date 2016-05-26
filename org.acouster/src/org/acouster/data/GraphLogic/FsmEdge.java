package org.acouster.data.GraphLogic;

import java.util.List;
import java.util.Vector;

import org.acouster.GameEvent;

public class FsmEdge implements IFsmInputListener, IFsmEdge
{
	private String command;
	private List<FsmBranch> branches;
	
	public FsmEdge() {
		this("");
	}
	public FsmEdge(String command)
	{
		this.command = command;
		branches = new Vector<FsmBranch>();
	}
	
	public List<? extends FsmBranch> getBranches() {
		return branches;
	}
	public String getCommand() {
		return command;
	}
	
	//================ logic ========================
	
	private IFsmRoot iGraph;
	private Vector<FsmBranch> tmpBranches;
	
	public void compile(IFsmRoot iGraph, IFsmNode iNode)
	{
		this.iGraph = iGraph;
		tmpBranches = new Vector<FsmBranch>();
		for (FsmBranch b : getBranches())
		{
			b.compile(iGraph, iNode, this);
		}
	}
	public void handleCommand(String command2)
	{
		// command2 == to command if called from Node's handleCommand
		
		// grab a list of all probs till the total prob >= 1
		tmpBranches.clear();
		double totalProb = 0;
		for (FsmBranch b : getBranches())
		{
			double val = b.executeBranchFunction();
			if (val > 0)
			{
				tmpBranches.add(b);
				totalProb += val;
			}
			if (totalProb >= 1)
				break;
		}
		
		// select a branch based on prob
		FsmBranch branch = null;
		if (tmpBranches.size() == 0)
			branch = null;
		else if (tmpBranches.size() == 0)
			branch = tmpBranches.firstElement();
		else
		{
			// jesus u must be kidding me, doesnt this exist somewhere.. the code for selecting a random element
			double marker = Math.random() * totalProb;
			totalProb = 0;
			for (FsmBranch b : tmpBranches)
			{
				totalProb += b.executeBranchFunction();
				if (totalProb > marker)
				{
					branch = b;
					break;
				}
			}
			branch = tmpBranches.lastElement();
		}
		if (branch == null)
			return;
		
		// haza
		for (GameEvent e : branch.getEvents())
			iGraph.sendGameEvent(e);
		iGraph.gotoNode(branch.getDestination());
	}
	
	//================ misc ========================
	
	public void addBranch(FsmBranch branch) {
		branches.add(branch);
	}
	
}
