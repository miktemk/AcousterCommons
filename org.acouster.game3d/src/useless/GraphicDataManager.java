package useless;
//package org.acouster.game3d.graphics;
//
//import java.util.HashMap;
//
//public class GraphicDataManager
//{
//	private HashMap<String, WireframeData> wireframeDatas;
//	private HashMap<String, ImageSequence> imageSequences;
//	private GraphicDataManager()
//	{
//		wireframeDatas = new HashMap<String, WireframeData>();
//		imageSequences = new HashMap<String, ImageSequence>();
//		
//		wireframeDatas.put(FLYBY3D_SHIP1, new WireframeData("ship1.dat"));
//		wireframeDatas.put(FLYBY3D_SHIP2, new WireframeData("ship2.dat"));
//		wireframeDatas.put(FLYBY3D_SHIP3, new WireframeData("ship3.dat"));
//		wireframeDatas.put(FLYBY3D_SHIP4, new WireframeData("ship4.dat"));
//		wireframeDatas.put(FLYBY3D_SHIP5, new WireframeData("ship5.dat"));
//		wireframeDatas.put(FLYBY3D_SHIP6, new WireframeData("ship6.dat"));
//		wireframeDatas.put(FLYBY3D_MISSILE0, new WireframeData("mis0.dat"));
//		wireframeDatas.put(FLYBY3D_MISSILE1, new WireframeData("mis1.dat"));
//		wireframeDatas.put(FLYBY3D_MISSILE2, new WireframeData("mis2.dat"));
//		wireframeDatas.put(FLYBY3D_MISSILE3, new WireframeData("mis3.dat"));
//		wireframeDatas.put(FLYBY3D_MISSILE4, new WireframeData("mis4.dat"));
//		wireframeDatas.put(FLYBY3D_MISSILE5, new WireframeData("mis5.dat"));
//		
//		imageSequences.put(FLYBY3D_PILOT_WALK, new ImageSequence("pilot", 1, 4, ".png"));
//		imageSequences.put(FLYBY3D_PILOT_DEATH, new ImageSequence("death", 1, 5, ".png"));
//		imageSequences.put(FLYBY3D_EXPLOSION1, new ImageSequence("exp", 1, 12, ".png"));
//		imageSequences.put(FLYBY3D_EXPLOSION2, new ImageSequence("explite", 1, 6, ".png"));
//	}
//	
//	public ImageSequence getImageSequence(String name)
//	{
//		return imageSequences.get(name);
//	}
//	
//	public WireframeData getWireframeData(String name)
//	{
//		return wireframeDatas.get(name);
//	}
//	
//	// ---------------------------------
//	
//	private static GraphicDataManager instance;
//	public static GraphicDataManager getInstance()
//	{
//		if (instance == null)
//			instance = new GraphicDataManager();
//		return instance;
//	}
//	
//	public static final String FLYBY3D_SHIP1 = "ship1";
//	public static final String FLYBY3D_SHIP2 = "ship2";
//	public static final String FLYBY3D_SHIP3 = "ship3";
//	public static final String FLYBY3D_SHIP4 = "ship4";
//	public static final String FLYBY3D_SHIP5 = "ship5";
//	public static final String FLYBY3D_SHIP6 = "ship6";
//	public static final String FLYBY3D_MISSILE0 = "mis0";
//	public static final String FLYBY3D_MISSILE1 = "mis1";
//	public static final String FLYBY3D_MISSILE2 = "mis2";
//	public static final String FLYBY3D_MISSILE3 = "mis3";
//	public static final String FLYBY3D_MISSILE4 = "mis4";
//	public static final String FLYBY3D_MISSILE5 = "mis5";
//	public static final String FLYBY3D_PILOT_WALK = "pilotWalk";
//	public static final String FLYBY3D_PILOT_DEATH = "pilotDeath";
//	public static final String FLYBY3D_EXPLOSION1 = "exp1";
//	public static final String FLYBY3D_EXPLOSION2 = "exp2";
//}
