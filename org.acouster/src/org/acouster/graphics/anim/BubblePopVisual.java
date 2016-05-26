package org.acouster.graphics.anim;

import java.util.Vector;

import org.acouster.Graph;
import org.acouster.GraphTicker;
import org.acouster.IFuncSolo;
import org.acouster.IFuncVoid;
import org.acouster.context.ContextBitmap;
import org.acouster.context.ContextBitmapFragment;
import org.acouster.context.ContextGraphics;
import org.acouster.context.ImageManager;
import org.acouster.graphics.RenderableObject2D;
import org.acouster.graphics.WorldLayer;
import org.acouster.logic.Sprite2D;
import org.acouster.res.ComicStrip;
import org.acouster.util.MathUtils;

/**
 * State machine:<br/>
 * <ul>
 *   <li>key: <code>CHAIN_POP</code>
 *     <ul>
 *       <li>incInit</li>
 *       <li>incRain</li>
 *       <li>incWetting</li>
 *       <li>bubblerSpecialPopping (key: <code>CHAIN_POP_POPPING</code>) | callback: funcPopBegin</li>
 *       <li>bubblerSpecialDone | callback: funcPopFinished</li></ul></li>
 *       <li>key: <code>CHAIN_POPNOW</code>
 *     <ul>
 *       <li>init quickly (similar to incInit + pop_initSetVisible)</li>
 *       <li>goto: <code>CHAIN_POP_POPPING</code></li></ul></li>
 *       <li>key: <code>CHAIN_FILL</code>
 *     <ul>
 *       <li>incRain</li>
 *       <li>incWetting</li>
 *       <li>stay filled and just rotate bubbles</li>
 *     </ul>
 *   </li>
 * </ul>
 * 
 */
public class BubblePopVisual extends RenderableObject2D
{
	// protected constants (nodes, etc)
	protected static final int STATE_ORIG = 1;
	protected static final int CHAIN_POP = 2;
	protected static final int CHAIN_POP_RAINING = 3;
	protected static final int CHAIN_POP_WETTING = 4;
	protected static final int CHAIN_POP_POPPING = 5;
	protected static final int CHAIN_FILL = 6;
	protected static final int CHAIN_FILL_RAINING = 7;
	protected static final int CHAIN_FILL_WETTING = 8;
	protected static final int CHAIN_FILL_ROTATING = 9;
	protected static final int CHAIN_POPNOW = 10;
	protected static final int STATE_DONE = 100;
	protected static final String STATE_KEY_ORIG = "orig";
	protected static final String STATE_KEY_DONE = "done";

	// public constants
	/** use when doing POP_ACTION_FILL */
	public static final int POP_MODE_WHATEVER = 0;
	public static final int POP_MODE_RANDOM = 1;
	public static final int POP_MODE_RANDOM_UP = 2;
	public static final int POP_MODE_STAR = 3;
	public static final int POP_MODE_STAR_UP = 4;
	/** use when doing POP_ACTION_POP_INSTANT */
	public static final int RAIN_MODE_WHATEVER = 0;
	public static final int RAIN_MODE_RANDOM_STEADY = 2;
	public static final int RAIN_MODE_RANDOM_RANDOM = 3;
	public static final int RAIN_MODE_SEQUENTIAL = 4;
	public static final int POP_ACTION_POP = CHAIN_POP;
	public static final int POP_ACTION_POP_INSTANT = CHAIN_POPNOW;
	public static final int POP_ACTION_FILL = CHAIN_FILL;
	
	// stuff
	protected ContextBitmap bmpOrig, bmpRain, bmpPieces, bmpGlobs;
	protected ComicStrip popCartoon;
	protected Vector<BubbleStruct> bubbles;
	protected int state, life, lifeExpiration;
	protected IFuncVoid<BubblePopVisual> funcPopBegin;
	protected IFuncVoid<BubblePopVisual> funcPopFinished;
	protected WorldLayer popLayerDifferent;
	
	// settable params
	protected double speedRange = 6,
			speedAngleRange = 0.1;
	// DELAYS
	protected double delayPopping = 1.5,
			delayRaining = 0.5,
			delayWetting = 0.3;
	// DELAYS: old
//	protected double delayPopping = 1,
//			delayRaining = 0.5,
//			delayWetting = 0.3;
	protected double
			halfLifeMin = 0.4,
			halfLifeMax = 0.8;
	private GraphTicker<BubbleIncrementer> ticker;
	private Graph<BubbleIncrementer> fsm;
	private BubbleIncrementer bubbler = null;
	private BubbleIncrementer bubblerSpecialPopping, bubblerSpecialDone;
	private int rainMode, popMode;
	
	public BubblePopVisual(Sprite2D sprite, String fileOrig, String fileCutoff, String fileGlobs, String filePopCartoon, int popCartoonFrames) {
		this(sprite, fileOrig, fileOrig, fileCutoff, fileGlobs, filePopCartoon, popCartoonFrames);
	}
	public BubblePopVisual(Sprite2D sprite, String fileOrig, String fileRain, String fileCutoff, String fileGlobs, String filePopCartoon, int popCartoonFrames)
	{
		super(sprite);
		bmpOrig = ImageManager.loadR(fileOrig);
		bmpRain = ImageManager.loadR(fileRain);
		bmpPieces = ImageManager.loadR(fileCutoff, true);
		bmpGlobs = ImageManager.loadR(fileGlobs, true);
		popCartoon = new ComicStrip(ImageManager.loadR(filePopCartoon), 1, popCartoonFrames, popCartoonFrames);
		bubbles = new Vector<BubblePopVisual.BubbleStruct>();
		initBubbleStructs();
		setDefaultDimensions(bmpOrig.getWidth(), bmpOrig.getHeight());
		
		fsm = new Graph<BubbleIncrementer>();
		fsm.setOnAdvanceListener(new Graph.OnAdvanceListener<BubbleIncrementer>() {
			@Override
			public void onAdvance(BubbleIncrementer content) {
				bubbler = content;
				if (bubbler != null)
					bubbler.init(fsm.getCurrentDelay());
				if (bubbler == bubblerSpecialPopping && funcPopBegin != null)
					funcPopBegin.lambda(BubblePopVisual.this);
				if (bubbler == bubblerSpecialDone)
				{
					if (funcPopFinished != null)
						funcPopFinished.lambda(BubblePopVisual.this);
					setIsPopping(false);
				}
			}
		});
		constructStateMachine(fsm);
		ticker = new GraphTicker<BubbleIncrementer>(fsm);
		
		reset();
	}
	
	/** When pop begins (when state == CHAIN_POP_POPPING) */
	public void setFuncOnPopBegin(IFuncVoid<BubblePopVisual> funcPopBegin)
	{
		this.funcPopBegin = funcPopBegin;
	}
	/** When all bubbles have popped */
	public void setFuncOnFinished(IFuncVoid<BubblePopVisual> funcPopFinished)
	{
		this.funcPopFinished = funcPopFinished;
	}
	/** Move this visual to this layer when state == CHAIN_POP_POPPING */
	public void setDifferentPopLayer(WorldLayer popLayerDifferent)
	{
		this.popLayerDifferent = popLayerDifferent;
	}
	
	// TODO: this function
//	public void setSpeeds(double speedRange, double speedAngleRange,
//			double delayPopping, double delayWetting, double halfLifeMin, double halfLifeMax)
//	{
//		this.speedRange = speedRange;
//		this.speedAngleRange = speedAngleRange;
//		this.delayPopping = delayPopping;
//		this.delayWetting = delayWetting;
//		this.halfLifeMin = halfLifeMin;
//		this.halfLifeMax = halfLifeMax;
//	}
	public int getImageWidth()
	{
		return bmpOrig.getWidth();
	}
	public int getImageHeight()
	{
		return bmpOrig.getHeight();
	}
	
	//============================= graph =================================
	
	private void constructStateMachine(Graph<BubbleIncrementer> fsm)
	{
		BubbleIncrementer incInit, incRain, incWetting;
		
		// ------------ orig node
		fsm.addNode(new BubbleIncrementer(false, false) {
			@Override
			public void init(double delay) {
				super.init(delay);
				pop_initSetVisible(false);
			}
		}, 0, STATE_KEY_ORIG);
		
		// ------------ chain 1: POP
		fsm.addNode(incInit = new BubbleIncrementer(false, false) {
			@Override
			public void init(double delay) {
				super.init(delay);
				pop_initStructs();
			}
		}, 0, Graph.key(CHAIN_POP), true);
		fsm.addNode(incRain = new BubbleIncrementer(false, true) {
			// raining....
			@Override
			public void init(double delay) {
				super.init(delay);
				pop_initRainLife(delay);
			}
			@Override
			public void inc(double progress) {
				for (BubbleStruct sss : bubbles) {
					if (progress > sss.rainLife)
						sss.visible = true;
				}
			}
		}, delayRaining, Graph.key(CHAIN_POP_RAINING));
		fsm.addNode(incWetting = new BubbleIncrementer(true, true) {
			// wetting.... just wait there, set them all visible
			@Override
			public void init(double delay) {
				super.init(delay);
				pop_initSetVisible(true);
			}
		}, delayWetting, Graph.key(CHAIN_POP_WETTING));
		fsm.addNode(bubblerSpecialPopping = new BubbleIncrementer(true, true) {
			@Override
			public void init(double delay) {
				super.init(delay);
				pop_initIncrements(delay);
				setIsPopping(true);
				if (popLayerDifferent != null)
					popLayerDifferent.moveToMeTemporarily(BubblePopVisual.this, new IFuncSolo<Boolean>() {
						@Override
						public Boolean lambda() {
							return !isPopping();
						}
					});
			}
			@Override
			public void inc(double progress)
			{
				for (BubbleStruct sss : bubbles) {
					sss.x += sss.dx;
					sss.y += sss.dy;
					sss.a += sss.da;
					if (progress >= sss.halfLife)
					{
						if (!sss.popped)
							sss.popFrame = -1; // +1 = 0 :) HAAAACK
						sss.popped = true;
						sss.visible = false;
						sss.popFrame++;
					}
				}
			}
		}, delayPopping, Graph.key(CHAIN_POP_POPPING));
		fsm.addNode(bubblerSpecialDone = new BubbleIncrementer(true, false) {}, 0, STATE_KEY_DONE);
		
		// ------------ quickpop
		fsm.addNode(new BubbleIncrementer(false, true) {
			@Override
			public void init(double delay) {
				super.init(delay);
				pop_initStructs();
				pop_initSetVisible(true);
			}
		}, 0, Graph.key(CHAIN_POPNOW), true);
		fsm.linkTo(Graph.key(CHAIN_POP_POPPING));
		
		// ------------ chain 2: FILL
		fsm.addNode(incInit, 0, Graph.key(CHAIN_FILL), true);
		fsm.addNode(incRain, delayRaining, Graph.key(CHAIN_FILL_RAINING));
		fsm.addNode(incWetting, delayWetting, Graph.key(CHAIN_FILL_WETTING));
		fsm.addNode(new BubbleIncrementer(true, true) {
			//....
			@Override
			public void init(double delay) {
				super.init(delay);
				for (BubbleStruct sss : bubbles) {
					sss.da = MathUtils.randomDouble(-speedAngleRange, speedAngleRange);
				}
			}
			@Override
			public void inc(double progress) {
				for (BubbleStruct sss : bubbles) {
					sss.a += sss.da;
				}
			}
		}, 0, Graph.key(CHAIN_FILL_ROTATING));
		
		
		fsm.gotoFirstNode();
	}
	private void pop_initStructs()
	{
		for (BubbleStruct sss : bubbles)
		{
			sss.x = sprite.getTransform().getX() + resizeFactor*(sss.imgPiece.srcX);
			sss.y = sprite.getTransform().getY() + resizeFactor*(sss.imgPiece.srcY);
			sss.offsetBubbleX = (int)(resizeFactor*(sss.imgPiece.srcW - sss.imgBubble.srcW) / 2);
			sss.offsetBubbleY = (int)(resizeFactor*(sss.imgPiece.srcH - sss.imgBubble.srcH) / 2);
			sss.offsetShadowX = (int)(resizeFactor*(sss.imgPiece.srcW - sss.imgShadow.srcW) / 2);
			sss.offsetShadowY = (int)(resizeFactor*(sss.imgPiece.srcH - sss.imgShadow.srcH) / 2);
			sss.a = 0;
			sss.popped = false;
			sss.visible = false;
		}
	}
	private void pop_initSetVisible(boolean visible)
	{
		for (BubbleStruct sss : bubbles)
			sss.visible = visible;
	}
	private void pop_initRainLife(double delay)
	{
		delayRaining = 0;
		int iii = 0;
		for (BubbleStruct sss : bubbles)
		{
			switch(rainMode)
			{
			case RAIN_MODE_RANDOM_STEADY:
				sss.visible = false;
				sss.rainLife = ((iii * (bubbles.size()-3)) % bubbles.size() + 1.0) / bubbles.size();
				break;
			case RAIN_MODE_RANDOM_RANDOM:
				sss.visible = false;
				sss.rainLife = Math.random();
				break;
			case RAIN_MODE_SEQUENTIAL:
				sss.visible = false;
				sss.rainLife = iii;
				break;
			}
			iii++;
		}
	}
	private void pop_initIncrements(double delayInPoppingState)
	{
		delayRaining = 0;
		for (BubbleStruct sss : bubbles)
		{
			sss.visible = true;
			switch(popMode)
			{
			case POP_MODE_RANDOM:
				sss.dx = MathUtils.randomDouble(-speedRange, speedRange);
				sss.dy = MathUtils.randomDouble(-speedRange, speedRange);
				sss.da = MathUtils.randomDouble(-speedAngleRange, speedAngleRange);
				sss.halfLife = MathUtils.randomDouble(halfLifeMin, halfLifeMax);
				break;
			case POP_MODE_RANDOM_UP:
				sss.dx = MathUtils.randomDouble(-speedRange, speedRange);
				sss.dy = MathUtils.randomDouble(-speedRange, 0);
				sss.da = MathUtils.randomDouble(-speedAngleRange, speedAngleRange);
				sss.halfLife = MathUtils.randomDouble(halfLifeMin, halfLifeMax);
				break;
			case POP_MODE_STAR:
				// TODO: this is same as random now
				sss.dx = MathUtils.randomDouble(-speedRange, speedRange);
				sss.dy = MathUtils.randomDouble(-speedRange, speedRange);
				sss.da = MathUtils.randomDouble(-speedAngleRange, speedAngleRange);
				sss.halfLife = MathUtils.randomDouble(halfLifeMin, halfLifeMax);
				break;
			}
		}
	}
	
	/** are the bubbles flying everywhere popping? */
	private boolean isPopping = false;
	private void setIsPopping(boolean b) {
		isPopping = b;
	}
	private boolean isPopping() {
		return isPopping;
	}
	
	//============================= behavior =================================
	
	public void reset()
	{
		ticker.gotoNode(STATE_KEY_ORIG);
	}
	public void pop(int popAction, int popMode, int rainMode)
	{
		this.popMode = popMode;
		this.rainMode = rainMode;
		ticker.gotoNode(Graph.key(popAction));
	}
	
	//============================= Game engine implementation =================================
	
	@Override
	public void increment(int width, int height)
	{
		ticker.increment();
		if (bubbler != null)
			bubbler.inc(ticker.getCurrentProgress());
	}
	
	@Override
	public void render(ContextGraphics g, int w, int h)
	{
		//debug_renderSpriteCrosshair(g);
		if (!bubbler.imageIsSoaked)
		{
			//int imgX = (int)sprite.getTransform().getX() - width/2;
			//int imgY = (int)sprite.getTransform().getY() - height/2;
			int imgX = sprite.getTransform().getIntX();
			int imgY = sprite.getTransform().getIntY();
			if (bubbler.showBubbles)
				g.drawImage(bmpRain, imgX, imgY, width, height);
			else
				g.drawImage(bmpOrig, imgX, imgY, width, height);
			
			if (bubbler.showBubbles)
			{
				for (BubbleStruct sss : bubbles)
				{
					if (sss.visible)
					{
						g.drawImage(sss.imgShadow, (int)sss.x + sss.offsetShadowX, (int)sss.y + sss.offsetShadowY, (int)(resizeFactor*sss.imgShadow.srcW), (int)(resizeFactor*sss.imgShadow.srcH));
						g.drawImage(sss.imgBubble, (int)sss.x + sss.offsetBubbleX, (int)sss.y +  + sss.offsetBubbleY, (int)(resizeFactor*sss.imgBubble.srcW), (int)(resizeFactor*sss.imgBubble.srcH));
					}
				}
			}
		}
		else if (bubbler.showBubbles)// if (state == STATE_POPPING || state == STATE_WETTING)
		{
			int iii = 0;
			for (BubbleStruct sss : bubbles)
			{
				if (sss.visible)
				{
					g.drawImage(sss.imgShadow, (int)sss.x + sss.offsetShadowX, (int)sss.y + sss.offsetShadowY, (int)(resizeFactor*sss.imgShadow.srcW), (int)(resizeFactor*sss.imgShadow.srcH));
					g.drawImage(sss.imgPiece, (int)sss.x, (int)sss.y, (int)(resizeFactor*sss.imgPiece.srcW), (int)(resizeFactor*sss.imgPiece.srcH),
							false, false, sss.a, true);
					g.drawImage(sss.imgBubble, (int)sss.x + sss.offsetBubbleX, (int)sss.y +  + sss.offsetBubbleY, (int)(resizeFactor*sss.imgBubble.srcW), (int)(resizeFactor*sss.imgBubble.srcH));
//					g.setColor(0x000000);
//					BasicGraphics.drawCrosshair(g, (int)sss.x + sss.imgPiece.srcW/2, (int)sss.y + sss.imgPiece.srcH/2);
//					BasicGraphics.drawCrosshair(g, (int)sss.x + sss.offsetShadowX + sss.imgShadow.srcW/2, (int)sss.y + sss.offsetShadowY + sss.imgShadow.srcH/2);
				}
				if (sss.popped)
				{
					ContextBitmap frame = popCartoon.getFrame(sss.popFrame, false); // slow mo?
					if (frame != null)
						g.drawImage(frame, (int)sss.x, (int)sss.y, (int)(resizeFactor*sss.imgBubble.srcW), (int)(resizeFactor*sss.imgBubble.srcH));
				}
//=========================== DEBUG: labels =======================
//				g.setColor(0x000000);
//				g.drawString("" + iii, (int)sss.x, (int)sss.y);
//				iii++;
			}
		}
	}
	
	
	//============================= bubble structs =================================
		
	/** Override this function with code from the bubble XML generator tool */
	public void initBubbleStructs() {}
	protected static class BubbleStruct
	{
		//int srcX, srcY, srcW, srcH;
		double x, y, dx, dy, a, da;
		int offsetBubbleX, offsetBubbleY, offsetShadowX, offsetShadowY;
		double halfLife, rainLife;
		int popFrame;
		boolean visible, popped = false;
		
		ContextBitmapFragment imgPiece, imgBubble, imgShadow;
		
		public BubbleStruct(
				ContextBitmapFragment imgPiece,
				ContextBitmapFragment imgBubble,
				ContextBitmapFragment imgShadow)
		{
			super();
			this.imgPiece = imgPiece;
			this.imgBubble = imgBubble;
			this.imgShadow = imgShadow;
		}
	}
	
	protected static class BubbleIncrementer
	{
		double delay;
		boolean imageIsSoaked, showBubbles;
		
		public BubbleIncrementer(boolean imageIsSoaked, boolean showBubbles)
		{
			super();
			this.imageIsSoaked = imageIsSoaked;
			this.showBubbles = showBubbles;
		}
		
		public void init(double delay)
		{
			this.delay = delay;
		}
		public void inc(double progress) {}
	}
	
	
}
