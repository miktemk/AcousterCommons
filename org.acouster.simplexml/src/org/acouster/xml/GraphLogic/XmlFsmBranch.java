package org.acouster.xml.GraphLogic;

import java.util.List;
import java.util.Vector;

import org.acouster.GameEvent;
import org.acouster.data.GraphLogic.FsmBranch;
import org.acouster.util.ListUtils;
import org.acouster.xml.common.XmlGameEvent;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;

public class XmlFsmBranch extends FsmBranch
{
	@Attribute
	private String func;
	@Attribute
	private double value;
	@Attribute
	private String destination;
	@ElementList(required=false, inline=true, entry="event")
	private List<XmlGameEvent> xmlEvents;
	private List<GameEvent> myEvents;
	
	// event shortcut....
	@Attribute
	private String output;
	
	public XmlFsmBranch() {
		this("", 0, null, "", "");
	}
	public XmlFsmBranch(String func, double value, List<XmlGameEvent> xmlEvents, String output, String destination) {
		super();
		if (xmlEvents == null)
		{
			xmlEvents = new Vector<XmlGameEvent>();
			xmlEvents.add(new XmlGameEvent(GameEvent.EVENT_TARGET_visual, output));
		}
		this.func = func;
		this.value = value;
		this.xmlEvents = xmlEvents;
		this.output = output;
		this.destination = destination;
	}
	
	@Override
	public String getFunc() {
		return func;
	}
	@Override
	public double getValue() {
		return value;
	}
	@Override
	public List<GameEvent> getEvents() {
		if (myEvents == null)
		{
			if (xmlEvents.size() == 0)
				xmlEvents.add(new XmlGameEvent());
			myEvents = ListUtils.select(xmlEvents, XmlGameEvent.toGameEventFuncInstance());
		}
		return myEvents;
	}
	@Override
	public String getDestination() {
		return destination;
	}
	
}
