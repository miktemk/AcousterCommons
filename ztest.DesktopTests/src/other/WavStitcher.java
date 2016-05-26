package other;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.SequenceInputStream;
import java.util.Arrays;
import java.util.Vector;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import org.acouster.DebugUtil;
import org.acouster.util.ListUtils;

public class WavStitcher
{
	
	public static void shuffle(File[] dirs, String outName, int number) throws IOException
	{
		Vector<File> filePool = new Vector<File>(Arrays.asList(dirs));
		Vector<File> rand = new Vector<File>();
		for (int i = 0; i < number; i++)
		{
			File fff = ListUtils.randomElem(dirs);
			rand.add(fff);
			filePool.remove(fff);
		}
		
		for (File also : filePool)
		{
			rand.add(also);
			//filePool.remove(also);
			DebugUtil.sss("forgot him: " + also);
		}
		
		merge(rand.toArray(new File[rand.size()]), outName);
	}
	public static void merge(File[] dirs, String outName) throws IOException
	{
		final File outFile = new File(outName);
		final File outFileTmp = new File(outName + ".tmp");
		File prev = null, next;
		for (int i = 0; i < dirs.length; i++)
		{
			File fff = dirs[i];
			if (i == 0)
			{
				prev = fff;
				continue;
			}
			next = fff;
			try {
				prev = stitchFiles_ab(prev, next, outFile, outFileTmp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (prev == outFileTmp)
			copyFile(outFileTmp.getPath(), outFile.getPath());
		if (outFileTmp.exists())
			outFileTmp.delete();
	}
	public static File stitchFiles_ab(File prev, File next, File outFile, File outFileTmp) throws Exception
	{
		File outFilez = outFile;
		if (prev == outFilez)
			outFilez = outFileTmp;
		stitchFiles(prev, next, outFilez);
		return outFilez; 
	}
	
	public static void stitchFiles(File prev, File next, File outFilez) throws Exception
	{
		DebugUtil.sss(prev + " + " + next);
		
		AudioInputStream clip1 = AudioSystem.getAudioInputStream(prev);
		AudioInputStream clip2 = AudioSystem.getAudioInputStream(next);
		clip2.skip(1000);
		SequenceInputStream sse = new SequenceInputStream(clip1, clip2);
		
		DebugUtil.sss(clip1.getFrameLength() + " + " + clip2.getFrameLength() + " = " + (clip1.getFrameLength() + clip2.getFrameLength()));
		
		AudioInputStream appendedFiles = new AudioInputStream(
			sse,
			clip1.getFormat(),
			clip1.getFrameLength() + clip2.getFrameLength());
        AudioSystem.write(appendedFiles, AudioFileFormat.Type.WAVE, outFilez);
        
        appendedFiles.close();
        clip1.close();
        clip2.close();
        sse.close();
	}
	
	public static void copyFile(String from, String to) throws IOException
	{
		InputStream in = new FileInputStream(from);
		OutputStream out = new FileOutputStream(to);
		byte[] buf = new byte[1024];
		int len;
		while ((len = in.read(buf)) > 0) {
		   out.write(buf, 0, len);
		}
		in.close();
		out.close();

	}
	
}
