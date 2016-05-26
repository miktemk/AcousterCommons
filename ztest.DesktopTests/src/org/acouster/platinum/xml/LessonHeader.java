package org.acouster.platinum.xml;

import org.simpleframework.xml.Attribute;

public class LessonHeader {
	@Attribute(required=false)
	private String wav;
	@Attribute(required=false)
	private String dic;
	@Attribute(required=false)
	private String dlgName;
	@Attribute(required=false)
	private String dlgFile;
	@Attribute(required=false)
	private String author;
	
	public LessonHeader(){}
	public LessonHeader(String wav, String dic, String dlgName, String dlgFile,
			String author) {
		super();
		this.wav = wav;
		this.dic = dic;
		this.dlgName = dlgName;
		this.dlgFile = dlgFile;
		this.author = author;
	}

	public String getWav() {
		return wav;
	}
	public String getDic() {
		return dic;
	}
	public String getDlgName() {
		return dlgName;
	}
	public String getDlgFile() {
		return dlgFile;
	}
	public String getAuthor() {
		return author;
	}
	
}
