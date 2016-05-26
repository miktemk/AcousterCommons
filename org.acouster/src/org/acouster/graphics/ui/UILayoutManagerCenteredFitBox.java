package org.acouster.graphics.ui;

import java.util.Vector;
import javax.swing.plaf.basic.BasicBorders.MarginBorder;

import org.acouster.context.ContextGraphics;
import org.acouster.graphics.Colorz;


/** So far this is the best of the Layout managers.
 * Everything sits nicely within the specified bounds and doesn't stick out. */
public class UILayoutManagerCenteredFitBox extends UILayoutManager
{
	// elements
	protected Vector<UIElementBase> elems;
	// layout parameters
	protected float mTop, mBottom, mLeft, mRight;
	protected float spacingVertical, spacingCol;
	protected int nColumns;
	
	public UILayoutManagerCenteredFitBox(float marginMin, float spacing) {
		this(marginMin, spacing, 1, 0);
	}
	public UILayoutManagerCenteredFitBox(float marginMin, float spacingVertical, int nColumns, float spacingCol)
	{
		this.mTop = marginMin;
		this.mBottom = marginMin;
		this.mLeft = marginMin;
		this.mRight = marginMin;
		this.spacingVertical = spacingVertical;
		this.nColumns = nColumns;
		this.spacingCol = spacingCol;
		elems = new Vector<UIElementBase>();
	}
	
	public UILayoutManagerCenteredFitBox setRect(float mTop, float mBottom, float mLeft, float mRight) {
		this.mTop = mTop;
		this.mBottom = mBottom;
		this.mLeft = mLeft;
		this.mRight = mRight;
		return this;
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
		int nCols = 1;
		if (width > height)
			nCols = nColumns;
		rearrangeInRect(
				width * (1 - mLeft - mRight),
				height * (1 - mTop - mBottom),
				width * mLeft,
				height * mTop,
				width*spacingCol,
				height*spacingVertical,
				nCols);
	}
	
	private void rearrangeInRect(
			float width, float height,
			float offsetX, float offsetY,
			float spaceX, float spaceY,
			int nCols)
	{
		int nPerColumn = elems.size() / nCols + (((elems.size() % nCols) > 0) ? 1 : 0);
		float elemWidth = (width -  spaceX*(nCols - 1)) / nCols;
		float elemHeight = (height - spaceY*(nPerColumn - 1)) / nPerColumn;
		int i = 0;
		for (UIElementBase elem : elems)
		{
			int column = i / nPerColumn;
			int columnIndex = i % nPerColumn;
			float x = column*(spaceX + elemWidth);
			float y = columnIndex*(spaceY + elemHeight);
			setFrameForElement(elem, offsetX + x, y + offsetY, elemWidth, elemHeight);
			i++;
		}
	}

	
	private void setFrameForElement(UIElementBase elem, float x, float y, float w, float h)
	{
		if (elem.isPreferredDimensionsAvailable())
		{
			float elemWidth = w;
			float elemHeight = elemWidth / elem.getPreferredAspectRatio();
			if (elemHeight < h) {
				// we have margin on top and bottom => x stays, need y-margin
				float yMargin = (h - elemHeight) / 2;
				elem.setFrame((int)x, (int)(y + yMargin), (int)elemWidth, (int)elemHeight);
			}
			else {
				// we have margin on the sides => y stays, need x-margin
				elemHeight = h;
				elemWidth = elemHeight * elem.getPreferredAspectRatio();
				float xMargin = (w - elemWidth) / 2;
				elem.setFrame((int)(x + xMargin), (int)y, (int)elemWidth, (int)elemHeight);
			}
			// TODO: fit!
		}
		else
		{
			elem.setFrame((int)x, (int)y, (int)w, (int)h);
		}
	}
	public void debug_drawGrid(ContextGraphics g, int width, int height) {
		g.setColor(Colorz.VIOLENT_VIOLET);
		g.drawRect((int)(width*mLeft), (int)(height*mTop), (int)(width*(1-mRight-mLeft)), (int)(height*(1-mBottom-mTop)));
	}
}
