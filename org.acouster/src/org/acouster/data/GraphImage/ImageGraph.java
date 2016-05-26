package org.acouster.data.GraphImage;

import java.util.List;
import java.util.Vector;

import org.acouster.Graph;
import org.acouster.IFunc;
import org.acouster.util.GraphUtils;

public class ImageGraph
{
	private List<ImageCommand> commands;
	
	public ImageGraph()
	{
		commands = new Vector<ImageCommand>();
	}
	
	public List<? extends ImageCommand> getCommands()
	{
		return commands;
	}
	
	public void addCommand(ImageCommand seq)
	{
		commands.add(seq);
	}
	
	//================ logic ========================
	
	public <T> Graph<T> compile(IFunc<ImageNode, T> func)
	{
		Graph<T> result = GraphUtils.fromCommandList(getCommands(), func);
		//result.printAllPaths();
		//images.gotoFirstNode();
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	//---------------- helper classes --------------
	
//	class ImageQueryOldish implements IFuncSolo<BitmapWithTransform>, IImageQuery
//	{
//		private String query;
//		private HashMap<String, String> varMap;
//		private HashMap<String, BitmapWithTransform> imageMap;
//		private IFunc<IImageQuery, BitmapWithTransform> id2image;
//
//		public ImageQueryOldish(
//				String query,
//				HashMap<String, String> varMap,
//				HashMap<String, BitmapWithTransform> imageMap,
//				IFunc<IImageQuery, BitmapWithTransform> id2image)
//		{
//			super();
//			this.query = query;
//			this.varMap = varMap;
//			this.imageMap = imageMap;
//			this.id2image = id2image;
//		}
//
//		@Override
//		public BitmapWithTransform lambda()
//		{
//			String request = query;
//			if (varMap.size() > 0)
//			{
//				StringBuilder varString = new StringBuilder();
//				for (String key : varMap.keySet())
//				{
//					String value = varMap.get(key);
//					if (varString.length() > 0)
//						varString.append("&");
//					varString.append(key + "=" + value);
//				}
//				request += "?" + varString.toString();
//			}
//			if (imageMap.containsKey(request))
//			{
//				return imageMap.get(request);
//			}
//			// TODO: precache at the top!!!!!!!!
//			BitmapWithTransform img = id2image.lambda(this);
//			imageMap.put(request, img);
//			return img;
//		}
//
//		@Override
//		public String getBody() {
//			return query;
//		}
//
//		@Override
//		public String getVariable(String name) {
//			return varMap.get(name);
//		}
//
//	}
}
