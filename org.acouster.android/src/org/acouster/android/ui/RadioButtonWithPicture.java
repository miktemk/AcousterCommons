package org.acouster.android.ui;

import org.acouster.android.RectRecyclable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.RadioButton;

public class RadioButtonWithPicture extends RadioButton {

	private Drawable drawable;
	private Bitmap bitmap;
	private RectRecyclable rectS, rectD;
	private Paint recycledPaint;
	
	public RadioButtonWithPicture(Context context, String text, int id, Drawable drawable)
	{
		super(context);
		setId(id);
		setText(text);
		this.drawable = drawable;
		bitmap = ((BitmapDrawable) drawable).getBitmap();
		rectS = new RectRecyclable();
		rectD = new RectRecyclable();
		recycledPaint = new Paint();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int heightResult = getHeight();
		int widthResult = heightResult * bitmap.getWidth() / bitmap.getHeight();
		canvas.drawBitmap(bitmap, rectS.set(0, 0, bitmap.getWidth(), bitmap.getHeight()), rectD.set(getWidth()-widthResult, 0, getWidth(), heightResult), recycledPaint);
		//canvas.drawBitmap(bitmap, rectS.set(0, 0, bitmap.getWidth(), bitmap.getHeight()), rectD.set(20, 0, 120, 100), recycledPaint);
	}
}
