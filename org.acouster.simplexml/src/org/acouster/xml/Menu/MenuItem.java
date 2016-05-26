package org.acouster.xml.Menu;

import org.simpleframework.xml.Attribute;

public class MenuItem
{
	@Attribute
	private String name;
	@Attribute
	private String description;
	@Attribute
	private String xmlUrl;
	@Attribute
	private String pictureUrl;
	
	public MenuItem() {
		this("", "", "", "");
	}
	public MenuItem(
			String name,
			String description,
			String xmlUrl,
			String pictureUrl) {
		super();
		this.name = name;
		this.description = description;
		this.xmlUrl = xmlUrl;
		this.pictureUrl = pictureUrl;
	}
	
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public String getXmlUrl() {
		return xmlUrl;
	}
	public String getPictureUrl() {
		return pictureUrl;
	}
	
	
}
