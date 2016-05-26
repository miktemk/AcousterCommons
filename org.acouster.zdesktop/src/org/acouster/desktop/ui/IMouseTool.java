package org.acouster.desktop.ui;

public interface IMouseTool
{
	void down(int x, int y);
	void drag(int x, int y);
	void up(int x, int y);
	void click(int x, int y);
}
