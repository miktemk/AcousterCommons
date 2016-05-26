package org.acouster;


public class GraphTicker<T> extends BasicTicker
{
	protected INavigableGraph<T> graph;
	protected double currentDelay;
	
	public GraphTicker(INavigableGraph<T> graph)
	{
		this.graph = graph;
		this.startTick = 0;
		isPaused = false;
		gonetoNode();
	}
	
	public INavigableGraph<T> getGraph()
	{
		return graph;
	}
	
	public void gotoNode(String rootKey)
	{
		graph.gotoNode(rootKey);
		gonetoNode();
	}
	public void gotoFirstNode()
	{
		graph.gotoFirstNode();
		gonetoNode();
	}
	
	public void gonetoNode()
	{
		currentDelay = graph.getCurrentDelay();
		//System.out.println("currentDelay set: " + currentDelay);
		//startTick = System.currentTimeMillis();
		reset();
	}

	public T getCurrent() {
		return graph.getCurrentNode();
	}

	@Override
	public double getCurrentDelay() {
		// TODO Auto-generated method stub
		return currentDelay;
	}

	@Override
	public void onTimeout() {
		if (graph == null)
			return;
		graph.advance();
		//System.out.println("===>>>>>>> advance called");
		currentDelay = graph.getCurrentDelay();
		//System.out.println("===>>>>>>> currentDelay set: " + currentDelay);
		reset();
	}
}
