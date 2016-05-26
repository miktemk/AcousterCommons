package org.acouster;

import java.util.HashMap;

import org.acouster.context.ContextBitmap;


public class Graph<T> implements INavigableGraph<T>
{
	public interface IGraphCommand<R>
	{
		public static final int COMMAND_TYPE_NODE = 1;
		/** gonna start a new chain. No link to prev */
		public static final int COMMAND_TYPE_KEYNODE = 2;
		/** links to id */
		public static final int COMMAND_TYPE_LINKTO = 3;
		public static final int COMMAND_TYPE_BREAK = 4;
		
		int getType();
		String getId();
		double getDelaySeconds();
		R getNodeObject();
	}
	private class GraphNode<R>
	{
		public R value;
		public double delaySeconds;
		public GraphNode<R> next;
		public String key;
		public boolean isLoop;
		public GraphNode(R name, double delaySeconds, String key) {
			this(name, delaySeconds, key, null);
		}
		public GraphNode(R name, double delaySeconds, String key, GraphNode<R> next) {
			super();
			this.delaySeconds = delaySeconds;
			this.value = name;
			this.next = next;
			this.key = key;
			isLoop = false;
		}
	}
	public interface OnAdvanceListener<R>
	{
		public void onAdvance(R content);
	}
	
	// Hopefully this is never used
	public static final String DEFAULT_FIRST_ROOT_KEY = "root";
	
	protected HashMap<String, GraphNode<T>> roots;
	protected GraphNode<T> last, cur, first;
	protected OnAdvanceListener<T> onAdvanceListener;
	public Graph()
	{
		roots = new HashMap<String, GraphNode<T>>();
	}
	
	// construction
	public void addNode(T value) {
		// used for non-timed graphs
		addNode(value, 1);
	}
	public void addNode(T value, double delaySeconds) {
		addNode(value, delaySeconds, null, false);
	}
	public void addNode(T value, double delaySeconds, String rootKey) {
		addNode(value, delaySeconds, rootKey, false);
	}
	public void addNode(T value, double delaySeconds, String rootKey, boolean startNewChain)
	{
		GraphNode<T> newGuy = new GraphNode<T>(value, delaySeconds, rootKey);
		// Hopefully this should never happen
		if ((last == null) && (rootKey == null))
			rootKey = DEFAULT_FIRST_ROOT_KEY;
		if (first == null)
			first = newGuy;
		// do linking
		if ((last != null) && !startNewChain)
			last.next = newGuy;
		if (rootKey != null)
			roots.put(rootKey, newGuy);
		last = newGuy;
	}
	public void linkTo(String nodeKey)
	{
		GraphNode<T> nextGuy = roots.get(nodeKey);
		linkTo(nextGuy);
	}
	public void linkToFirstNode()
	{
		linkTo(first);
	}
	private void linkTo(GraphNode<T> nextGuy)
	{
		if (last != null)
		{
			last.next = nextGuy;
			// loop checker
//			if (nextGuy != null && nextGuy.isLoop)
//				last.isLoop = 
//			GraphNode<T> test = nextGuy;
//			while (test != null)
//			{
//				test = test.next;
//			}
		}
		// TODO: look at this and tell me if we need to set last,
		// i mean, u dont wanna ruin the existing chain, do u
		last = null;
		//last = nextGuy;
	}
	public void breakChain() {
		last = null;
	}
	
	// listeners
	public OnAdvanceListener<T> getOnAdvanceListener() {
		return onAdvanceListener;
	}
	public void setOnAdvanceListener(OnAdvanceListener<T> onAdvanceListener) {
		this.onAdvanceListener = onAdvanceListener;
	}

	// INavigableGraph members
	public void gotoNode(String nodeKey)
	{
		gotoNodeSilent(nodeKey);
		triggerAdvanceListener();
	}
	public void gotoNodeSilent(String nodeKey)
	{
		cur = roots.get(nodeKey);
		if (cur == null)
			throw new RuntimeException("gotoNode called with non existing key: " + nodeKey);
	}
	public void gotoFirstNode()
	{
		cur = first;
		triggerAdvanceListener();
	}
	public void gotoFirstNodeSilent()
	{
		cur = first;
	}
	public void advance()
	{
		if ((cur != null) && (cur.next != null))
		{
			cur = cur.next;
			triggerAdvanceListener();
		}
	}
	public void advanceTillEnd(int timeout)
	{
		int i = 0;
		while ((cur != null) && (cur.next != null))
		{
			advance();
			i++;
			if (i >= timeout)
				throw new RuntimeException("Timeout " + timeout + " exceeded on advanceTillEnd!");
		}
	}
	public T getCurrentNode()
	{
		if (cur != null)
			return cur.value;
		return null;
	}
	public double getCurrentDelay()
	{
		if (cur != null)
			return cur.delaySeconds;
		return 0;
	}
	public String getCurrentNodeKey()
	{
		if (cur != null)
			return cur.key;
		return null;
	}
	public T getFirstNode() {
		return first.value;
	}
	public T getNode(String nodeKey) {
		if (roots.containsKey(nodeKey))
			return roots.get(nodeKey).value;
		return null;
	}

	
	// misc navigation
	public boolean isEndOfTheLine()
	{
		return (cur == null || cur.next == null);
	}
	private void triggerAdvanceListener()
	{
		if (onAdvanceListener != null && cur != null)
			onAdvanceListener.onAdvance(cur.value);
	}
	
	// ============= static functions ========================

	public static String key(int key)
	{
		return "" + key;
	}
	
	// ============= DEBUG SHITE ========================

	public void printAllPaths() {
		for (String key : roots.keySet())
		{
			GraphNode<T> walker = roots.get(key);
			int safetyTimeout = 0;
			DebugUtil.lll(key + ": ");
			DebugUtil.lll("[" + walker.delaySeconds + "," + walker.value.toString() + "]");
			while (true)
			{
				walker = walker.next;
				if (walker == null)
				{
					DebugUtil.lll(" -> |");
					break;
				}
				DebugUtil.lll(" -> [" + walker.delaySeconds + "," + walker.value.toString() + "]");
				safetyTimeout++;
				if (safetyTimeout > 20)
					break;
			}
			DebugUtil.sss("");
		}
		
	}

}
