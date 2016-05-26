package org.acouster.platinum.xml.mp3split;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

import org.acouster.IFuncVoid;
import org.acouster.desktop.ui.JButtonMine;
import org.acouster.desktop.ui.JFrameMine;
import org.acouster.platinum.xml.Lesson;
import org.acouster.simplexml.ObjectFactory;

public class _PlatinumGenerator extends JFrameMine implements KeyListener
{
	public static void main(String[] args) throws IOException, JavaLayerException
	{
		new _PlatinumGenerator("C:\\Users\\mtemkine.TFO\\Desktop\\shitpile\\out\\su02.mp3");
		//new _PlatinumGenerator("C:\\Users\\miktemk\\Desktop\\kazzz\\kaz_fast01.mp3");
		//new _PlatinumGenerator("C:\\Users\\G\\Desktop\\mikeshit\\kaz\\");
	}
	
	
	
	public static String CARD_TEXT_AREA = "area";
	public static String CARD_PLATE = "plate";
	
	// view
	protected JLabel topLabel;
	protected JProgressBar progressBar;
	private JTextArea textArea;
	private CurLinePanel curLinePanel;
	private CardLayout centerCards;
	private AdvancedPlayer player;
	
	//model
	private LineSplittingModel model;
	private JButtonMine centerSwitchButton;
	private boolean linesLoaded = false;
	private boolean shitRunning = false;
	
	public _PlatinumGenerator(final String mp3Name) throws IOException
	{
		super("Image harvester");
		
		final String mp3FilePart = new File(mp3Name).getName();
		
		addCenterPanel(centerCards = new CardLayout());
		center.add(new JScrollPane(textArea = new JTextArea(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), CARD_TEXT_AREA);
		center.add(curLinePanel = new CurLinePanel(), CARD_PLATE);
		centerCards.show(center, CARD_TEXT_AREA);
		textArea.setLineWrap(true);
		
		addToolbarLabel();
		addToolbarItem(centerSwitchButton = new JButtonMine("Prepare Lines", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (linesLoaded)
					switchCenter(CARD_TEXT_AREA);
				else
				{
					switchCenter(CARD_PLATE);
					model = new LineSplittingModel(textArea.getText(), mp3FilePart);
					updateCenterState();
				}
			}
		}, false));
		addToolbarItem(new JButtonMine("Play", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (player != null)
					return;
				shitRunning = true;
				final SoundJLayer soundToPlay = new SoundJLayer(mp3Name);
				soundToPlay.setProgressCallback(new IFuncVoid<AdvancedPlayer>() {
					@Override
					public void lambda(AdvancedPlayer value) {
						player = value;
						setLabelText("pos: " + value.getPosition());
					}
				}, new IFuncVoid<AdvancedPlayer>() {
					@Override
					public void lambda(AdvancedPlayer value) {
						model.setCurEndTimeAndNextBeginTime(value.getMaxPosition());
						model.incIndex();
						updateCenterState();
						shitRunning = false;
					}
				});
				soundToPlay.play();
				player = soundToPlay.getPlayer();
				requestFocus();
			}
		}, false));
		addToolbarItem(new JButtonMine("Save", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String path = mp3Name.replace("mp3", "xml");
				if (new File(path).exists() && JOptionPane.showConfirmDialog(_PlatinumGenerator.this, "file exists. overWrite?") != JOptionPane.YES_OPTION)
					return;
				model.saveLesson(path);
			}
		}, false));
		showToolbar();
		updateUI();
		addKeyListener(this);
		
		// load the xml if exists
		String path = mp3Name.replace("mp3", "xml");
		if (new File(path).exists())
		{
			Lesson lll = ObjectFactory.loadXml(Lesson.class, path);
			model = new LineSplittingModel(lll);
			switchCenter(CARD_PLATE);
			updateCenterState();
		}
	}
	
	private void switchCenter(String key)
	{
		centerCards.show(center, key);
		linesLoaded = CARD_PLATE.equals(key);
		if (linesLoaded)
			centerSwitchButton.setText("get other text");
		else
			centerSwitchButton.setText("Prepare lines");
	}
	private void updateCenterState() {
		curLinePanel.setLines(model.line1(), model.line2());
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (!shitRunning)
			return;
		if (e.getKeyCode() != KeyEvent.VK_SPACE)
			return;
		if (model != null) {
			long position = 10;
			if (player != null)
				position = player.getPosition();
			model.setCurEndTimeAndNextBeginTime(position);
			model.incIndex();
			updateCenterState();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {}
	@Override
	public void keyTyped(KeyEvent arg0) {}
}