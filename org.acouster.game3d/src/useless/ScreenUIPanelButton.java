package useless;
//package org.acouster.game3d.ui;
//
//import org.acouster.context.ContextGraphics;
//import org.acouster.context.ContextKeyEvent;
//import org.acouster.context.ContextMouseEvent;
//import org.acouster.game3d.ScreenObject;
//import org.acouster.game3d.Sprite3D;
//import org.acouster.game3d.World3D;
//
//public class ScreenUIPanelButton  implements ScreenObject {
//
//	protected String text;
//	protected int buttonId;
//	protected int x, y, myWidth, myHeight;
//	protected ScreenUIButtonListener listener;
//	public ScreenUIPanelButton(String text, int buttonId, ScreenUIButtonListener listener)
//	{
//		this.text = text;
//		this.buttonId = buttonId;
//		this.listener = listener;
//	}
//	
//	public int getId() {
//		return buttonId;
//	}
//	public String getText() {
//		return text;
//	}
//	public void setText(String text) {
//		this.text = text;
//	}
//
//	public void render(ContextGraphics g, World3D w, Sprite3D p, int width, int height)
//	{
//		g.setColor(0x33FFAA);
//		g.drawRect(x, y, myWidth, myHeight);
//		g.drawString(text, x + 15, y + 30);
//	}
//
//	public void increment(World3D w, Sprite3D p)
//	{
//		
//	}
//
//	// interaction
//	public void mousePressed(ContextMouseEvent e)
//	{
//		listener.actionDown(this);
//	}
//	public void mouseDragged(ContextMouseEvent e)
//	{
//		listener.actionScratch(this, e.getX(), e.getY());
//	}
//	public void mouseReleased(ContextMouseEvent e)
//	{
//		listener.actionUp(this);
//	}
//	public void keyPressed(ContextKeyEvent e) {}
//	public void keyReleased(ContextKeyEvent e) {}
//	public void keyTyped(ContextKeyEvent e) {}
//	public void mouseClicked(ContextMouseEvent e) {}
//	public void mouseEntered(ContextMouseEvent e) {}
//	public void mouseExited(ContextMouseEvent e) {}
//	public void mouseMoved(ContextMouseEvent e) {}
//	
//	public void setFrame(int x, int y, int width, int height)
//	{
//		this.x = x;
//		this.y = y;
//		this.myWidth = width;
//		this.myHeight = height;
//	}
//
//	public boolean checkCollision(int mx, int my) {
//		if ((mx >= x) && (mx <= x + myWidth) && (my >= y) && (my <= y + myHeight))
//			return true;
//		return false;
//	}
//}
