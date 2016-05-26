package org.acouster.data.GraphImage;

import org.acouster.Graph;

/** shorthand for link-to type graph command */
public class ImageCommandLinkTo extends ImageCommand
{	
	public ImageCommandLinkTo(String id) {
		super(Graph.IGraphCommand.COMMAND_TYPE_LINKTO, 0, id, "", false, false);
	}
}
