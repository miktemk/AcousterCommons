package org.acouster.game3d.graphics;

import org.acouster.GameEvent;
import org.acouster.GraphTicker;
import org.acouster.INavigableGraph;
import org.acouster.game3d.math.TransformMatrix3D;
import org.acouster.graphics.BitmapWithTransform;

public class BillBoard extends BillBoardBase
{
	protected GraphTicker<BitmapWithTransform> ticker;
	
	public BillBoard(INavigableGraph<BitmapWithTransform> images) {
		this(1, 100, images);
	}
	public BillBoard(double size, double pixelSize, INavigableGraph<BitmapWithTransform> images)
	{
		super(size, pixelSize);
		ticker = new GraphTicker<BitmapWithTransform>(images);
	}
	
	public GraphTicker<BitmapWithTransform> getTicker()
	{
		return ticker;
	}
	
	@Override
	protected BitmapWithTransform getCurrentImage(TransformMatrix3D objectToCam, TransformMatrix3D camToWorld)
	{
		return ticker.getCurrent();
	}
	@Override
	public void dispose() {}
	@Override
	public void increment(int width, int height)
	{
		ticker.increment();
	}
	@Override
	public void handleMessage(GameEvent event)
	{
		ticker.gotoNode(event.getBody());
	}
	
	
}
