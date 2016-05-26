package org.acouster.context.desktop;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import org.acouster.context.ContextBitmap;
import org.acouster.context.ContextBitmapFragment;
import org.acouster.context.ContextGraphics;
import org.acouster.util.StringUtils;

public class DesktopContextGraphics extends ContextGraphics {

	private Graphics2D g;
	private AffineTransform resetTransform;
	public void setGraphics2D(Graphics2D g) {
		this.g = g;
		resetTransform = new AffineTransform();
	}
	
	private ImageObserver imageObserver;
	private Color curFillColor;
	public void setImageObserver( ImageObserver imageObserver) {
		this.imageObserver = imageObserver;
	}
	
	// inherited methods
	@Override
	public void translate(int x, int y) {
		g.translate(x, y);
	}

	@Override
	public void rotate(double a) {
		g.rotate(a);
	}
	
	@Override
	public void resetTransform() {
		g.setTransform(resetTransform);
	}

	@Override
	public void setColor(int rgb) {
		g.setColor(new Color(rgb));
	}
	
	@Override
	public void setColor(int r, int green, int b) {
		g.setColor(new Color(r, green, b));
	}

	@Override
	public void setStroke(int rgb, int width) {
		setColor(rgb);
		g.setStroke(new BasicStroke(width, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
	}
	
	@Override
	public void resetStroke() {
		g.setStroke(new BasicStroke(1));
	}
	
	@Override
	public void drawLine(float x, float y, float x2, float y2) {
		g.drawLine((int)x, (int)y, (int)x2, (int)y2);
	}
	
	@Override
	public void drawRect(float x, float y, float width, float height) {
		g.drawRect((int)x, (int)y, (int)width, (int)height);
	}
	
	@Override
	public void drawRectRound(float x, float y, float width, float height, float borderRadiusX, float borderRadiusY) {
		g.drawRoundRect((int)x, (int)y, (int)width, (int)height, (int)borderRadiusX, (int)borderRadiusY);
	}

	@Override
	public void fillRectRound(float x, float y, float width, float height, float borderRadiusX, float borderRadiusY) {
		g.fillRoundRect((int)x, (int)y, (int)width, (int)height, (int)borderRadiusX, (int)borderRadiusY);
	}

	@Override
	public void drawOval(float x, float y, float width, float height) {
		g.drawOval((int)x, (int)y, (int)width, (int)height);
	}

	@Override
	public void fillRect(float x, float y, float width, float height) {
		g.fillRect((int)x, (int)y, (int)width, (int)height);
	}

	@Override
	public void fillOval(float x, float y, float width, float height) {
		g.fillOval((int)x, (int)y, (int)width, (int)height);
	}
	
	@Override
	public void drawString(String string, int x, int y) {
		g.drawString(string, x, y);
	}
	
	@Override
	public int getStringWidth(String string, int fontSize) {
		if (StringUtils.isNullOrEmpty(string))
			return 0;
		//g.setFont(metrics.getFont().deriveFont(fontSize));
		//TODO: what font?
		g.setFont(new Font("Verdana", Font.BOLD, fontSize));
		FontMetrics metrics = g.getFontMetrics();
		int w = metrics.stringWidth(string);
		return w;
	}
	
	@Override
	public void drawImage(ContextBitmap ci, int x, int y, int width2b, int height2b) {
		if (ci.isFragment())
		{
			ContextBitmapFragment imgPiece = (ContextBitmapFragment)ci;
			ContextBitmap_BufferedImage ci_bi = (ContextBitmap_BufferedImage)(imgPiece.src);
			BufferedImage bi = ci_bi.getImg();
			g.drawImage(bi, x, y, x+width2b, y+height2b, imgPiece.srcX, imgPiece.srcY, imgPiece.srcX+imgPiece.srcW, imgPiece.srcY+imgPiece.srcH, imageObserver);
		}
		else
		{
			ContextBitmap_BufferedImage ci_bi = (ContextBitmap_BufferedImage)ci;
			BufferedImage bi = ci_bi.getImg();
			g.drawImage(bi, x, y, width2b, height2b, imageObserver);
		}
	}
	
	@Override
	public void drawArc(double x, double y, double rw, double rh, double a, double aSize) {
		g.drawArc((int)x, (int)y, (int)rw, (int)rh, (int)(180*a/Math.PI), (int)(180*aSize/Math.PI));
	}

	@Override
	public void setFill(int r, int g, int b) {
		curFillColor = new Color(r,g,b);
		this.g.setPaint(curFillColor);
	}

	@Override
	public void setFill(int rgb) {
		curFillColor = new Color(rgb);
		g.setPaint(curFillColor);
	}
	
	@Override
	public void setFillAndStroke(int rgbStroke, int width, int rgbFill) {
		setStroke(rgbStroke, width);
		setFill(rgbFill);
		// is this enough???
		//g.setP.setStyle(Style.FILL_AND_STROKE);		
	}
	
	@Override
	public void setFillAlpha(double alpha) {
		curFillColor = new Color(curFillColor.getRed(), curFillColor.getGreen(), curFillColor.getBlue(), (int)(255*alpha));
		g.setPaint(curFillColor);
	}

	@Override
	public void fillBackground() {
		g.setBackground(curFillColor);
	}
	
	// --------- protected shit ----------------
	@Override
	protected void drawImage_flip(ContextBitmap bi, int x, int y, int width2b, int height2b, boolean flipX, boolean flipY)
	{
		int factorW = 1, factorH = 1, offsetX = 0, offsetY = 0;
		if (flipX)
		{
			factorW = -1;
			offsetX = width2b;
		}
		if (flipY)
		{
			factorH = -1;
			offsetY = height2b;
		}
		drawImage(bi, x+offsetX, y+offsetY, width2b*factorW, height2b*factorH);
	}

	
	//================= static shit ========================
	
	private static DesktopContextGraphics instance = new DesktopContextGraphics();
	public static DesktopContextGraphics sharedInstance(Graphics2D g)
	{
		instance.setGraphics2D(g);
		return instance;
	}


}
