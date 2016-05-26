package org.acouster.graphics;

import java.util.Vector;

import org.acouster.context.ContextGraphics;



public class World2D
{
	protected Vector<WorldLayer> layers;
	protected WorldLayer defaultLayer;
	
	public World2D()
	{
		layers = new Vector<WorldLayer>();
	}
	
	public void increment(int width, int height)
	{
		for (WorldLayer l : layers)
			l.increment(width, height);
	}
	
	public void render(ContextGraphics g, int width, int height)
	{
		for (WorldLayer l : layers)
			l.render(g, width, height);
	}
	
	public void addLayer(WorldLayer layer)
	{
		int level = layer.getLevel();
		int index = 0;
		for (WorldLayer l : layers)
		{
			if (l.level > level)
				break;
			index++;
		}
		layers.add(index, layer);
	}

	// ------------ default layer -----------------
	public void addRenderableToDefaultLayer(RenderableObject obj)
	{
		if (defaultLayer == null)
		{
			defaultLayer = new WorldLayer(0);
			layers.add(defaultLayer);
		}
		defaultLayer.addRenderable(obj);
	}
	public void removeRenderableFromDefaultLayer(RenderableObject obj)
	{
		if (defaultLayer == null)
			return;
		defaultLayer.removeRenderable(obj);
	}

	public void dispose()
	{
		for (WorldLayer l : layers)
			l.dispose();
	}
}
