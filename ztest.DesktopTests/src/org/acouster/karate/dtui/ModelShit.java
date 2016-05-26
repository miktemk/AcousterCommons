package org.acouster.karate.dtui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.acouster.IFunc;
import org.acouster.karate.xml.XKata;
import org.acouster.simplexml.ObjectFactory;
import org.acouster.util.ListUtils;



public class ModelShit implements IAngleContext
{
	private XKata kata;
	private ImageTuple[] images;
	private int nImages;
	private int angle = 0;
	
	public ModelShit(String xmlPath, String imgPath)
	{
		try {
			kata = ObjectFactory.loadXml(XKata.class, xmlPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		File[] imgFiles = new File(imgPath).listFiles();
		String[] imgKeys = getImage4wayTupleNames(imgFiles);
		List<File> imgFilesList = Arrays.asList(imgFiles);
		images = new ImageTuple[imgKeys.length];
		HashMap<String, ImageTuple> imgMap = new HashMap<String, ImageTuple>(); // do we need imgMap?
		
		int iii = 0;
		for (String sss : imgKeys)
		{
			//System.out.println(sss);
			File file0 = ListUtils.firstOrDefault(imgFilesList, makeSearchLambda(sss, 0));
			File file90 = ListUtils.firstOrDefault(imgFilesList, makeSearchLambda(sss, 90));
			File file180 = ListUtils.firstOrDefault(imgFilesList, makeSearchLambda(sss, 180));
			File file270 = ListUtils.firstOrDefault(imgFilesList, makeSearchLambda(sss, 270));
			
			if (file0 == null)
				System.err.println("missing an angle 0 for " + sss);
			if (file90 == null)
				System.err.println("missing an angle 90 for " + sss);
			if (file180 == null)
				System.err.println("missing an angle 180 for " + sss);
			// ... and if so, then skip
			if (file0 == null || file90 == null || file180 == null)
				continue;
			
			ImageTuple tuple;
			try {
				tuple = makeImageTuple(sss, file0, file90, file180, file270);
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}
			imgMap.put(sss, tuple);
			images[iii] = tuple;
			iii++;
		}
		nImages = iii;
	}

	//---------------- getters ----------------------
	
	public ImageTuple[] getImages() {
		return images;
	}
	public int getNImages() {
		return nImages;
	}
	public int getAngle() {
		return angle;
	}
	public void setAngle(int angle) {
		if (angle < 0)
			angle = 0;
		this.angle = angle % 4;
	}
	public void incAngle() {
		setAngle(angle+1);
	}
	public void decAngle() {
		setAngle(angle-1);
	}
	
	//---------------- privates for loading ----------------------
	

	private ImageTuple makeImageTuple(String key, File file0, File file90, File file180, File file270) throws IOException
	{
		BufferedImage bi90 = ImageIO.read(file90);
		ImageTuple ttt = new ImageTuple(new BufferedImage[] {
				ImageIO.read(file0),
				bi90,
				ImageIO.read(file180),
				bi90,
		}, true, key);
		if (file270 != null)
		{
			ttt.images[3] = ImageIO.read(file270);
			ttt.isSymmetrical = false;
		}
		return ttt;
	}

	private IFunc<File, Boolean> makeSearchLambda(final String sss, final int angle) {
		return new IFunc<File, Boolean>() {
			@Override
			public Boolean lambda(File value) {
				if (value.getName().equals(sss + "_" + angle + ".png"))
					return true;
				return false;
			}
		};
	}

	private String[] getImage4wayTupleNames(File[] imgFiles)
	{
		HashSet<String> uniqueFilenames = new HashSet<String>();
		Pattern pattern = Pattern.compile("^(.*)_(0|90|180|270)\\.(png|jpg|gif)$");
		for (File imgFile : imgFiles)
		{
			String fff = imgFile.getName();
			Matcher mo = pattern.matcher(fff);
			if (!mo.matches())
				continue;
			//fff = mo.group(1) + "." + mo.group(3);
			fff = mo.group(1);
			uniqueFilenames.add(fff);
		}
		String[] uniqueArr = uniqueFilenames.toArray(new String[uniqueFilenames.size()]);
		Arrays.sort(uniqueArr);
		return uniqueArr;
	}

	
}
