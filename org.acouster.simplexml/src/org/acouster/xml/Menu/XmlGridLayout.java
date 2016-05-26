package org.acouster.xml.Menu;

import java.util.List;
import java.util.Vector;

import org.acouster.graphics.ui.GridLayoutBlock;
import org.acouster.graphics.ui.UILayoutManagerGrid;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;


public class XmlGridLayout
{
	@ElementList(inline=true, entry="profile")
	public List<XmlGridLayoutProfile> profiles;
	
	public static class XmlGridLayoutProfile
	{
		@Attribute
		public String name;
		@Attribute
		public int w, h;
		
		@ElementList(required=false, inline=true, entry="block")
		public List<XmlGridLayoutBlock> blocks;
		
		public XmlGridLayoutProfile()
		{
			blocks = new Vector<XmlGridLayout.XmlGridLayoutBlock>();
		}
	}
	
	public static class XmlGridLayoutBlock
	{
		@Attribute
		public int x, y, w, h;
		
		@ElementList(required=false, inline=true, entry="block")
		public List<XmlGridLayoutBlock> blocks;
		
		public XmlGridLayoutBlock()
		{
			blocks = new Vector<XmlGridLayout.XmlGridLayoutBlock>();
		}
	}
	
	
	
	
	//=========================
	
	public UILayoutManagerGrid compile()
	{
		List<GridLayoutBlock> blocks = new Vector<GridLayoutBlock>();
	
		// TODO: compile multiple profiles
		XmlGridLayoutProfile profile = profiles.get(0);
		compileBlocks(blocks, profile.blocks, 0, 0, profile.w, profile.h);
		UILayoutManagerGrid layout = new UILayoutManagerGrid(profile.w, profile.h, blocks);
		
		return layout;
	}

	private void compileBlocks(List<GridLayoutBlock> result, List<XmlGridLayoutBlock> blocks, int x, int y, int w, int h)
	{
		for (XmlGridLayoutBlock bbb : blocks)
		{
			if (bbb.blocks.size() == 0)
				result.add(new GridLayoutBlock(x + bbb.x, y + bbb.y, bbb.w, bbb.h));
			else
				compileBlocks(result, bbb.blocks, x + bbb.x, y + bbb.y, bbb.w, bbb.h);
		}
	}
}
