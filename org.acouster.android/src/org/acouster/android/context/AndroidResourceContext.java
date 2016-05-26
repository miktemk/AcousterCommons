package org.acouster.android.context;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.acouster.DebugUtil;
import org.acouster.IFuncClassed;
import org.acouster.android.R;
import org.acouster.context.ContextBitmap;
import org.acouster.context.ResourceContext;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;

public abstract class AndroidResourceContext extends ResourceContext {

	public  static final String XML_ASSET_FOLDER = "xml";
	// should we move deserialization code here too?
	
	protected Activity activity;
	protected BitmapFactory.Options bmpOptions;
	public AndroidResourceContext(Activity activity) {
		super();
		this.activity = activity;
		bmpOptions = new BitmapFactory.Options();
		bmpOptions.inScaled = false;
	}
	
	//------------------------------------------------------
	// IFileSystemContext members

	/** you will override this in your superclass */
	@Override
	public ContextBitmap LoadBitmap(String resourceName, boolean preciseSize)
	{
		// TODO: add a string to int mapping for resources?
		Bitmap bbb = null;
		return new AndroidContextBitmap(bbb);
	}
	@Override
	public ContextBitmap LoadBitmapFromFile(String imgPath)
	{
		Bitmap bbb = loadBitmapFromFile(imgPath);
		return new AndroidContextBitmap(bbb);
	}
	@Override
	public InputStream OpenAssetFile(String filename) throws IOException {
		AssetManager assetManager = activity.getAssets();
		InputStream inputStream = assetManager.open(filename);
		if (inputStream == null)
			Log.e(DebugUtil.TAG, "error reading file: " + filename);
		return inputStream;
	}

	@Override
	public String LoadText(String filename) throws IOException {
		InputStream inputStream = OpenAssetFile(filename);
		if (inputStream == null)
			return null;
		String s = readTextFile(inputStream);
		return s;
	}
	
	/**
	 * This method reads simple text file
	 * @param inputStream
	 * @return data from file
	 */
	protected String readTextFile(InputStream inputStream) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte buf[] = new byte[1024];
		int len;
		try {
			while ((len = inputStream.read(buf)) != -1) {
				outputStream.write(buf, 0, len);
			}
			outputStream.close();
			inputStream.close();
		} catch (IOException e) {
		}
		return outputStream.toString();
	}
	
	//------- android os utils -----------------------------
	public File getExternalFile(String filename)
	{
		File fff = new File(Environment.getExternalStorageDirectory(), filename);
		return fff;
	}
	public <T> T LoadStructureExternal(Class<? extends T> clazz, String filename, IFuncClassed<InputStream, T> bindingFunc) throws IOException
	{
		InputStream inputStream = new FileInputStream(getExternalFile(filename));
		return LoadStructureExternal(clazz, inputStream, bindingFunc);
	}
	public ContextBitmap LoadBitmapFromResource(int id, int defaultId)
	{
		Bitmap bbb = BitmapFactory.decodeResource(activity.getResources(), id);
		if (bbb == null)
			bbb = BitmapFactory.decodeResource(activity.getResources(), defaultId, bmpOptions);
		return new AndroidContextBitmap(bbb);
	}
	public Bitmap loadBitmapByName(String resource) {
		return loadBitmapByName(resource, false);
	}
	public Bitmap loadBitmapByName(String resource, boolean preciseSize)
	{
		if (resource == null)
			return null;
		resource = resource
				.replace(".jpg", "")
				.replace(".png", "");
		int id = activity.getResources().getIdentifier(resource, "drawable", activity.getPackageName());
		if (id == 0)
			return null;
		if (preciseSize)
			return BitmapFactory.decodeResource(activity.getResources(), id, bmpOptions);
		return BitmapFactory.decodeResource(activity.getResources(), id);
	}
	public Drawable loadDrawableByName(String resource)
	{
		resource = resource
				.replace(".jpg", "")
				.replace(".png", "");
		int id = activity.getResources().getIdentifier(resource, "drawable", activity.getPackageName());
		if (id == 0)
			return null;
		return activity.getResources().getDrawable(id);
	}
	public Bitmap loadBitmapFromFile(String imgPath) {
		return loadBitmapFromFile(imgPath, R.drawable.icon);
	}
	public Bitmap loadBitmapFromFile(String imgPath, int defResourceId) {
		Bitmap img = null;
		img = BitmapFactory.decodeFile(getExternalFile(imgPath).getPath());
		if (img != null)
			return img;
		return BitmapFactory.decodeResource(activity.getResources(), defResourceId);
	}
	//------------------------------------------------------
	//------------------------------------------------------
	
	
	// singleton
	public static AndroidResourceContext ainstance()
	{
		return (AndroidResourceContext)instance;
	}
}
