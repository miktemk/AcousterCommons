package org.acouster.antsStoned.desktop;

import org.acouster.antsStoned.LevelConfig;
import org.acouster.antsStoned.LevelDifficulty;


public class OptionsStruct
{
	protected int skin;
	protected boolean doSounds;
	protected int nTries, minNStones, minNAntsPerStone, crawlSpeed;
	
	protected LevelConfig config;
	
	public OptionsStruct() {
		//this(LevelConfig.SKIN_ANTS_AND_STONES, true, 5, false, false, false, 0, false);
		this (LevelConfig.SKIN_ANTS_AND_STONES,
				true,
				LevelDifficulty.DEFAULT_NTRIES,
				25,
				LevelDifficulty.DEFAULT_NANTSMAX,
				10);
	}
	public OptionsStruct(
			int skin,
			boolean doSounds,
			int nTries,
			int minNStones,
			int minNAntsPerStone,
			int crawlSpeed)
	{
		super();
		this.skin = skin;
		this.doSounds = doSounds;
		this.nTries = nTries;
		this.minNStones = minNStones;
		this.minNAntsPerStone = minNAntsPerStone;
		this.crawlSpeed = crawlSpeed;
		config = new LevelConfig();
	}

	// ---------- mapping between this and LevelConfig -------------
	public void UpdateLevelConfig()
	{
		config.skin = skin; // we trust xml has proper values for this sort of shit
		config.doSounds = doSounds;
		config.difficultyStruct.setValues(nTries, minNStones, crawlSpeed/10.0, false);
		
//		//config.antCrawlSpeed =
//		if (customDufficulty)
//		{
//			config.nStones = 10; // TODO: nstones
//			config.setCustomDifficulty(
//					gameEndsWhenFail,
//					oneAntAtATime,
//					crawlSpeed,
//					enableHints
//					);
//		}
//		else
//		{
//			config.nStones = 10; // TODO: nstones
//			int difficultyValue = SeekBarPreference_difficulty.difficulty2Enum(difficulty);
//			//Log.v(DebugUtil.TAG, "difficultyValue = " + difficultyValue);
//			config.setDifficultyLevel(difficultyValue);
//		}
	}
	
	public LevelConfig levelConfig()
	{
		return config;
	}

	// singleton
	protected static OptionsStruct instance;
	public static OptionsStruct instance()
	{
		return instance;
	}
	public void makeInstance()
	{
		instance = this;
	}
}
