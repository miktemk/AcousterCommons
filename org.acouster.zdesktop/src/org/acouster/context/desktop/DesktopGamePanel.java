package org.acouster.context.desktop;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import org.acouster.Game;
import org.acouster.IFuncVoid;
import org.acouster.context.ContextKeyEvent;
import org.acouster.context.ContextMouseEvent;
import org.acouster.desktop.ImagePipelineShitter;

public class DesktopGamePanel extends JPanel implements MouseListener, MouseMotionListener, KeyListener
{
	private static final String FILENAME_HAND_UP = "../org.acouster.zdesktop/data/hand_up_800.png";
	private static final String FILENAME_HAND_DOWN = "../org.acouster.zdesktop/data/hand_down_800.png";
	private static final String FILENAME_HAND_ARRR = "../org.acouster.zdesktop/data/hand_open_800.png";
	private static final String FILENAME_HAND_FIST = "../org.acouster.zdesktop/data/hand_fist_800.png";
	private static final int OFFSET_HAND_DOWN_X = 150;
	private static final int OFFSET_HAND_DOWN_Y = 280;
	private static final int OFFSET_HAND_UP_X = 150;
	private static final int OFFSET_HAND_UP_Y = 280;
	private static final int OFFSET_HAND_ARRR_X = 150;
	private static final int OFFSET_HAND_ARRR_Y = 280;
	private static final int OFFSET_HAND_FIST_X = 150;
	private static final int OFFSET_HAND_FIST_Y = 150;
    
	private Game game;
    //private AnimationRepainter repainter;
    private int prevKeyCode;
    private DesktopContextGraphics ggg;
    // video recording
	private boolean recordInProgress = false;
//	private boolean recordInProgress = true; //////////// DEBUG: set this to true for quick recording when u have many trials and u keep skrewing up...
	private int recordInProgress_index = 0;
	private ImagePipelineShitter imageWriter;
	// hand :)
	private ContextBitmap_BufferedImage imgHandUp, imgHandDown, imgHandArrr, imgHandFist;
	private boolean handVisible = false;
//	private boolean handVisible = true; //////////// DEBUG: set this to true for quick recording when u have many trials and u keep skrewing up...
	private boolean debuggHelpzz = true;
	private boolean mmmDown = false, mmmArrr = false, mmmFist = false, mmmDownOneFrameNotYetDrawn = false, mmmVisible = false;
	private int mmmX = 0, mmmY = 0;
	
    public DesktopGamePanel()
    {
    	this(null);
    }
    public DesktopGamePanel(Game game)
    {
    	setGame(game);
    	
    	addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        setFocusable(true);
        prevKeyCode = 0;
        
        // context environment
        ggg = new DesktopContextGraphics();
        try {
			imgHandUp = new ContextBitmap_BufferedImage(ImageIO.read(new File(FILENAME_HAND_UP)));
			imgHandDown = new ContextBitmap_BufferedImage(ImageIO.read(new File(FILENAME_HAND_DOWN)));
			imgHandArrr = new ContextBitmap_BufferedImage(ImageIO.read(new File(FILENAME_HAND_ARRR)));
			imgHandFist = new ContextBitmap_BufferedImage(ImageIO.read(new File(FILENAME_HAND_FIST)));
	    } catch (IOException e) {
			e.printStackTrace();
		}
        imageWriter = new ImagePipelineShitter();
    }
    
    public Game getGame()
    {
        return game;
    }
    
    public void setHandVisible(boolean value) {
    	handVisible = value;
	}
    public boolean isHandVisible() {
		return handVisible;
	}
    public void setDebugVisible(boolean value) {
    	debuggHelpzz = value;
	}
    public boolean isDebugVisible() {
		return debuggHelpzz;
	}
    public boolean isRecording() {
		return recordInProgress;
	}
	public void startRecording() {
		recordInProgress = true;
	}
	public void stopRecording() {
		recordInProgress = false;
	}
	public void setRecordingQueueSizeChangedListener(IFuncVoid<Integer> queueSizeChanged) {
		imageWriter.setQueueSizeChangedListener(queueSizeChanged);
	}
    
    // GameContext overrides
	public void setGame(Game game)
    {
    	if (game == null)
    		return;
    	this.game = game;
        AnimationThread thread = new AnimationThread(game);
        thread.start();
    }
    
	public void paint(Graphics g1)
    {
    	paint_inner(g1, false);
        if (recordInProgress)
        {
        	BufferedImage bi = new BufferedImage(720, 480, BufferedImage.TYPE_INT_ARGB); 
        	Graphics g = bi.createGraphics();
        	g.translate(-(getWidth()-722)/2-1, -(getHeight()-482)/2-1);
        	paint_inner(g, true);  //this == JComponent
        	g.dispose();
        	imageWriter.pushImage(bi, "video/frame_" + String.format("%05d", recordInProgress_index) + ".png");
        	recordInProgress_index++;
        }
    }
    
    private void paint_inner(Graphics g1, boolean forOffscreen)
    {
    	Graphics2D g = (Graphics2D)g1;
        ggg.setGraphics2D(g);
        ggg.setImageObserver(this);
        
        //BasicGraphics.paintBackground(ggg, getWidth(), getHeight(), 0xFFFFFF);
        if (game != null)
        {
            game.paintCharacters(ggg, getWidth(), getHeight());
            if (!forOffscreen && debuggHelpzz)
            	game.drawHints(ggg, getWidth(), getHeight());
        }
        
        // frame 720x480
        if (!forOffscreen && recordInProgress)
        {
        	ggg.setStroke(0xFF0000, 1);
        	ggg.drawRect((getWidth()-722)/2, (getHeight()-482)/2, 722, 482);
        }
        
        // handdddd
       	if (handVisible && mmmVisible && (!debuggHelpzz || forOffscreen))
        {
    		if (mmmDown || mmmDownOneFrameNotYetDrawn)
    		{
            	ggg.drawImage(imgHandDown, mmmX-OFFSET_HAND_DOWN_X, mmmY-OFFSET_HAND_DOWN_Y, imgHandDown.getWidth(), imgHandDown.getHeight());
            	mmmDownOneFrameNotYetDrawn = false;
    		}
    		else if (mmmArrr)
    			ggg.drawImage(imgHandArrr, mmmX-OFFSET_HAND_ARRR_X, mmmY-OFFSET_HAND_ARRR_Y, imgHandArrr.getWidth(), imgHandArrr.getHeight());
    		else if (mmmFist)
    			ggg.drawImage(imgHandFist, mmmX-OFFSET_HAND_FIST_X, mmmY-OFFSET_HAND_FIST_Y, imgHandFist.getWidth(), imgHandFist.getHeight());
    		else
    			ggg.drawImage(imgHandUp, mmmX-OFFSET_HAND_UP_X, mmmY-OFFSET_HAND_UP_Y, imgHandUp.getWidth(), imgHandUp.getHeight());
        }
    }
    
    public void mouseClicked(MouseEvent arg0)
    {
        if (game != null)
            game.mouseClicked(toContextMouseEvent(arg0));
    }
    public void mouseEntered(MouseEvent arg0)
    {
        if (game != null)
            game.mouseEntered(toContextMouseEvent(arg0));
        mmmVisible = true;
    }
    public void mouseExited(MouseEvent arg0)
    {
        if (game != null)
            game.mouseExited(toContextMouseEvent(arg0));
        mmmVisible = false;
    }
    public void mousePressed(MouseEvent arg0)
    {
        if (game != null)
            game.mousePressed(toContextMouseEvent(arg0));
        mmmDown = true;
        mmmDownOneFrameNotYetDrawn = true;
        updateFist(arg0);
    }
    public void mouseReleased(MouseEvent arg0)
    {
        if (game != null)
            game.mouseReleased(toContextMouseEvent(arg0));
        mmmDown = false;
        updateFist(arg0);
    }
    public void mouseDragged(MouseEvent arg0)
    {
        if (game != null)
            game.mouseDragged(toContextMouseEvent(arg0));
        mmmVisible = true;
        mmmX = arg0.getX();
        mmmY = arg0.getY();
        updateFist(arg0);
    }
    public void mouseMoved(MouseEvent arg0)
    {
        if (game != null)
            game.mouseMoved(toContextMouseEvent(arg0));
        mmmVisible = true;
        mmmX = arg0.getX();
        mmmY = arg0.getY();
        updateFist(arg0);
    }
    private void updateFist(MouseEvent e)
    {
    	if (e.getModifiersEx() == MouseEvent.SHIFT_DOWN_MASK)
        	mmmFist = true;
        else if (e.getModifiersEx() == MouseEvent.CTRL_DOWN_MASK)
        	mmmArrr = true;
        else
        	mmmFist = mmmArrr = false;
	}
	public void keyPressed(KeyEvent arg0)
    {
    	if (game == null)
    		return;
    	
        if (arg0.getKeyCode() != prevKeyCode)
        	game.keyPressed(toContextKeyEvent(arg0));
        
        prevKeyCode = arg0.getKeyCode();
    }
    public void keyReleased(KeyEvent arg0)
    {
    	if (game == null)
    		return;
    	
    	game.keyReleased(toContextKeyEvent(arg0));
        prevKeyCode = 0;
    }
    public void keyTyped(KeyEvent arg0)
    {
    	if (game == null)
    		return;
    	
    	game.keyTyped(toContextKeyEvent(arg0));
    }
    
    //privates
    private ContextMouseEvent toContextMouseEvent(MouseEvent e)
    {
    	return new ContextMouseEvent(e.getX(), e.getY(), e.getID());
    }
    private ContextKeyEvent toContextKeyEvent(KeyEvent e)
    {
    	return new ContextKeyEvent(e.getKeyCode(), e.getID());
    }
	
}
