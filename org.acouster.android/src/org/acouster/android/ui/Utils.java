package org.acouster.android.ui;

import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class Utils
{
	/** new LinearLayout.LayoutParams(FILL_PARENT, WRAP_CONTENT) */
	public static LinearLayout.LayoutParams getStandardLinearFitParams() {
		if (sharedStandardFitParams == null)
			sharedStandardFitParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		return sharedStandardFitParams;
	}
	private static LinearLayout.LayoutParams sharedStandardFitParams = null;
	
	/** new RelativeLayout.LayoutParams(FILL_PARENT, FILL_PARENT) */
	public static RelativeLayout.LayoutParams getFullRelativeFitParams() {
		if (sharedFullFitParams == null)
			sharedFullFitParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);
		return sharedFullFitParams;
	}
	private static RelativeLayout.LayoutParams sharedFullFitParams = null;
}
