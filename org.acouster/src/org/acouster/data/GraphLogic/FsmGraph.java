package org.acouster.data.GraphLogic;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.acouster.GameEvent;
import org.acouster.IFunc;
import org.acouster.util.ListUtils;

public class FsmGraph implements IFsmInputListener, IFsmRoot
{
	private String startnode;
	private List<FsmNode> nodes;
	
	public FsmGraph() {
		this("");
	}
	public FsmGraph(String startnode) {
		this.startnode = startnode;
		nodes = new Vector<FsmNode>();
	}
	
	public List<? extends FsmNode> getNodes() {
		return nodes;
	}
	public String getStartnode() {
		return startnode;
	}
	
	//================ logic ========================
	
	private FsmNode curNode;
	private HashMap<String, FsmNode> map;
	private List<IFsmOutputListener> fsmOutputListeners;
	
	public void compile()
	{
		map = new HashMap<String, FsmNode>();
		fsmOutputListeners = new Vector<IFsmOutputListener>();
		for (FsmNode node : getNodes())
		{
			if (!map.containsKey(node.getId()))
				map.put(node.getId(), node);
			node.compile(this);
		}
		gotoNode(getStartnode());
	}
	public void addFsmOutputListener(IFsmOutputListener listener)
	{
		fsmOutputListeners.add(listener);
	}
	public void gotoNode(String nodeId)
	{
		FsmNode newNode = map.get(nodeId);
		if (newNode == null)
			throw new RuntimeException("unknown destination: " + nodeId + "; curnode=" + ((curNode != null) ? curNode.getId() : "null..."));
		newNode.visit(newNode == curNode);
		curNode = newNode;
		
	}
	@Override
	public void handleCommand(String command)
	{
		if (curNode == null)
			return;
		curNode.handleCommand(command);
	}
	public String getCurNodeId()
	{
		if (curNode == null)
			return null;
		return curNode.getId();
	}
	public String[] getCurNodeCommands()
	{
		if (curNode == null)
			return null;
		List<String> result = ListUtils.select(curNode.getEdges(), funcEdge2Command);
		return result.toArray(new String[result.size()]);
	}
	@Override
	public void sendGameEvent(GameEvent event)
	{
		for (IFsmOutputListener l : fsmOutputListeners)
			l.handleGameEvent(event);
	}
	
	//================ misc ========================
	
	public void addNode(FsmNode node) {
		nodes.add(node);
	}
	
	
	//================ statics ========================
	private static final IFunc<FsmEdge, String> funcEdge2Command = new IFunc<FsmEdge, String>()
	{
		@Override
		public String lambda(FsmEdge value) {
			return value.getCommand();
		}
		
	};
}
