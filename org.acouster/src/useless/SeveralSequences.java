package useless;
//package org.acouster.game2D.graphics;
//
//import java.util.HashMap;
//
//import org.acouster.context.ContextBitmap;
//import org.acouster.context.ContextGraphics;
//import org.acouster.game2D.logic.Sprite2D;
//import org.acouster.game2D.math.Transform2D;
//import org.acouster.graphics.ImageSequence;
//import org.acouster.graphics.ImageSequenceTicker;
//import org.acouster.graphics.RenderableObject;
//
//
//public class SeveralSequences extends RenderableObject
//{
//	protected ImageSequenceTicker curSequence;
//	protected HashMap<String, ImageSequenceTicker> sequences;
//
//	public SeveralSequences(Sprite2D sprite)
//	{
//		super(sprite);
//		sequences = new HashMap<String, ImageSequenceTicker>();
//	}
//	
//	public void addImageSequence(String key, ImageSequence sequence, double frameRate, boolean loop)
//	{
//		sequences.put(key, new ImageSequenceTicker(sequence, frameRate, loop));
//	}
//	public ImageSequenceTicker setCurSequence(String key)
//	{
//		curSequence = sequences.get(key);
//		return curSequence;
//	}
//	public ImageSequenceTicker getCurSequence()
//	{
//		return curSequence;
//	}
//	
//	@Override
//	public void render(ContextGraphics g)
//	{
//		if (curSequence == null)
//			return;
//		
//		ContextBitmap ci = curSequence.getImage();
//		
//		g.drawImage(ci, (int)sprite.getTransform().getX(), (int)sprite.getTransform().getY(), ci.getWidth(), ci.getHeight());
//	}
//
//	@Override
//	public void increment() {
//		setCurSequence(sprite.getCurrentAnimation());
//		curSequence.increment();
//	}
//	
//
//}
