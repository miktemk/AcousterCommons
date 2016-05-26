package org.acouster;

/**
 * gay event hehe
 * @author miktemk
 */
public class GameEvent
{
	// TODO: find out what was "output" used for
	//public static final String EVENT_TARGET_VIEW_OUTPUT = "output";
	public static final String EVENT_TARGET_CONTEXT = "context";
	public static final String EVENT_TARGET_game = "game";
	public static final String EVENT_TARGET_visual = "visual";
	public static final String DELIMETER_COLON = ":";
	
	protected String target;
	protected String body;
	public GameEvent() {
		super();
	}
	public GameEvent(String target, String body) {
		super();
		this.target = target;
		this.body = body;
	}
	public GameEvent(String target, String directive, String value) {
		super();
		this.target = target;
		this.body = directive + DELIMETER_COLON + value;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public void setBody(String directive, String value) {
		this.body = directive + DELIMETER_COLON + value;
	}
	public String getDirective() {
		return getDirective(DELIMETER_COLON);
	}
	public String getDirective(String delimeter)
	{
		String[] splits = body.split(delimeter, 2);
		return splits[0];
	}
	public String getLatterPart() {
		return getLatterPart(DELIMETER_COLON);
	}
	public String getLatterPart(String delimeter)
	{
		String[] splits = body.split(delimeter, 2);
		if (splits == null || splits.length < 2)
			return body;
		return splits[1];
	}
	public int getLatterPartInt() {
		return getLatterPartInt(DELIMETER_COLON);
	}
	public int getLatterPartInt(String delimeter)
	{
		String part = getLatterPart(delimeter);
		return Integer.parseInt(part);
	}
	public boolean isDirective(String prefix) {
		return body.startsWith(prefix + DELIMETER_COLON);
	}
	public boolean is(String thing) {
		return body.equals(thing);
	}
	
	@Override
	public String toString()
	{
		return target + ": " + body;
	}
	
}
