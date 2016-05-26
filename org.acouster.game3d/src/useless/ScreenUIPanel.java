package useless;
//package org.acouster.game3d.ui;
//
//import java.util.Vector;
//
//import org.acouster.context.ContextGraphics;
//import org.acouster.context.ContextKeyEvent;
//import org.acouster.context.ContextMouseEvent;
//import org.acouster.game3d.ScreenObject;
//import org.acouster.game3d.Sprite3D;
//import org.acouster.game3d.World3D;
//
//public class ScreenUIPanel implements ScreenObject {
//
//	protected boolean mouseDown;
//	protected int mouseX = 0, mouseX1 = 0, mouseY = 0, mouseY1 = 0, mouseDeltaX = 0, mouseDeltaX1 = 0, mouseDeltaY = 0, mouseDeltaY1 = 0;
//	protected Vector<ScreenUIPanelButton> buttons;
//	protected ScreenUIMousePointer mousePointer;
//	
//	public ScreenUIPanel() {
//		mouseDown = false;
//		buttons = new Vector<ScreenUIPanelButton>();
//	}
//	
//	// UI related shit
//	public void addButton(ScreenUIPanelButton button) {
//		buttons.add(button);
//	}
//	public ScreenUIMousePointer getMousePointer() {
//		return mousePointer;
//	}
//	public void setMousePointer(ScreenUIMousePointer mousePointer) {
//		this.mousePointer = mousePointer;
//	}
//
//	// threading
//	public void increment(World3D w, Sprite3D p)
//	{
//		for (ScreenUIPanelButton b : buttons)
//			b.increment(w, p);
//		if (mousePointer != null)
//			mousePointer.increment(w, p);
//	}
//	public void render(ContextGraphics g, World3D w, Sprite3D p, int width, int height)
//	{
//		for (ScreenUIPanelButton b : buttons)
//			b.render(g, w, p, width, height);
//		if (mouseDown)
//			if (mousePointer != null)
//				mousePointer.render(g, w, p, width, height);
//	}
//
//	// interaction
//	public void mousePressed(ContextMouseEvent e)
//	{
//		mouseDown = true;
//		mouseX = e.getX();
//		mouseX1 = e.getX();
//		mouseY = e.getY();
//		mouseY1 = e.getY();
//		mouseDeltaX = 0;
//		mouseDeltaX1 = 0;
//		mouseDeltaY = 0;
//		mouseDeltaY1 = 0;
//		for (ScreenUIPanelButton b : buttons)
//		{
//			if (b.checkCollision(e.getX(), e.getY()))
//			{
//				b.mousePressed(e);
//				mouseDown = false;
//			}
//		}
//		if (mousePointer != null)
//		{
//			mousePointer.reset();
//			mousePointer.setLocation(mouseX, mouseY, 0, 0);
//		}
//	}
//	public void mouseDragged(ContextMouseEvent e)
//	{
//		mouseDeltaX = e.getX() - mouseX;
//		mouseDeltaX1 = e.getX() - mouseX1;
//		mouseDeltaY = e.getY() - mouseY;
//		mouseDeltaY1 = e.getY() - mouseY1;
//		mouseX = e.getX();
//		mouseY = e.getY();
//		if (mousePointer != null)
//			mousePointer.setLocation(mouseX, mouseY, mouseDeltaX1, mouseDeltaY1);
//	}
//	public void mouseReleased(ContextMouseEvent e)
//	{
//		mouseDown = false;
//		for (ScreenUIPanelButton b : buttons)
//		{
//			if (b.checkCollision(e.getX(), e.getY()))
//				b.mouseReleased(e);
//		}
//	}
//	public void keyPressed(ContextKeyEvent arg0) {}
//	public void keyReleased(ContextKeyEvent arg0) {}
//	public void keyTyped(ContextKeyEvent e){}
//	public void mouseClicked(ContextMouseEvent e){}
//	public void mouseEntered(ContextMouseEvent e){}
//	public void mouseExited(ContextMouseEvent e){}
//	public void mouseMoved(ContextMouseEvent e){}
//}
