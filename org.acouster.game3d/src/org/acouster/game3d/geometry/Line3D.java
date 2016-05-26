package org.acouster.game3d.geometry;

import org.acouster.context.TextLineReader;

/**
 * Represents a simple 3D line
 * @author Mikhail Temkine
 */
public class Line3D extends Shape3D
{
	public int p1Index, p2Index;
	public Line3D()
	{
		super();
		p1Index = p2Index = -1;
	}
	public Line3D(int p1Index, int p2Index)
	{
		super();
		this.p1Index = p1Index;
		this.p2Index = p2Index;
	}
	@Override
	public void readFromFileStream(TextLineReader reader, Point3DSet set)
	{
		int x1 = Integer.parseInt(reader.nextLine());
		int z1 = Integer.parseInt(reader.nextLine());
		int y1 = Integer.parseInt(reader.nextLine());
		int x2 = Integer.parseInt(reader.nextLine());
		int z2 = Integer.parseInt(reader.nextLine());
		int y2 = Integer.parseInt(reader.nextLine());
		p1Index = set.addPoint(x1, y1, z1);
		p2Index = set.addPoint(x2, y2, z2);
	}
	
}