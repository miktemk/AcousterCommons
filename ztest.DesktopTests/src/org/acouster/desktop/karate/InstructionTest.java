package org.acouster.desktop.karate;

import java.io.IOException;

import org.acouster.DebugUtil;
import org.acouster.Game;
import org.acouster.Graph;
import org.acouster.Graph.OnAdvanceListener;
import org.acouster.IFuncVoid;
import org.acouster.context.ResourceContext;
import org.acouster.context.desktop.DesktopResourceContext;
import org.acouster.desktop.BlankGameContext;
import org.acouster.karate.builder.ExerciseCall;
import org.acouster.karate.builder.Instruction;
import org.acouster.karate.builder.ScheduleBuilder;
import org.acouster.karate.builder.Utils;
import org.acouster.karate.builder.WorkoutCode;
import org.acouster.karate.builder.WorkoutPlanner;
import org.acouster.karate.debug.DebugCardiometer;
import org.acouster.karate.xml.XmlSettings;
import org.acouster.karate.xml.exercise.XmlExercise;
import org.acouster.karate.xml.workout.XmlWo;
import org.acouster.xml.ZOther.GraphImageAndLogic;

public class InstructionTest
{

	protected Graph<ExerciseCall> practice;
	protected ScheduleBuilder builder;
	//protected GraphTicker<Instruction> curTicker;
	protected Graph<Instruction> curSchedule;
	private WorkoutCode code;
	
	public InstructionTest()
	{
		new DesktopResourceContext().makeInstance();
		DesktopResourceContext.myInstance().setAssetPrefix("../zterminal.karate/assets");
		DesktopResourceContext.myInstance().setBitmapPrefix("../_res/karate");
		
		new DebugCardiometer(new BlankGameContext()).makeInstance();
		Game game = DebugCardiometer.instance();
		
		
		code = new WorkoutCode();
		code.setListeners(new IFuncVoid<Instruction>() {
			@Override
			public void lambda(Instruction node) {
				DebugUtil.sss(node.getTtsLine());
			}
		}, new IFuncVoid<GraphImageAndLogic>() {
			@Override
			public void lambda(GraphImageAndLogic avatar) {
				// do nothing here
			}
		});
		
		code.fastIncrement();
	}

	
	public static void main(String[] args)
	{
		new InstructionTest();
	}

}
