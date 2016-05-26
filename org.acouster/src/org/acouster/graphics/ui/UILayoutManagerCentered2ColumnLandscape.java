package org.acouster.graphics.ui;

import java.util.Vector;

import org.acouster.context.ContextGraphics;

/**
 * 
 */
public class UILayoutManagerCentered2ColumnLandscape extends UILayoutManager
{
	private static final int WIDTH_STANDARD_IPHONE = 480;
	
	// elements
	protected Vector<UIElementBase> elems;
	// layout parameters
	protected float marginMin, spacingVertical, spacing2Col;
	protected boolean fillAllHorizontal;
	
	public UILayoutManagerCentered2ColumnLandscape(
			float marginMin,
			float spacingVertical,
			float spacing2Col)
	{
		this.marginMin = marginMin;
		this.spacingVertical = spacingVertical;
		this.spacing2Col = spacing2Col;
		elems = new Vector<UIElementBase>();
	}
	
	public void addUI(UIElementBase elem)
	{
		elems.add(elem);
	}
	public void clear() {
		elems.clear();
	}
	
	@Override
	public void rearrange(int width, int height)
	{
		//if (width <= WIDTH_STANDARD_IPHONE)
		if (width < height)
		{
			// 1 column
			computeDimensionVars(width, height, 1, spacingVertical);
			int i = 0;
			for (UIElementBase elem : elems)
			{
				double y = height*marginY + i*(height*spacingVertical + elemHeight);
				elem.setFrame((int)x0, (int)y, (int)elemWidth, (int)elemHeight);
				i++;
			}
		}
		else
		{
			// 2 columns
			computeDimensionVars(width, height, 2, spacing2Col);
			double elemWidth = width * (1-3*marginMin) / 2;
			int i = 0;
			for (UIElementBase elem : elems)
			{
				int column = i / nPerColumn;
				double x = x0 + column*(marginMin*width + elemWidth);
				double y = height*marginY + (i % nPerColumn)*(height*spacingVertical + elemHeight);
				elem.setFrame((int)x, (int)y, (int)elemWidth, (int)elemHeight);
				i++;
			}
		}
	}

	protected int nPerColumn;
	protected float elemWidth, elemHeight, marginY, marginTop, marginBottom, x0;
	
	/** Computes
	 * - protected int nPerColumn;
	 * - protected double elemWidth, elemHeight, marginY, x0; */
	protected void computeDimensionVars(int width, int height, int nColumns, float space)
	{
		// NOTE: works only for up to 2 cols
		nPerColumn = elems.size() / nColumns + (elems.size() % nColumns);
		elemWidth = width * (1 - (1+nColumns)*marginMin) / nColumns;
		UIElementBase head = elems.firstElement();
		if (head != null && head.isPreferredDimensionsAvailable())
		{
			elemHeight = elemWidth * head.getPreferredHeight() / head.getPreferredWidth();
			marginY = (1 - (nPerColumn * elemHeight / height) - (nPerColumn-1)*space) / 2;
		}
		else
		{
			// backup
			marginY = marginMin;
			elemHeight = height * (1 - 2*marginY - space*(nPerColumn-1)) / nPerColumn;
		}
		x0 = width*marginMin;
	}
	
	// DEBUG functions
	public void debug_drawGrid(ContextGraphics g, int width, int height) {
		g.setColor(0x0);
		g.drawLine(x0, height*marginY, x0, height-height*marginY);
	}

}
