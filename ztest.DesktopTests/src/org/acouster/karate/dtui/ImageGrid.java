package org.acouster.karate.dtui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import org.acouster.IFuncVoid;
import org.acouster.context.desktop.ContextBitmap_BufferedImage;
import org.acouster.context.desktop.DesktopContextGraphics;

public class ImageGrid<T extends IImageGridItem> extends JPanel
{
	protected int cellW, cellH;
	protected IAngleContext angleContext;
	protected IFuncVoid<T> callbackCellClick;
	
	public ImageGrid(IAngleContext angleContext, int cellW, int cellH)
	{
		super(new FlowLayout());
		this.angleContext = angleContext;
		this.cellW = cellW;
		this.cellH = cellH;
	}
	
	public void addImg(final T img)
	{
		Component bbb = createBoxy(img); 
		bbb.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (callbackCellClick != null)
					callbackCellClick.lambda(img);
			}
		});
		add(bbb);
	}
	
	public void setClickidyListener(IFuncVoid<T> func)
	{
		this.callbackCellClick = func;
	}
	
	/** override this mofo to create ur own gridz */
	protected Component createBoxy(T img)
	{
		return new ImageBoxy(img, cellW, cellH);
	}
	
	
	
	
	//==============================================================
	
	private class ImageBoxy extends JPanel
	{
		private T img;

		public ImageBoxy(T img, int w, int h)
		{
			this.img = img;
			//setSize(new Dimension(w, h));
			setPreferredSize(new Dimension(w, h));
		}
		
		public void paint(Graphics g1)
		{
			DesktopContextGraphics ggg = DesktopContextGraphics.sharedInstance((Graphics2D)g1);
			ImageGridUtils.drawImageTuple(ggg, img, angleContext.getAngle(), getWidth(), getHeight());
			
		}
	}
}


