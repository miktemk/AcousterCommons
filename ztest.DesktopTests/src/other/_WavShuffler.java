package other;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.SequenceInputStream;
import java.util.Arrays;
import java.util.Vector;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import org.acouster.DebugUtil;
import org.acouster.util.ListUtils;

public class _WavShuffler
{
	public static void main(String[] args)
	{
		new _WavShuffler();
	}
	
	
	final int NUMBER = 200;
	final String folderName = "C:\\WS\\Musica\\italian1wpd";
	final String outName = "merged.wav";
	
	public _WavShuffler()
	{
		File dir = new File(folderName);
		File[] dirs = dir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File arg0, String arg1) {
				if (arg1.contains(outName))
					return false;
				return arg1.toLowerCase().endsWith(".wav");
			}
		});
		
		try {
			WavStitcher.shuffle(dirs, folderName + "\\" + outName, NUMBER);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
