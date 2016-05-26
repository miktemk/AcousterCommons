package org.acouster.graphics.anim;

import org.acouster.context.ContextGraphics;
import org.acouster.graphics.RenderableObject2D;

/** a fountain of particles driven by gravity (ggg) parameter.
 * override renderParticle to gain full control */
public class ParticleEffect extends RenderableObject2D
{
	protected int n, x, y, rgb, life, time;
	protected float vx, vy, ggg;
	protected Particle[] particles;
	
	// TODO: if removal of these contructors causes any errors, use the new builder pattern
//	public ParticleEffect(int x, int y)
//	{
//		this(x, y, 0x0);
//	}
//	public ParticleEffect(int x, int y, int rgb)
//	{
//		this(10, x, y, rgb, 20, 4, 15, 1);
//	}
//	public ParticleEffect(int n, int x, int y, int rgb, int life, float vx, float vy, float ggg)
//	{
//		super(null);
//		this.n = n;
//		this.x = x;
//		this.y = y;
//		this.rgb = rgb;
//		this.life = life;
//		this.vx = vx;
//		this.vy = vy;
//		this.ggg = ggg;
//		time = 0;
//	}
	
	public ParticleEffect(int n)
	{
		super(null);
		this.n = n;
		// defaults
		rgb = 0x0;
		life = 20;
		vx = 4;
		vy = 15;
		ggg = 1;
	}
	public ParticleEffect setXY(int x, int y) {
		this.x = x;
		this.y = y;
		return this;
	}
	public ParticleEffect setLife(int life) {
		this.life = life;
		return this;
	}
	public ParticleEffect setV(float vx, float vy) {
		this.vx = vx;
		this.vy = vy;
		return this;
	}
	public ParticleEffect setGravity(float ggg) {
		this.ggg = ggg;
		return this;
	}
	public ParticleEffect setColoe(int rgb) {
		this.rgb = rgb;
		return this;
	}
	
	public void init()
	{
		particles = new Particle[n];
		for (int i = 0; i < n; i++)
			particles[i] = createParticle(x, y);
	}
	
	@Override
	public void increment(int width, int height)
	{
		if (particles == null)
			throw new RuntimeException("ParticleEffect.init must be called!");
		time++;
		if (time > life)
		{
			expire();
			return;
		}
		for (Particle p : particles)
		{
			p.dy += ggg;
			p.increment(time);
		}
	}
	
	@Override
	public void render(ContextGraphics g, int w, int h)
	{
		if (particles == null)
			throw new RuntimeException("ParticleEffect.init must be called!");
		if (!visible)
			return;
		g.setColor(rgb);
		int index = 0;
		for (Particle p : particles)
		{
			renderParticle(g, p, index);
			index++;
		}
	}
	
	/** override this for custom particles */
	protected Particle createParticle(int x, int y)
	{
		return new Particle(x, y, (float)(2*Math.random()-1) * vx, -(float)Math.random() * vy, 0, 0);
	}
	
	/** override this for custom particles */
	protected void renderParticle(ContextGraphics g, Particle p, int index)
	{
		final int rad = 3;
		final int curRadius = (int)(rad * (life-time) / life) + 1;
		g.drawOval((int)p.x, (int)p.y, curRadius, curRadius);
	}
	
	//============================================
	
	protected static class Particle
	{
		public float x, y, dx, dy, angle, da;
		public Particle(float x, float y, float dx, float dy, float angle, float da) {
			super();
			this.x = x;
			this.y = y;
			this.dx = dx;
			this.dy = dy;
			this.angle = angle;
			this.da = da;
		}
		/** override this for custom particles */
		protected void increment(int time)
		{
			x += dx;
			y += dy;
			angle += da;
		}
	}
	
}
