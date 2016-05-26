package org.acouster.context;

import org.acouster.util.MathUtils;

public abstract class ContextGraphics
{
	// abstract methods
	public abstract void translate(int x, int y);
	public abstract void rotate(double a);
	public abstract void resetTransform();
	public abstract void setColor(int r, int g, int b);
	public abstract void setColor(int rgb);
	public abstract void setStroke(int rgb, int width);
	public abstract void resetStroke();
	public abstract void setFill(int r, int g, int b);
	public abstract void setFill(int rgb);
	public abstract void setFillAlpha(double alpha);
	public abstract void setFillAndStroke(int rgbStroke, int width, int rgbFill);
	public abstract void fillBackground();
	public abstract void drawLine(float x, float y, float x2, float y2);
	public abstract void drawRect(float x, float y, float width, float height);
	public abstract void drawRectRound(float x, float y, float width, float height, float borderRadiusX, float borderRadiusY);
	public abstract void drawOval(float x, float y, float width, float height);
	public abstract void fillRect(float x, float y, float width, float height);
	public abstract void fillRectRound(float x, float y, float width, float height, float borderRadiusX, float borderRadiusY);
	public abstract void fillOval(float x, float y, float width, float height);
	public abstract void drawArc(double x, double y, double rw, double rh, double a, double aSize);
	public abstract void drawString(String string, int x, int y);
	public abstract int getStringWidth(String text, int fontSize);
	public abstract void drawImage(ContextBitmap bi, int x, int y, int width2b, int height2b);
	
	// --------- protected shit ----------------
	protected abstract void drawImage_flip(ContextBitmap bi, int x, int y, int width2b, int height2b, boolean flipX, boolean flipY);
	
	// extension methods
	public void drawLine(double x, double y, double x2, double y2) { drawLine((int)x, (int) y, (int)x2, (int)y2); }
	public void drawCircle(double x, double y, double radius) { drawOval((int)(x-radius), (int)(y-radius), (int)(radius*2), (int)(radius*2)); }
	public void fillOval(double x, double y, double width, double height) { fillOval((int)x, (int) y, (int)width, (int)height); }
	public void fillCircle(double x, double y, double radius) { fillOval((int)(x-radius), (int)(y-radius), (int)(radius*2), (int)(radius*2)); }
	public void drawPixel(double x, double y) { drawLine((int)x, (int)y, (int)x, (int)y); }
	public void drawString(String string, double x, double y) { drawString(string, (int)x, (int)y); }
	public void drawStringCentered(String text, int x, int y, int fontSize) {
		int w = getStringWidth(text, fontSize);
		int h = fontSize;
		drawString(text, x-w/2, y + h/2);
	}
	public void drawImage(ContextBitmap bi, int x, int y, int width2b, int height2b, double angle) { drawImage(bi, x, y, width2b, height2b, false, false, angle, true); }
	public void drawImage(ContextBitmap bi, int x, int y, int width2b, int height2b, double angle, boolean pivotAroundCenter) { drawImage(bi, x, y, width2b, height2b, false, false, angle, pivotAroundCenter); }
	public void drawImage(ContextBitmap bi, int x, int y, int width2b, int height2b, boolean flipX, boolean flipY, double angle) { drawImage(bi, x, y, width2b, height2b, flipX, flipY, angle, true); }
	public void drawImageCentered(ContextBitmap bi, int x, int y, int width2b, int height2b) { drawImage(bi, x - width2b/2, y - height2b/2, width2b, height2b); }
	public void drawImageCentered(ContextBitmap bi, int x, int y, int width2b, int height2b, double angle) { drawImageCentered(bi, x, y, width2b, height2b, false, false, angle); }
	public void drawImageCentered(ContextBitmap bi, int x, int y, int width2b, int height2b, boolean flipX, boolean flipY) { drawImage(bi, x - width2b/2, y - height2b/2, width2b, height2b, flipX, flipY); }
	public void drawImage(ContextBitmap bi, int x, int y, int width2b, int height2b, boolean flipX, boolean flipY, double angle, boolean pivotAroundCenter)
	{
		if (pivotAroundCenter)
		{
			drawImageCentered(bi, x+width2b/2, y+height2b/2, width2b, height2b, flipX, flipY, angle);
		}
		else
		{
			translate(x, y);
			rotate(angle);
			drawImage(bi, 0, 0, width2b, height2b, flipX, flipY);
//			resetTransform();
			rotate(-angle);
			translate(-x, -y);
		}
	}
	public void drawImageCentered(ContextBitmap bi, int x, int y, int width2b, int height2b, boolean flipX, boolean flipY, double angle)
	{
		// angle is in degrees...
		//double radians = MathUtils.angleToRadians(angle);
		double radians = angle;
		double rotationVectorX = MathUtils.rotateVectorX(width2b/2, height2b/2, -radians);
		double rotationVectorY = MathUtils.rotateVectorY(width2b/2, height2b/2, -radians);
		int correctionVectorX = (int)(width2b/2 - rotationVectorX);
		int correctionVectorY = (int)(height2b/2 - rotationVectorY);
		translate(x - width2b/2, y - height2b/2);
		rotate(angle);
		translate(-correctionVectorX, -correctionVectorY);
		drawImage(bi, 0, 0, width2b, height2b, flipX, flipY);
//		resetTransform();
		translate(correctionVectorX, correctionVectorY);
		rotate(-angle);
		translate(-(x - width2b/2), -(y - height2b/2));
	}
	public void drawImage(ContextBitmap ci, int x, int y, int width, int height, boolean flipX, boolean flipY)
	{
		if (!flipX && !flipY)
			drawImage(ci, x, y, width, height);
		else
			drawImage_flip(ci, x, y, width, height, flipX, flipY);
	}
	public void drawImage(ContextBitmap bi, int x, int y, int width2b, int height2b, boolean flipX, boolean flipY, double angle, int pivotX, int pivotY)
	{
		translate(pivotX, pivotY);
		rotate(angle);
		translate(-(pivotX-x), -(pivotY-y));
		drawImage(bi, 0, 0, width2b, height2b, flipX, flipY);
//		resetTransform();
		translate(pivotX-x, pivotY-y);
		rotate(-angle);
		translate(-pivotX, -pivotY);
	}
	
	
	// ----------- other methods ---------------------
	protected boolean imageAntialias = false;
	public void enableImageAntialiasing(boolean flag)
	{
		this.imageAntialias = flag;
	}
}
