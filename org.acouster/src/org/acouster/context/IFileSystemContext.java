package org.acouster.context;

import java.io.IOException;
import java.io.InputStream;


public interface IFileSystemContext
{
	ContextBitmap LoadBitmap(String resourceName, boolean preciseSize);
	ContextBitmap LoadBitmapFromFile(String filename);
	/** Loads text from an asset file */
	String LoadText(String resourceName) throws IOException;
	InputStream OpenAssetFile(String resourceName) throws IOException;
	<T> T LoadAny(Class<? extends T> clazz, String filename) throws IOException;
	<T> T LoadAnyExternal(Class<? extends T> clazz, String filename) throws IOException;
}
