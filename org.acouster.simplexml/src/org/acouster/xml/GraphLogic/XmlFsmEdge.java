package org.acouster.xml.GraphLogic;

import java.util.List;
import java.util.Vector;

import org.acouster.data.GraphLogic.FsmBranch;
import org.acouster.data.GraphLogic.FsmEdge;
import org.acouster.xml.common.XmlGameEvent;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;

public class XmlFsmEdge extends FsmEdge
{
	@Attribute
	private String command;
	@Attribute(required=false)
	private String destination;
	@ElementList(required=false, inline=true, entry="branch")
	private List<XmlFsmBranch> branches;
	
	// branch/event shortcuts...
	@Attribute(required=false)
	private String output;
	@ElementList(required=false, inline=true, entry="event")
	private List<XmlGameEvent> events;
	
	public List<? extends FsmBranch> getBranches() {
		// xml shortcut for branches...
		if (branches == null)
		{
			branches = new Vector<XmlFsmBranch>();
			branches.add(new XmlFsmBranch(FsmBranch.FUNC_Prob, 1, events, output, destination));
		}
		return branches;
	}
	public String getCommand() {
		return command;
	}
	
}
