package org.acouster.graphics;

import org.acouster.IFuncSolo;
import org.acouster.context.ContextGraphics;

/** Wraps RenderableObject for temporary WorldLayer switch.
 * When expire lambda returns true, move to old layer. */
public class TempLayerResident extends RenderableObject {

	private RenderableObject obj;
	private IFuncSolo<Boolean> funcExpiration;
	private WorldLayer oldPapa;
	
	/**
	 * @param obj - the object
	 * @param funcExpiration - called from increment on every frame. Once it returns true obj is returned to oldPapa
	 * @param oldPapa - old layer, where obj is returned to
	 * @param newPapa - where we are moving the object
	 */
	public TempLayerResident(
			RenderableObject obj,
			IFuncSolo<Boolean> funcExpiration,
			WorldLayer oldPapa) {
		super();
		this.obj = obj;
		this.funcExpiration = funcExpiration;
		this.oldPapa = oldPapa;
	}

	@Override
	public void increment(int width, int height) {
		obj.increment(width, height);
		if (funcExpiration.lambda())
		{
			oldPapa.addRenderableAsync(obj);
			expire(); // kill this tempLayerResident form
		}
	}

	@Override
	public void render(ContextGraphics g, int w, int h) {
		obj.render(g, w, h);
	}

}
