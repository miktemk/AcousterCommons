package useless;
//package org.acouster.graphics;
//
//import java.util.HashMap;
//
//import org.acouster.context.ContextBitmap;
//
//
//// TODO: extract superclass for more uses of "goto state" graphical functionality
//public class ImageGraph {
////	private class ImageNode
////	{
////		public ContextBitmap image;
////		public double delaySeconds;
////		public ImageNode next;
////		public ImageNode(ContextBitmap image, double delaySeconds) {
////			this(image, delaySeconds, null);
////		}
////		public ImageNode(ContextBitmap image, double delaySeconds, ImageNode next) {
////			super();
////			this.delaySeconds = delaySeconds;
////			this.image = image;
////			this.next = next;
////		}
////	}
////	
////	// Hopefully this is never used
////	public static final String DEFAULT_FIRST_ROOT_KEY = "root";
////	
////	protected HashMap<String, ImageNode> roots;
////	protected ImageNode last, cur;
////	public ImageGraph()
////	{
////		roots = new HashMap<String, ImageGraph.ImageNode>();
////	}
////	
////	public void addImage(ContextBitmap image, double delaySeconds) {
////		addImage(image, delaySeconds, null, false);
////	}
////	public void addImage(ContextBitmap image, double delaySeconds, String rootKey, boolean isTerminal)
////	{
////		ImageNode newGuy = new ImageNode(image, delaySeconds);
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
////		ImageNode nextGuy = roots.get(rootKey);
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
////	public ContextBitmap getCurrentImage()
////	{
////		if (cur != null)
////			return cur.image;
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
////			ImageNode walker = roots.get(key);
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
////	
//}
