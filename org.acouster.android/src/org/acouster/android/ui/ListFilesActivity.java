package org.acouster.android.ui;

import org.acouster.android.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/** Currently this shit does i.putExtra(ListFilesActivity.EXTRA_FileName, item.filename); */ 
public abstract class ListFilesActivity extends ActivityListGeneric<String>
{
	public static final String EXTRA_FileName = "EXTRA_FileName";
	
	@Override
	protected void putExtras(String data, Intent i)
	{
		i.putExtra(ListFilesActivity.EXTRA_FileName, data);
	}
}