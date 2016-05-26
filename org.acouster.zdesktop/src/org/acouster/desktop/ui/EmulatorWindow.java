package org.acouster.desktop.ui;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

import org.acouster.Game;
import org.acouster.GameContext;
import org.acouster.GameEvent;
import org.acouster.IFuncVoid;
import org.acouster.context.desktop.DesktopGamePanel;

// its a bit rendudndant to reimplements gamecontext
public class EmulatorWindow extends JFrameMine implements GameContext, ComponentListener
{
	private static final long serialVersionUID = 1L;
	private static final int JSPLITS_DIVIDE_LOCATION = 490;
	private static final float PIXEL_GRAVITY_FACTOR = 0.00001f;
	private static final float PIXEL_GRAVITY_DEFAULT_DOWN = 10;
	
	protected DesktopGamePanel gamePanel;
	protected JTextArea console;
	private JButtonMine recButton;
	private JCheckBoxMine chkHand, chkDebug;
	private boolean gravityEnabled = false;
    
	public EmulatorWindow()
    {
		this("EmulatorWindow");
    }
	public EmulatorWindow(String title)
    {
		this(title, null);
    }
    public EmulatorWindow(String title, Game gameToPlay)
    {
        super(title);
        
        gamePanel = new DesktopGamePanel();
        console = new JTextArea(12, 200);
        //console.setPreferredSize(new Dimension(700, 150));
        console.setEditable(false);
        console.setWrapStyleWord(true);
        
        addComponentListener(this);
        
        // TODO: add checkboxes for 
        
        addToolbarItem(chkHand = new JCheckBoxMine("hand", gamePanel.isHandVisible(), new IFuncVoid<Boolean>() {
			@Override
			public void lambda(Boolean value) {
				gamePanel.setHandVisible(value);
			}
		}));
        addToolbarItem(chkDebug = new JCheckBoxMine("debugg", gamePanel.isDebugVisible(), new IFuncVoid<Boolean>() {
			@Override
			public void lambda(Boolean value) {
				gamePanel.setDebugVisible(value);
			}
		}).withTooltip("hide hand on screen (still show in video capture)...\n" +
				"also render game hints on screen (not in video)\n" +
				"See? I'm SMART!!! :)"));
        addToolbarItem(recButton = new JButtonMine("REC", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent eee) {
				if (gamePanel.isRecording())
					stopRecording();
				else
					beginRecording();
			}
		}));
        final JLabel queueSizeLabel = new JLabel();
        gamePanel.setRecordingQueueSizeChangedListener(new IFuncVoid<Integer>() {
			@Override
			public void lambda(Integer value) {
				queueSizeLabel.setText("inQ:" + value);
			}
		});
        addToolbarItem(queueSizeLabel);
        
        LeftToolbar toolbar2 = new LeftToolbar();
        toolbar2.add(new JButtonMine("<--", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent eee) {
				Game ggg = gamePanel.getGame();
		    	if (ggg != null && ggg.backButtonPressed())
		    		console.append("SYS BACK BUTTON\n");
			}
		}));
        toolbar2.add(new JButtonMine("clr", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent eee) {
	    		console.setText("");
			}
		}));
        
        addByJSplitPane(JSplitPane.VERTICAL_SPLIT,
        		gamePanel,
        		JSwingUtils.makeByBorderLayout(new JPanel(),
        				new JScrollPane(console, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER),
                		null, toolbar, null, toolbar2),
        		JSPLITS_DIVIDE_LOCATION);
        
        setSize(750, 700);
        validate();
        
        if (gameToPlay != null)
        {
        	gameToPlay.setContext(this);
            setGame(gameToPlay);
        }
        onInit();
        
        gamePanel.requestFocus();
    }
    
    /** override to call setGame() from here */
    public void onInit() {}
    
    //------------ UI programmatic controls -----------------
    public void beginRecording() {
    	gamePanel.startRecording();
    	recButton.setText("stop");
   	}
    public void stopRecording() {
    	gamePanel.stopRecording();
    	recButton.setText("REC");
   	}
    public void setHandVisible(boolean flag) {
    	gamePanel.setHandVisible(flag);
    	if (chkHand.isSelected() != flag)
    		chkHand.setSelected(flag);
   	}
    public void setDebugVisible(boolean flag) {
    	gamePanel.setDebugVisible(flag);
    	if (chkDebug.isSelected() != flag)
    		chkDebug.setSelected(flag);
   	}
    public void setGravityEnabled(boolean b) {
		gravityEnabled = b;
	}
    
    //------------- game ----------------
	public void setGame(Game game)
	{
		gamePanel.setGame(game);
	}

    //------------- GameContext members ----------------
	@Override
	public int getContextHeight() {
		return gamePanel.getHeight();
	}
	@Override
	public int getContextWidth() {
		return gamePanel.getWidth();
	}
	@Override
	public int getContextDiagonal() {
		return gamePanel.getWidth() + gamePanel.getHeight();
	}
	@Override
	public void triggerRepaint() {
		gamePanel.repaint();
	}
	@Override
	public void killActivity() {
		console.append("ACTIVITY KILLED... KJONK!\n");
	}

	@Override
	public void handleMessage(GameEvent message) {
		console.append(message.getTarget() + ": " + message.getBody() + "\n");
	}
	@Override
	public void forceStartThread() {
		console.append("TODO: desktop forceStartThread not implemented\n");
	}
	@Override
	public void forceStopThread() {
		console.append("TODO: desktop forceStopThread not implemented\n");
	}
	@Override
	public void componentHidden(ComponentEvent arg0) {}
	private long lastMoveTime = 0;
	private int prevOnScreenX, prevOnScreenY;
	@Override
	public void componentMoved(ComponentEvent arg0)
	{
		// simulate gravity changes
		long curTime = System.currentTimeMillis();
		float dt = (curTime - lastMoveTime) / 1000.0f;
		// 1 second thres?
		if (dt < 1) {
			if (gamePanel.getGame() != null) {
				Point location = getLocationOnScreen();
				float vx = (location.x - prevOnScreenX) / dt;
				float vy = (location.y - prevOnScreenY) / dt;
				float ax = vx / dt;
				float ay = vy / dt;
				if (gravityEnabled)
					ay += PIXEL_GRAVITY_DEFAULT_DOWN;
				gamePanel.getGame().onAccelerometerChange(PIXEL_GRAVITY_FACTOR * ax, PIXEL_GRAVITY_FACTOR * ay);
				prevOnScreenX = location.x;
				prevOnScreenY = location.y;
			}
		}
		lastMoveTime = curTime;
			
	}
	@Override
	public void componentResized(ComponentEvent arg0) {}
	@Override
	public void componentShown(ComponentEvent arg0) {}
	
    
    //----------------------------------------------

    //TODO: we will need the keylistener here in case the panel fails
//	public void keyPressed(KeyEvent arg0)
//	{
//	    if (arg0.getKeyCode() != prevKeyCode)
//	        p.getGame().keyPressed(arg0);
//	    
//	    prevKeyCode = arg0.getKeyCode();
//	}
//	public void keyReleased(KeyEvent arg0)
//	{
//	    p.getGame().keyReleased(arg0);
//	    
//	    prevKeyCode = 0;
//	}
//	public void keyTyped(KeyEvent arg0)
//	{
//	    p.getGame().keyTyped(arg0);
//	}
    
    //----------------------------------------------

//    public static void main(String[] args)
//    {
//    	Flyby3D_test1 f = new Flyby3D_test1();
//    	DesktopGamePanel p = f.getGamePanel();
//    	p.setGame(new Flyby3DGame(p));
//    }
}
