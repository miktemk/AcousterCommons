package useless;
//package org.acouster.game2D.graphics;
//
//import org.acouster.context.ContextBitmap;
//import org.acouster.game2D.math.GameMath;
//import org.acouster.graphics.ImageGraph;
//
//
//public class ImageGraphTicker {
////	protected ImageGraph graph;
////	protected double currentDelay;
////	protected double timeline;
////	
////	public ImageGraphTicker(ImageGraph graph)
////	{
////		this.graph = graph;
////		this.timeline = 0;
////	}
////	
////	public ImageGraph getGraph()
////	{
////		return graph;
////	}
////	
////	public void gotoRoot(String rootKey)
////	{
////		graph.gotoRoot(rootKey);
////		currentDelay = graph.getCurrentDelay();
////	}
////
////	public void increment()
////	{
////		if (graph != null)
////		{
////			// TODO: right now currentDelay is used like frameRate in ImageSequeceTicker... this is its inverse meaning
////			if ((int)timeline != (int)(timeline+currentDelay))
////			{
////				graph.advanceImage();
////				currentDelay = graph.getCurrentDelay();
////				timeline = 0;
////			}
////			timeline += currentDelay;
////		}
////	}
////
////	public ContextBitmap getImage() {
////		return graph.getCurrentImage();
////	}
//}
