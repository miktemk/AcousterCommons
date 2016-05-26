package org.acouster.gameTests.sleep;

import org.acouster.GameContext;
import org.acouster.IFuncVoid;
import org.acouster.context.ContextBitmap;
import org.acouster.context.ContextGraphics;
import org.acouster.context.ImageManager;
import org.acouster.graphics.Colorz;
import org.acouster.graphics.WorldLayer;
import org.acouster.graphics.ui.UIActionEngine;
import org.acouster.graphics.ui.UIButton;
import org.acouster.graphics.ui.UIElementBase;
import org.acouster.graphics.ui.UIImageButton;
import org.acouster.graphics.ui.UILayoutManagerCenteredFitBox;
import org.acouster.graphics.ui.UILayoutManagerHtmlPositioned;
import org.acouster.logic.HtmlPositioner;
import org.acouster.util.MathUtils;

public class GameMain extends GameBaseClass
{
	public static final String DIRECTIVE_SLEEPTIME = "sleepytime";
	public static final String DIRECTIVE_STATS = "stats";
	public static final String DIRECTIVE_CANCEL = "cancel";
	public static final String DIRECTIVE_SETTINGS = "settings";
	
	private static final int COLOR_BTN_UP = MathUtils.getRgb(20, 40, 60);
	private static final int COLOR_BTN_DOWN = Colorz.WHITE;
	
	protected UILayoutManagerCenteredFitBox layoutMain;
	protected UILayoutManagerHtmlPositioned layoutHtml;
	protected HtmlPositioner posStats, posCancel, posSettings;

	public GameMain(GameContext gameContext)
	{
		super(gameContext);
		
		// TODO: get this from options
		String[] buttons = new String[] {
			"quick",
			"8:30",
			"9:00",
		};
		
		ContextBitmap bmpBtn = ImageManager.loadR("cloud_big_800.png");
		ContextBitmap bmpBtn_down = ImageManager.loadR("cloud_big_down_800.png");
		ContextBitmap bmpBtn_stats = ImageManager.loadR("cloud_stats_240.png");
		ContextBitmap bmpBtn_cancel = ImageManager.loadR("cloud_cancel_240.png");
		ContextBitmap bmpBtn_settings = ImageManager.loadR("cloud_settings_240.png");
		ContextBitmap bmpBtn_downSm = ImageManager.loadR("cloud_small_down_200.png");
		
		layoutMain = new UILayoutManagerCenteredFitBox(0.15f, 0.05f, 2, 0.05f)
			.setRect(0.05f, 0.25f, 0.05f, 0.05f);
		layoutHtml = new UILayoutManagerHtmlPositioned();
		
		for (final String bbb : buttons) {
			UIElementBase btn;
			uiActions.addUI(btn = new UIImageButton(bmpBtn, bmpBtn_down, bbb)
				.setTextColors(COLOR_BTN_UP, COLOR_BTN_DOWN)
				.setLambda(new IFuncVoid<UIButton>() {
					@Override
					public void lambda(UIButton value) {
						sendEventToContext(DIRECTIVE_SLEEPTIME, bbb);
					}
				}));
			layerButtons.addRenderable(btn);
			layoutMain.addUI(btn);
		}
		
		final int initSize = 120;
		
		addUIToActionsAndLayerHtmlPositioned(new UIImageButton(bmpBtn_stats, bmpBtn_downSm)
			.setLambda(new IFuncVoid<UIButton>() {
				@Override
				public void lambda(UIButton value) {
					sendEventToContext(DIRECTIVE_STATS);
				}
			}), layerButtons, uiActions, layoutHtml, posStats = new HtmlPositioner(initSize, initSize)
				.setBottom(Constants.Values.BOTTOM_MARGIN_PX)
				.setLeft(Constants.Values.BOTTOM_MARGIN_PX));
		addUIToActionsAndLayerHtmlPositioned(new UIImageButton(bmpBtn_cancel, bmpBtn_downSm)
			.setLambda(new IFuncVoid<UIButton>() {
				@Override
				public void lambda(UIButton value) {
					sendEventToContext(DIRECTIVE_CANCEL);
				}
			}), layerButtons, uiActions, layoutHtml, posCancel = new HtmlPositioner(initSize, initSize)
				.setBottom(Constants.Values.BOTTOM_MARGIN_PX)
				.centerX());
		addUIToActionsAndLayerHtmlPositioned(new UIImageButton(bmpBtn_settings, bmpBtn_downSm)
			.setLambda(new IFuncVoid<UIButton>() {
				@Override
				public void lambda(UIButton value) {
					sendEventToContext(DIRECTIVE_SETTINGS);
				}
			}), layerButtons, uiActions, layoutHtml, posSettings = new HtmlPositioner(initSize, initSize)
				.setBottom(Constants.Values.BOTTOM_MARGIN_PX)
				.setRight(Constants.Values.BOTTOM_MARGIN_PX));
	}
	
	@Override
	public void dimensionsChanged(int width, int height)
	{
		int www = GameDimensions.getBottomButtonWidth(width, height);
		posStats.setObjectWidth(www);
		posStats.setObjectHeight(www);
		posCancel.setObjectWidth(www);
		posCancel.setObjectHeight(www);
		posSettings.setObjectWidth(www);
		posSettings.setObjectHeight(www);
		layoutMain.rearrange(width, height);
		layoutHtml.rearrange(width, height);
	}
	
	@Override
	public void paintCharacters(ContextGraphics g, int width, int height)
	{
		super.paintCharacters(g, width, height);
		//layoutMain.debug_drawGrid(g, width, height);
	}
	
}
