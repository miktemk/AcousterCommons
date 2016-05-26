package useless;
//package org.acouster.xml.GraphImage;
//
//import java.util.List;
//
//import org.acouster.data.GraphImage.ImageCommand;
//import org.simpleframework.xml.Attribute;
//import org.simpleframework.xml.ElementList;
//
//import useless.ImageSequence;
//
//public class XmlImageSequence extends ImageSequence
//{
//	public XmlImageSequence() {
//		this("");
//	}
//	public XmlImageSequence(String name) {
//		super(name);
//	}
//	
//	@Attribute(name="name")
//	private String name;
//	@ElementList(required=false, inline=true, entry="set")
//	private List<XmlImageVarSetter> xmlVarSetters;
//	@ElementList(inline=true, entry="image")
//	private List<XmlImageNode> xmlNodes;
//	
//	@Override
//	public List<? extends ImageCommand> getNodes() {
//		return xmlNodes;
//	}
//	@Override
//	public String getName() {
//		return name;
//	}
//	
//}
