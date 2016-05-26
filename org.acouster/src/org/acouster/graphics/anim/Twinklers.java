package org.acouster.graphics.anim;

import org.acouster.context.ContextBitmap;
import org.acouster.context.ContextGraphics;
import org.acouster.context.ImageManager;
import org.acouster.graphics.Colorz;
import org.acouster.graphics.RenderableObject;
import org.acouster.math.Rect2D;
import org.acouster.res.ComicStrip;
import org.acouster.util.ArrayUtils;
import org.acouster.util.MathUtils;

/** Twinckle-twinckle little star. Helper for Renderables appearing randomly,
 * playing with no loop and, upon finish, disappear. Within a rect. */
public class Twinklers
{
	private ComicStrip seq;
	private Rect2D rect;
	private LittleStar[] stars;
	private int frameRateReduction;
	private int framesAnim, beforeAnimMin, beforeAnimMax;

	/** @param freq - freq/nStars is how many stars we see in rect at a time */
	public Twinklers(ComicStrip seq, int nStars, int freq, int frameRateReduction) {
		this.seq = seq;
		this.frameRateReduction = frameRateReduction;
		this.framesAnim = seq.getNFrames() * frameRateReduction;
		this.beforeAnimMin = framesAnim * nStars / freq;
		this.beforeAnimMax = this.beforeAnimMin * 2;
		rect = new Rect2D();
		stars = new LittleStar[nStars];
		for (int i = 0; i < nStars; i++)
			stars[i] = new LittleStar();
	}
	
	public Twinklers setRect(int x, int y, int w, int h) {
		rect.set(x, y, w, h);
		for (LittleStar sss : stars) {
			sss.reset();
			sss.jumpAheadSomewhereHalfway();
		}
		return this;
	}
	
	public void render(ContextGraphics g) {
		for (LittleStar sss : stars) {
			ContextBitmap bmpWave = seq.getFrame(sss.getFrameIndex(), true);
			if (bmpWave == null)
				continue;
			g.drawImageCentered(bmpWave, sss.x, sss.y, bmpWave.getWidth(), bmpWave.getHeight());
		}
	}
	
	public void inc() {
		for (LittleStar sss : stars) {
			sss.inc();
		}
	}
	
	
	//=====================================================
	
	private class LittleStar {
		public int iii;
		public int lifeBefore, maturity;
		public int x, y;
		public boolean active;
		
		public LittleStar() {
			reset();
		}
		
		public void jumpAheadSomewhereHalfway() {
			iii = MathUtils.randomInt(beforeAnimMin, beforeAnimMax);
		}

		private void reset() {
			iii = 0;
			lifeBefore = MathUtils.randomInt(beforeAnimMin, beforeAnimMax);
			x = MathUtils.randomInt(rect.x, rect.x + rect.w);
			y = MathUtils.randomInt(rect.y, rect.y + rect.h);
		}

		public void inc() {
			iii++;
			if (iii > lifeBefore+framesAnim)
				reset();
		}
		
		public int getFrameIndex()
		{
			if (iii < lifeBefore)
				return -1;
			return (iii - lifeBefore)/frameRateReduction;
		}
	}
	
}
