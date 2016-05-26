package org.acouster.data.GraphLogic;

import org.acouster.GameEvent;

public interface IFsmOutputListener
{
	void handleGameEvent(GameEvent event);
}
