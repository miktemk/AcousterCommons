package acdev.tools;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.acouster.Game;
import org.acouster.context.ContextGraphics;
import org.acouster.context.ContextMouseEvent;
import org.acouster.context.desktop.DesktopResourceContext;
import org.acouster.data.GraphLogic.FsmGraph;
import org.acouster.desktop.ui.EmulatorWindow;
import org.acouster.desktop.ui.JButtonMine;
import org.acouster.graphics.WorldLayer;
import org.acouster.graphics.anim.AnimatedStroke;
import org.acouster.simplexml.ObjectFactory;
import org.acouster.xml.AnimatedStroke.XmlAnimatedStroke;

public class PenCaptureProgram extends Game
{
	public static void main(String[] str)
	{
		new DesktopResourceContext().makeInstance();
		DesktopResourceContext.myInstance().setAssetPrefix("../zterminal.karate/assets");
		DesktopResourceContext.myInstance().setBitmapPrefix("../_res/karate");
		
		PenCaptureProgram kgame = new PenCaptureProgram();
		
		EmulatorWindow w = new EmulatorWindow(
				"Pen capture",
				kgame);
		w.showToolbar();
		kgame.addToolbarButtons(w);
		w.invalidate();
	}
	
	
	//protected FsmGraph curFsm;
	protected WorldLayer layer;
	private AnimatedStroke thing;
	private JButtonMine btnRec;
	private boolean isRecording, isFirst;
	private long d0;
	
	public PenCaptureProgram() {
		super(null);
		
		addLayer(new WorldLayer(0) {
			@Override
			public void render(ContextGraphics g, int w, int h) {
				super.render(g, w, h);
				g.setColor(0xFFFFFF);
				g.fillRect(0, 0, w, h);
				g.setColor(0xDDDDDD);
				g.drawLine(0, h/4, w, h/4);
				g.drawLine(w/4, 0, w/4, h);
				g.drawLine(0, 3*h/4, w, 3*h/4);
				g.drawLine(3*w/4, 0, 3*w/4, h);
				g.setColor(0x777777);
				g.drawLine(0, h/2, w, h/2);
				g.drawLine(w/2, 0, w/2, h);
			}
		});
		addLayer(layer = new WorldLayer(1));
		layer.addRenderable(thing = new AnimatedStroke(0xFF0000));
	}
	
	private void addToolbarButtons(final EmulatorWindow w) {
		w.addToolbarItem(btnRec = new JButtonMine("Start REC", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (btnRec.getText().equals("Start REC"))
				{
					btnRec.setText("Stop REC");
					thing.clear();
					isRecording = true;
					isFirst = true;
				}
				else
				{
					btnRec.setText("Start REC");
					isRecording = false;
				}
			}
		}).setMySize(150, 70));
		w.addToolbarItem(new JButtonMine("Playback", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				thing.playFromNow();
			}
		}).setMySize(150, 70));
		w.addToolbarItem(new JButtonMine("Save", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				if (fc.showSaveDialog(w) != JFileChooser.APPROVE_OPTION)
					return;
				
				//thing.getPoints(); ... to xml
				XmlAnimatedStroke xml = XmlAnimatedStroke.fromAnimatedStroke(thing);
				try {
					ObjectFactory.writeXmlToFile(XmlAnimatedStroke.class, xml, fc.getSelectedFile().getPath());
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(w, "Whoopsie... failed to save!\n" + e1.getMessage());
					e1.printStackTrace();
				}
			}
		}).setMySize(150, 70));
		
		w.updateUI();
	}
	
	@Override
	public void paintCharacters(ContextGraphics g, int w, int h)
	{
		super.paintCharacters(g, w, h);
	}
	
	@Override
	public void mousePressed(ContextMouseEvent e)
	{
		super.mousePressed(e);
		if (!isRecording)
			return;
		long dt = 0;
		if (isFirst)
		{
			d0 = System.currentTimeMillis();
		}
		else
			dt = System.currentTimeMillis() - d0;
		isFirst = false;
		thing.append(dt, (float)e.getX() / curContextWidth, (float)e.getY() / curContextHeight, 10, true);
	}
	@Override
	public void mouseDragged(ContextMouseEvent e)
	{
		super.mouseDragged(e);
		if (!isRecording)
			return;
		long dt = System.currentTimeMillis() - d0;
		thing.append(dt, (float)e.getX() / curContextWidth, (float)e.getY() / curContextHeight, 10, false);
	}
	
	

}
