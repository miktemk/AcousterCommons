package org.acouster.data.GraphLogic;

public interface IFsmNode
{
	int getVisitCount();
	int getNewVisitCount();
	void visit(boolean encore);
}
