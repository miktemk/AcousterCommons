package org.acouster.res;

import org.acouster.INodeGetter;
import org.acouster.context.ContextBitmap;
import org.acouster.context.ImageManager;

public class ImageSingle implements INodeGetter<ContextBitmap>
{
	private static final String TEMPLATE_NUM = "{num}";

	private ContextBitmap img;
	public ImageSingle(ContextBitmap img, boolean flipX, boolean flipY) {
		this.img = img;
	}
	/** TODO: use flipX/Y... aka template these as Bitmap with transform */
	public ImageSingle(String filename, boolean flipX, boolean flipY) {
		img = ImageManager.loadR(filename);
	}

	@Override
	public ContextBitmap getCurrentNode() {
		return img;
	}
	
}
