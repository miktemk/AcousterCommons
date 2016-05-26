package org.acouster;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import java.util.Vector;

import org.acouster.context.ContextBitmap;
import org.acouster.context.ContextGraphics;
import org.acouster.context.ContextKeyEvent;
import org.acouster.context.ContextMouseEvent;
import org.acouster.context.ImageManager;
import org.acouster.graphics.World2D;
import org.acouster.graphics.WorldLayer;
import org.acouster.graphics.ui.UIActionEngine;
import org.acouster.graphics.ui.UIElementBase;
import org.acouster.graphics.ui.UILayoutManagerHtmlPositioned;
import org.acouster.logic.HtmlPositioner;
import org.acouster.logic.Sprite;
import org.acouster.logic.Sprite2D;
import org.acouster.logic.Sprite2DHtmlPositioned;
import org.acouster.logic.Spritework;
import org.acouster.util.ArrayUtils;

public abstract class Game implements Flashable, IContextDimensions
{
	public static final String MESSAGE_GO = "GO";
	public static final String BUNDLE_KEY_SCREEN_STACK = "Game.innerScreenStack";
	public static final String BUNDLE_KEY_GAME_JSON = "Game.gameJson";
	
	public static final String DIRECTIVE_KILL_ACTIVITY = "kill-activity";
	public static final String DIRECTIVE_SOUND = "sound";
	
	protected GameContext gameContext;
	protected Vector<ActionEngine> actionEngines;
	protected Spritework spritework;
	protected World2D graphicals;
	protected int prevContextWidth = 0, prevContextHeight = 0, curContextWidth = 0, curContextHeight = 0;
	/** Updated every mouse down-drag-release */
	protected int mouseDownX, mouseDownY, mouseCurX, mouseCurY;
	/** Set to currentTimeMillis during constructor time */
	protected long timestamp0;
	private HashMap<Integer, IInnerScreen> screens;
	private Stack<Integer> screenStack;
	
	// initialization
	public Game()
	{
		this(null);
	}
	public Game(GameContext gameContext)
	{
		setContext(gameContext);
		actionEngines = new Vector<ActionEngine>();
		spritework = new Spritework();
		graphicals = new World2D();
		timestamp0 = System.currentTimeMillis();
		
		screens = new HashMap<Integer, IInnerScreen>();
		screenStack = new Stack<Integer>();
	}
	
	public void setContext(GameContext gameContext) {
		this.gameContext = gameContext;
	}
	public GameContext getContext() {
		return gameContext;
	}
	public int getContextWidth() {
		return curContextWidth;
	}
	public int getContextHeight() {
		return curContextHeight;
	}
	public int getContextDiagonal() {
		return curContextWidth + curContextHeight;
	}
	protected boolean isContextDimensionsChanged()
	{
		return (prevContextWidth != curContextWidth || prevContextHeight != curContextHeight);
	}
	/** AKA: not zzzzero */
	protected boolean isContextDimensionsValid() {
		return curContextWidth > 0 && curContextHeight > 0;
	}
	
	//========= util and helper methods to be called from within ===============
	protected void addActionEngine(ActionEngine actionEngine) {
		actionEngines.add(actionEngine);
    }
	//----------- sprite -----------------
	protected Sprite addSprite(Sprite sprite) {
		if (sprite == null)
			throw new NullPointerException("You added a null sprite, you goddamn vegetarian!");
		spritework.addSprite(sprite);
		return sprite;
	}
	protected Sprite2D addSprite(Sprite2D sprite) {
		if (sprite == null)
			throw new NullPointerException("You added a null sprite, you goddamn vegetarian!");
		spritework.addSprite(sprite);
		return sprite;
	}
	protected Sprite2D addNewSprite2D() {
		return addSprite(new Sprite2D());
	}
	protected Sprite2DHtmlPositioned addNewSprite2DHtmlPositioned(HtmlPositioner positioner) {
		Sprite2DHtmlPositioned sss = new Sprite2DHtmlPositioned(positioner);
		addSprite(sss);
		return sss;
	}
	protected void removeSprite(Sprite sprite) {
		spritework.removeSprite(sprite);
	}
	protected void removeSprite(String key) {
		spritework.removeSprite(key);
	}
	
	//---------- inner screens --------------
	protected void registerInnerScreen(int id, IInnerScreen screen)
	{
		screens.put(id, screen);
	}
	protected void gotoInnerScreen(int id)
	{
		screenStack.push(id);
		updateCurScreenByStackPeek();
	}
	private void updateCurScreenByStackPeek()
	{
		if (screenStack.isEmpty())
			return;
		int id = screenStack.peek();
		for (IInnerScreen sss : screens.values())
			sss.setActive(false);
		screens.get(id).setActive(true);
	}
	/** will return false to prevent android back button default behavior
	 * TYPICAL USAGE:
	 * @Override
	 * public void onBackPressed() {
	 *     if (game.backButtonPressed())
	 *         super.onBackPressed();
	 * }
	 *  */
	public boolean backButtonPressed()
	{
		// desktop overclick hack... not to skrew up stack when it is == 1
		if (screenStack.size() == 1)
			return true;
		if (!screenStack.isEmpty())
			screenStack.pop();
		updateCurScreenByStackPeek();
		return screenStack.isEmpty();
	}
	public int[] getInnerScreenStack() {
		return ArrayUtils.ToIntArray(screenStack.toArray(new Integer[screenStack.size()]));
	}
	public void setInnerScreenStack(int[] values)
	{
		screenStack.clear();
		for (int i = 0; i < values.length; i++)
			screenStack.push(values[i]);
		updateCurScreenByStackPeek();
	}
	
	//---------- renderable --------------
	protected void addLayer(WorldLayer layer) {
		graphicals.addLayer(layer);
	}
//	protected void addRenderableToDefaultLayer(RenderableObject obj) {
//		graphicals.addRenderableToDefaultLayer(obj);
//	}
//	protected void removeRenderableFromDefaultLayer(RenderableObject obj) {
//		graphicals.removeRenderableFromDefaultLayer(obj);
//	}
	protected void switchContext(Game g) {
		// throw exception?
		if (gameContext == null)
			return;
		gameContext.setGame(g);
    }
	protected GameEvent recycledGameEvent;
	protected void sendEventToContext(String body) {
		if (gameContext == null)
			return;
		if (recycledGameEvent == null)
			recycledGameEvent = new GameEvent();
		recycledGameEvent.setTarget(GameEvent.EVENT_TARGET_CONTEXT);
		recycledGameEvent.setBody(body);
		gameContext.handleMessage(recycledGameEvent);
    }
	protected void sendEventToContext(String directive, String value) {
		if (gameContext == null)
			return;
		if (recycledGameEvent == null)
			recycledGameEvent = new GameEvent();
		recycledGameEvent.setTarget(GameEvent.EVENT_TARGET_CONTEXT);
		recycledGameEvent.setBody(directive, value);
		gameContext.handleMessage(recycledGameEvent);
    }
	//-----------Game UI and layout -------------
	protected UIElementBase addUIToActionsAndLayer(
			UIElementBase elem,
			WorldLayer papa,
			UIActionEngine engine) {
		engine.addUI(elem);
		papa.addRenderable(elem);
		return elem;
	}
	protected UIElementBase addUIToActionsAndLayerHtmlPositioned(
			UIElementBase elem,
			WorldLayer papa,
			UIActionEngine engine,
			UILayoutManagerHtmlPositioned layout,
			HtmlPositioner pos) {
		layout.addUI(addUIToActionsAndLayer(elem, papa, engine), pos);
		return elem;
	}
	//---------- other helpers ------------------
	protected ContextBitmap loadImage(String fname) {
		return ImageManager.instance().loadFromResource(fname, false);
	}
	protected ContextBitmap loadImage(String fname, boolean preciseSize) {
		return ImageManager.instance().loadFromResource(fname, preciseSize);
	}
	
	// communication with user
	public List<ActionEngine> getActionEngines() {
		return actionEngines;
	}
	/** this method is called by ActionEngine */
	public void handleActionMessage(GameEvent message) {
		spritework.handleActionMessage(message);
	}
	/** this method is called by n'importe qui */
	public void handleCustomMessage(GameEvent message) {
		// override this to get better control
	}

	@Override
	public void flash()
    {
        if (gameContext == null)
        	return;
        incrementCharacters(gameContext.getContextWidth(), gameContext.getContextHeight());
    	gameContext.triggerRepaint();
        return;
    }
    
    // abstract methods
    public void incrementCharacters(int width, int height)
    {
    	prevContextWidth = curContextWidth;
    	prevContextHeight = curContextHeight;
    	curContextWidth = width;
    	curContextHeight = height;
    	spritework.increment(width, height);
		graphicals.increment(width, height);
		if (isContextDimensionsChanged()) {
			spritework.dimensionsChanged(width, height);
			dimensionsChanged(width, height);
		}
    }
    /** override to respond to dimension changes */
    protected void dimensionsChanged(int width, int height) {}
	public void paintCharacters(ContextGraphics g, int width, int height)
    {
    	graphicals.render(g, width, height);
    }
    /** override this to add a layer of hints (won't be rendered in video capture) */
    public void drawHints(ContextGraphics ggg, int width, int height) {}
    
    // action engine shortcuts
	public void mouseClicked(ContextMouseEvent contextMouseEvent)
	{
		mouseDownX = mouseCurX = contextMouseEvent.getX();
		mouseDownY = mouseCurY = contextMouseEvent.getY();
		for (ActionEngine actionEngine : actionEngines)
			if (actionEngine.isActive() && !contextMouseEvent.isProcessed())
				actionEngine.mouseClicked(contextMouseEvent);
	}
	public void mousePressed(ContextMouseEvent contextMouseEvent)
	{
		mouseDownX = mouseCurX = contextMouseEvent.getX();
		mouseDownY = mouseCurY = contextMouseEvent.getY();
		for (ActionEngine actionEngine : actionEngines)
			if (actionEngine.isActive() && !contextMouseEvent.isProcessed())
				actionEngine.mousePressed(contextMouseEvent);
	}
	public void mouseReleased(ContextMouseEvent contextMouseEvent)
	{
		mouseCurX = contextMouseEvent.getX();
		mouseCurY = contextMouseEvent.getY();
		for (ActionEngine actionEngine : actionEngines)
			if (actionEngine.isActive() && !contextMouseEvent.isProcessed())
				actionEngine.mouseReleased(contextMouseEvent);
	}
	public void mouseEntered(ContextMouseEvent contextMouseEvent)
	{
		for (ActionEngine actionEngine : actionEngines)
			if (actionEngine.isActive() && !contextMouseEvent.isProcessed())
				actionEngine.mouseEntered(contextMouseEvent);
	}
	public void mouseExited(ContextMouseEvent contextMouseEvent)
	{
		for (ActionEngine actionEngine : actionEngines)
			if (actionEngine.isActive() && !contextMouseEvent.isProcessed())
				actionEngine.mouseExited(contextMouseEvent);
	}
	public void mouseMoved(ContextMouseEvent contextMouseEvent)
	{
		for (ActionEngine actionEngine : actionEngines)
			if (actionEngine.isActive() && !contextMouseEvent.isProcessed())
				actionEngine.mouseMoved(contextMouseEvent);
	}
	public void mouseDragged(ContextMouseEvent contextMouseEvent)
	{
		mouseCurX = contextMouseEvent.getX();
		mouseCurY = contextMouseEvent.getY();
		for (ActionEngine actionEngine : actionEngines)
			if (actionEngine.isActive() && !contextMouseEvent.isProcessed())
				actionEngine.mouseDragged(contextMouseEvent);
	}
	public void keyPressed(ContextKeyEvent e)
	{
		for (ActionEngine actionEngine : actionEngines)
			if (actionEngine.isActive() && !e.isProcessed())
				actionEngine.keyPressed(e);
	}
	public void keyReleased(ContextKeyEvent e)
	{
		for (ActionEngine actionEngine : actionEngines)
			if (actionEngine.isActive() && !e.isProcessed())
				actionEngine.keyReleased(e);
	}
	public void keyTyped(ContextKeyEvent e)
	{
		for (ActionEngine actionEngine : actionEngines)
			if (actionEngine.isActive() && !e.isProcessed())
				actionEngine.keyTyped(e);
	}
	/** override this to support accelerometer */
	public void onAccelerometerChange(float mSensorX, float mSensorY) {}
	
	public void dispose()
	{
		spritework.dispose();
		graphicals.dispose();
	}
	/** override this to save JSON string of game state to Android bundle before device is put to sleep. */
	public String getBundleValues()
	{
		return null;
	}
	/** override this to get JSON string of game state from Android when GameActivity reawakens again */
	public void restoreBundleValues(String json) {}
}
