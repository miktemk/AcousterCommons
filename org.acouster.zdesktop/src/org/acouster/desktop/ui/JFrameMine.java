package org.acouster.desktop.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.acouster.desktop.ImageObserverUtil;

// its a bit rendudndant to reimplements gamecontext
public class JFrameMine extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	protected LeftToolbar toolbar;
	protected JPanel center;
	
	public JFrameMine()
    {
		this("My JFrame");
    }
    public JFrameMine(String title)
    {
        super(title);
        
        ImageObserverUtil.setInstance(this);
        
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });
        
        toolbar = new LeftToolbar();
        
        setSize(900, 700);
        setVisible(true);
    }
    
    public void addByBorderLayout(
    		Component center,
    		Component top,
    		Component right,
    		Component bottom,
    		Component left)
    {
    	JSwingUtils.makeByBorderLayout(getContentPane(), center, top, right, bottom, left);
    }
    /** omits right side... */
    public void addByBorderLayoutLeaveRoomForToolbar(
    		Component center,
    		Component top,
    		Component bottom,
    		Component left)
    {
    	JSwingUtils.makeByBorderLayout(getContentPane(), center, top, null, bottom, left);
    }
    public void addOneAndOnly(Component ccc)
    {
    	JSwingUtils.makeByBorderLayout(getContentPane(), ccc, null, null, null, null);
    }
    
    /**
     * @param orientation - JSplitPane.HORIZONTAL_SPLIT or JSplitPane.VERTICAL_SPLIT
     */
    public void addByJSplitPane(int orientation, Component c1, Component c2, int dividerLocation)
    {
    	addOneAndOnly(JSwingUtils.makeByJSplitPane(orientation, c1, c2, dividerLocation));
    }
    
    public void updateUI() {
    	invalidate();
    	validate();
    	repaint();
    	toolbar.updateUI();
	}
    
    //------------- toolbar ----------------
	public void showToolbar()
    {
    	add(toolbar, BorderLayout.EAST);
    }
	public void addToolbarItem(Component c)
	{
		toolbar.add(c);
	}
	public void addToolbarLabel()
	{
		toolbar.addMyLabel();
	}
	public void setLabelText(String text)
	{
		toolbar.setLabelText(text);
	}
	public void addMyProgressBar()
	{
		toolbar.addMyProgressBar();
	}
	public void setProgress(double outOf1)
	{
		toolbar.setProgress(outOf1);
	}
	public void addCenterPanel()
	{
		addCenterPanel(new BorderLayout());
	}
	public void addCenterPanel(LayoutManager man)
	{
		add(center = new JPanel(man), BorderLayout.CENTER);
	}
}
