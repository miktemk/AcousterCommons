package org.acouster.xml.common;

import org.acouster.GameEvent;
import org.acouster.IFunc;
import org.simpleframework.xml.Attribute;

public class XmlGameEvent
{
	@Attribute
	public String target;
	@Attribute
	public String body;

	public XmlGameEvent() {
		this("", "");
	}
	public XmlGameEvent(String target, String body) {
		super();
		this.target = target;
		this.body = body;
	}
	
	
	//================ toGameEventFuncInstance ============
	private static IFunc<XmlGameEvent, GameEvent> toGameEventFunc;
	public static IFunc<XmlGameEvent, GameEvent> toGameEventFuncInstance() {
		if (toGameEventFunc == null)
			toGameEventFunc = new IFunc<XmlGameEvent, GameEvent>() {
				@Override
				public GameEvent lambda(XmlGameEvent value) {
					return new GameEvent(value.target, value.body);
				}};
		return toGameEventFunc;
	}
	
}
