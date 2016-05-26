package org.acouster.game3d.geometry;

import org.acouster.context.TextLineReader;

public abstract class Shape3D
{
	public abstract void readFromFileStream(TextLineReader reader, Point3DSet set);
}
