package useless;
//package org.acouster.game3d.ui;
//
//import org.acouster.context.ContextGraphics;
//import org.acouster.context.ContextKeyEvent;
//import org.acouster.context.ContextMouseEvent;
//import org.acouster.game3d.ScreenObject;
//import org.acouster.game3d.Sprite3D;
//import org.acouster.game3d.World3D;
//import org.acouster.game3d.math.GameMath3D;
//
//public class ScreenUIMousePointer  implements ScreenObject
//{
//	public static final int INNER_RADIUS = 20;
//	public static final int OUTER_RADIUS = 30;
//	public static final double ARM_ARC_ANGLE = 0.5;
//	public static final double ARM_ARC_ANGLE_INNER = 0.2;
//	public static final int N_INNER_ARM_ARCS = 3;
//	public static final double ANGLE_XFACTOR = 0.010;
//	public static final double THRUST_ANGLE_YFACTOR = 0.025;
//	public static final double ARMOFFSET_XFACTOR = 0.3;
//	//public static final double ANGLE_ = 0.4;
//	
//	protected int x, y, deltaX, deltaY;
//	protected double decayFactor;
//	public ScreenUIMousePointer()
//	{
//	}
//	
//	public void setLocation(int x, int y)
//	{
//		setLocation(x, y, 0, 0);
//	}
//	public void setLocation(int x, int y, int deltaX, int deltaY)
//	{
//		this.x = x;
//		this.y = y;
//		this.deltaX = deltaX;
//		this.deltaY = deltaY;
//	}
//	public void reset()
//	{
//		this.deltaX = 0;
//		this.deltaY = 0;
//	}
//	
//	// threading
//	public void render(ContextGraphics g, World3D w, Sprite3D p, int width, int height)
//	{
//		g.setColor(0xFF3333);
//		g.drawOval(x - INNER_RADIUS, y - INNER_RADIUS, 2*INNER_RADIUS, 2*INNER_RADIUS);
//		g.drawOval(x - OUTER_RADIUS, y - OUTER_RADIUS, 2*OUTER_RADIUS, 2*OUTER_RADIUS);
//		
//		double angle = -ANGLE_XFACTOR * deltaX;
//		double armOffest = ARMOFFSET_XFACTOR * deltaX;
//		double arcRadius = OUTER_RADIUS + Math.abs(armOffest);
//		double arcX = x - arcRadius;
//		double arcY = y - arcRadius;
//		g.drawArc(arcX, arcY, 2*arcRadius, 2*arcRadius, angle-ARM_ARC_ANGLE, 2*ARM_ARC_ANGLE);
//		g.drawArc(arcX, arcY, 2*arcRadius, 2*arcRadius, angle+Math.PI-ARM_ARC_ANGLE, 2*ARM_ARC_ANGLE);
//		for (int i = 1; i < N_INNER_ARM_ARCS; i++)
//		{
//			arcRadius = OUTER_RADIUS + Math.abs(armOffest) * i / N_INNER_ARM_ARCS;
//			arcX = x - arcRadius;
//			arcY = y - arcRadius;
//			g.drawArc(arcX, arcY, 2*arcRadius, 2*arcRadius, angle-ARM_ARC_ANGLE_INNER, 2*ARM_ARC_ANGLE_INNER);
//			g.drawArc(arcX, arcY, 2*arcRadius, 2*arcRadius, angle+Math.PI-ARM_ARC_ANGLE_INNER, 2*ARM_ARC_ANGLE_INNER);
//		}
//		
//		double thrustAngle = THRUST_ANGLE_YFACTOR * deltaY;
//		arcRadius = (INNER_RADIUS + OUTER_RADIUS) / 2;
//		arcX = x - arcRadius;
//		arcY = y - arcRadius;
//		//g.setStrokeWidth();
//		g.drawArc(arcX, arcY, 2*arcRadius, 2*arcRadius, angle + GameMath3D.sign(thrustAngle)*Math.PI/2 - thrustAngle, 2*thrustAngle);
//	}
//	public void increment(World3D w, Sprite3D p)
//	{
//		
//	}
//
//	// interaction
//	public void mousePressed(ContextMouseEvent e) {}
//	public void mouseDragged(ContextMouseEvent e) {}
//	public void mouseReleased(ContextMouseEvent e) {}
//	public void keyPressed(ContextKeyEvent e) {}
//	public void keyReleased(ContextKeyEvent e) {}
//	public void keyTyped(ContextKeyEvent e) {}
//	public void mouseClicked(ContextMouseEvent e) {}
//	public void mouseEntered(ContextMouseEvent e) {}
//	public void mouseExited(ContextMouseEvent e) {}
//	public void mouseMoved(ContextMouseEvent e) {}
//}
