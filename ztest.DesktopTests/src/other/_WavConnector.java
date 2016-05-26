package other;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.regex.Pattern;

public class _WavConnector {
	public static void main(String[] args) {
		new _WavConnector();
	}
	
	//////////// change this /////////////////////////
	final String wavpart = "su04";
	final int NUMBER = 52;
	
	// other shit
	final String NUMKEY = "{NUM}";
	final String template = "C:\\Users\\mtemkine.TFO\\Desktop\\shitpile\\" + wavpart + "_" + NUMKEY + ".wav";
	final String outName = "C:\\Users\\mtemkine.TFO\\Desktop\\shitpile\\out\\" + wavpart + ".wav";
	
	public _WavConnector()
	{
		File[] files = new File[NUMBER];
		for (int i = 1; i <= NUMBER; i++)
		{
			String path = template.replace(NUMKEY, String.format("%02d", i));
			files[i-1] = new File(path);
		}
		try {
			WavStitcher.merge(files, outName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
