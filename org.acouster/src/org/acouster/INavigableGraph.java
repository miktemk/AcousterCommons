package org.acouster;

public interface INavigableGraph<T> extends INodeGetter<T>
{
	void gotoNode(String nodeKey);
	void gotoFirstNode();
	void advance();
	double getCurrentDelay();
	String getCurrentNodeKey();
}
