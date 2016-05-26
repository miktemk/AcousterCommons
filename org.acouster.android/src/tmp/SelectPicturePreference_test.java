package tmp;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.GridView;
import android.widget.LinearLayout;


public class SelectPicturePreference_test extends SelectPicturePreference
{
	public SelectPicturePreference_test(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void initImageAdapter(ImageAdapter addImagesToMe)
	{
		// TODO: uncomment this shit
		//addImagesToMe.addImageId(R.drawable.pic1);
		//addImagesToMe.addImageId(R.drawable.pic2);
	}

}

