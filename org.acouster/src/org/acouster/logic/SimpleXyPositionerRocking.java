package org.acouster.logic;


/** Same as {@link SimpleXyPositioner} but adds y-rocking via small sinuside */
public class SimpleXyPositionerRocking extends SimpleXyPositioner
{
	protected double amplitudeX, amplitudeY, rockFrameFactorX, rockFrameFactorY;
	private int iii;
	
	/** NOTE: amplitudeY is in pixels, whereas posX and posY is in 0-1.0 od screen 
	 * Hopefully this class is only used for small rocking. No big shitstorms! */
	public SimpleXyPositionerRocking(
			double posX, double posY,
			double amplitudeX, double rockFrameFactorX,
			double amplitudeY, double rockFrameFactorY)
	{
		super(posX, posY);
		this.amplitudeX = amplitudeX;
		this.amplitudeY = amplitudeY;
		this.rockFrameFactorX = rockFrameFactorX;
		this.rockFrameFactorY = rockFrameFactorY;
		iii = 0;
	}
	
	@Override
	protected void updateTransform() {
		double rockX = amplitudeX * Math.sin(iii * rockFrameFactorX);
		double rockY = amplitudeY * Math.sin(iii * rockFrameFactorY);
		transform.set(prevWidth * posX + rockX, prevHeight * posY + rockY);
	}
	
	@Override
	public void increment(int width, int height) {
		super.increment(width, height);
		iii += 1;
		updateTransform();
	}
}
