package org.acouster.android.ui;

import org.acouster.Acouster;
import org.acouster.android.R;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class RadioButtonPreference extends Preference
{
	protected RadioGroup radioGroup;
	protected Context mContext;
	
	private int mDefault, mValue = 0;

	public RadioButtonPreference(Context context, AttributeSet attrs) { 
		super(context,attrs); 
		mContext = context;

		//options = attrs.getAttributeValue(acousterns,"options");
		mDefault = attrs.getAttributeIntValue(Acouster.ANDROID_NS, "defaultValue", 0);
	}
	
	@Override
	protected View onCreateView(ViewGroup parent)
	{
		View pLayout = super.onCreateView(parent);
		
		radioGroup = new RadioGroup(mContext);
		radioGroup.setOrientation(LinearLayout.VERTICAL);
		initGridParameters(radioGroup);
		radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				//Log.v("========>", "onCheckedChanged  checkedId=" + checkedId);
				if (shouldPersist())
					persistInt(checkedId);
				callChangeListener(Integer.valueOf(checkedId));
			}
		});
		
		LinearLayout layout = new LinearLayout(mContext);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.addView(pLayout);
		
		int pad = mContext.getResources().getInteger(R.integer.standardPadding);
		//layout.setPadding(pad, 0, pad, 0);
//		MarginLayoutParams params = new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//		params.setMargins(pad, 0, pad, 0);
//		layout.setLayoutParams(params);
        radioGroup.setPadding(pad, 0, pad, 0);
        
		layout.addView(radioGroup, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

		if (shouldPersist())
			mValue = getPersistedInt(mDefault);

		//Log.v("========>", "radioGroup.check... mValue=" + mValue + " mDefault=" + mDefault);
		radioGroup.check(mValue);
		
		return layout;
	}

	@Override
	protected void onBindView(View view) {
		super.onBindView(view);
		//radioGroup.setSelection(mValue);
	}

	@Override
	protected void onSetInitialValue(boolean restore, Object defaultValue)	
	{
		super.onSetInitialValue(restore, defaultValue);
		if (restore) 
			mValue = shouldPersist() ? getPersistedInt(mDefault) : 0;
		else 
			mValue = (Integer)defaultValue;
		//radioGroup.check(mValue);
	}
	
	/** override this to add radio buttons */
	protected void initGridParameters(RadioGroup grid)
	{
//		addRadioButton("opt 1 = 10", 10);
//		addRadioButton("opt 2 = 12", 12);
//		addRadioButton("opt 3 = 14", 14);
	}

	protected RadioButton addRadioButton(RadioButton button) {
		return addRadioButton(button, Utils.getStandardLinearFitParams());
	}
	protected RadioButton addRadioButton(String text, int id) {
		RadioButton button = new RadioButton(mContext);
		button.setId(id);
		button.setText(text);
		return addRadioButton(button);
	}
	protected RadioButton addRadioButton(RadioButton button, LayoutParams params) {
		radioGroup.addView(button, params);
		return button;
	}
	
//	protected void addRadioButton(String text, int id, int drawableId) {
//		RadioButton button = new RadioButton(mContext);
//		button.setId(id);
//		button.setText(text);
//		int pad = mContext.getResources().getInteger(R.integer.standardPadding);
//		button.setPadding(pad, pad, pad, pad);
//		//button.setCompoundDrawablesWithIntrinsicBounds()
//		addRadioButton(button);
//	}
//	protected void addRadioButtonPicturesque(int drawableId, int id) {
//		RadioButton button = new RadioButton(mContext);
//		button.setId(id);
//		// TODO: set drawable for this babe
//		button.setText("TODO: drawableId = " + drawableId);
//		addRadioButton(button);
//	}
	
}

