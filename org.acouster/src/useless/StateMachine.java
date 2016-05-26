package useless;
//package org.acouster.logic;
//
//import java.util.HashMap;
//
//import org.acouster.context.ContextBitmap;
//
//
//public class StateMachine
//{
////	//private class
////	private class StateMachineNode
////	{
////		public String name;
////		public double delaySeconds;
////		public StateMachineNode next;
////		public StateMachineNode(String name, double delaySeconds) {
////			this(name, delaySeconds, null);
////		}
////		public StateMachineNode(String name, double delaySeconds, StateMachineNode next) {
////			super();
////			this.delaySeconds = delaySeconds;
////			this.name = name;
////			this.next = next;
////		}
////	}
////	
////	// Hopefully this is never used
////	public static final String DEFAULT_FIRST_ROOT_KEY = "root";
////	
////	protected HashMap<String, StateMachineNode> roots;
////	protected StateMachineNode last, cur;
////	public StateMachine()
////	{
////		roots = new HashMap<String, StateMachineNode>();
////	}
////	
////	public void addImage(String name, double delaySeconds) {
////		addImage(name, delaySeconds, null, false);
////	}
////	public void addImage(String name, double delaySeconds, String rootKey, boolean isTerminal)
////	{
////		StateMachineNode newGuy = new StateMachineNode(name, delaySeconds);
////		// Hopefully this should never happen
////		if ((last == null) && (rootKey == null))
////			rootKey = DEFAULT_FIRST_ROOT_KEY;
////		// do linking
////		if ((last != null) && !isTerminal)
////			last.next = newGuy;
////		if (rootKey != null)
////			roots.put(rootKey, newGuy);
////		last = newGuy;
////	}
////	public void linkTo(String rootKey) {
////		StateMachineNode nextGuy = roots.get(rootKey);
////		if (last != null)
////			last.next = nextGuy;
////		last = nextGuy;
////	}
////	
////	public void gotoRoot(String rootKey)
////	{
////		cur = roots.get(rootKey);
////	}
////	public void advanceImage()
////	{
////		if ((cur != null) && (cur.next != null))
////			cur = cur.next;
////	}
////	
////	public String getCurrentStateName()
////	{
////		if (cur != null)
////			return cur.name;
////		return null;
////	}
////	public double getCurrentDelay()
////	{
////		if (cur != null)
////			return cur.delaySeconds;
////		return 0;
////	}
////
////	public void printAllPaths() {
////		for (String key : roots.keySet())
////		{
////			StateMachineNode walker = roots.get(key);
////			int safetyTimeout = 0;
////			System.out.print("[" + walker.delaySeconds + "]");
////			while (true)
////			{
////				walker = walker.next;
////				if (walker == null)
////				{
////					System.out.print(" -> |");
////					break;
////				}
////				System.out.print(" -> [" + walker.delaySeconds + "]");
////				safetyTimeout++;
////				if (safetyTimeout > 20)
////					break;
////			}
////			System.out.println("");
////		}
////		
////	}
//}
