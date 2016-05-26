package org.acouster.data.GraphImage;

import org.acouster.Graph;

/**
 * Used to construct graph/sequences of images. See just typing for a nice demo
 */
public class ImageCommand implements Graph.IGraphCommand<ImageNode>, ImageNode
{
	// IGraphCommand items
	protected int type;
	protected double delaySeconds;
	protected String id;
	
	// IGraphCommand items
	protected String query;
	protected boolean flipX, flipY;
	
	
	public ImageCommand() {
		this(Graph.IGraphCommand.COMMAND_TYPE_NODE, 0, null, "", false, false);
	}
	public ImageCommand(String query, double delay) {
		this(Graph.IGraphCommand.COMMAND_TYPE_NODE, delay, null, query, false, false);
	}
	public ImageCommand(String query, double delay, String id) {
		this(Graph.IGraphCommand.COMMAND_TYPE_NODE, delay, id, query, false, false);
	}
	public ImageCommand(String query, double delay, boolean flipX, boolean flipY) {
		this(Graph.IGraphCommand.COMMAND_TYPE_NODE, delay, null, query, flipX, flipY);
	}
	public ImageCommand(int type, double delaySeconds, String id, String query,
			boolean flipX, boolean flipY) {
		super();
		this.type = type;
		this.delaySeconds = delaySeconds;
		this.id = id;
		this.query = query;
		this.flipX = flipX;
		this.flipY = flipY;
	}
	
	//---------- ImageNode members
	@Override
	public boolean isFlipX() {
		return flipX;
	}
	@Override
	public boolean isFlipY() {
		return flipY;
	}
	
	//-------- IGraphCommand members
	@Override
	public int getType() {
		return type;
	}
	@Override
	public double getDelaySeconds() {
		return delaySeconds;
	}
	@Override
	public String getId() {
		return id;
	}
	@Override
	public ImageNode getNodeObject() {
		return this;
	}
	@Override
	public String getQuery() {
		return query;
	}
}
