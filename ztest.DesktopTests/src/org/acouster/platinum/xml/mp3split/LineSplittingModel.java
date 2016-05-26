package org.acouster.platinum.xml.mp3split;

import java.io.IOException;
import java.util.Vector;

import org.acouster.DebugUtil;
import org.acouster.platinum.xml.AudioTime;
import org.acouster.platinum.xml.Lesson;
import org.acouster.platinum.xml.LessonHeader;
import org.acouster.platinum.xml.LessonLine;
import org.acouster.simplexml.ObjectFactory;

public class LineSplittingModel
{
	private static final String DEFAULT_IMAGE = "default.jpg";
	private static final String DEFAULT_SOMENUM = "None";
	private static final String DEFAULT_AUTHOR = "acouster";
	private static final double STUPID_FACTOR = 1.004;
	private static final int MAX_LINE_LENGTH = 60;
	private static final String PREMIER_LINE = "______________get_ready____________";
	
	//shit
	private Lesson lesson;
	private int index;
	
	public LineSplittingModel(String allText, String mp3filename)
	{
		this(buildShit(allText, mp3filename));
	}
	public LineSplittingModel(Lesson xmlshit)
	{
		this.lesson = xmlshit;
		index = 0;
		lesson.getLines().add(0, new LessonLine(PREMIER_LINE, "", DEFAULT_IMAGE, DEFAULT_SOMENUM, new AudioTime()));
	}
	
	public int totalLines() {
		return lesson.getLines().size();
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public void incIndex() {
		this.index++;
	}
	public String line1() {
		return lineTextAt(index);
	}
	public String line2() {
		return lineTextAt(index+1);
	}
	
	public void setCurEndTimeAndNextBeginTime(long position) {
		LessonLine line1 = lineAt(index);
		LessonLine line2 = lineAt(index+1);
		if (line1 != null)
			line1.getAudioTime().out = (long)(position / STUPID_FACTOR);
		if (line2 != null)
			line2.getAudioTime().in = (long)(position / STUPID_FACTOR);
	}
	
	public void saveLesson(String path)
	{
		if (PREMIER_LINE.equals(lineTextAt(0)))
			lesson.getLines().remove(0);
		try {
			ObjectFactory.writeXmlToFile(Lesson.class, lesson, path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// ---------- privates -------------
	private String lineTextAt(int i)
	{
		if (i < totalLines())
			return lesson.getLines().get(i).getLang1();
		return null;
	}
	private LessonLine lineAt(int i)
	{
		if (i < totalLines())
			return lesson.getLines().get(i);
		return null;
	}
	private static Lesson buildShit(String allText, String mp3filename)
	{
		//String[] splits = allText.split("\\.|\n");
		String[] splits = allText.split("\\.");
		Vector<String> list = new Vector<String>();
		for (String line : splits)
		{
			line = line.trim() + ".";
			line = line.replace("\n", "");
			line = line.replace("\r", "");
			line = line.replace("\"", "'");
			recursiveSplitIfNeeded(list, line);
		}
		Lesson lessonx = new Lesson();
		lessonx.header = new LessonHeader(mp3filename, "dic", mp3filename, mp3filename.replace(".mp3", ".xml"), DEFAULT_AUTHOR);
		for (String line : list)
		{
			lessonx.addLine(new LessonLine(line, "", DEFAULT_IMAGE, DEFAULT_SOMENUM, new AudioTime()));
		}
		return lessonx;
	}	
	private static void recursiveSplitIfNeeded(Vector<String> list, String line)
	{
		if (line.length() <= MAX_LINE_LENGTH)
		{
			list.add(line);
			return;
		}
		int i;
		for (i = MAX_LINE_LENGTH; i > 0; i--)
		{
			if (line.charAt(i) == ' ')
				break;
		}
		list.add(line.substring(0, i));
		recursiveSplitIfNeeded(list, line.substring(i+1)); // +1 to skip that space
	}

	
}
