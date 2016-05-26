package org.acouster.context;

public class TextLineReader
{
	private String[] lines;
	private int index = 0;
	public TextLineReader(String s)
	{
		lines = s.split("\n");
	}
	public void reset()
	{
		index = 0;
	}
	public String nextLine()
	{
		if (!hasMoreLines())
			return null;
		return lines[index++].trim();
	}
	private boolean hasMoreLines()
	{
		return (index < lines.length);
	}
}
