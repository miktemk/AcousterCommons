package org.acouster.xml.GraphImage;

import java.util.List;

import org.acouster.data.GraphImage.ImageCommand;
import org.acouster.data.GraphImage.ImageGraph;
import org.simpleframework.xml.ElementList;

public class XmlImageGraph extends ImageGraph
{
	@ElementList(inline=true, entry="do")
	private List<XmlImageCommand> xmlCommands;
	
	@Override
	public List<? extends ImageCommand> getCommands()
	{
		return xmlCommands;
	}
//	public List<? extends ImageNode> getNodes() {
//		return xmlNodes;
//	}
}
