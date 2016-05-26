package org.acouster.data.GraphLogic;

import org.acouster.GameEvent;

public interface IFsmRoot
{
	void sendGameEvent(GameEvent event);
	void gotoNode(String destination);
}
