package acdev.tools;

import java.io.File;
import java.io.IOException;

import org.acouster.simplexml.ObjectFactory;
import org.acouster.xml.AnimatedStroke.XmlAnimatedStroke;
import org.acouster.xml.AnimatedStroke.XmlAnimatedStrokeFrame;

public class PenXmlToHardcode
{
	final String filename = "../org.acouster.EyeFitness/data/round_counter_clock.xml";
	
	public PenXmlToHardcode()
	{
		File fff = new File(filename);
		String funcName = "hardcodedPen_" + fff.getName().replace(".", "_");
		XmlAnimatedStroke xml = null;
		try {
			xml = ObjectFactory.loadXml(XmlAnimatedStroke.class, filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
		print("public static AnimatedStroke " + funcName + "() {");
		print("\tAnimatedStroke s = new AnimatedStroke(" + xml.getRgb() + ");");
		for (XmlAnimatedStrokeFrame frame : xml.frames)
		{
			print("\ts.append(" + frame.time + ", " + frame.x + "f, " + frame.y + "f, " + frame.width + ", " + frame.isNew + ");");
		}
		print("\treturn s;");
		print("}");
	}
	
	private void print(Object o)
	{
		System.out.println(o);
	}
	
	public static void main(String[] args)
	{
		new PenXmlToHardcode();
	}

}
