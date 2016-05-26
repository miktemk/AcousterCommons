package org.acouster.res;

import org.acouster.Graph;
import org.acouster.context.ContextBitmap;
import org.acouster.context.ContextBitmapFragment;
import org.acouster.context.ImageManager;
import org.acouster.util.MathUtils;

public class ComicStrip
{
	protected ContextBitmap[] frames;
	protected int rows, cols;
	
	public ComicStrip(ContextBitmap bmp, int rows, int cols, int nFrames)
	{
		this.rows = rows;
		this.cols = cols;
		int unitW = bmp.getWidth() / cols;
		int unitH = bmp.getHeight() / rows;
		frames = new ContextBitmap[nFrames];
		for (int r = 0; r < rows; r++)
		{
			for (int c = 0; c < cols; c++)
			{
				int x = c * unitW;
				int y = r * unitH;
				int i = r * cols + c;
				frames[i] = new ContextBitmapFragment(bmp, x, y, unitW, unitH);
			}
		}
	}
	
	public ContextBitmap[] getFrames()
	{
		return frames;
	}
	public int getNFrames() {
		return frames.length;
	}

	public ContextBitmap getFrame(int id) { return getFrame(id, true); }
	public ContextBitmap getFrame(int id, boolean loop)
	{
		if (id < 0)
			return null;
		if (!loop && id >= frames.length)
			return null;
		return frames[id % frames.length];
	}

	/** use graph.gotoFirstNode(); if you don't want to start at random frame */
	public Graph<ContextBitmap> toSingleChainCircularGraphGotoRandomFrame(double delay)
	{
		Graph<ContextBitmap> graph = new Graph<ContextBitmap>();
		for (int i = 0; i < frames.length; i++)
		{
			//DebugUtil.sss(filename);
			graph.addNode(frames[i], delay, "" + i);
		}
		graph.linkToFirstNode();
		graph.gotoNode("" + MathUtils.random(frames.length));
		return graph;
	}

}
