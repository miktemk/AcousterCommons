package org.acouster.graphics.ui;

import java.util.Vector;


public class UILayoutManagerCentered extends UILayoutManager
{
	// elements
	protected Vector<UIElementBase> elems;
	// layout parameters
	protected float marginMin, spacingVertical, spacingCol;
	protected int nColumns;
	
	public UILayoutManagerCentered(float marginMin, float spacing) {
		this(marginMin, spacing, 1, 0);
	}
	public UILayoutManagerCentered(float marginMin, float spacingVertical, int nColumns, float spacingCol)
	{
		this.marginMin = marginMin;
		this.spacingVertical = spacingVertical;
		this.spacingCol = spacingCol;
		elems = new Vector<UIElementBase>();
	}
	
	public void addUI(UIElementBase elem)
	{
		elems.add(elem);
	}
	
	@Override
	public void rearrange(int width, int height)
	{
		// 1 column
		//computeDimensionVars(width, height, 1, spacing);\
		float margin = Math.min(width, height) * marginMin;
		float boxX = margin,
				boxY = margin,
				boxW = width-2*margin,
				boxH = height-2*margin;
		float spaceY = spacingVertical * height;
		float spaceX = spacingCol * width;
		
		// assume 1 column
		int nColumns = 1;
		int nPerColumn = elems.size() / nColumns + (elems.size() % nColumns);
		float elemHeight = (boxH - spaceY*(elems.size()-1)) / nPerColumn;
		float elemWidth = (boxW - spaceX*(nColumns-1)) / nColumns;
		
		int i = 0;
		float curX = 0, curY = 0;
		for (UIElementBase elem : elems)
		{
			//double y = height*marginY + i*(height*spacing + elemHeight);
			//elem.setFrame((int)x0, (int)y, (int)elemWidth, (int)elemHeight);
			
			fitIntoABoxCentered(elem, (int)(boxX + curX + elemWidth/2), (int)(boxY + curY + elemHeight/2), (int)elemWidth, (int)elemHeight);
			
			curY += elemHeight + spaceY;
			i++;
			if (i % nPerColumn == 0)
			{
				curX += elemWidth + spaceX;
			}
		}
	}
	
	protected void fitIntoABoxCentered(UIElementBase elem, int centerX, int centerY, int width, int height)
	{
		if (elem.isPreferredDimensionsAvailable())
		{
			float aspect = elem.getPreferredAspectRatio();
			int resultWidth = (int)Math.min(width, height * aspect);
			int resultHeight = (int)Math.min(height, width/aspect);
			elem.setFrame(centerX - resultWidth/2, centerY - resultHeight/2, resultWidth, resultHeight);
		}
		else
		{
			// no rules
			elem.setFrame(centerX - width/2, centerY - height/2, width, height);
		}
	}


}
