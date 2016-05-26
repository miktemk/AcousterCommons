package tmp;

import org.acouster.Acouster;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class SelectPicturePreference extends Preference implements OnItemSelectedListener
{
	private GridView grid;
	private Context mContext;
	
	private int mDefault, mValue = 0;

	public SelectPicturePreference(Context context, AttributeSet attrs) { 
		super(context,attrs); 
		mContext = context;

		//options = attrs.getAttributeValue(acousterns,"options");
		mDefault = attrs.getAttributeIntValue(Acouster.ANDROID_NS,"selectedIndex", 0);
	}
	
	@Override
	protected View onCreateView(ViewGroup parent) {
		View pLayout = super.onCreateView(parent);
		
		grid = new GridView(mContext);
		grid.setOnItemSelectedListener(this);
		ImageAdapter adapter = new ImageAdapter(mContext);
		initImageAdapter(adapter);
		grid.setAdapter(adapter);
		
		LinearLayout layout = new LinearLayout(mContext);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.addView(pLayout);

		TextView mValueText = new TextView(mContext);
		mValueText.setGravity(Gravity.CENTER_HORIZONTAL);
		mValueText.setTextSize(26);
		mValueText.setText("yohohoho");
		layout.addView(mValueText, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

		layout.addView(grid, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

		if (shouldPersist())
			mValue = getPersistedInt(mDefault);

		grid.setSelection(mValue);
		
		return layout;
	}
	@Override
	protected void onBindView(View view) {
		super.onBindView(view);
		grid.setSelection(mValue);
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
	
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
		if (shouldPersist())
			persistInt(position);
		callChangeListener(Integer.valueOf(position));
	}
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {}

//	public void onProgressChanged(SeekBar seek, int value, boolean fromTouch)
//	{
//		mValueText.setText(getDescription(value));
//		if (shouldPersist())
//			persistInt(value);
//		callChangeListener(new Integer(value));
//	}
//	public void onStartTrackingTouch(SeekBar seek) {}
//	public void onStopTrackingTouch(SeekBar seek) {}
//	public void setMax(int max) { mMax = max; }
//	public int getMax() { return mMax; }
//	public void setProgress(int progress) { 
//		mValue = progress;
//		if (mSeekBar != null)
//			mSeekBar.setProgress(progress); 
//	}
//	public int getProgress() { return mValue; }
	
	/** override this to add images */
	protected void initImageAdapter(ImageAdapter addImagesToMe) {}

}

