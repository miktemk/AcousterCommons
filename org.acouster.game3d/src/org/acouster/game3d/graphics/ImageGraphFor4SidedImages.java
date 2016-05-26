//package org.acouster.game3d.graphics;
//
//import org.acouster.Graph;
//import org.acouster.INavigableGraph;
//import org.acouster.data.GraphImage.ImageNode;
//import org.acouster.graphics.BitmapWithTransform;
//
//public class ImageGraphFor4SidedImages implements INavigableGraph<BitmapWithTransform>
//{
//	private Graph<Image4Sided> imageStructures;
//	private double angle;
//	private boolean isOppositeSide;
//
//	public ImageGraphFor4SidedImages(Graph<Image4Sided> imageStructures)
//	{
//		this.imageStructures = imageStructures;
//	}
//	
//	
//	public double getAngle() {
//		return angle;
//	}
//	public int getRoundedAngle() {
//		return (int)(90 * (Math.round(angle/90) % 4));
//	}
//	public void setAngle(double angle) {
//		this.angle = angle;
//	}
//	public boolean isOppositeSide() {
//		return isOppositeSide;
//	}
//	public void setOppositeSide(boolean isOppositeSide) {
//		this.isOppositeSide = isOppositeSide;
//	}
//	
//	// ------------ INavigableGraph members -----------------
//
//	@Override
//	public void gotoNode(String nodeKey) {
//		imageStructures.gotoNode(nodeKey);
//	}
//
//	@Override
//	public void gotoFirstNode() {
//		imageStructures.gotoFirstNode();
//	}
//
//	@Override
//	public void advance() {
//		imageStructures.advance();
//	}
//
//	@Override
//	public double getCurrentDelay() {
//		return imageStructures.getCurrentDelay();
//	}
//
//	@Override
//	public BitmapWithTransform getCurrentNode() {
//		Image4Sided cur = imageStructures.getCurrentNode();
//		if (cur == null)
//			return null;
//		return cur.getBitmap(angle, isOppositeSide);
//	}
//
//
////	public ImageNode getCurrentImageNode() {
////		Image4Sided cur = imageStructures.getCurrentNode();
////		if (cur == null)
////			return null;
////		return cur.getImageNode();
////	}
//
//}
