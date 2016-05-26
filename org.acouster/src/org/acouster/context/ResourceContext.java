package org.acouster.context;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.acouster.IFuncClassed;

public abstract class ResourceContext implements IFileSystemContext
{
	public ResourceContext()
	{
		//TODO: pass custom func??
		new ImageManager().makeInstance();
	}
	
	
	// abstract methods
	// ...see interfaces
	
	
	// extension methods
	/** Loads lines of text from an asset file */
	public String[] LoadTextLines(String resourceName) throws IOException
	{
		return LoadText(resourceName).split("\n");
	}
	public TextLineReader LoadTextReader(String resourceName) throws IOException
	{
		return new TextLineReader(LoadText(resourceName));
	}
	public <T> T LoadStructure(Class<? extends T> clazz, String filename, IFuncClassed<InputStream, T> bindingFunc) throws IOException
	{
		InputStream inputStream = OpenAssetFile(filename);
		T obj = bindingFunc.lambda(clazz, inputStream);
		inputStream.close();
		return obj;
	}
	public <T> T LoadStructureExternal(Class<? extends T> clazz, InputStream inputStream, IFuncClassed<InputStream, T> bindingFunc) throws IOException
	{
		//InputStream inputStream = new FileInputStream(filename);
		T obj = bindingFunc.lambda(clazz, inputStream);
		inputStream.close();
		return obj;
	}
	
	// singleton
	protected static ResourceContext instance;
	public static ResourceContext instance()
	{
		return instance;
	}
	public void makeInstance()
	{
		instance = this;
	}
}
