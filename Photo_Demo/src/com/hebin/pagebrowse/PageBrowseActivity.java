package com.hebin.pagebrowse;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hebin.picturetest.R;
import com.hebin.selectpic.imageloader.SelectPicActivity;

import java.util.ArrayList;

public class PageBrowseActivity extends Activity implements View.OnClickListener {
    private TextView tv_photo_select;
    private Context mContext;
    private static final int FLAG_SELECT_PIC = 2;
    private PageWidgetAdapter adapter;
    private ArrayList<String> photoList=new ArrayList<String>();
    private String TAG="PageBrowseActivity";
    private  PageWidget page;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagebrowse);
        mContext=PageBrowseActivity.this;
        tv_photo_select= (TextView) findViewById(R.id.tv_photo_select);



        tv_photo_select.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.activity_pagebrowse, menu);
        return true;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_photo_select:
                photoList.clear();
                Intent intent1 = new Intent(mContext, SelectPicActivity.class);
                startActivityForResult(intent1, FLAG_SELECT_PIC);// 这里采用startActivityForResult来做跳转，此处的0为一个依据，可以写其他的值，但一定要>=0
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (null == data) {
            return;
        }
        if (resultCode == RESULT_OK) {
            if (requestCode == FLAG_SELECT_PIC) {
                final ArrayList<String> result = data
                        .getStringArrayListExtra("result");

                Log.e(TAG,"result-->"+result.toString());
                for(int i=0;i<result.size();i++){
                    photoList.add(result.get(i));
                }
                page = (PageWidget) findViewById(R.id.main_pageWidget);
                adapter = new PageWidgetAdapter(this);
                adapter.setData(photoList);
                page.setAdapter(adapter);
               // adapter.notifyDataSetChanged();
            }
        }

    }
}
