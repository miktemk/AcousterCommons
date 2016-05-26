package org.acouster.android.ui;

import java.util.List;

import org.acouster.android.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public abstract class ActivityListGeneric<T> extends BaseActivity
{
	public class ListActivityElement
	{
		public String title;
		public T data;
		public ListActivityElement(String title, T data) {
			super();
			this.title = title;
			this.data = data;
		}
		@Override
		public String toString()
		{
			return title;	
		}
	}
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutList());
        
        // load the shite
        List<ListActivityElement> listing = CreateList();
		
		final ArrayAdapter<ListActivityElement> adapter = new ArrayAdapter<ListActivityElement>(this, getLayoutListItem(), listing);
		
		ListView lv = getMyListView();
        lv.setAdapter(adapter);
		lv.setTextFilterEnabled(true);
		lv.setOnItemClickListener(new OnItemClickListener() {
        	@Override
        	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        	{
        		if (getSubActivityClass() == null)
        			return;
        		Intent i = new Intent(ActivityListGeneric.this, getSubActivityClass());
        		ListActivityElement item = adapter.getItem(position);
        		putExtras(item.data, i);
        		startActivityForResult(i, getSubActivityId());
			}
        });
    }
    
    protected int getLayoutListItem() {
		return R.layout.basic_list_item;
	}
    protected int getLayoutList() {
		return R.layout.basic_list;
	}
	protected ListView getMyListView() {
		return (ListView)this.findViewById(R.id.list);
	}
    
    protected abstract List<ListActivityElement> CreateList();
    protected abstract Class<?> getSubActivityClass();
    protected abstract int getSubActivityId();
    protected abstract void putExtras(T data, Intent i);
}