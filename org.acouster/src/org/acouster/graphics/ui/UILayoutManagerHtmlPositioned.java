package org.acouster.graphics.ui;

import java.util.Vector;

import org.acouster.DebugUtil;
import org.acouster.logic.HtmlPositioner;

public class UILayoutManagerHtmlPositioned extends UILayoutManager
{
	private class ElemPosTuple
	{
		public UIElementBase elem;
		public HtmlPositioner pos;
		
		public ElemPosTuple(UIElementBase elem, HtmlPositioner pos) {
			super();
			this.elem = elem;
			this.pos = pos;
		}
	}
	
	
	// elements
	protected Vector<ElemPosTuple> elems;
	
	public UILayoutManagerHtmlPositioned()
	{
		elems = new Vector<ElemPosTuple>();
	}
	
	public void addUI(UIElementBase elem, HtmlPositioner pos)
	{
		elems.add(new ElemPosTuple(elem, pos));
	}
	
	public void clear() {
		elems.clear();
	}
	
	@Override
	public void rearrange(int width, int height)
	{
		for (ElemPosTuple elem : elems)
		{
			elem.pos.updateCache(width, height);
			elem.elem.setFrame(
					elem.pos.getCacheX(),
					elem.pos.getCacheY(),
					elem.pos.getObjectWidth(),
					elem.pos.getObjectHeight());
		}
	}

	

}
