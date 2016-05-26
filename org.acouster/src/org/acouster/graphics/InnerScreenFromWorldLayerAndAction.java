package org.acouster.graphics;

import java.util.Vector;

import org.acouster.ActionEngine;
import org.acouster.IFuncVoid;
import org.acouster.IInnerScreen;

public class InnerScreenFromWorldLayerAndAction implements IInnerScreen {

	private Vector<WorldLayer> layers;
	private ActionEngine actions;
	private IFuncVoid<InnerScreenFromWorldLayerAndAction> funcOnActivate;
	
	public InnerScreenFromWorldLayerAndAction(WorldLayer layer) {
		this (layer, null);
	}
	public InnerScreenFromWorldLayerAndAction(WorldLayer layer, ActionEngine actions)
	{
		layers = new Vector<WorldLayer>();
		layers.add(layer);
		this.actions = actions;
	}
	public InnerScreenFromWorldLayerAndAction addWorldLayer(WorldLayer layer)
	{
		layers.add(layer);
		return this;
	}
	public InnerScreenFromWorldLayerAndAction setActiveLambda(IFuncVoid<InnerScreenFromWorldLayerAndAction> func)
	{
		this.funcOnActivate = func;
		return this;
	}

	@Override
	public void setActive(boolean flag)
	{
		for (WorldLayer layer : layers)
			layer.setVisible(flag);
		if (actions != null)
			actions.setActive(flag);
		if (funcOnActivate != null && flag)
			funcOnActivate.lambda(this);
	}

}
