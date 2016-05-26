package org.acouster.game3d;

import java.util.Arrays;
import java.util.Vector;

import org.acouster.DebugUtil;
import org.acouster.context.ContextGraphics;
import org.acouster.game3d.graphics.RenderableObject3D;
import org.acouster.game3d.math.TransformMatrix3D;
import org.acouster.game3d.math.VectorXYZ;
import org.acouster.graphics.WorldLayer;

public class CameraRenderer3D extends WorldLayer
{
	private class ObjectMatrixTuple implements Comparable<ObjectMatrixTuple>
	{
		public RenderableObject3D object;
		public TransformMatrix3D matrix;
		public double zValue;
		public ObjectMatrixTuple(RenderableObject3D object, TransformMatrix3D matrix) {
			super();
			this.object = object;
			this.matrix = matrix;
			VectorXYZ camVector = new VectorXYZ(0, 0, 0, 1);
			matrix.transformNoW(camVector, camVector);
			zValue = camVector.z;
		}
		public int compareTo(ObjectMatrixTuple other) {
			if (this.zValue > other.zValue)
				return -1;
			else if (this.zValue < other.zValue)
				return 1;
			return 0;
		}
	}
	
	public static final double Z_ANGLE_FACTOR = 0.2;
	public static final double SCALE_FACTOR = 2;
	public static final double alpha = 0.7;
	public static final int view = 80;
	public static final double ZOOM_CONSTANT = 5;
	public static final double FOV_X_ANGLE = 0.7;
	public static final double FOV_X_FACTOR = Math.tan(FOV_X_ANGLE);
	public static final double FOV_Y_ANGLE = 0.7;
	public static final double FOV_Y_FACTOR = Math.tan(FOV_Y_ANGLE);
	
	protected Camera3D camera;
	protected World3d world3d;
	
	public CameraRenderer3D(int level, Camera3D camera, World3d world3d)
	{
		super(level);
		this.camera = camera;
		this.world3d = world3d;
	}
	
	@Override
	public void render(ContextGraphics g, int width, int height)
	{
		TransformMatrix3D.nCalled = 0;
		
		world3d.flushRenderableList();
		
		double camX = camera.getX();
		double camY = camera.getY();
		double camZ = camera.getZ();
		double camAY = camera.getAy();
		
		//DebugUtil.sss(camX + " : " + camY + " : " + camZ + " : " + camAY);
		
		//TODO:
		//double camAX = camera.getAX();
		
		double focalFactorX = width/(2.0*FOV_X_FACTOR);
		double focalFactorY = height/(2.0*FOV_Y_FACTOR);
		
		// multiply world coords by this to get coords wrt camera
		TransformMatrix3D worldToCam = new TransformMatrix3D();
		worldToCam.rotateAboutY(-(float)camAY);
		worldToCam.translateTo(-(float)camX, -(float)camY, -(float)camZ);
		
		// multiply camera coords by this to get coords wrt world
		TransformMatrix3D camToWorld = new TransformMatrix3D();
		//camToWorld.translateTo((float)camX, (float)camY, (float)camZ);
		//camToWorld.rotateAboutY((float)camAY);
		camToWorld.flyby3DConvention_setLocation(camX, camY, camZ);
		camToWorld.flyby3DConvention_setAy(camAY);
		
		double azTilt = 4*camera.getAz();
		g.translate(width/2, height/2);
		g.rotate(azTilt);
		
		for (RenderableObject3D obj : world3d.getNonSortZObjects())
		{
			//TODO:
			//change the signature of the function,
			//pass the rendering through a callback object
			
			VectorXYZ destVector = new VectorXYZ(0, 0, 0, 1);
			worldToCam.flyby3DConvention_transformJustLocation(destVector, obj.transform);
			
			if ((destVector.z > 0) || world3d.isRenderedAlways(obj))
			{
				// multiply object's local coords by this to get coords wrt camera
				TransformMatrix3D objectToCam = new TransformMatrix3D();
				objectToCam.setTransform(obj.transform);
				objectToCam.mulMByMe(worldToCam);
				
				obj.render(g, objectToCam, camToWorld, focalFactorX, focalFactorY);
			}
		}
		
		// do z-sorting
		Vector<ObjectMatrixTuple> zSortedList = new Vector<ObjectMatrixTuple>();
		for (RenderableObject3D obj : world3d.getYesSortZObjects())
		{
			VectorXYZ destVector = new VectorXYZ(0, 0, 0, 1);
			worldToCam.flyby3DConvention_transformJustLocation(destVector, obj.transform);

			if ((destVector.z > 0) || world3d.isRenderedAlways(obj))
			{
				// multiply object's local coords by this to get coords wrt camera
				TransformMatrix3D objectToCam = new TransformMatrix3D();
				objectToCam.setTransform(obj.transform);
				objectToCam.mulMByMe(worldToCam);
				zSortedList.add(new ObjectMatrixTuple(obj, objectToCam));
			}
		}
		Object[] zList = zSortedList.toArray();
		Arrays.sort(zList);
		
		for (int i = 0; i < zList.length; i++)
		{
			ObjectMatrixTuple cur = (ObjectMatrixTuple)zList[i];
			RenderableObject3D obj = cur.object;
			TransformMatrix3D objectToCam = cur.matrix;
			obj.render(g, objectToCam, camToWorld, focalFactorX, focalFactorY);
		}
		
		g.rotate(-azTilt);
		g.translate(-width/2, -height/2);
	}
	
//	public void increment(World3D w, Sprite3D p){}
//	public void keyPressed(ContextKeyEvent arg0) {}
//	public void keyReleased(ContextKeyEvent arg0) {}
//	public void keyTyped(ContextKeyEvent arg0){}
//	public void mouseClicked(ContextMouseEvent arg0){}
//	public void mouseDragged(ContextMouseEvent arg0){}
//	public void mouseEntered(ContextMouseEvent arg0){}
//	public void mouseExited(ContextMouseEvent arg0){}
//	public void mouseMoved(ContextMouseEvent arg0){}
//	public void mousePressed(ContextMouseEvent arg0){}
//	public void mouseReleased(ContextMouseEvent arg0){}
}
