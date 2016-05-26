package org.acouster.graphics;

import java.util.Collections;
import java.util.List;
import java.util.Vector;

import org.acouster.IFuncSolo;
import org.acouster.context.ContextGraphics;
import org.acouster.util.GameUtils;


public class WorldLayer
{
	protected List<RenderableObject> renderables;
	protected List<RenderableObject> toAdd, toDelete;
	protected int level;
	protected boolean isVisible;
	
	public WorldLayer(int level)
	{
		this.level = level;
		isVisible = true;
		renderables = Collections.synchronizedList(new Vector<RenderableObject>());
		toAdd = Collections.synchronizedList(new Vector<RenderableObject>());
		toDelete = Collections.synchronizedList(new Vector<RenderableObject>());
	}

	public void increment(int width, int height)
	{
		GameUtils.Increment(renderables, width, height);
	}
	public void render(ContextGraphics g, int width, int height)
	{
		synchronized(renderables)
		{
			// dispose of expired renderables
			for (RenderableObject o : renderables)
				if (o.isExpired())
					toDelete.add(o);
			for (RenderableObject o : toDelete)
				renderables.remove(o);
			for (RenderableObject o : toAdd)
				renderables.add(o);
			toDelete.clear();
			toAdd.clear();
			
			// dispose of expired renderables
			if (!isVisible)
				return;
			for (RenderableObject o : renderables) {
				if (!o.isVisible())
					continue;
				o.render(g, width, height);
			}
		}
	}

	/** to be called from anywhere BUT this Layer's increment loop */
	public void addRenderable(RenderableObject obj) {
		if (obj == null)
			throw new NullPointerException("You added a null renderable, you goddamn vegetarian :(");
		renderables.add(obj);
		obj.setDaddy(this);
		
		// TODO: if Collections.synchronizedList still fails us
		//synchronized(renderables) { renderables.add(obj); }
	}
	/** to be called from this Layer's increment loop ONLY */
	public void addRenderableAsync(RenderableObject obj) {
		if (obj == null)
			throw new NullPointerException("You added a null renderable, you goddamn vegetarian :(");
		toAdd.add(obj);
		obj.setDaddy(this);
		
		// TODO: if Collections.synchronizedList still fails us
		//synchronized(renderables) { renderables.add(obj); }
	}
	/** to be called from anywhere BUT this Layer's increment loop */
	public void removeRenderable(RenderableObject obj) {
		if (obj == null)
			throw new NullPointerException("Remove what renderable? A null?? Goddamn vegetarian... :(");
		renderables.remove(obj);
		
		// TODO: if Collections.synchronizedList still fails us
		//synchronized(renderables) { renderables.remove(obj); }
	}
	/** to be called from this Layer's increment loop ONLY */
	public void removeRenderableAsync(RenderableObject obj) {
		if (obj == null)
			throw new NullPointerException("Remove what renderable? A null?? Goddamn vegetarian... :(");
		toDelete.add(obj);
	}
	/** to be called from anywhere BUT this Layer's increment loop */
	public void clear() {
		renderables.clear();
	}
	/** to be called from this Layer's increment loop ONLY */
	public void clearAsync() {
		for (RenderableObject obj : renderables)
			toDelete.add(obj);
	}

	public int getLevel() {
		return level;
	}
	public boolean isVisible() {
		return isVisible;
	}
	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public void dispose()
	{
		for (RenderableObject o : renderables)
			o.dispose();
	}

	/**
	 * Moves obj to me... then, when funcExpiration returns true it back to the old layer (oldPapa)
	 * @param obj - the renderable
	 * @param funcExpiration - called from increment on every frame. Once it returns true obj is returned to old layer
	 */
	public void moveToMeTemporarily(RenderableObject obj, IFuncSolo<Boolean> funcExpiration)
	{
		WorldLayer oldPapa = obj.whoIsYourDaddy();
		oldPapa.removeRenderableAsync(obj);
		addRenderableAsync(new TempLayerResident(obj, funcExpiration, oldPapa));
	}

}
