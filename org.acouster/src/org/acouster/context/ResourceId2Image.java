package org.acouster.context;

import org.acouster.IFunc;

public class ResourceId2Image implements IFunc<String, ContextBitmap> {

	@Override
	public ContextBitmap lambda(String value) {
		return ResourceContext.instance().LoadBitmap(value, false);
	}

}
