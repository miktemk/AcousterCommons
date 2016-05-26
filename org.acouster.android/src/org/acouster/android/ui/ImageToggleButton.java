package org.acouster.android.ui;

import org.acouster.Acouster;
import org.acouster.IFuncVoid;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class ImageToggleButton extends ImageView implements OnTouchListener, OnClickListener
{
	protected boolean isDown = false, isOver = false;
	protected boolean isToggle;
	protected int img, imgOver, imgDown, imgDownOver;
	private IFuncVoid<Boolean> lambda;
	
	public ImageToggleButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		isToggle = attrs.getAttributeBooleanValue(Acouster.ACOUSTER_NS, "isToggle", false);
		
		img = attrs.getAttributeResourceValue(Acouster.ANDROID_NS, "src", 0);
		imgOver = attrs.getAttributeResourceValue(Acouster.ACOUSTER_NS, "srcOver", 0);
		imgDown = attrs.getAttributeResourceValue(Acouster.ACOUSTER_NS, "srcDown", 0);
		imgDownOver = attrs.getAttributeResourceValue(Acouster.ACOUSTER_NS, "srcDownOver", 0);

		setOnTouchListener(this);
		setOnClickListener(this);
	}

	@Override
	public boolean onTouch(View v, MotionEvent e) {
		final int action = e.getAction();
	    switch (action) {
		    case MotionEvent.ACTION_DOWN:
		    	isOver = true;
		        break;
		    case MotionEvent.ACTION_MOVE:
		        break;
		    case MotionEvent.ACTION_UP:
		    	isOver = false;
		        break;
	    }
	    updateUI();
		return false;
	}

	@Override
	public void onClick(View v) {
		if (isToggle) {
			isDown = !isDown;
		}
		updateUI();
		if (lambda != null)
			lambda.lambda(isDown);
	}
	
	private void updateUI() {
		if (isDown)
	    	setImageResource(isOver ? imgDownOver : imgDown);
	    else
	    	setImageResource(isOver ? imgOver : img);
	}
	
	public void setOnToggleListener(IFuncVoid<Boolean> lambda) {
		this.lambda = lambda;
	}
	
	public boolean isDown() {
		return isDown;
	}
	
}
