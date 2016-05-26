package org.acouster.xml.GraphImage;

import org.acouster.Graph;
import org.acouster.data.GraphImage.ImageCommand;
import org.acouster.data.GraphImage.ImageNode;
import org.simpleframework.xml.Attribute;

public class XmlImageCommand extends ImageCommand
{
	private static final String STRCOMMAND_TYPE_NODE = "node";
	private static final String STRCOMMAND_TYPE_KEYNODE = "keynode";
	private static final String STRCOMMAND_TYPE_LINKTO = "linkto";
	private static final String STRCOMMAND_TYPE_BREAK = "break";
	
	@Attribute
	private String type;
	@Attribute(required=false, name="delay")
	private double delaySeconds;
	@Attribute(required=false)
	private String id;
	
	@Attribute(required=false)
	private String query;
	@Attribute(required=false)
	private boolean flipX, flipY;

	
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
		if (STRCOMMAND_TYPE_NODE.equals(type))
			return Graph.IGraphCommand.COMMAND_TYPE_NODE;
		if (STRCOMMAND_TYPE_KEYNODE.equals(type))
			return Graph.IGraphCommand.COMMAND_TYPE_KEYNODE;
		if (STRCOMMAND_TYPE_LINKTO.equals(type))
			return Graph.IGraphCommand.COMMAND_TYPE_LINKTO;
		if (STRCOMMAND_TYPE_BREAK.equals(type))
			return Graph.IGraphCommand.COMMAND_TYPE_BREAK;
		return 0;
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
