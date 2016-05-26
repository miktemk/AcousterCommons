package org.acouster.logic;

public class HtmlPositioner
{
	public static final int FIELD_UNINITIALIZED = Integer.MIN_VALUE;
	public static final int FIELD_CENTER = Integer.MIN_VALUE + 1;
	
	private int width, height, cacheX, cacheY, prevContainerWidth, prevContainerHeight;
	private int
		top = FIELD_UNINITIALIZED,
		bottom = FIELD_UNINITIALIZED,
		left = FIELD_UNINITIALIZED,
		right = FIELD_UNINITIALIZED;
	
	public HtmlPositioner() {
		this (0, 0);
	}
	public HtmlPositioner(int width, int height)
	{
		this.width = width;
		this.height = height;
	}
	// builder pattern:
	// http://stackoverflow.com/questions/965690/java-optional-parameters
	// http://en.wikipedia.org/wiki/Builder_pattern
	public HtmlPositioner setTop(int top) {
		this.top = top;
		updateCache();
		return this;
	}
	public HtmlPositioner setBottom(int bottom) {
		this.bottom = bottom;
		updateCache();
		return this;
	}
	public HtmlPositioner setLeft(int left) {
		this.left = left;
		updateCache();
		return this;
	}
	public HtmlPositioner setRight(int right) {
		this.right = right;
		updateCache();
		return this;
	}
	
	public HtmlPositioner setObjectWidth(int width) {
		this.width = width;
		updateCache();
		return this;
	}
	public HtmlPositioner setObjectHeight(int height) {
		this.height = height;
		updateCache();
		return this;
	}
	public HtmlPositioner setObjectDimensions(int width, int height)
	{
		this.height = height;
		this.width = width;
		updateCache();
		return this;
	}
	public HtmlPositioner centerX() {
		left = FIELD_CENTER;
		updateCache();
		return this;
	}
	public HtmlPositioner centerY() {
		top = FIELD_CENTER;
		updateCache();
		return this;
	}

	public int computeX(int containerWidth)
	{
		if (left == FIELD_CENTER)
			return containerWidth / 2 - width/2;
		if (left != FIELD_UNINITIALIZED)
			return left;
		if (right != FIELD_UNINITIALIZED)
			return containerWidth - right - width;
		return 0;
	}
	public int computeY(int containerHeight)
	{
		if (top == FIELD_CENTER)
			return containerHeight / 2 - height/2;
		if (top != FIELD_UNINITIALIZED)
			return top;
		if (bottom != FIELD_UNINITIALIZED)
			return containerHeight - bottom - height;
		return 0;
	}
	
	/** called from Sprite2DHtmlPositioned.dimensionsChanged */
	public void updateCache() {
		updateCache(prevContainerWidth, prevContainerHeight);
	}
	public void updateCache(int containerWidth, int containerHeight)
	{
		cacheX = computeX(containerWidth);
		cacheY = computeY(containerHeight);
		prevContainerWidth = containerWidth;
		prevContainerHeight = containerHeight;
	}
	public int getCacheX() {
		return cacheX;
	}
	public int getCacheY() {
		return cacheY;
	}
	public int getObjectWidth() {
		return width;
	}
	public int getObjectHeight() {
		return height;
	}
	
	
}
