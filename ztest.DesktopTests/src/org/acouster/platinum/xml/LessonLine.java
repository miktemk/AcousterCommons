package org.acouster.platinum.xml;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

public class LessonLine {
	@Attribute(required=false)
	private String lang1;
	@Attribute(required=false)
	private String lang2;
	@Attribute(required=false)
	private String image;
	@Attribute(required=false)
	private String someNumber;
	@Element
	private AudioTime audioTime;
	
	public LessonLine(){}
	public LessonLine(String lang1, String lang2, String image,
			String someNumber, AudioTime audioTime) {
		super();
		this.lang1 = lang1;
		this.lang2 = lang2;
		this.image = image;
		this.someNumber = someNumber;
		this.audioTime = audioTime;
	}

	public String getLang1() {
		return lang1;
	}
	public String getLang2() {
		return lang2;
	}
	public String getImage() {
		return image;
	}
	public String getSomeNumber() {
		return someNumber;
	}
	public AudioTime getAudioTime() {
		return audioTime;
	}
	
}
