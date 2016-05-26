package org.acouster.graphics.anim;

import org.acouster.IFuncVoidSolo;
import org.acouster.SimpleTicker;
import org.acouster.context.ContextGraphics;
import org.acouster.graphics.BasicGraphics;
import org.acouster.util.MathUtils;

public class FullScreenSolidColorAnimated extends FullScreenSolidColor
{
	protected int[] rgbs;
	protected int curFrame;
	protected SimpleTicker ticktock;
	private int curRgb;
	
	public FullScreenSolidColorAnimated(double delayBwColors, final int[] rgbs)
	{
		super(rgbs[0]);
		this.rgbs = rgbs;
		curFrame = 0;
		ticktock = new SimpleTicker(delayBwColors, true, new IFuncVoidSolo() {
			@Override
			public void lambda() {
				curFrame++;
				curFrame %= rgbs.length;
			}
		});
	}
	
	@Override
	public void increment(int width, int height) {
		super.increment(width, height);
		ticktock.increment();
		double progress = ticktock.getCurrentProgress();
		int r = (int)MathUtils.weightedAverage(MathUtils.getR(rgbs[curFrame]), MathUtils.getR(rgbs[(curFrame+1) % rgbs.length]), progress);
		int g = (int)MathUtils.weightedAverage(MathUtils.getG(rgbs[curFrame]), MathUtils.getG(rgbs[(curFrame+1) % rgbs.length]), progress);
		int b = (int)MathUtils.weightedAverage(MathUtils.getB(rgbs[curFrame]), MathUtils.getB(rgbs[(curFrame+1) % rgbs.length]), progress);
		setColor(MathUtils.getRgb(r, g, b));
	}
}
