package org.acouster.game3d;

import java.util.HashSet;
import java.util.Vector;

import javax.management.RuntimeErrorException;

import org.acouster.game3d.graphics.RenderableObject3D;


public class World3d
{
	private class RenderableAddScheduleTuple
	{
		public RenderableObject3D renderable;
		public boolean increment;
		public boolean doZSort;
		public boolean renderAlways;
		public RenderableAddScheduleTuple(RenderableObject3D renderable,
				boolean increment, boolean doZSort, boolean renderAlways) {
			super();
			this.renderable = renderable;
			this.increment = increment;
			this.doZSort = doZSort;
			this.renderAlways = renderAlways;
		}
	}
	//TODO: background objects
	//protected Vector<RenderableObject3D> backgroundObjects;
	
	protected Vector<RenderableObject3D> allRenderables;
	protected Vector<RenderableObject3D> yesZSortRenderables;
	protected Vector<RenderableObject3D> nonZSortRenderables;
	protected Vector<RenderableObject3D> incrementableRenderables;
	protected Vector<RenderableAddScheduleTuple> addListRenderables;
	protected Vector<RenderableObject3D> deleteListRenderables;
	protected HashSet<RenderableObject3D> renderAlwaysRenderables;
	
	public World3d()
	{
		//backgroundObjects = new Vector<RenderableObject3D>();
		
		allRenderables = new Vector<RenderableObject3D>();
		yesZSortRenderables = new Vector<RenderableObject3D>();
		nonZSortRenderables = new Vector<RenderableObject3D>();
		incrementableRenderables = new Vector<RenderableObject3D>();
		addListRenderables = new Vector<RenderableAddScheduleTuple>();
		deleteListRenderables = new Vector<RenderableObject3D>();
		renderAlwaysRenderables = new HashSet<RenderableObject3D>();
	}
	
	
	// ---------------------------- renderables ---------------------------------------
	
	public void increment(int width, int height)
	{
		incrementRenderables(width, height);
	}
	
	public void incrementRenderables(int width, int height)
	{
		for (RenderableObject3D ro : incrementableRenderables)
		{
			ro.increment(width, height);
			if (ro.isExpired())
				scheduleRenderableForDeletion(ro);
		}
	}
	
	public void scheduleRenderableForAddition(RenderableObject3D obj)
	{
		scheduleRenderableForAddition(obj, false, false);
	}
	public void scheduleRenderableForAddition(RenderableObject3D obj, boolean increment)
	{
		scheduleRenderableForAddition(obj, increment, false);
	}
	public void scheduleRenderableForAddition(RenderableObject3D obj, boolean increment, boolean doZSort)
	{
		scheduleRenderableForAddition(obj, increment, doZSort, false);
	}
	public void scheduleRenderableForAddition(RenderableObject3D obj, boolean increment, boolean doZSort, boolean renderAlways)
	{
		addListRenderables.add(new RenderableAddScheduleTuple(obj, increment, doZSort, renderAlways));
	}
	public void addScheduledRenderables()
	{
		for (RenderableAddScheduleTuple cur : addListRenderables)
			addRenderable(cur.renderable, cur.increment, cur.doZSort, cur.renderAlways);
		addListRenderables.removeAllElements();
	}
	public void scheduleRenderableForDeletion(RenderableObject3D obj)
	{
		deleteListRenderables.add(obj);
	}
	public void deleteScheduledRenderables()
	{
		for (RenderableObject3D obj : deleteListRenderables)
		{
			allRenderables.remove(obj);
			if (yesZSortRenderables.contains(obj))
				yesZSortRenderables.remove(obj);
			if (nonZSortRenderables.contains(obj))
				nonZSortRenderables.remove(obj);
			if (incrementableRenderables.contains(obj))
				incrementableRenderables.remove(obj);
		}
	}
	/**
	 * Must be called from the paint thread
	 */
	public synchronized void flushRenderableList()
	{
		deleteScheduledRenderables();
		addScheduledRenderables();
		// before: incrementRenderables();
		// tmp after:
		incrementRenderables(0, 0);
		throw new RuntimeException("mar 22 2013, ading width/height to incrementable... this is when we find who calls the flush and why we need to incrementRenderable after");
	}
	
	//---------------------------------------------------
//	public Vector<RenderableObject3D> getBackgroundObjects()
//	{
//		return backgroundObjects;
//	}
	public synchronized Vector<RenderableObject3D> getObjects()
	{
		return allRenderables;
	}
	public synchronized Vector<RenderableObject3D> getYesSortZObjects()
	{
		return yesZSortRenderables;
	}
	public synchronized Vector<RenderableObject3D> getNonSortZObjects()
	{
		return nonZSortRenderables;
	}
	public synchronized boolean isRenderedAlways(RenderableObject3D obj)
	{
		return renderAlwaysRenderables.contains(obj);
	}

//	public void addBackgroundObject(RenderableObject3D obj)
//	{
//		backgroundObjects.add(obj);
//	}
	protected void addRenderable(RenderableObject3D obj)
	{
		addRenderable(obj, false, false);
	}
	protected void addRenderable(RenderableObject3D obj, boolean increment)
	{
		addRenderable(obj, false, false);
	}
	protected void addRenderable(RenderableObject3D obj, boolean increment, boolean doZSort)
	{
		addRenderable(obj, false, false, false);
	}
	protected void addRenderable(RenderableObject3D obj, boolean increment, boolean doZSort, boolean renderAlways)
	{
		allRenderables.add(obj);
		if (increment)
			incrementableRenderables.add(obj);
		if (doZSort)
			yesZSortRenderables.add(obj);
		else
			nonZSortRenderables.add(obj);
		if (renderAlways)
			renderAlwaysRenderables.add(obj);
	}
	
	//------------------------------ terrain ----------------------------------
	public double getTerrainHeightAt(double x, double z)
	{
		//TODO: implement terrain
		return 0;
	}
}
