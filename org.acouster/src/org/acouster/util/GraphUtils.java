package org.acouster.util;

import java.util.List;

import org.acouster.Graph;
import org.acouster.IFunc;

public class GraphUtils
{
	public static <R> Graph<R> fromCommandList(List<? extends Graph.IGraphCommand<R>> commands)
	{
		return fromCommandList(commands, new IFunc.SameThing<R>());
	}
	public static <T,R> Graph<R> fromCommandList(List<? extends Graph.IGraphCommand<T>> commands, IFunc<T, R> func)
	{
		Graph<R> graph = new Graph<R>();
		
		for (Graph.IGraphCommand<T> command : commands)
		{
			switch (command.getType())
			{
			case Graph.IGraphCommand.COMMAND_TYPE_KEYNODE:
				graph.addNode(
					func.lambda(command.getNodeObject()),
					command.getDelaySeconds(),
					command.getId(),
					true);
				break;
			case Graph.IGraphCommand.COMMAND_TYPE_NODE:
				graph.addNode(
					func.lambda(command.getNodeObject()),
					command.getDelaySeconds(),
					command.getId());
				break;
			case Graph.IGraphCommand.COMMAND_TYPE_LINKTO:
				graph.linkTo(command.getId());
				break;
			case Graph.IGraphCommand.COMMAND_TYPE_BREAK:
				graph.breakChain();
				break;
			}
		}
		
		//graph.gotoFirstNode();
		return graph;
	}
}
