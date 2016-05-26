package org.acouster.graphics.ui;

import org.acouster.IFuncVoid;
import org.acouster.context.ContextGraphics;
import org.acouster.context.ContextMouseEvent;
import org.acouster.graphics.anim.BubblePopVisual;

public class UIBubblyButton extends UIButton
{
	protected BubblePopVisual visual;
	protected boolean resetButtonUponDone = true, doLambdaOnPopBegin = false;
	
	public UIBubblyButton(BubblePopVisual visual)
	{
		super();
		this.visual = visual;
		setPreferredDimensions(visual.getImageWidth(), visual.getImageHeight());
	}
	
	@Override
	public org.acouster.graphics.ui.UIElementBase setFrame(int x, int y, int width, int height) 
	{
		super.setFrame(x, y, width, height);
		visual.getSprite().getTransform().set(x, y);
		visual.setWidthKeepAspectRatio(width);
		return this;
	}
	public UIBubblyButton setResetButtonUponDone(boolean flag)
	{
		resetButtonUponDone = flag;
		return this;
	}
	/**
	 * See setLambda
	 * @flag - 
	 * <ul>
	 *   <li>true - lambdarrr called on setFuncOnPopBegin (CHAIN_POP_POPPING)</li>
	 *   <li>false - lambdarrr called on setFuncOnFinished</li>
	 * </ul>
	 */
	public UIBubblyButton doLambdaOnPopBegin(boolean flag)
	{
		doLambdaOnPopBegin = flag;
		return this;
	}
	
	/**
	 * Set callbacks for user interaction.
	 * @param lambdarrr - called on CHAIN_POP_POPPING state of <code>visual</code>
	 * <ul>
	 *   <li>if doLambdaOnPopBegin is true - called on setFuncOnPopBegin (CHAIN_POP_POPPING)</li>
	 *   <li>if doLambdaOnPopBegin is false - called on setFuncOnFinished</li>
	 * </ul>
	 * @param onClick - sets UIButton.lambda property. Called on mouseReleased. Used to disable Ui Actions
	 */
	public UIBubblyButton setLambda(final IFuncVoid<UIButton> lambdarrr, final IFuncVoid<UIButton> onClick)
	{
		if (onClick != null)
			super.setLambda(onClick); // super.lambda = onClick
		visual.setFuncOnPopBegin(new IFuncVoid<BubblePopVisual>() {
			@Override
			public void lambda(BubblePopVisual value) {
				if (doLambdaOnPopBegin)
					lambdarrr.lambda(UIBubblyButton.this);
			}
		});
		visual.setFuncOnFinished(new IFuncVoid<BubblePopVisual>() {
			@Override
			public void lambda(BubblePopVisual value) {
				if (!doLambdaOnPopBegin)
					lambdarrr.lambda(UIBubblyButton.this);
				if (resetButtonUponDone)
					visual.reset();
			}
		});
		return this;
	}
	
	@Override
	public UIBubblyButton setLambda(final IFuncVoid<UIButton> lambdarrr) {
		setLambda(lambdarrr, null);
		return this;
	}

	@Override
	public void render(ContextGraphics g, int w, int h)
	{
		// TODO: render is done outside... visual is already in the WorldLayer.
		// This has to do with refactor: kill inheritance of RenderableObject in UIElementBase
		//visual.render(g, w, h);
	}
	
	@Override
	public void mousePressed(ContextMouseEvent e)
	{
		super.mousePressed(e);
		visual.pop(BubblePopVisual.POP_ACTION_FILL, BubblePopVisual.POP_MODE_WHATEVER, BubblePopVisual.RAIN_MODE_RANDOM_STEADY);
	}
	
	@Override
	public void mouseDragged(ContextMouseEvent e)
	{
		super.mouseDragged(e);
		if (mouseLeft)
			visual.reset();
		if (mouseReentered)
			visual.pop(BubblePopVisual.POP_ACTION_FILL, BubblePopVisual.POP_MODE_WHATEVER, BubblePopVisual.RAIN_MODE_RANDOM_STEADY);
	}
	
	@Override
	public void mouseReleased(ContextMouseEvent e)
	{
		//super.mousePressed(e);
		if (isDown)
		{
			// set in setLambda of this class, 2nd parameter
			if (lambda != null)
				lambda.lambda(this);
			visual.pop(BubblePopVisual.POP_ACTION_POP_INSTANT, BubblePopVisual.POP_MODE_RANDOM, BubblePopVisual.RAIN_MODE_WHATEVER);
		}
	}

}
