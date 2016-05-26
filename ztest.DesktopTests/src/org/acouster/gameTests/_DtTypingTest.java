package org.acouster.gameTests;

import java.io.IOException;

import org.acouster.context.desktop.DesktopResourceContext;
import org.acouster.desktop.ui.EmulatorWindow;
import org.acouster.justTyping.Constants;
import org.acouster.justTyping.GameMain;
import org.acouster.justTyping.KeyMapDvorak;
import org.acouster.justTyping.KeyMapQwerty;
import org.acouster.justTyping.xml.XmlGame;
import org.acouster.simplexml.ObjectFactory;

public class _DtTypingTest
{
	public static void main(String[] args) {
		new DesktopResourceContext().makeInstance();
		DesktopResourceContext.myInstance().setAssetPrefix( "../zterminal.JustTyping/assets");
		DesktopResourceContext.myInstance().setBitmapPrefix("../zterminal.JustTyping/res/drawable-hdpi");
		DesktopResourceContext.myInstance().setExternalDir("C:\\Users\\mtemkine\\Desktop");
		new EmulatorWindow() {
			@Override
			public void onInit() {
				//GameMain gggame = new GameMain(this, new KeyMapDvorak());
				GameMain gggame = new GameMain(this, new KeyMapQwerty(), Constants.Filenames.GameConfig_QWERTY);
	    		setGame(gggame);
	    		try {
					XmlGame xml = ObjectFactory.parseXml(XmlGame.class, DesktopResourceContext.myInstance().OpenAssetFile("game_qwerty.xml"));
					gggame.beginLevel(xml.levels.get(0));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
	}
}
