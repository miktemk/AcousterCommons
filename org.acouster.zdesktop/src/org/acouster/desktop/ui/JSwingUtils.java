package org.acouster.desktop.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.LayoutManager;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import org.acouster.desktop.ImageObserverUtil;

public class JSwingUtils
{
    public static Container makeByBorderLayout(
    		Container pane,
    		Component center,
    		Component top,
    		Component right,
    		Component bottom,
    		Component left)
    {
        pane.setLayout(new BorderLayout());
        if (center != null)
        	pane.add(center, BorderLayout.CENTER);
        if (right != null)
        	pane.add(right, BorderLayout.EAST);
        if (left != null)
        	pane.add(left, BorderLayout.WEST);
        if (top != null)
        	pane.add(top, BorderLayout.NORTH);
        if (bottom != null)
        	pane.add(bottom, BorderLayout.SOUTH);
        return pane;
    }
    
    /** omits right side... */
    public static Container makeByBorderLayoutLeaveRoomForToolbar(
    		Container pane,
    		Component center,
    		Component top,
    		Component bottom,
    		Component left)
    {
    	return makeByBorderLayout(pane, center, top, null, bottom, left);
    }
    
    /**
     * @param orientation - JSplitPane.HORIZONTAL_SPLIT or JSplitPane.VERTICAL_SPLIT
     */
    public static JSplitPane makeByJSplitPane(int orientation, Component c1, Component c2, int dividerLocation)
    {
    	JSplitPane splits = new JSplitPane(orientation, c1, c2);
        splits.setDividerLocation(dividerLocation);
    	return splits;
    }
}
