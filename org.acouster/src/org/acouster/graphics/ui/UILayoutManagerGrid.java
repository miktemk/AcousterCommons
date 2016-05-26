package org.acouster.graphics.ui;

import java.util.List;

// TODO: decouple from XML and put into acouster
public class UILayoutManagerGrid extends UILayoutManagerByPercentage
{
	private List<GridLayoutBlock> blocks;
	private int w, h, index = 0;
	
	public UILayoutManagerGrid(int w, int h, List<GridLayoutBlock> blocks)
	{
		this.w = w;
		this.h = h;
		this.blocks = blocks;
	}
	
	public void addUI(UIElementBase elem)
	{
		if (index > blocks.size() - 1)
			return;
		GridLayoutBlock curBlock = blocks.get(index);
		index++;
		
		//super.addUI(elem, 100.0 * curBlock.w / w, 100.0 * curBlock.x / w, 100.0 * curBlock.y / w, RATIO_TYPE_X_DOMINANT);
		super.addUI(elem,
				100.0 * curBlock.w / w,
				100.0 * curBlock.h / h,
				100.0 * curBlock.x / w,
				100.0 * curBlock.y / h,
				RATIO_TYPE_INDEPENDANT, false);
	}

	public void clear() {
		elems.clear();
	}

}

