package org.acouster.desktop.karate;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.acouster.Game;
import org.acouster.IFuncVoid;
import org.acouster.context.desktop.DesktopResourceContext;
import org.acouster.desktop.ui.EmulatorWindow;
import org.acouster.desktop.ui.JButtonMine;
import org.acouster.desktop.ui.JSliderMine;
import org.acouster.karate.builder.StatConstants;
import org.acouster.karate.debug.DebugCardiometer;

public class InstructionTestVisual extends EmulatorWindow {
	private static final long serialVersionUID = 1L;
	
	public InstructionTestVisual()
	{
		super();
		
		// ui
		showToolbar();
		addToolbarLabel();
		addToolbarItem(new JButtonMine("REDO!!!", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DebugCardiometer.instance().clear();
				new InstructionTest();
			}
		}).setMySize(150, 70));
//		public static double JOUELS_PER_HEART_BEAT = 0.05;
//		public static double HEART_RATE_MIN = 60;
//		public static double DEFAULT_TIME = 15;
//		public static double COOLDOWN_DDH = 0.5;
		addToolbarItem(new JSliderMine("COOLDOWN_DDH", 0.01, 2, StatConstants.COOLDOWN_FACTOR, new IFuncVoid<Double>() {
			@Override
			public void lambda(Double value) {
				StatConstants.COOLDOWN_FACTOR = value;
			}
		}));
		addToolbarItem(new JSliderMine("DEFAULT_TIME", 1, 60, StatConstants.DEFAULT_TIME, new IFuncVoid<Double>() {
			@Override
			public void lambda(Double value) {
				StatConstants.DEFAULT_TIME = value;
			}
		}));
		addToolbarItem(new JSliderMine("JOUELS_PER_HEART_BEAT", 0.01, 1, StatConstants.JOUELS_PER_HEART_BEAT, new IFuncVoid<Double>() {
			@Override
			public void lambda(Double value) {
				StatConstants.JOUELS_PER_HEART_BEAT = value;
			}
		}));

		
		new DebugCardiometer(this).makeInstance();
		Game game = DebugCardiometer.instance();
		setGame(game);
		//game.handleCustomMessage(new GameEvent("game", ScheduleBuilder.GRAPH_MESSAGE_GO));
		
		new InstructionTest();
	}
	
	
	
	
	public static void main(String[] args) {
		new DesktopResourceContext().makeInstance();
		DesktopResourceContext.myInstance().setAssetPrefix("../zterminal.karate/assets");
		DesktopResourceContext.myInstance().setBitmapPrefix("../_res/karate");
		new InstructionTestVisual();
	}

}
