package org.acouster.graphics.ui;

import org.acouster.ActionEngineAdapter;
import org.acouster.Game;
import org.acouster.context.ContextMouseEvent;

public class GestureActionEngine extends ActionEngineAdapter
{
	public static interface IGestureObserver {
		void dragBegin(int initX, int initY);
		void dragContinues(int deltaX, int deltaY);
		void dragFinalize(int deltaX, int deltaY);
	}
	
	
	private IGestureObserver observer;
	private boolean isDown = false;
	private int initX, initY;
	
	public GestureActionEngine(Game game, IGestureObserver observer) {
		super(game);
		this.observer = observer;
	}
	
	@Override
	public void mousePressed(ContextMouseEvent arg0) {
		super.mousePressed(arg0);
		isDown = true;
		initX = arg0.getX();
		initY = arg0.getY();
		observer.dragBegin(initX, initY);
	}
	
	@Override
	public void mouseDragged(ContextMouseEvent arg0) {
		super.mouseDragged(arg0);
		if (!isDown)
			return;
		int deltaX = arg0.getX() - initX;
		int deltaY = arg0.getY() - initY;
		observer.dragContinues(deltaX, deltaY);
	}
	
	@Override
	public void mouseReleased(ContextMouseEvent arg0) {
		super.mouseReleased(arg0);
		isDown = false;
		int deltaX = arg0.getX() - initX;
		int deltaY = arg0.getY() - initY;
		observer.dragFinalize(deltaX, deltaY);
	}
}