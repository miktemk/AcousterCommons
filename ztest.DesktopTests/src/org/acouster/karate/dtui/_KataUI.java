package org.acouster.karate.dtui;


import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.TransferHandler;

import org.acouster.IFunc;
import org.acouster.IFuncVoid;
import org.acouster.desktop.ui.JFrameMine;
import org.acouster.desktop.ui.JSwingUtils;
import org.acouster.desktop.ui.LeftToolbar;
import org.acouster.desktop.ui.NextPrevButtonsWithTicker;

public class _KataUI extends JFrameMine
{
	public static void main(String[] args) {
		new _KataUI(
				"../zterminal.Karate/assets/xml/new_kata_test.xml",
				"C:\\Users\\miktemk\\Desktop\\merde\\kataimg");
	}

	
	//========================================================================
	
	
	
	// ui
	private ImageGrid<ImageTuple> imageGrid;
	// actions
	private ActionShit actionz;
	private ImageGrid<ImageTuple> timeline;
	
	public _KataUI(String xmlPath, String imgPath)
	{
		super("Kata UI: " + xmlPath);
		
		ModelShit model = new ModelShit(xmlPath, imgPath);
		actionz = new ActionShit(model);
		
		addByJSplitPane(JSplitPane.VERTICAL_SPLIT,
				JSwingUtils.makeByJSplitPane(JSplitPane.HORIZONTAL_SPLIT,
						makeComponent_mainTimeline(model),
						makeComponent_toolbar(model), 600),
				JSwingUtils.makeByJSplitPane(JSplitPane.HORIZONTAL_SPLIT,
						makeComponent_mediaLib(model),
						makeComponent_preview(model), 600),
				450);
				
		updateUI();
	}
	
	private Component makeComponent_preview(ModelShit model)
	{
		return new JPanel();
	}

	private Component makeComponent_mediaLib(ModelShit model)
	{
		imageGrid = new ImageGrid<ImageTuple>(model, 100, 100);
		for (ImageTuple img : model.getImages())
			imageGrid.addImg(img);
		// TODO: drag and drop
		imageGrid.setClickidyListener(new IFuncVoid<ImageTuple>() {
			@Override
			public void lambda(ImageTuple value) {
				//actionz.addImageFromLibrary(value);
				timeline.addImg(value);
				timeline.updateUI();
			}
		});
		//return new JScrollPane(imageGrid, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		return new JScrollPane(imageGrid);
	}

	private Component makeComponent_toolbar(final ModelShit model)
	{
		LeftToolbar bar = new LeftToolbar();
		bar.add(new NextPrevButtonsWithTicker(0, 3, new IFuncVoid<Integer>() {
			@Override
			public void lambda(Integer value) {
				model.setAngle(value); //////////////////////////////////////////////////////////// ANGLE CHANGED!!!
				imageGrid.updateUI();
				timeline.updateUI();
			}
		}).setCustomLabelCallback(new IFunc<Integer, String>() {
			@Override
			public String lambda(Integer value) {
				return "" + (value*90);
			}
		}).setWrap(true));
		
		
		draggyTets(bar);
		
		
		return bar;
	}

	private void draggyTets(LeftToolbar bar) {
		DnDButton button = new DnDButton ("This is the text");
//		TransferHandler transfer = new TransferHandler("text");
//
//		//the JButton can now be dragged and the text will be dropped
//		button.setTransferHandler(transfer);
//
//		//add a MouseListener to initiate the Drag on the appropriate
//		//MouseEvent
//		button.addMouseListener(new MouseAdapter(){
//		            public void mousePressed(MouseEvent e){
//		                JButton button = (JButton)e.getSource();
//		                TransferHandler handle = button.getTransferHandler();
//		                handle.exportAsDrag(button, e, TransferHandler.COPY);
//		            }
//		        });
		
		bar.add(button);
	}

	private Component makeComponent_mainTimeline(ModelShit model)
	{
		// TODO: make a 
		timeline = new KataUiTimeline(model, 100, 100);
		return new JScrollPane(timeline);
	}

}

