package org.acouster.gameTests.other;

import org.acouster.DebugUtil;
import org.acouster.context.desktop.DesktopResourceContext;
import org.json.simple.JsonField;
import org.json.simple.JsonSerialiser;

public class _JsonTest {
	
	public _JsonTest()
	{
		super();
		
		JsonTestClass diff = new JsonTestClass();
		diff.skinBgFilename = "ahahahah you suck with women"; 
		
		String ser = JsonSerialiser.serialize(diff);
		JsonTestClass diff2 = JsonSerialiser.deserialize(JsonTestClass.class, ser);
		
		//String parseme = "{\"nTries\":\"5\",\"nAntsMin\":\"5\",\"antCrawlSpeedFactor\":\"1.0\",\"nStones\":\"7\",\"nAntsMax\":\"10\",\"doWiggle\":\"false\",\"antCrawlSpeedFactor_\":\"1.0\"}";
		//LevelDifficulty diff2 = JsonSerialiser.deserialize(LevelDifficulty.class, parseme);
		
		DebugUtil.sss(ser);
		DebugUtil.sss(diff);
		DebugUtil.sss(diff2);
	}
	
	public static void main(String[] args) {
		new DesktopResourceContext().makeInstance();
		DesktopResourceContext.myInstance().setAssetPrefix("../zterminal.AntsStoned/assets");
		DesktopResourceContext.myInstance().setBitmapPrefix("../zterminal.AntsStoned/res/drawable-hdpi");
		new _JsonTest();
	}

	
	//=========================================================================================
	
	private static class JsonTestClass
	{
		// ------------ difficulty settings --------------
		@JsonField
		public int nTries,
				nStones,
				nAntsMin,
				nAntsMax;
		@JsonField
		public double antCrawlSpeedFactor,
				antCrawlSpeedFactor_;
		@JsonField
		public boolean doWiggle = false;
		@JsonField
		public int nLosses;
		
		// ------------ difficulty settings --------------
		@JsonField
		public int skinId;
		@JsonField
		public String skinBgFilename;
		
		@Override
		public String toString() {
			return "BundleStruct [nTries=" + nTries + ", nStones=" + nStones
					+ ", nAntsMin=" + nAntsMin + ", nAntsMax=" + nAntsMax
					+ ", antCrawlSpeedFactor=" + antCrawlSpeedFactor
					+ ", antCrawlSpeedFactor_=" + antCrawlSpeedFactor_
					+ ", doWiggle=" + doWiggle + ", nLosses=" + nLosses
					+ ", skinId=" + skinId + ", skinBgFilename=" + skinBgFilename
					+ "]";
		}
		
		
	}

}
