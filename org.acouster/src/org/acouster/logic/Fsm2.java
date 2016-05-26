package org.acouster.logic;

import java.util.HashMap;
import java.util.Map;

import org.acouster.IFuncVoid;

/**
 * @author MTemkine
 * @param <TEdge> typically int or String, something that triggers transitions.
 * Callbacks are defined to trigger wh
 * 
 * A graph like this simplifies logic in Games, and other programs. Code taken from AudioBooker (C#)
 * Actions happen on the edges. Nodes just have names, they represent what state you are in.
 * To contruct the graph use a sequence of 
 * addNode_0_
 * addEdge___
 * addEdge___
 * addEdge___
 * addEdge___
 * addNode_0_
 * addEdge___
 * addEdge___
 * etc...
 */
public class Fsm2<TEdge>
{
	private Fsm2Node _lastAddedNode;
    private CallbackArgs args;
    private Map<String, Fsm2Node> nodes;
    private Fsm2Node curNode;

    public Fsm2() {
        nodes = new HashMap<String, Fsm2Node>();
        args = new CallbackArgs();
    }

    public Fsm2<TEdge> addNode_0_(String key) {
        _lastAddedNode = new Fsm2Node(key);
        nodes.put(key, _lastAddedNode);
        return this;
    }
    public Fsm2<TEdge> addEdge___(TEdge trigger, String destination, IFuncVoid<CallbackArgs> callback) {
        if (_lastAddedNode == null)
            throw new RuntimeException("Must call addNode before calling addEdge");
        _lastAddedNode.Transitions.put(trigger, new Fsm2Edge(destination, callback));
        return this;
    }

    public void Goto(String key) {
        curNode = null;
        if (nodes.containsKey(key)) {
            curNode = nodes.get(key);
        }
    }
    public void Reset() {
        curNode = null;
    }

    public void Advance(TEdge action) {
        if (curNode == null)
            return;
        if (!curNode.Transitions.containsKey(action))
            return;
        Fsm2Edge edge = curNode.Transitions.get(action);
        if (!nodes.containsKey(edge.Destination))
            throw new RuntimeException("Incorrect graph. Cannot go to key " + edge.Destination);
        args.Destination = edge.Destination;
        edge.Callback.lambda(args);
        Goto(args.Destination);
    }


    //----------------- nested classes --------------------

    private class Fsm2Node {
        public String Key;
        public Map<TEdge, Fsm2Edge> Transitions;
        public Fsm2Node(String Key) {
        	this.Key = Key;
            Transitions = new HashMap<TEdge, Fsm2Edge>();
        }
    }
    private class Fsm2Edge {
        public String Destination;
        public IFuncVoid<CallbackArgs> Callback;
		public Fsm2Edge(String destination, IFuncVoid<CallbackArgs> callback) {
			Destination = destination;
			Callback = callback;
		}
    }
    
    public static class CallbackArgs {
        public String Destination;
    }
    
}

