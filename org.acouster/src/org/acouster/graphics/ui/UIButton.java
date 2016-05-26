package org.acouster.graphics.ui;

import org.acouster.IFuncVoid;
import org.acouster.context.ContextGraphics;
import org.acouster.context.ContextMouseEvent;
import org.acouster.logic.Sprite2D;

public class UIButton extends UIElementBase
{
	protected IFuncVoid<UIButton> lambda;
	protected ScreenUIButtonListener complexLambda;
	protected String text;
	protected int color = 0x0;
	protected boolean isDown = false, mouseLeft=false, mouseReentered=false;
	private int horizontalMarginPercentage;
	
	public UIButton() {
		this("", null);
	}
	public UIButton(String text) {
		this(text, null);
	}
	public UIButton(String text, Sprite2D trans) {
		super(trans);
		this.text = text;
	}
	
	public IFuncVoid<UIButton> getLambda() {
		return lambda;
	}
	public UIButton setLambda(IFuncVoid<UIButton> lambda) {
		this.lambda = lambda;
		return this;
	}
	public ScreenUIButtonListener getComplexLambda() {
		return complexLambda;
	}
	public UIButton setComplexLambda(ScreenUIButtonListener complexLambda) {
		this.complexLambda = complexLambda;
		return this;
	}
	public int getColor() {
		return color;
	}
	public UIButton setColor(int color) {
		this.color = color;
		return this;
	}
	/** margin for text when displaying text on the button, nice and centered */
	public int getHorizontalMarginPercentage() {
		return horizontalMarginPercentage;
	}
	public UIButton setHorizontalMarginPercentage(int horizontalMarginPercentage) {
		this.horizontalMarginPercentage = horizontalMarginPercentage;
		return this;
	}
	
	@Override
	public void render(ContextGraphics g, int w, int h)
	{
		if (!isDown)
		{
			g.setFill(0xFFFFFF);
			g.fillRect(x+1, y+1, myWidth-2, myHeight-2);
			g.setColor(color);
			g.drawRect(x, y, myWidth, myHeight);
		}
		else
		{
			g.setFill(color);
			g.fillRect(x, y, myWidth, myHeight);
			g.setColor(0xFFFFFF);
		}
		drawTextNiceAndCentered(g, x, y, myWidth);
	}

	@Override
	public void mousePressed(ContextMouseEvent e) {
		isDown = true;
		if (complexLambda != null)
			complexLambda.actionDown(this);
	}
	@Override
	public void mouseDragged(ContextMouseEvent e) {
		boolean isDownNew = checkCollision(e.getX(), e.getY());
		mouseLeft = (isDownNew != isDown) && !isDownNew;
		mouseReentered = (isDownNew != isDown) && isDownNew;
		isDown = isDownNew;
		if (complexLambda != null)
			complexLambda.actionScratch(this, e.getX(), e.getY());
	}
	@Override
	public void mouseReleased(ContextMouseEvent e) {
		boolean withinBounds = checkCollision(e.getX(), e.getY());
		if (complexLambda != null)
			complexLambda.actionUp(this, withinBounds);
		if (lambda != null && withinBounds)
			lambda.lambda(this);
		isDown = false;
	}
	
	protected void drawTextNiceAndCentered(ContextGraphics g, int x, int y, int buttonWidth) {
		//TODO: convert w to fontsize properly
		int fontSize = Math.max(7, buttonWidth/8);
		g.drawStringCentered(text, x, y, fontSize);
	}
}
