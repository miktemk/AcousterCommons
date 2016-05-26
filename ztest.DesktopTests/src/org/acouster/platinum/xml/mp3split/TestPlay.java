package org.acouster.platinum.xml.mp3split;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.acouster.DebugUtil;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class TestPlay
{

	public static void main(String[] args) throws FileNotFoundException, JavaLayerException
	{
		new TestPlay();
	}
	
	public TestPlay() throws FileNotFoundException, JavaLayerException
	{
		final String filename = "C:\\Users\\G\\Desktop\\mikeshit\\kaz\\kaz_fast01.mp3";

//		Player mp3 = new Player(new FileInputStream(filename));
//		mp3.play();
		
		SoundJLayer soundToPlay = new SoundJLayer(filename);
        soundToPlay.play();
		
		//DebugUtil.sss("wtf");
	}

}

