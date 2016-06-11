package com.hebin.picturetest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import com.hebin.pagebrowse.PageBrowseActivity;
import com.hebin.scale.ScaleActivity;

/**
 * Created by zhangxin on 16/5/31.
 */
public class MainActivity extends Activity implements View.OnClickListener{
    private TextView tv_photo_take;
    private TextView tv_photo_select;
    private TextView tv_photo_cale;
    private TextView tv_photo_gesture;
    private TextView tv_photo_attribute;

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
        tv_photo_cale=(TextView)findViewById(R.id.tv_photo_cale);
        tv_photo_gesture=(TextView)findViewById(R.id.tv_photo_gesture);
        tv_photo_attribute=(TextView)findViewById(R.id.tv_photo_attribute);



        tv_photo_take.setOnClickListener(this);
        tv_photo_select.setOnClickListener(this);
        tv_photo_cale.setOnClickListener(this);
        tv_photo_gesture.setOnClickListener(this);
        tv_photo_attribute.setOnClickListener(this);

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
            case R.id.tv_photo_cale:
                Intent intent2=new Intent(mContext, ScaleActivity.class);
                startActivity(intent2);
                break;
            case R.id.tv_photo_gesture:
//                Intent intent3=new Intent(mContext, GestureZoom.class);
//                startActivity(intent3);
                break;

        }
    }
}
