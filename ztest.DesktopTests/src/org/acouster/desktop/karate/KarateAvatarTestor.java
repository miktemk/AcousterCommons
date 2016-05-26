package org.acouster.desktop.karate;

import java.io.File;
import java.io.IOException;

import org.acouster.IFunc;
import org.acouster.context.desktop.DesktopResourceContext;
import org.acouster.data.GraphLogic.FsmGraph;
import org.acouster.desktop.tests.TestorAvatar;
import org.acouster.karate.DojoGame;
import org.acouster.karate.builder.Utils;
import org.acouster.logic.FsmSprite;
import org.acouster.xml.ZOther.GraphImageAndLogic;

public class KarateAvatarTestor extends DojoGame implements TestorAvatar.IAvatarContext
{
	public static void main(String[] str)
	{
		new DesktopResourceContext().makeInstance();
		DesktopResourceContext.myInstance().setAssetPrefix("../zterminal.karate/assets");
		DesktopResourceContext.myInstance().setBitmapPrefix("../_res/karate");
		
		KarateAvatarTestor kgame = new KarateAvatarTestor();
		
		new TestorAvatar(
				"../zterminal.karate/assets/xml",
				"ex_.*",
				new IFunc<File, GraphImageAndLogic>(){
					@Override
					public GraphImageAndLogic lambda(File value) {
						try {
							return Utils.loadExcercise("xml/" + value.getName()).avatar;
						} catch (IOException e) {
							e.printStackTrace();
						}
						
						return null;
					}
				},
				kgame,
				kgame);
	}
	
	
	protected FsmGraph curFsm;
	
	public KarateAvatarTestor() {
		super(null, true);
	}
	
	public void setAvatar(GraphImageAndLogic thing)
	{
		if (thing == null)
			return;
		curFsm = thing.logic;
		setNewAvatar(thing.logic, thing.visuals);
	}
	public FsmSprite getSprite()
	{
		return avatar;
	}
	public FsmGraph getSpriteFsm() {
		return curFsm;
	}
	
	
	
	

}
