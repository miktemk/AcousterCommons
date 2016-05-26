package org.acouster.desktop.tests;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Pattern;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.acouster.Game;
import org.acouster.IFunc;
import org.acouster.data.GraphLogic.FsmGraph;
import org.acouster.desktop.ui.EmulatorWindow;
import org.acouster.desktop.ui.JButtonMine;
import org.acouster.logic.FsmSprite;
import org.acouster.xml.GraphImage.XmlImageGraph;
import org.acouster.xml.ZOther.GraphImageAndLogic;

public class TestorVisuals extends EmulatorWindow
{
	protected IVisualsContext vContext;
	protected File lastFile;
	protected IFunc<File, XmlImageGraph> lambda;
	public TestorVisuals(String dir, String pattern, final IFunc<File, XmlImageGraph> loadFunc, Game gameToPlay, IVisualsContext vContext)
    {
        super("AvatarTestor");
        
        this.lambda = loadFunc;
        this.vContext = vContext;
        gameToPlay.setContext(this);
        setGame(gameToPlay);
        
        File fdir = new File(dir);
        final Pattern regex = Pattern.compile(pattern);
        final File[] files = fdir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return regex.matcher(name).matches();
				//return true;
			}
		});
        final FileWrapper[] fileWrappers = new FileWrapper[files.length];
        for (int i = 0; i < files.length; i++)
        	fileWrappers[i] = new FileWrapper(files[i]);
        
        final JList listView = new JList(fileWrappers);
        listView.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //listView.setPreferredSize(new Dimension(200, 500));
        listView.setMinimumSize(new Dimension(200, 500));
        listView.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent eee) {
				if (eee.getValueIsAdjusting())
					return;
				FileWrapper fw = fileWrappers[listView.getSelectedIndex()];
				File fff = fw.file;
				lastFile = fff;
				reloadLastAvatar();
			}
		});
        
        showToolbar();
        getContentPane().add(new JScrollPane(listView), BorderLayout.WEST);
        //getContentPane().invalidate();
        //getContentPane().repaint();
        //repaint();
        setSize(1001, 700);
    }
	
	private void updateCommandList()
	{
		toolbar.removeAll();
		
		toolbar.add(new JButtonMine("RELOAD", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				reloadLastAvatar();
			}
		}).setMySize(150, 70));
		
		final XmlImageGraph visuals = vContext.getVisuals();
		if (visuals == null)
		{
			JOptionPane.showMessageDialog(this, "avatar not loaded!");
			return;
		}
		
		// TODO: compile this shit from withing the game
		
//		final FsmGraph curGraph = vContext.getSpriteFsm();
//		String[] commands = curGraph.getCurNodeCommands();
//		for (String command : commands)
//		{
//			final String ccc = command;
//			//DebugUtil.sss("updateing command: " + ccc);
//			toolbar.add(new JButtonMine(ccc, new ActionListener() {
//				@Override
//				public void actionPerformed(ActionEvent e) {
//					visuals.sendFmsCommand(ccc);
//					//updateCommandList();
//				}
//			}));
//		}
		
		toolbar.updateUI();
		
	}
	
	private void reloadLastAvatar()
	{
		if (lastFile == null)
			return;
		
		XmlImageGraph thing = lambda.lambda(lastFile);
		
		vContext.setVisuals(thing);
		updateCommandList();
	}
	
	
	
	
	
	
	public interface IVisualsContext
	{
		void setVisuals(XmlImageGraph visuals);
		XmlImageGraph getVisuals();
	}
	
	public class FileWrapper
	{
		public File file;
		public FileWrapper(File file)
		{
			this.file = file;
		}
		@Override
		public String toString()
		{
			return file.getName();
		}
	}
	
}
