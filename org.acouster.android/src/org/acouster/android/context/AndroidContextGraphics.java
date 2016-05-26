package org.acouster.android.context;

import org.acouster.android.RectFRecyclable;
import org.acouster.android.RectRecyclable;
import org.acouster.context.ContextBitmap;
import org.acouster.context.ContextBitmapFragment;
import org.acouster.util.MathUtils;
import org.acouster.util.StringUtils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Shader;

public class AndroidContextGraphics extends org.acouster.context.ContextGraphics
{
	private Canvas canvas;
	private Matrix mMatrixflip;
	private Paint paint, paintBmpAntialias;
	private RectRecyclable rectRecycledS, rectRecycledD;
	private RectFRecyclable rectFRecycled;
	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
		mMatrixflip = new Matrix();
		rectFRecycled = new RectFRecyclable();
		rectRecycledS = new RectRecyclable();
		rectRecycledD = new RectRecyclable();
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		resetPaint();
		paintBmpAntialias = new Paint(Paint.FILTER_BITMAP_FLAG);
//		paintBmpAntialias.setAntiAlias(true);
//		paintBmpAntialias.setFilterBitmap(true);
//		paintBmpAntialias.setDither(true);
	}
	
	// publics
	public Canvas getCanvas()
	{
		return canvas;
	}
	
	// privates
	private void resetPaint()
	{
		paint.reset();
		paint.setStyle(Paint.Style.STROKE);
	}
	
	//ContextGraphics members
	@Override
	public void translate(int x, int y) {
		canvas.translate(x, y);
	}

	@Override
	public void rotate(double a) {
		canvas.rotate((float)MathUtils.angleToDegrees(a));
	}
	
	@Override
	public void resetTransform() {
		canvas.setMatrix(null);
	}

	@Override
	public void setColor(int r, int g, int b) {
		resetPaint();
		paint.setColor(Color.rgb(r, g, b));
	}

	@Override
	public void setColor(int rgb) {
		resetPaint();
		paint.setColor(0xff000000 | rgb);
		paint.setStyle(Paint.Style.STROKE);
	}
	
	@Override
	public void setStroke(int rgb, int width) {
		setColor(rgb);
		paint.setStrokeWidth(width);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeCap(Paint.Cap.ROUND);
	}
	
	@Override
	public void resetStroke() {
		resetPaint();
		paint.setStrokeWidth(1);
		paint.setStrokeCap(Paint.Cap.SQUARE);
	}
	
	@Override
	public void setFill(int r, int g, int b) {
		setFill(Color.rgb(r, g, b));
	}

	@Override
	public void setFill(int rgb) {
		paint.setShader(new LinearGradient(0, 0, 100, 100, 0xff000000 | rgb, 0xff000000 | rgb, Shader.TileMode.CLAMP));
		paint.setStyle(Style.FILL);
	}

	@Override
	public void setFillAlpha(double alpha) {
		paint.setAlpha((int)(255*alpha));
		paint.setStyle(Style.FILL);
	}
	
	@Override
	public void setFillAndStroke(int rgbStroke, int width, int rgbFill)
	{
		setStroke(rgbStroke, width);
		setFill(rgbFill);
		paint.setStyle(Style.FILL_AND_STROKE);
	}
	
	@Override
	public void fillBackground() {
		canvas.drawPaint(paint);
	}

	@Override
	public void drawLine(float x, float y, float x2, float y2) {
		canvas.drawLine(x, y, x2, y2, paint);
	}

	@Override
	public void drawRect(float x, float y, float width, float height) {
		canvas.drawRect(rectFRecycled.set(x, y, x + width, y + height), paint);
	}
	
	@Override
	public void drawRectRound(float x, float y, float width, float height, float borderRadiusX, float borderRadiusY) {
		canvas.drawRoundRect(rectFRecycled.set(x, y, x + width, y + height), borderRadiusX, borderRadiusY, paint);
	}
	
	@Override
	public void drawOval(float x, float y, float width, float height) {
		canvas.drawOval(rectFRecycled.set(x, y, x + width, y + height), paint);
	}

	@Override
	public void fillRect(float x, float y, float width, float height) {
		canvas.drawRect(rectFRecycled.set(x, y, x + width, y + height), paint);
	}
	
	@Override
	public void fillRectRound(float x, float y, float width, float height, float borderRadiusX, float borderRadiusY) {
		canvas.drawRoundRect(rectFRecycled.set(x, y, x + width, y + height), borderRadiusX, borderRadiusY, paint);
	}

	@Override
	public void fillOval(float x, float y, float width, float height) {
		canvas.drawOval(rectFRecycled.set(x, y, x + width, y + height), paint);
	}

	@Override
	public void drawString(String string, int x, int y) {
		canvas.drawText(string, x, y, paint);
	}
	
	@Override
	public int getStringWidth(String string, int fontSize) {
		if (StringUtils.isNullOrEmpty(string))
			return 0;
		paint.setTextSize(fontSize);
		float w = paint.measureText(string);
		return (int)w;
	}

	@Override
	public void drawImage(ContextBitmap bi, int x, int y, int width2b, int height2b)
	{
		if (bi.isFragment())
		{
			ContextBitmapFragment imgPiece = (ContextBitmapFragment)bi;
			AndroidContextBitmap abi = (AndroidContextBitmap)imgPiece.src;
			Bitmap bbb = abi.getBitmap();
			if (bbb != null)
				canvas.drawBitmap(bbb,
						rectRecycledS.set(imgPiece.srcX, imgPiece.srcY, imgPiece.srcX + imgPiece.srcW, imgPiece.srcY + imgPiece.srcH),
						rectRecycledD.set(x, y, x+width2b, y+height2b), imageAntialias ? paintBmpAntialias : null);
		}
		else
		{
			AndroidContextBitmap abi = (AndroidContextBitmap)bi;
			Bitmap bbb = abi.getBitmap();
			if (bbb != null)
				canvas.drawBitmap(bbb, rectRecycledS.set(0, 0, bbb.getWidth(), bbb.getHeight()), rectRecycledD.set(x, y, x+width2b, y+height2b), imageAntialias ? paintBmpAntialias : null);
			//setColor(0x0);
			//drawString(bbb.getWidth() + ":" + bbb.getHeight(), x, y);
		}
		
	}
	
	@Override
	public void drawArc(double x, double y, double rw, double rh, double a, double aSize) {
		canvas.drawArc(rectFRecycled.set((float)x, (float)y, (float)(x + rw), (float)(y + rh)), -(float)(180*a/Math.PI), -(float)(180*aSize/Math.PI), false, paint);
		canvas.drawArc(rectFRecycled.set((float)x, (float)y, (float)(x + rw), (float)(y + rh)), -(float)(180*a/Math.PI), -(float)(180*aSize/Math.PI), false, paint);
	}
	
	
	
	// --------- protected shit ----------------
	
	@Override
	protected void drawImage_flip(ContextBitmap ci, int x, int y, int width, int height, boolean flipX, boolean flipY)
	{
		float factorW = 1, factorH = 1, offsetX = 0, offsetY = 0;
		if (flipX)
		{
			factorW = -1;
			offsetX = width;
		}
		if (flipY)
		{
			factorH = -1;
			offsetY = height;
		}
		mMatrixflip.reset();
		mMatrixflip.setTranslate(x + offsetX, y + offsetY);
		mMatrixflip.preScale(factorW * width / ci.getWidth(), factorH * height / ci.getHeight());
		
		AndroidContextBitmap abi = (AndroidContextBitmap)ci;
		Bitmap bbb = abi.getBitmap();
		if (bbb != null)
			canvas.drawBitmap(bbb, mMatrixflip, paint);
	}


}
