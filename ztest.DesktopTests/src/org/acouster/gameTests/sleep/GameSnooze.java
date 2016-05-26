package org.acouster.gameTests.sleep;

import org.acouster.GameContext;
import org.acouster.IFuncVoid;
import org.acouster.context.ContextBitmap;
import org.acouster.context.ImageManager;
import org.acouster.graphics.WorldLayer;
import org.acouster.graphics.ui.UIActionEngine;
import org.acouster.graphics.ui.UIButton;
import org.acouster.graphics.ui.UIElementBase;
import org.acouster.graphics.ui.UIImageButton;
import org.acouster.graphics.ui.UILayoutManagerCentered;
import org.acouster.graphics.ui.UILayoutManagerCentered2ColumnLandscape;

public class GameSnooze extends GameBaseClass
{
	public static final String DIRECTIVE_SLEEPTIME = "sleepytime";
	public static final String BTN_SNOOZE = "snoozebtn";

	protected UILayoutManagerCentered2ColumnLandscape layoutMain;
	
	public GameSnooze(GameContext gameContext)
	{
		super(gameContext);
		
		ContextBitmap bmpBtn = ImageManager.loadR("cloud_big_800.png");
		ContextBitmap bmpBtn_down = ImageManager.loadR("cloud_big_down_800.png");
		
		layoutMain = new UILayoutManagerCentered2ColumnLandscape(0.15f, 0.05f, 0.15f);
		
		addUIToActionsAndLayerAndLayout(new UIImageButton(bmpBtn, bmpBtn_down, "I'm up!").setLambda(new IFuncVoid<UIButton>() {
			@Override
			public void lambda(UIButton value) {
				sendEventToContext(BTN_SNOOZE);
			}
		}), layerButtons, uiActions);
	}
	
	private void addUIToActionsAndLayerAndLayout(
			UIElementBase elem,
			WorldLayer layer,
			UIActionEngine uiActions) {
		addUIToActionsAndLayer(elem, layer, uiActions);
		layoutMain.addUI(elem);
	}

	@Override
	public void dimensionsChanged(int width, int height)
	{
		layoutMain.rearrange(width, height);
	}
	
}
