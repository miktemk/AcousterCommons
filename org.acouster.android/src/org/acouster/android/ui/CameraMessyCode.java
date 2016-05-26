package org.acouster.android.ui;

import java.io.IOException;

import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;

/** use it like this: 
 * 1. new CameraMessyCode(blahblah)
 * 2. setParam..., etc, etc
 * 3. startPreview()
 * 4. stopPreview
 * */
public class CameraMessyCode
{
	private SurfaceHolder previewHolder = null;
	private Camera camera;
	private boolean shouldBeInPreview = false,
			isHolderReady = false,
			flagUseSmallestSize = false;
	private int paramCameraType;
	private String typeParamFlash = Parameters.FLASH_MODE_OFF;
	
	public CameraMessyCode(SurfaceHolder holder) {
		this(holder, Camera.CameraInfo.CAMERA_FACING_BACK);
	}
	public CameraMessyCode(SurfaceHolder holder, int type)
	{
		this.previewHolder = holder;
		this.paramCameraType = type;
		
		if (camera == null)
			camera = getCamera(paramCameraType);

		previewHolder.addCallback(new Callback() {
			@Override
			public void surfaceDestroyed(SurfaceHolder holder) { }
			
			@Override
			public void surfaceCreated(SurfaceHolder holder) { }
			
			@Override
			public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
				initDimdims(width, height);
				isHolderReady = true;
				if (shouldBeInPreview)
					startPreview();
			}
		});
	}
	
	// --------------- params -------------------
	
	/** for example: Parameters.FLASH_MODE_TORCH */
	public void setParamFlash(String type) {
		typeParamFlash = type;
	}
	public void useSmallestSize(boolean flag) {
		flagUseSmallestSize = flag;
	}

	// --------------- controls -------------------
	
	public void startPreview()
	{
		shouldBeInPreview = true; // set before to allow to be called from surfaceChanged
		if (!isHolderReady)
			return;
		Camera.Parameters parameters=camera.getParameters();
		parameters.setFlashMode(typeParamFlash);
		camera.setParameters(parameters);
		camera.startPreview();
	}
	public void stopPreview()
	{
		shouldBeInPreview = false;
		// make sure to turn the torch off (if it had ever been on)
		Parameters p = camera.getParameters();
		p.setFlashMode(Parameters.FLASH_MODE_OFF);
		camera.setParameters(p);
		camera.stopPreview();
	}
	public void release()
	{
		if (camera == null)
			return;
		camera.stopPreview(); //TODO: see if it throws exception
		camera.release();
		camera = null;
	}
	public boolean shouldBePreviewing() {
		return shouldBeInPreview;
	}
	
	private void initDimdims(int width, int height) {
//		if (camera == null)
//			return;
//		if (previewHolder.getSurface() == null)
//			return;
		try {
			camera.setPreviewDisplay(previewHolder);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Camera.Parameters parameters=camera.getParameters();
		//Log.v("=========>", "Camera.Size size = getBestPreviewSize(width(" + width + "), height(" + height + "), parameters);");
		Camera.Size size = flagUseSmallestSize
				? getSmallestPreviewSize(parameters)
				: getBestPreviewSize(width, height, parameters);
		if (!flagUseSmallestSize && size == null) // couldn't find anything... use smallest as backup
			size = getSmallestPreviewSize(parameters);
		//Log.v("=========>", "size:(" + size.width + ", " + size.height + ")");
		
		Camera.Size pictureSize = getSmallestPictureSize(parameters);

		if (size != null && pictureSize != null) {
			parameters.setPreviewSize(size.width, size.height);
			parameters.setPictureSize(pictureSize.width, pictureSize.height);
			parameters.setPictureFormat(ImageFormat.JPEG);
		}
		camera.setParameters(parameters);
	}
	
	
	
	// -------------- static shit -------------------------
	
	/** get the back-facing camera by default */
	public static Camera getCamera_backwardOne() {
    	return getCamera(Camera.CameraInfo.CAMERA_FACING_BACK);
    }
    /** Camera.CameraInfo.CAMERA_FACING_BACK or Camera.CameraInfo.CAMERA_FACING_FRONT */
	public static Camera getCamera(int type)
    {
    	Camera.CameraInfo info = new Camera.CameraInfo();
		for (int i = 0; i < Camera.getNumberOfCameras(); i++) {
			Camera.getCameraInfo(i, info);
			if (info.facing == type) {
				return Camera.open(i);
			}
		}
		// just return the default cam
		return Camera.open();
    }
	
	public static Camera.Size getSmallestPictureSize(Camera.Parameters parameters)
	{
		Camera.Size result=null;
		for (Camera.Size size : parameters.getSupportedPictureSizes()) {
			if (result == null) {
				result=size;
			}
			else {
				int resultArea=result.width * result.height;
				int newArea=size.width * size.height;

				if (newArea < resultArea) {
					result=size;
				}
			}
		}
		return(result);
	}
	public static Camera.Size getSmallestPreviewSize(Camera.Parameters parameters)
	{
		Camera.Size result=null;
		for (Camera.Size size : parameters.getSupportedPreviewSizes()) {
			if (result == null) {
				result = size;
			}
			else {
				int resultArea=result.width * result.height;
				int newArea=size.width * size.height;
				if (newArea < resultArea) {
					result=size;
				}
			}
		}
		return(result);
	}
	public static Camera.Size getBestPreviewSize(int width, int height, Camera.Parameters parameters)
	{
		Camera.Size result=null;
		for (Camera.Size size : parameters.getSupportedPreviewSizes()) {
			if (size.width <= width && size.height <= height) {
				if (result == null) {
					result=size;
				}
				else {
					int resultArea=result.width * result.height;
					int newArea=size.width * size.height;

					if (newArea > resultArea) {
						result=size;
					}
				}
			}
		}
		return(result);
	}
	
}
