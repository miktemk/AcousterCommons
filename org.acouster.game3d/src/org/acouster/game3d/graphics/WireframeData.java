package org.acouster.game3d.graphics;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Vector;

import org.acouster.context.ResourceContext;
import org.acouster.context.TextLineReader;
import org.acouster.game3d.geometry.Point2D;
import org.acouster.game3d.geometry.Point3DSet;
import org.acouster.game3d.geometry.Shape3D;
import org.acouster.game3d.math.GameMath3D;
import org.acouster.game3d.math.TransformMatrix3D;
import org.acouster.game3d.math.VectorXYZ;


public class WireframeData
{
	protected Shape3D[] shapes;
	protected Point3DSet pointSet;
	protected VectorXYZ[] points;
	protected Point2D[] pointsOut;
	protected double maxRadius;
	public WireframeData(String filename)
	{
		pointSet = new Point3DSet();
		try {
			loadData(filename);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void loadData(String filename) throws ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException, IOException
	{
		Vector<Shape3D> list = new Vector<Shape3D>();
		TextLineReader reader = ResourceContext.instance().LoadTextReader(filename);
		
		String token;
		while ((token = reader.nextLine()) != null)
		{
			token = token.trim();
			String className = translateClassName(token);
			Class c = Class.forName("acouster.game.game3D.geometry."+className);
			Constructor constructor = c.getConstructor();
			Shape3D shape = (Shape3D)constructor.newInstance();
			
			shape.readFromFileStream(reader, pointSet);
			list.add(shape);
		}
		
		shapes = list.toArray(new Shape3D[list.size()]);
		points = pointSet.compile();
		maxRadius = 0;
		//TODO: apply this scaling in the matrix!!!
		for (int i = 0; i < points.length; i++)
		{
			points[i].scale(WireframePerspectiveObject.GEOMETRY_SCALE_FACTOR);
			double curRadius = GameMath3D.distance(0, 0, 0, points[i].x, points[i].y, points[i].z);
			if (maxRadius < curRadius)
				maxRadius = curRadius;
		}

		pointsOut = new Point2D[points.length];
		for (int i = 0; i < points.length; i++)
			pointsOut[i] = new Point2D();
	}
	
	public Shape3D[] getShapes()
	{
		return shapes;
	}
	public VectorXYZ[] getPoints()
	{
		return points;
	}
	/**
	 * Don't mess with the return value! it belongs to this class!
	 */
	public Point2D[] transformPoints(TransformMatrix3D m, double focalFactorX, double focalFactorY)
	{
		VectorXYZ dest = new VectorXYZ(0, 0, 0, 1);
		for (int i = 0; i < points.length; i++)
		{
			m.transformNoW(dest, points[i]);
			//TODO: cut the lines off!
			pointsOut[i].x = (int)(focalFactorX * dest.x / Math.abs(dest.z));
			pointsOut[i].y = (int)(-focalFactorY * dest.y / Math.abs(dest.z));
		}
		return pointsOut;
	}
	/**
	 * Don't mess with the return value! it belongs to this class!
	 */
	public Point2D[] transformPoints(TransformMatrix3D m, double focalFactorX, double focalFactorY, double zDist)
	{
		double factorX = focalFactorX / zDist;
		double factorY = -focalFactorY / zDist;
		VectorXYZ dest = new VectorXYZ(0, 0, 0, 1);
		for (int i = 0; i < points.length; i++)
		{
			m.transformNoWNoZ(dest, points[i]);
			pointsOut[i].x = (int)(factorX * dest.x);
			pointsOut[i].y = (int)(factorY * dest.y);
		}
		return pointsOut;
	}
	
	//--------------------------------------------------------
	
	public static final String[] CLASS_NAME_MAP = new String[]
	{
		"line", "Line3D",
	};
	public static String translateClassName(String s)
	{
		for (int i = 0; i < CLASS_NAME_MAP.length; i += 2)
		{
			if (s.equals(CLASS_NAME_MAP[i]))
				return CLASS_NAME_MAP[i+1];
		}
		return s;
	}
	public double getMaxRadius()
	{
		return maxRadius;
	}
}

