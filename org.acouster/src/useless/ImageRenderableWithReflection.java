package useless;
//package org.acouster.graphics;
//
//import org.acouster.INavigableGraph;
//import org.acouster.context.ContextBitmap;
//import org.acouster.context.ContextGraphics;
//import org.acouster.logic.Sprite2D;
//
//public class ImageRenderableWithReflection extends ImageRenderable {
//
//	public ImageRenderableWithReflection(Sprite2D trans,
//			INavigableGraph<BitmapWithTransform> images) {
//		super(trans, images);
//	}
//	
//	@Override
//	public void render(ContextGraphics g) {
//		BitmapWithTransform bmp = ticker.getCurrent();
//		if (bmp == null)
//			return;
//		ContextBitmap ci = bmp.getBitmap();
//		if (ci != null)
//		{
//			g.drawImage(ci, (int)sprite.getTransform().getX(), (int)sprite.getTransform().getY(), ci.getWidth(), ci.getHeight(), bmp.isFlipX(), bmp.isFlipY());
//			
//			// TODO: leave this for iteration 2
//			//g.drawImage(ci, (int)sprite.getTransform().getX(), (int)sprite.getTransform().getY()+ci.getHeight(), ci.getWidth(), ci.getHeight(), bmp.isFlipX(), !bmp.isFlipY());
//			//g.setFill(0x88886600);
//			//g.fillRect((int)sprite.getTransform().getX(), (int)sprite.getTransform().getY()+ci.getHeight(), ci.getWidth(), ci.getHeight());
//		}
//	}
//
//}
