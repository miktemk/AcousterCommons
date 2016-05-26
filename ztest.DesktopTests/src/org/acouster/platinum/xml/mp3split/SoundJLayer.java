package org.acouster.platinum.xml.mp3split;

import org.acouster.Flashable;
import org.acouster.IFuncVoid;
import org.acouster.context.desktop.AnimationThread;

import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

class SoundJLayer extends PlaybackListener implements Runnable
{
    private String filePath;
    private AdvancedPlayer player;
    private Thread playerThread;
    private IFuncVoid<AdvancedPlayer> progressFunc, doneFunc;

    public SoundJLayer(String filePath)
    {
        this.filePath = filePath;
    }
    
    public void setProgressCallback(IFuncVoid<AdvancedPlayer> progressFunc, IFuncVoid<AdvancedPlayer> doneFunc)
    {
    	this.progressFunc = progressFunc;
    	this.doneFunc = doneFunc;
    }

    public void play()
    {
        try
        {
            String urlAsString = 
                "file:///"
            	//+ new java.io.File(".").getCanonicalPath() 
                //+ "/" 
                + this.filePath;

            this.player = new AdvancedPlayer
            (
                new java.net.URL(urlAsString).openStream(),
                javazoom.jl.player.FactoryRegistry.systemRegistry().createAudioDevice()
            );

            this.player.setPlayBackListener(this);
            //player.pla
            playerThread = new Thread(this);
            playerThread.start();
            
            playerThread = new AnimationThread(new Flashable() {
				@Override
				public void flash() {
					//System.out.println("pos: " + player.getPosition());
					if (progressFunc != null)
						progressFunc.lambda(player);
				}
			}, 20);
            playerThread.start();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    // PlaybackListener members

    public void playbackStarted(PlaybackEvent playbackEvent)
    {
        //System.out.println("playbackStarted()");
    }

    public void playbackFinished(PlaybackEvent playbackEvent)
    {
        ///System.out.println("playbackEnded()");
    	if (doneFunc != null)
    		doneFunc.lambda(player);
    }

    // Runnable members

    public void run()
    {
    	try
        {
            this.player.play();
        }
        catch (javazoom.jl.decoder.JavaLayerException ex)
        {
            ex.printStackTrace();
        }
    }

	public AdvancedPlayer getPlayer() {
		return this.player;
	}
}