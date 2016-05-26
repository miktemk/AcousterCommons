package org.acouster.android.ui;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.ViewDebug.CapturedViewProperty;
import android.widget.RadioButton;

public class RadioButtonWithSummary extends RadioButton {
	// protected RadioButton radiooo;
	// protected Button btnSelect;
	// protected TextView summary;

	private int textSize;
	private ColorStateList textColor;
	private Paint paint;
	private String summary;

	public RadioButtonWithSummary(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = getContext().getTheme()
				.obtainStyledAttributes(
						attrs,
						new int[] { android.R.attr.textSize,
								android.R.attr.textColor },
						android.R.attr.textAppearanceSmall, 0);
		textSize = a.getDimensionPixelSize(0, 15);
		textColor = a.getColorStateList(1);
		paint = new Paint(getPaint());
		paint.setTextSize(textSize);
		a.recycle();

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (summary != null && summary.length() > 0) {
			int horizontalStartPosition = getCompoundPaddingLeft()
					+ getCompoundDrawablePadding();
			int verticalStartPosition = getBaseline() + getLineHeight();

			paint.setColor(textColor.getColorForState(getDrawableState(), 0));
			canvas.drawText((String) summary, horizontalStartPosition,
					verticalStartPosition, paint);
		}
	}

	// public RadioButtonWithSummary setSummary(String summary) {
	// this.summary = summary;
	// return this;
	// }

	// Upon setSummary add an additional new-line to the text.
	// This is a bit hacky but I couldn't figure out a better way to make the
	// super class to position the text correctly.

	public void setSummary(String summary) {
		if (summary != null && summary.length() > 0) {
			setText(getText() + "\n");
		} else {
			setText(getText());
		}
		if (summary == null && this.summary != null || summary != null
				&& !summary.equals(this.summary)) {
			this.summary = summary;
		}
	}

	// Thus, we need to override the getText as well and remove the new-line
	// when the summary is present.

	@Override
	@CapturedViewProperty
	public CharSequence getText() {
		CharSequence text = super.getText();
		if (summary != null && summary.length() > 0) {
			text = text.subSequence(0, text.length() - 1);
		}
		return text;
	}

	// public RadioButtonWithSummary setButtonText(String txt) {
	// btnSelect.setText(txt);
	// return this;
	// }
	//
	// public RadioButtonWithSummary setButtonOnClickListener(
	// OnClickListener listener) {
	// btnSelect.setOnClickListener(listener);
	// return this;
	// }

	public void setPaddingBottom(int bottom) {
		setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), bottom);
	}
}
