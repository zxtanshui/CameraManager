package com.hebin.scale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hebin.picturetest.R;
import com.hebin.selectpic.imageloader.SelectPicActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by zhangxin on 16/6/4.
 */
public class ScaleActivity extends Activity implements View.OnClickListener {
    private ImageView imageView;
    private TextView tv_photo_select;
    private Context mContext;
    private static final int FLAG_SELECT_PIC_ALONG = 1003;
    private TextView tv_photo_attribute;
    private TextView tv_photo_create_time;
    private TextView tv_photo_create_location;

    String imagePath="";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scale);
        mContext=ScaleActivity.this;
        tv_photo_select=(TextView) findViewById(R.id.tv_photo_select);
        tv_photo_attribute=(TextView)findViewById(R.id.tv_photo_attribute);
        tv_photo_create_time=(TextView)findViewById(R.id.tv_photo_create_time);
        tv_photo_create_location=(TextView)findViewById(R.id.tv_photo_create_location);

        imageView = (ImageView) this.findViewById(R.id.imageView);
        imageView.setOnTouchListener(new TouchListener());
        tv_photo_select.setOnClickListener(ScaleActivity.this);
        tv_photo_attribute.setOnClickListener(this);
        tv_photo_create_time.setOnClickListener(this);
        tv_photo_create_location.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_photo_select:
                Intent intent1 = new Intent(mContext, SelectPicActivity.class);
                startActivityForResult(intent1, FLAG_SELECT_PIC_ALONG);
                break;
            case R.id.tv_photo_attribute:
                if(!TextUtils.isEmpty(imagePath)){
                    File file = new File(imagePath);
                    FileInputStream fis =null;
                    try {
                        fis=new FileInputStream(file);
                        fis.getChannel();
                        Toast.makeText(mContext,FormetFileSize(fis.available())+"",Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(mContext,"请选择照片",Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.tv_photo_create_time://获取拍照时间
                if(!TextUtils.isEmpty(imagePath)){
                    File file = new File(imagePath);
                    FileInputStream fis =null;
                    try {
                        fis=new FileInputStream(file);

                        Toast.makeText(mContext,getFileCreated(file)+"",Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(mContext,"请选择照片",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_photo_create_location://获取图片路径
                if(!TextUtils.isEmpty(imagePath)){
                    File file = new File(imagePath);
                    FileInputStream fis =null;
                    try {
                        fis=new FileInputStream(file);

                        Toast.makeText(mContext,file.getAbsolutePath()+"",Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(mContext,"请选择照片",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public String FormetFileSize(long fileS) {//转换文件大小
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) +"G";
        }
        return fileSizeString;
    }


        private final class TouchListener implements View.OnTouchListener {

        /** 记录是拖拉照片模式还是放大缩小照片模式 */
        private int mode = 0;// 初始状态
        /** 拖拉照片模式 */
        private static final int MODE_DRAG = 1;
        /** 放大缩小照片模式 */
        private static final int MODE_ZOOM = 2;

        /** 用于记录开始时候的坐标位置 */
        private PointF startPoint = new PointF();
        /** 用于记录拖拉图片移动的坐标位置 */
        private Matrix matrix = new Matrix();
        /** 用于记录图片要进行拖拉时候的坐标位置 */
        private Matrix currentMatrix = new Matrix();

        /** 两个手指的开始距离 */
        private float startDis;
        /** 两个手指的中间点 */
        private PointF midPoint;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            /** 通过与运算保留最后八位 MotionEvent.ACTION_MASK = 255 */
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                // 手指压下屏幕
                case MotionEvent.ACTION_DOWN:
                    mode = MODE_DRAG;
                    // 记录ImageView当前的移动位置
                    currentMatrix.set(imageView.getImageMatrix());
                    startPoint.set(event.getX(), event.getY());
                    break;
                // 手指在屏幕上移动，改事件会被不断触发
                case MotionEvent.ACTION_MOVE:
                    // 拖拉图片
                    if (mode == MODE_DRAG) {
                        float dx = event.getX() - startPoint.x; // 得到x轴的移动距离
                        float dy = event.getY() - startPoint.y; // 得到x轴的移动距离
                        // 在没有移动之前的位置上进行移动
                        matrix.set(currentMatrix);
                        matrix.postTranslate(dx, dy);
                    }
                    // 放大缩小图片
                    else if (mode == MODE_ZOOM) {
                        float endDis = distance(event);// 结束距离
                        if (endDis > 10f) { // 两个手指并拢在一起的时候像素大于10
                            float scale = endDis / startDis;// 得到缩放倍数
                            matrix.set(currentMatrix);
                            matrix.postScale(scale, scale,midPoint.x,midPoint.y);
                        }
                    }
                    break;
                // 手指离开屏幕
                case MotionEvent.ACTION_UP:
                    // 当触点离开屏幕，但是屏幕上还有触点(手指)
                case MotionEvent.ACTION_POINTER_UP:
                    mode = 0;
                    break;
                // 当屏幕上已经有触点(手指)，再有一个触点压下屏幕
                case MotionEvent.ACTION_POINTER_DOWN:
                    mode = MODE_ZOOM;
                    /** 计算两个手指间的距离 */
                    startDis = distance(event);
                    /** 计算两个手指间的中间点 */
                    if (startDis > 10f) { // 两个手指并拢在一起的时候像素大于10
                        midPoint = mid(event);
                        //记录当前ImageView的缩放倍数
                        currentMatrix.set(imageView.getImageMatrix());
                    }
                    break;
            }
            imageView.setImageMatrix(matrix);
            return true;
        }

        /** 计算两个手指间的距离 */
        private float distance(MotionEvent event) {
            float dx = event.getX(1) - event.getX(0);
            float dy = event.getY(1) - event.getY(0);
            /** 使用勾股定理返回两点之间的距离 */
            return FloatMath.sqrt(dx * dx + dy * dy);
        }

        /** 计算两个手指间的中间点 */
        private PointF mid(MotionEvent event) {
            float midX = (event.getX(1) + event.getX(0)) / 2;
            float midY = (event.getY(1) + event.getY(0)) / 2;
            return new PointF(midX, midY);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data==null){
            return;
        }

        if (resultCode == RESULT_OK) {
            if (requestCode == FLAG_SELECT_PIC_ALONG) {

                final ArrayList<String> result = data
                        .getStringArrayListExtra("result");
                if(result.size()>0){
                    imagePath=result.get(0);
                }

                //显示图片的配置
                DisplayImageOptions options = new DisplayImageOptions.Builder()
                        // .showImageOnLoading(R.drawable.ic_stub)
                        // .showImageOnFail(R.drawable.ic_error)
                        .cacheInMemory(true)
                        .cacheOnDisk(true)
                        .bitmapConfig(Bitmap.Config.RGB_565)
                        .build();

                String imageUrl1 = ImageDownloader.Scheme.FILE.wrap(imagePath);
                ImageLoader.getInstance().displayImage(imageUrl1, imageView, options);
            }
        }

    }




    public  String getFileCreated(final File file) {
        if(null == file)
        {
            return null;
        }

        String rs = null;
        final StringBuilder sb = new StringBuilder();
        Process p = null;

        try
        {
            p = Runtime.getRuntime().exec(String.format("cmd /C dir %s /tc", file.getAbsolutePath()));
        }
        catch(IOException e)
        {
            return rs;
        }

        final InputStream is = p.getInputStream();
        final InputStreamReader ir = new InputStreamReader(is);
        final BufferedReader br = new BufferedReader(ir);

        Runnable runnable = new Runnable()
        {
            @Override
            public void run()
            {
                String data = null;

                try
                {
                    while(null != (data = br.readLine()))
                    {
                        if(-1 != data.toLowerCase().indexOf(file.getName().toLowerCase()))
                        {
                            String[] temp = data.split(" +");

                            if(2 <= temp.length)
                            {
                                String time = String.format("%s %s", temp[0], temp[1]);
                                sb.append(time);
                            }

                            break;
                        }
                    }
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    try
                    {
                        if(null != br)
                        {
                            br.close();
                        }

                        if(null != ir)
                        {
                            ir.close();
                        }

                        if(null != is)
                        {
                            is.close();
                        }
                    }
                    catch(IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();

        try
        {
            thread.join();
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }

        if(0 != sb.length())
        {
            rs = sb.toString();
        }

        return rs;
    }
}
