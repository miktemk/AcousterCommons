package useless;
//package org.acouster.game3d;
//
//import java.io.IOException;
//
//import org.acouster.context.ContextGraphics;
//import org.acouster.context.ContextKeyEvent;
//import org.acouster.context.ContextMouseEvent;
//import org.acouster.context.ResourceContext;
//
//public class HelpFileDisplay implements ScreenObject
//{
//	public static final int Y_STEP = 15;
//	
//	protected String[] helpTextLines;
//	protected boolean hideText;
//	
//	int x = 400;
//	int y = 50;
//	
//	public HelpFileDisplay(String filename)
//	{
//		try {
//			helpTextLines = ResourceContext.instance().LoadTextLines(filename);
//		} catch (IOException e) {
//			helpTextLines = new String[] {};
//			e.printStackTrace();
//		}
//		hideText = false;
//	}
//	
//	public void render(ContextGraphics g, World3D w, Sprite3D p, int width, int height)
//	{
//		if (hideText)
//			return;
//		
//		g.setColor(0xFFFFFF);
//		
//		int xBase = x;
//		int yBase = y;
//		for (String line : helpTextLines)
//		{
//			g.drawString(line, xBase, yBase);
//			yBase += Y_STEP;
//		}
//		
//	}
//	
//	public void increment(World3D w, Sprite3D p){}
//	public void keyPressed(ContextKeyEvent arg0)
//	{
//		switch (arg0.getKeyCode())
//		{
//			case ContextKeyEvent.VK_H:
//				hideText = !hideText;
//				break;
//		}
//	}
//	public void keyReleased(ContextKeyEvent arg0) {}
//	public void keyTyped(ContextKeyEvent arg0){}
//	public void mouseClicked(ContextMouseEvent arg0){}
//	public void mouseDragged(ContextMouseEvent arg0){}
//	public void mouseEntered(ContextMouseEvent arg0){}
//	public void mouseExited(ContextMouseEvent arg0){}
//	public void mouseMoved(ContextMouseEvent arg0){}
//	public void mousePressed(ContextMouseEvent arg0){}
//	public void mouseReleased(ContextMouseEvent arg0){}
//}
