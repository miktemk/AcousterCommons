package org.acouster.graphics;


public class BitmapWithTransformQuery
{
	private String query;
	private boolean flipX, flipY;
	
	public BitmapWithTransformQuery(String query, boolean flipX, boolean flipY) {
		super();
		this.query = query;
		this.flipX = flipX;
		this.flipY = flipY;
	}

	public String getQuery() {
		return query;
	}
	public boolean isFlipX() {
		return flipX;
	}
	public boolean isFlipY() {
		return flipY;
	}
	
	
}
