package xmltests;

import java.io.IOException;

import org.acouster.karate.xml.XKata;
import org.acouster.simplexml.ObjectFactory;

public class _XmlTestsMain
{
	public _XmlTestsMain() throws IOException
	{
		XKata kata = ObjectFactory.loadXml(XKata.class, "../zterminal.Karate/assets/xml/new_kata_test.xml");
	}
	
	public static void main(String[] args) throws IOException
	{
		new _XmlTestsMain();
	}
}
