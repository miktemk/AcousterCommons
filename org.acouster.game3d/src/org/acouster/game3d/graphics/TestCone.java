package org.acouster.game3d.graphics;

import org.acouster.context.ContextGraphics;
import org.acouster.context.ImageManager;
import org.acouster.game3d.math.TransformMatrix3D;
import org.acouster.graphics.BitmapWithTransform;

public class TestCone extends BillBoardSimple {

	private static final String TEST_CONE_FILENAME = "test_cone.png";

	public TestCone(double x, double y, double z)
	{
		super(new BitmapWithTransform(ImageManager.instance().loadFromFile(TEST_CONE_FILENAME), false, false));
		transform.flyby3DConvention_setLocation(x, y, z);
	}

}
