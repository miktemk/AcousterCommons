package org.acouster.android.ui;

import org.acouster.Acouster;
import org.acouster.android.R;
import org.acouster.util.StringUtils;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;


/**
 * Options: minValue, maxValue, defaultValue, suffix, expression (overrides suffix)
 * template shortcodes: {num}, {s}, {es}, {yies}
 * */
public class SeekBarPreference extends Preference implements SeekBar.OnSeekBarChangeListener
{
	protected static final String TEMPLATE_NUM = "{num}";
	protected static final String TEMPLATE_PLURAL_S = "{s}";
	protected static final String TEMPLATE_PLURAL_ES = "{es}";
	protected static final String TEMPLATE_PLURAL_YIES = "{yies}";

	protected SeekBar mSeekBar;
	protected TextView mValueText;
	protected Context mContext;

	protected String mStringExp;
	protected int mDefault, baseMin, sliderMax, mValue = 0;

	public SeekBarPreference(Context context, AttributeSet attrs) { 
		super(context,attrs); 
		mContext = context;

		mStringExp = context.getResources().getString(attrs.getAttributeResourceValue(Acouster.ACOUSTER_NS, "expression", R.string.nullValue));
		if (StringUtils.isNullOrEmpty(mStringExp))
			mStringExp = TEMPLATE_NUM + context.getResources().getString(attrs.getAttributeResourceValue(Acouster.ACOUSTER_NS, "suffix", R.string.nullValue));
		mDefault = attrs.getAttributeIntValue(Acouster.ANDROID_NS, "defaultValue", 0);
		baseMin = attrs.getAttributeIntValue(Acouster.ACOUSTER_NS, "minValue", 0);
		sliderMax = attrs.getAttributeIntValue(Acouster.ACOUSTER_NS, "maxValue", 100);
		sliderMax = sliderMax - baseMin;
	}
	
	@Override
	protected View onCreateView(ViewGroup parent) {
		View pLayout = super.onCreateView(parent);
		
		LinearLayout layout = new LinearLayout(mContext);
		layout.setOrientation(LinearLayout.VERTICAL);
		
		layout.addView(pLayout);

		mValueText = new TextView(mContext);
		mValueText.setGravity(Gravity.CENTER_HORIZONTAL);
		mValueText.setTextSize(26);
		layout.addView(mValueText, Utils.getStandardLinearFitParams());

		mSeekBar = new SeekBar(mContext);
		int pad = mContext.getResources().getInteger(R.integer.standardPadding);
		int padSmall = mContext.getResources().getInteger(R.integer.standardPaddingSmall);
		mSeekBar.setPadding(pad, padSmall, pad, padSmall);
		mSeekBar.setOnSeekBarChangeListener(this);
		layout.addView(mSeekBar, Utils.getStandardLinearFitParams());

		if (shouldPersist())
			mValue = getPersistedInt(mDefault);
		//Log.v("========>", "SeekBarPreference.setProgress... mValue=" + mValue + " mDefault=" + mDefault);
		
		//mSeekBar.setMin(mMin);
		mSeekBar.setMax(sliderMax);
		mSeekBar.setProgress(mValue - baseMin);
		return layout;
	}
	@Override
	protected void onBindView(View view) {
		super.onBindView(view);
		mSeekBar.setMax(sliderMax);
		mSeekBar.setProgress(mValue - baseMin);
	}

	@Override
	protected void onSetInitialValue(boolean restore, Object defaultValue)	
	{
		super.onSetInitialValue(restore, defaultValue);
		if (restore) 
			mValue = shouldPersist() ? getPersistedInt(mDefault) : 0;
		else 
			mValue = (Integer)defaultValue;
	}

	public void onProgressChanged(SeekBar seek, int value, boolean fromTouch)
	{
		value += baseMin;
		mValueText.setText(getDescription(value));
		if (shouldPersist())
			persistInt(value);
		callChangeListener(Integer.valueOf(value));
	}
	public void onStartTrackingTouch(SeekBar seek) {}
	public void onStopTrackingTouch(SeekBar seek) {}

	public void setMax(int max) { sliderMax = max; }
	public int getMax() { return sliderMax; }

	public void setProgress(int progress) { 
		mValue = progress + baseMin;
		if (mSeekBar != null)
			mSeekBar.setProgress(progress); 
	}
	public int getProgress() { return mValue; }
	
	// override to have fun
	protected String getDescription(int value)
	{
		String t = String.valueOf(value);
		if (StringUtils.isNullOrEmpty(mStringExp))
			return t;
		String result = mStringExp.replace(TEMPLATE_NUM, t);
		result = result.replace(TEMPLATE_PLURAL_S, (value == 1) ? "" : "s");
		result = result.replace(TEMPLATE_PLURAL_ES, (value == 1) ? "" : "es");
		result = result.replace(TEMPLATE_PLURAL_YIES, (value == 1) ? "y" : "ies");
		return result;
	}
}

