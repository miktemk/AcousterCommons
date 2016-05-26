package org.acouster.graphics.ui;

import java.util.Vector;

public class UILayoutManagerByPercentage extends UILayoutManager
{

	public static final int RATIO_TYPE_INDEPENDANT = 0;
	public static final int RATIO_TYPE_X_DOMINANT = 1;
	public static final int RATIO_TYPE_Y_DOMINANT = 2;
	
	private class ElemCoordsTuple
	{
		public UIElementBase elem;
		public double percentWidth, percentHeight, percentX, percentY;
		public int ratioType;
		public boolean maintainAspect;
		
		public ElemCoordsTuple(UIElementBase elem,
				double percentWidth, double percentHeight,
				double percentX, double percentY,
				int ratioType, boolean maintainAspect) {
			super();
			this.elem = elem;
			this.percentWidth = percentWidth;
			this.percentHeight = percentHeight;
			this.percentX = percentX;
			this.percentY = percentY;
			this.ratioType = ratioType;
			this.maintainAspect = maintainAspect;
		}
		
		public double getWidth(double scrWidth)
		{
			return scrWidth * percentWidth / 100;
		}
		public double getHeight(double scrWidth, double scrHeight)
		{
			if (maintainAspect)
				return getWidth(scrWidth) * elem.getPreferredHeight() / elem.getPreferredWidth();
			return scrHeight * percentHeight / 100;
		}
	}
	
	
	// elements
	protected Vector<ElemCoordsTuple> elems;
	
	public UILayoutManagerByPercentage()
	{
		elems = new Vector<ElemCoordsTuple>();
	}
	
	public void addUI(UIElementBase elem, double percentWidth, double percentHeight, double percentX, double percentY, int ratioType, boolean maintainAspect)
	{
		elems.add(new ElemCoordsTuple(elem, percentWidth, percentHeight, percentX, percentY, ratioType, maintainAspect));
	}
	
	@Override
	public void rearrange(int width, int height)
	{
		for (ElemCoordsTuple elem : elems)
		{
			double xxx, yyy, www, hhh;
			
			www = elem.getWidth(width);
			hhh = elem.getHeight(width, height);
			switch (elem.ratioType)
			{
			case RATIO_TYPE_X_DOMINANT:
				xxx = elem.percentX * width / 100;
				yyy = elem.percentY * width / 100;
				break;
			case RATIO_TYPE_Y_DOMINANT:
				xxx = elem.percentX * height / 100;
				yyy = elem.percentY * height / 100;
				break;
			case RATIO_TYPE_INDEPENDANT:
			default:
				xxx = elem.percentX * width / 100;
				yyy = elem.percentY * height / 100;
				break;
			}
			elem.elem.setFrame((int)xxx, (int)yyy, (int)www, (int)hhh);
			
			//DebugUtil.sss((int)xxx + " : " + (int)yyy + " : " + (int)www + " : " + (int)hhh);
			
//			int column = i / nPerColumn;
//			double x = x0 + column*(marginX*width + elemWidth);
//			double y = height*marginY + (i % nPerColumn)*(height*spacing2Col + elemHeight);
//			elem.setFrame((int)x, (int)y, (int)elemWidth, (int)elemHeight);
//			i++;
		}
	}

//	private void computeDimensionVars(int width, int height, int nColumns, double space)
//	{
//		nPerColumn = elems.size() / nColumns + (elems.size() % nColumns);
//		elemWidth = width * (1 - (1+nColumns)*marginX) / nColumns;
//		UIElementBase head = elems.firstElement();
//		if (head != null && head.isPreferredDimensionsAvailable())
//		{
//			elemHeight = elemWidth * head.getPreferredHeight() / head.getPreferredWidth();
//			marginY = (1 - (nPerColumn * elemHeight / height) - (nPerColumn-1)*space) / 2;
//		}
//		else
//		{
//			// backup
//			marginY = marginX;
//			elemHeight = height * (1 - 2*marginY - space*(nPerColumn-1)) / nPerColumn;
//		}
//		x0 = width*marginX;
//	}
	
	
	

}
