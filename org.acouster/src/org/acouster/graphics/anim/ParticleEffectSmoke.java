package org.acouster.graphics.anim;

import org.acouster.context.ContextBitmap;
import org.acouster.context.ContextGraphics;
import org.acouster.util.MathUtils;

/** a fountain of particles driven by gravity (ggg) parameter.
 * override renderParticle to gain full control */
public class ParticleEffectSmoke extends ParticleEffect
{
	public static final float ANGLE_SPEED_MAX_DEFAULT = 0.05f;
	public static final int USE_IMAGE_DIMENSIONS = -1;
	
	protected float evaporateSpeedMin, evaporateSpeedMax, angleSpeedRange, aPlusMinus, horizSpeedRange;
	protected int imgW, imgH;
	private ContextBitmap[] imgSmoke;

	// TODO: if removal of these contructors causes any errors, use the new builder pattern
//	public ParticleEffectSmoke(ContextBitmap[] imgSmoke, int x, int y, int imgW, int imgH) {
//		this(imgSmoke, 3, x, y, imgW, imgH, 20, 20, 20, 1);
//	}
//	/** in this case vx,vy are width and height of the area */
//	public ParticleEffectSmoke(ContextBitmap[] imgSmoke, int n, int x, int y, int imgW, int imgH, int life, float vx, float vy, float evaporateSpeed) {
//		this(imgSmoke, n, x, y, imgW, imgH, life, vx, vy, evaporateSpeed, evaporateSpeed, ANGLE_SPEED_MAX_DEFAULT, Math.PI*2, 0, 0);
//	}
//	public ParticleEffectSmoke(ContextBitmap[] imgSmoke, int n, int x, int y, int imgW, int imgH, int life, float vx, float vy, float evaporateSpeed, float angleSpeedRange) {
//		this(imgSmoke, n, x, y, imgW, imgH, life, vx, vy, evaporateSpeed, evaporateSpeed, angleSpeedRange, Math.PI*2, 0, 0);
//	}
//	public ParticleEffectSmoke(ContextBitmap[] imgSmoke, int n, int x, int y, int imgW, int imgH, int life, float vx, float vy, float evaporateSpeed, float angleSpeedRange, float angleInitRange) {
//		this(imgSmoke, n, x, y, imgW, imgH, life, vx, vy, evaporateSpeed, evaporateSpeed, angleSpeedRange, angleInitRange, 0, 0);
//	}
//	public ParticleEffectSmoke(ContextBitmap[] imgSmoke, int n, int x, int y, int imgW, int imgH, int life, float vx, float vy, float evaporateSpeedMin, float evaporateSpeedMax, float angleSpeedRange, float angleInitRange, float horizSpeedRange, float ggg)
//	{
//		super(n, x, y, 0x0, life, vx, vy, ggg);
//		this.evaporateSpeedMin = evaporateSpeedMin;
//		this.evaporateSpeedMax = evaporateSpeedMax;
//		this.angleSpeedRange = angleSpeedRange;
//		this.angleInitRange = angleInitRange;
//		this.horizSpeedRange = horizSpeedRange;
//		this.imgSmoke = imgSmoke;
//		if (imgSmoke == null || imgSmoke.length == 0)
//			throw new RuntimeException("What the hell have you given the particle effect?? the images are null or empty!! what am I supposed to render, you bitch?");
//		this.imgW = imgW;
//		this.imgH = imgH;
//		if (imgW == USE_IMAGE_DIMENSIONS)
//			this.imgW = imgSmoke[0].getWidth();
//		if (imgH == USE_IMAGE_DIMENSIONS)
//			this.imgH = imgSmoke[0].getHeight();
//	}
	
	public ParticleEffectSmoke(ContextBitmap[] imgSmoke, int n) {
		super(n);
		this.imgSmoke = imgSmoke;
		if (imgSmoke == null || imgSmoke.length == 0)
			throw new RuntimeException("What the hell have you given the particle effect?? the images are null or empty!! what am I supposed to render, you bitch?");
		// defaults...
		aPlusMinus = (float)Math.PI * 2;
		angleSpeedRange = ANGLE_SPEED_MAX_DEFAULT;
		ggg = 0;
//		this.imgW = imgSmoke[0].getWidth();
//		this.imgH = imgSmoke[0].getHeight();
	}
	
	public ParticleEffectSmoke setXY(int x, int y) {
		this.x = x;
		this.y = y;
		return this;
	}
	/** to set imageSize from img[0], use USE_IMAGE_DIMENSIONS */
	public ParticleEffectSmoke setImageDimensions(int imgW, int imgH) {
		this.imgW = imgW;
		this.imgH = imgH;
		if (imgW == USE_IMAGE_DIMENSIONS)
			this.imgW = imgSmoke[0].getWidth();
		if (imgH == USE_IMAGE_DIMENSIONS)
			this.imgH = imgSmoke[0].getHeight();
		return this;
	}
	public ParticleEffectSmoke setLife(int life) {
		this.life = life;
		return this;
	}
	public ParticleEffectSmoke setGravity(float ggg) {
		this.ggg = ggg;
		return this;
	}
	public ParticleEffectSmoke setInitAreaRanges(float vx, float vy) {
		this.vx = vx;
		this.vy = vy;
		return this;
	}
	public ParticleEffectSmoke setInitAngleRanges(float aPlusMinus) {
		this.aPlusMinus = aPlusMinus;
		return this;
	}
	public ParticleEffectSmoke setEvaporateSpeedRange(float evaporateSpeedMin, float evaporateSpeedMax) {
		this.evaporateSpeedMin = evaporateSpeedMin;
		this.evaporateSpeedMax = evaporateSpeedMax;
		return this;
	}
	public ParticleEffectSmoke setHorizSpeedRange(float horizSpeedRange) {
		this.horizSpeedRange = horizSpeedRange;
		return this;
	}
	public ParticleEffectSmoke setAngleSpeedRange(float angleSpeedRange) {
		this.angleSpeedRange = angleSpeedRange;
		return this;
	}
	
	
	/** override this for custom particles */
	protected ParticleEffect.Particle createParticle(int x, int y)
	{
		return new ParticleEffect.Particle(
				x + (float)(2*Math.random()-1) * vx,
				y + (float)(2*Math.random()-1) * vy,
				(float)((2*Math.random()-1)*horizSpeedRange),
				-MathUtils.randomFloat(evaporateSpeedMin, evaporateSpeedMax),
				(float)((2*Math.random()-1)*aPlusMinus),
				(float)((2*Math.random()-1)*angleSpeedRange));
	}
	
	/** override this for custom particles */
	protected void renderParticle(ContextGraphics g, Particle p, int index)
	{
		ContextBitmap img = imgSmoke[index % imgSmoke.length];
		// we assume here that all particle images are of same dimensions
		g.drawImageCentered(img, (int)p.x, (int)p.y, imgW, imgH, p.angle);
	}
}
