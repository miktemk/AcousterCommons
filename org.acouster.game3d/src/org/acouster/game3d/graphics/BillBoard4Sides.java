package org.acouster.game3d.graphics;

import org.acouster.DebugUtil;
import org.acouster.GameEvent;
import org.acouster.GraphTicker;
import org.acouster.INavigableGraph;
import org.acouster.game3d.math.TransformMatrix3D;
import org.acouster.game3d.math.VectorXYZ;
import org.acouster.graphics.BitmapWithTransform;
import org.acouster.util.MathUtils;

public class BillBoard4Sides extends BillBoardBase 
{
	protected GraphTicker<Image4Sided> ticker;
	protected boolean isOppositeSide = false;
	
	public BillBoard4Sides(INavigableGraph<Image4Sided> images) {
		this(1, 100, images);
	}
	public BillBoard4Sides(double size, double pixelSize, INavigableGraph<Image4Sided> images)
	{
		super(size, pixelSize);
		ticker = new GraphTicker<Image4Sided>(images);
	}
	
	public GraphTicker<Image4Sided> getTicker()
	{
		return ticker;
	}
	public boolean isOppositeSide() {
		return isOppositeSide;
	}
	public void setOppositeSide(boolean isOppositeSide) {
		this.isOppositeSide = isOppositeSide;
	}
	
	@Override
	protected BitmapWithTransform getCurrentImage(TransformMatrix3D objectToCam, TransformMatrix3D camToWorld)
	{
		// calculate the damn angle
		double aobj = camToWorld.flyby3DConvention_getAy();
		double ame = -transform.flyby3DConvention_getAy();
		double witnessAngle = MathUtils.angleWithin360(MathUtils.angleToDegrees(aobj + ame));
		Image4Sided img = ticker.getCurrent();
		return img.getBitmap(witnessAngle, isOppositeSide);
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
