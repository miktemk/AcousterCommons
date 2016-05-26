package org.acouster.logic;


import org.acouster.Game;
import org.acouster.GameEvent;
import org.acouster.data.GraphLogic.FsmBranch;
import org.acouster.data.GraphLogic.FsmGraph;
import org.acouster.data.GraphLogic.IFsmOutputListener;

public class FsmSprite extends Sprite2D
{
	protected Game game;
	protected FsmGraph fsm;
	protected IFsmOutputListener eventListener;
	private GameEvent recycledEvent;
	
	public FsmSprite(Game game, FsmGraph fsm, String name, double x, double y) {
		super(name, x, y);
		this.game = game;
		this.fsm = fsm;
		recycledEvent = new GameEvent(GameEvent.EVENT_TARGET_visual, "");
		eventListener = new IFsmOutputListener() {
			@Override
			public void handleGameEvent(GameEvent event) {
				if (event.getTarget().equals(GameEvent.EVENT_TARGET_visual))
					FsmSprite.this.sendMessageToRenderable(event);
				if (event.getTarget().equals(GameEvent.EVENT_TARGET_game))
					FsmSprite.this.game.handleCustomMessage(event);
			}
		};
		if (fsm != null)
			fsm.addFsmOutputListener(eventListener);
//				new IFsmOutputListener() {
//			@Override
//			public void handleGameEvent(String command) {
//				recycledEvent.setBody(command);
//				FsmSprite.this.sendMessageToRenderable(recycledEvent);
//			}
//		});
	}
	
	@Override
	public void handleActionMessage(GameEvent message)
	{
		if (fsm == null)
		{
			eventListener.handleGameEvent(message);
			return;
		}
		sendFmsCommand(message.getBody());
	}
	
	public void sendFmsCommand(String command)
	{
		if (fsm == null)
		{
			recycledEvent.setBody(command);
			eventListener.handleGameEvent(recycledEvent);
			return;
		}
		
		fsm.handleCommand(command);
	}
	
}
