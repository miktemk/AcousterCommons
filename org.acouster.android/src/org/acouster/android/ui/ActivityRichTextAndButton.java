package org.acouster.android.ui;

import org.acouster.android.R;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ActivityRichTextAndButton extends BaseActivity
{
	protected Class<?> whereTo;
	protected boolean irreversible;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rich_text_panel_and_bottom_button);
        Button bbb = (Button)findViewById(R.id.bottom_button);
    	bbb.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (irreversible)
					ActivityRichTextAndButton.this.finish();
				if (whereTo != null)
					ActivityRichTextAndButton.this.startDestinationActivity();
			}
		});
    }
    
    protected void startDestinationActivity()
	{
		startActivity(new Intent(this, whereTo));
	}
    
    public void setButtonText(String text)
    {
    	Button bbb = (Button)findViewById(R.id.bottom_button);
    	bbb.setText(text);
    }
    
    public void setRichText(String text)
    {
    	TextView bbb = (TextView)findViewById(R.id.rich_text_view);
    	bbb.setText(text);
    }
    
    public void setRichTextAsHtml(String text)
    {
    	TextView bbb = (TextView)findViewById(R.id.rich_text_view);
    	bbb.setText(Html.fromHtml(text, new ImageGetter() {
			@Override
			public Drawable getDrawable(String source) {
				return null;
			}
		}, null));
    }
    
    /** @param whereTo - pass null if you want OK button just to go back
     * @param irreversible -  When back button is pressed we don't go back to it */
    public void setDestination(Class<?> whereTo, boolean irreversible)
    {
    	this.whereTo = whereTo;
    	this.irreversible = irreversible;
    }
    
    /** setDestination(null, true) */
    protected void setDestinationDeadEnd() {
    	setDestination(null, true);
	}

}
