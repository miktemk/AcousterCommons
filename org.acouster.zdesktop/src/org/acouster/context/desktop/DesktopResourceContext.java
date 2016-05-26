package org.acouster.context.desktop;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.acouster.DebugUtil;
import org.acouster.context.ContextBitmap;
import org.acouster.context.ResourceContext;
import org.acouster.simplexml.ObjectFactoryFunc;
import org.acouster.util.StringUtils;

public class DesktopResourceContext extends ResourceContext
{
	private String bitmapPrefix;
	private String assetPrefix;
	private String externalDir;
	public void setBitmapPrefix(String bitmapPrefix) {
		this.bitmapPrefix = bitmapPrefix;
	}
	public void setAssetPrefix(String assetPrefix) {
		this.assetPrefix = assetPrefix;
	}
	public void setExternalDir(String externalDir) {
		this.externalDir = externalDir;
	}
	
	@Override
	public ContextBitmap LoadBitmap(String resourceName, boolean preciseSize) 
	{
		try {
			return new ContextBitmap_BufferedImage(ImageIO.read(new File(bitmapPrefix + "/" + resourceName)));
		} catch (IOException e) {
			DebugUtil.eee("Could not load img: " + bitmapPrefix + "/" + resourceName);
			//e.printStackTrace();
		}
		return null;
	}
	@Override
	public ContextBitmap LoadBitmapFromFile(String filename) {
		if (!StringUtils.isNullOrEmpty(externalDir))
			filename = externalDir + "/" + filename;
		try {
			return new ContextBitmap_BufferedImage(ImageIO.read(new File(filename)));
		} catch (IOException e) {
			DebugUtil.eee("Could not load img: " + filename);
			//e.printStackTrace();
		}
		return null;
	}

	@Override
	public String LoadText(String resourceName) {
		//return "";
		StringBuilder sb = new StringBuilder();
		BufferedReader curfile;
		try {
			curfile = new BufferedReader(new FileReader(assetPrefix + "/" + resourceName));
			String str;
	        while ((str = curfile.readLine()) != null)
	        {
	        	sb.append(str + "\n");
			}
			curfile.close();
			return sb.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	@Override
	public InputStream OpenAssetFile(String resourceName) throws IOException {
		return new FileInputStream(assetPrefix + "/" + resourceName);
	}

	@Override
	public <T> T LoadAny(Class<? extends T> clazz, String filename) throws IOException {
		return LoadStructure(clazz, filename, new ObjectFactoryFunc<T>());
	}
	@Override
	public <T> T LoadAnyExternal(Class<? extends T> clazz, String filename) throws IOException {
		if (!StringUtils.isNullOrEmpty(externalDir))
			filename = externalDir + "/" + filename;
		InputStream inputStream = new FileInputStream(filename);
		return LoadStructureExternal(clazz, inputStream, new ObjectFactoryFunc<T>());
	}

	public static DesktopResourceContext myInstance() {
		return (DesktopResourceContext)instance;
	}
	
	

}
