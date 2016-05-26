package org.acouster.desktop;

import org.acouster.DebugUtil;
import org.acouster.Game;
import org.acouster.GameContext;
import org.acouster.GameEvent;

public class BlankGameContext implements GameContext
{

	@Override
	public void setGame(Game g) {
		
	}

	@Override
	public int getContextHeight() {
		return 110;
	}

	@Override
	public int getContextWidth() {
		return 110;
	}
	
	@Override
	public void triggerRepaint() {}
	@Override
	public void killActivity() {}
	@Override
	public void forceStartThread() {}
	@Override
	public void forceStopThread() {}

	@Override
	public void handleMessage(GameEvent message) {
		DebugUtil.sss(message.getTarget() + ": " + message.getBody());
	}

	@Override
	public int getContextDiagonal() {
		return 110;
	}

}
