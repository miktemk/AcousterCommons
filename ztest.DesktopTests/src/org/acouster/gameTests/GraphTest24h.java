package org.acouster.gameTests;

import java.awt.Color;

import org.acouster.Game;
import org.acouster.GameContext;
import org.acouster.IFuncVoid;
import org.acouster.context.ContextGraphics;
import org.acouster.context.desktop.DesktopResourceContext;
import org.acouster.desktop.ui.EmulatorWindow;
import org.acouster.graphics.Colorz;
import org.acouster.graphics.RenderableObject2D;
import org.acouster.graphics.WorldLayer;
import org.acouster.graphics.anim.FullScreenSolidColor;
import org.acouster.graphics.ui.GestureActionEngine;
import org.acouster.logic.Sprite2D;

public class GraphTest24h extends Game
//implements GestureActionEngine.IGestureObserver
{
	private static final int LAYER_LEVEL_BG = 1;
	private static final int LAYER_LEVEL_CHART = 2;
	private static final int LAYER_LEVEL_LABELS = 3;
	
	protected WorldLayer layerBg, layerChart, layerLabels;
	protected GestureActionEngine uiActions;
	protected int offsetX, offsetY;
	private DraggableSurface dragSurface;
	private Graph24Visual graphVisual;
	
	public GraphTest24h(GameContext gameContext)
	{
		super(gameContext);
		
		// layers
		addLayer(layerBg = new WorldLayer(LAYER_LEVEL_BG));
		addLayer(layerChart = new WorldLayer(LAYER_LEVEL_CHART));
		addLayer(layerLabels = new WorldLayer(LAYER_LEVEL_LABELS));
		
		dragSurface = new DraggableSurface();
		Graph24Sprite spriteGraph = new Graph24Sprite(dragSurface);
		graphVisual = new Graph24Visual(spriteGraph);
		
		addSprite(spriteGraph);
		addActionEngine(uiActions = new GestureActionEngine(this, dragSurface));
		layerBg.addRenderable(new FullScreenSolidColor(Colorz.WHITE));
		layerChart.addRenderable(graphVisual);
	}
	
	@Override
	public void incrementCharacters(int width, int height) {
		super.incrementCharacters(width, height);
	}
	
	@Override
	public void paintCharacters(ContextGraphics g, int width, int height) {
		super.paintCharacters(g, width, height);
	}
	
	// =================== helper classes ========================
	
	
	public static class Graph24Visual extends RenderableObject2D
	{
		public static class GraphDims {
			// out of 24
			public static final float BedTimeOffset = -3; // 3 hrs back = 9pm of the night before
			public static final float Sunrise = 7;
			public static final float Sunset = 19;
		}
		
		public Graph24Visual(Sprite2D trans) {
			super(trans);
		}

		@Override
		public void render(ContextGraphics g, int w, int h) {
			int scrollX = sprite.getIntX();
			int scrollY = sprite.getIntY();
			
			float ySunrise = scrollY + h * GraphDims.Sunrise / 24;
			float ySunset = scrollY + h * GraphDims.Sunset / 24;
			
			// night rects
			g.setFill(Colorz.BLUE);
			g.setFill(Colorz.Html.Wheat);
			g.fillRect(0, 0, w, ySunrise);
			g.fillRect(0, ySunset, w, h-ySunset);
			// midnight line
			g.setColor(Colorz.GREEK_TORQUOISE);
			float yMidnight = scrollY;
			if (yMidnight < 0)
				yMidnight += h;
			g.drawLine(0, yMidnight, w, yMidnight);
			
			// test elem
			g.drawCircle(scrollX, scrollY, 10);
		}
	}
	
	public static class Graph24Sprite extends Sprite2D
	{
		private DraggableSurface surface;

		public Graph24Sprite(DraggableSurface surface) {
			this.surface = surface;
			surface.setOnCoordsUpdated(new IFuncVoid<GraphTest24h.DraggableSurface>() {
				@Override
				public void lambda(DraggableSurface sss) {
					transform.set(sss.getX(), sss.getY());
				}
			});
		}
		
		@Override
		public void dimensionsChanged(int width, int height) {
			super.dimensionsChanged(width, height);
		}
		
//		@Override
//		public void increment(int width, int height) {
//			super.increment(width, height);
//		}
	}
	
	public static class DraggableSurface implements GestureActionEngine.IGestureObserver
	{
		protected int x, y,dx, dy;
		protected int thresX, thresY;
		protected boolean thresXtriggered, thresYtriggered;
		protected int minX, maxX, minY, maxY;
		protected boolean minXset, maxXset, minYset, maxYset;
		protected IFuncVoid<DraggableSurface> coordsUpdated;
		
		public DraggableSurface() {
			this(0, 0);
		}
		public DraggableSurface(int x, int y) {
			this.x = x;
			this.y = y;
			dx = dy = 0;
			thresX = thresY = 0;
			thresXtriggered = thresYtriggered = false;
			minXset = maxXset = minYset = maxYset = false;
		}

		public DraggableSurface setInitialX(int x) {
			this.x = x;
			return this;
		}
		public DraggableSurface setInitialY(int y) {
			this.y = y;
			return this;
		}
		public DraggableSurface setStabilityThreshX(int thresX) {
			this.thresX = thresX;
			return this;
		}
		public DraggableSurface setStabilityThreshY(int thresY) {
			this.thresY = thresY;
			return this;
		}
		public DraggableSurface setMinX(int minX) {
			this.minX = minX;
			minXset = true;
			return this;
		}
		public DraggableSurface setMaxX(int maxX) {
			this.maxX = maxX;
			maxXset = true;
			return this;
		}
		public DraggableSurface setMinY(int minY) {
			this.minY = minY;
			minYset = true;
			return this;
		}
		public DraggableSurface setMaxY(int maxY) {
			this.maxY = maxY;
			maxYset = true;
			return this;
		}
		public DraggableSurface setOnCoordsUpdated(IFuncVoid<DraggableSurface> lambda) {
			this.coordsUpdated = lambda;
			return this;
		}
		
		public int getX() {
			int resX = x + dx;
			resX = restrictX(resX);
			return resX;
		}
		public int getY() {
			int resY = y + dy;
			resY = restrictY(resY);
			return resY;
		}
		private int restrictY(int resY) {
			return resY;
		}
		private int restrictX(int resX) {
			return resX;
		}

		@Override
		public void dragBegin(int initX, int initY) {}
		@Override
		public void dragContinues(int deltaX, int deltaY) {
			if (!thresXtriggered && !thresYtriggered) {
				if (Math.abs(deltaX) > Math.abs(deltaY)) {
					dx = deltaX;
					thresXtriggered = true;
				}
				else {
					dy = deltaY;
					thresYtriggered = true;
				}
			}
			if (thresXtriggered || Math.abs(deltaX) >= thresX) {
				dx = deltaX;
				thresXtriggered = true;
			}
			if (thresYtriggered || Math.abs(deltaY) >= thresY) {
				dy = deltaY;
				thresYtriggered = true;
			}
			if (coordsUpdated != null)
				coordsUpdated.lambda(this);
		}
		@Override
		public void dragFinalize(int deltaX, int deltaY) {
			this.x += deltaX;
			this.y += deltaY;
			dx = dy = 0;
			thresXtriggered = thresYtriggered = false;
			if (coordsUpdated != null)
				coordsUpdated.lambda(this);
		}
	}
	
	
	
	
	// =================== copy up to here ========================
	
	
	// guitar main
	
	public static void main(String[] args) {
		new DesktopResourceContext().makeInstance();
		DesktopResourceContext.myInstance().setAssetPrefix("../zterminal.SplashCalc/assets");
		DesktopResourceContext.myInstance().setBitmapPrefix("../zterminal.SplashCalc/res/drawable-hdpi");
		DesktopResourceContext.myInstance().setExternalDir("C:\\Users\\miktemk\\Desktop");
		//DesktopResourceContext.myInstance().setExternalDir("C:\\Users\\mtemkine.TFO\\Desktop");
		new EmulatorWindow() {
			@Override
			public void onInit() {
	    		setGame(new GraphTest24h(this));
			}
		};
	}

}
