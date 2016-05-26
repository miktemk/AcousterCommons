package org.acouster.karate.dtui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import org.acouster.context.desktop.DesktopContextGraphics;

public class KataUiTimeline extends ImageGrid<ImageTuple> {

	public KataUiTimeline(IAngleContext angleContext, int cellW, int cellH) {
		super(angleContext, cellW, cellH);
	}
	
	@Override
	protected Component createBoxy(ImageTuple img)
	{
		return new TimelineFrame(img, cellW, cellH);
	}
	
	
	
	
	//==============================================================
	
	private class TimelineFrame extends JPanel
	{
		private static final int WIDTH_DELAY_BOX = 30;
		
		private ImageTuple img;

		public TimelineFrame(ImageTuple img, int w, int h)
		{
			this.img = img;
			//setSize(new Dimension(w, h));
			setPreferredSize(new Dimension(w + WIDTH_DELAY_BOX, h));
		}
		
		public void paint(Graphics g1)
		{
			DesktopContextGraphics ggg = DesktopContextGraphics.sharedInstance((Graphics2D)g1);
			ImageGridUtils.drawImageTuple(ggg, img, angleContext.getAngle(), getWidth()-WIDTH_DELAY_BOX, getHeight());
			
			// plus delay boxy
			
		}
	}
	
	
	
}
