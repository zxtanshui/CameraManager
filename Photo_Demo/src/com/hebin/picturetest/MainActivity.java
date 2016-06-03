package com.hebin.picturetest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hebin.pagebrowse.PageBrowseActivity;

/**
 * Created by zhangxin on 16/5/31.
 */
public class MainActivity extends Activity implements View.OnClickListener{
    private TextView tv_photo_take;
    private TextView tv_photo_select;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
    public void initView(){
        mContext=MainActivity.this;
        tv_photo_take=(TextView) findViewById(R.id.tv_photo_take);
        tv_photo_select=(TextView) findViewById(R.id.tv_photo_select);

        tv_photo_take.setOnClickListener(this);
        tv_photo_select.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_photo_take:
                Intent intent=new Intent(mContext,MainActivity1.class);
                startActivity(intent);
                break;
            case R.id.tv_photo_select:
                Intent intent1=new Intent(mContext, PageBrowseActivity.class);
                startActivity(intent1);
                break;
        }
    }
}
