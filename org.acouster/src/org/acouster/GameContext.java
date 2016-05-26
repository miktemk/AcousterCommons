package org.acouster;


// TODO: rename to IGameContext
public interface GameContext extends IContextDimensions
{
	void setGame(Game g);
	void handleMessage(GameEvent message);
	void triggerRepaint();
	void killActivity();
	void forceStartThread();
	void forceStopThread();
}

// what is also inherited from IContextDimensions
//public interface IContextDimensions
//{
//	int getContextHeight();
//	int getContextWidth();
//	int getContextDiagonal();
//}