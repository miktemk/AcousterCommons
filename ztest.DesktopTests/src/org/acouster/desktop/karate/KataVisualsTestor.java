package org.acouster.desktop.karate;

import org.acouster.karate.DojoGame;
import org.acouster.xml.GraphImage.XmlImageGraph;
import org.acouster.desktop.tests.TestorVisuals;

public class KataVisualsTestor extends DojoGame implements TestorVisuals.IVisualsContext
{
	public static void main(String[] str)
	{
//		new DesktopResourceContext().makeInstance();
//		DesktopResourceContext.myInstance().setAssetPrefix("../zterminal.karate/assets");
//		DesktopResourceContext.myInstance().setBitmapPrefix("../_res/karate");
//		new KarateAvatarTestor(
//				"../zterminal.karate/assets/xml",
//				"kata_.*",
//				new IFunc<File, GraphImageAndLogic>(){
//					@Override
//					public GraphImageAndLogic lambda(File value) {
//						try {
//							XmlKata kata = ResourceContext.instance().LoadAny(XmlKata.class, "xml/" + value.getName());
//							//return new GraphImageAndLogic(kata.visuals, kata.);
//						} catch (IOException e) {
//							e.printStackTrace();
//						}
//						
//						return null;
//					}
//				});
	}
	
	
	public KataVisualsTestor() {
		super(null, true);
	}
	
	private XmlImageGraph visuals;
	
	public void setVisuals(XmlImageGraph visuals)
	{
		if (visuals == null)
			return;
		this.visuals = visuals;
		setNewAvatar(null, visuals);
	}
	public XmlImageGraph getVisuals()
	{
		return visuals;
	}
	
}
