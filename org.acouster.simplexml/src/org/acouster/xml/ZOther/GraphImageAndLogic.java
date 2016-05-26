package org.acouster.xml.ZOther;

import org.acouster.xml.GraphImage.XmlImageGraph;
import org.acouster.xml.GraphLogic.XmlFsmGraph;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="avatar")
public class GraphImageAndLogic
{
	@Attribute(required=false)
	public String ref;	
	@Element(required=false)
	public XmlFsmGraph logic;	
	@Element(required=false)
	public XmlImageGraph visuals;
	
	public GraphImageAndLogic()
	{
		logic = new XmlFsmGraph();
		visuals = new XmlImageGraph();
	}
	public GraphImageAndLogic(XmlFsmGraph logic, XmlImageGraph visuals)
	{
		this.logic = logic;
		this.visuals = visuals;
	}
	
//	public void compile(IFunc<IImageQuery, BitmapWithTransform> id2image)
	public void compile()
	{
		logic.compile();
		//visuals.compile(id2image);
	}
}
