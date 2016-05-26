package org.acouster.desktop;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import org.acouster.IFuncVoid;

/** Launches a thread saves the images as they are piped in */
public class ImagePipelineShitter
{
	private static class ImgNode
	{
		public BufferedImage img;
		public String filename;
		public ImgNode(BufferedImage img, String filename) {
			super();
			this.img = img;
			this.filename = filename;
		}
	}
	
	private LinkedList<ImgNode> queue;
	private IFuncVoid<Integer> queueSizeChanged = null;
	
	public void setQueueSizeChangedListener(IFuncVoid<Integer> queueSizeChanged) {
		this.queueSizeChanged = queueSizeChanged;
		queueSizeChanged.lambda(queue.size());
	}

	public ImagePipelineShitter()
	{
		queue = new LinkedList<ImagePipelineShitter.ImgNode>();
		final Thread ttt = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true)
		        {
					ImgNode node = null;
					synchronized(queue)
					{
						int saize;
						if ((saize = queue.size()) > 0) {
							node = queue.pop();
							queueSizeChanged.lambda(saize-1);
						}
					}
					if (node == null)
					{
						try {
							Thread.sleep(30);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					else
						try{ImageIO.write(node.img,"png",new File(node.filename));}catch (Exception e) {}
		        }
			}
		});
		ttt.start();
	}
	
	public void pushImage(BufferedImage img, String filename)
	{
		synchronized(queue)
		{
			queue.push(new ImgNode(img, filename));
		}
	}
}
