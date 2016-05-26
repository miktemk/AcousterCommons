package org.acouster.graphics.anim;

import org.acouster.context.ContextBitmap;
import org.acouster.context.ContextGraphics;
import org.acouster.graphics.RenderableObject;
import org.acouster.util.MathUtils;

public class ParallaxBackground extends RenderableObject
{
	private ParallaxLayer[] pLayers;
	private float globalPosition, paralaxSpeed;

	public ParallaxBackground(int nLayers)
	{
		pLayers = new ParallaxLayer[nLayers];
		globalPosition = 0;
	}
	
	//---------------- settings -----------------------
	public ParallaxBackground setLayer(int id, ContextBitmap img, float hTopPercent, float hBottomPercent, float velocityFactor)
	{
		pLayers[id] = new ParallaxLayer(img, hTopPercent, hBottomPercent, velocityFactor);
		return this;
	}
	public void setPosition(float globalPosition) {
		this.globalPosition = globalPosition;
	}
	public void setDelta(float paralaxSpeed) {
		this.paralaxSpeed = paralaxSpeed;
	}
	
	//---------------- events -----------------------
	@Override
	public void render(ContextGraphics g, int w, int h) {
		for (ParallaxLayer ppp : pLayers) {
			if (ppp == null)
				continue;
			ppp.render(g, w, h);
		}
	}
	@Override
	public void increment(int width, int height) {
		globalPosition += paralaxSpeed;
		for (ParallaxLayer ppp : pLayers) {
			if (ppp == null)
				continue;
			ppp.inc();
		}
	}
	public void dimensionsChanged(int w, int h) {
		for (ParallaxLayer ppp : pLayers) {
			if (ppp == null)
				continue;
			ppp.dimsChanged(w, h);
		}
	}
	
	
	//=====================================================================
	
	private class ParallaxLayer
	{
		public ContextBitmap img;
		public float hTopPercent, hBottomPercent, velocityFactor;
		public int hTop, imgHeight, imgWidth;
		
		public ParallaxLayer(ContextBitmap img, float hTopPercent, float hBottomPercent, float velocityFactor) {
			this.img = img;
			this.hTopPercent = hTopPercent;
			this.hBottomPercent = hBottomPercent;
			this.velocityFactor = velocityFactor;
		}
		public void dimsChanged(int w, int h) {
			hTop = (int)(h * hTopPercent);
			imgHeight = (int)(h * (hBottomPercent - hTopPercent));
			// TODO: introduce stretchIsOK
			imgWidth = Math.max(w, imgHeight * img.getWidth() / img.getHeight());
		}
		public void render(ContextGraphics g, int w, int h)
		{
			int posPixel = (int)(w * globalPosition * velocityFactor);
			posPixel = (int)MathUtils.wrap(posPixel, imgWidth, 1000) - imgWidth;
			g.drawImage(img, posPixel, hTop, imgWidth, imgHeight);
			if (posPixel + imgWidth < imgWidth)
				g.drawImage(img, posPixel + imgWidth, hTop, imgWidth, imgHeight);
		}
		public void inc() {
			// TODO Auto-generated method stub
			
		}
	}
}
