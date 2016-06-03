package com.hebin.pagebrowse;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.BaseAdapter;

import com.hebin.picturetest.R;

public class PageBrowseActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagebrowse);
        PageWidget page = (PageWidget) findViewById(R.id.main_pageWidget);
        BaseAdapter adapter = new PageWidgetAdapter(this);
        page.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_pagebrowse, menu);
        return true;
    }

    
}
