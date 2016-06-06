package com.hebin.scale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hebin.picturetest.R;
import com.hebin.selectpic.imageloader.SelectPicActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;

import java.util.ArrayList;


/**
 * 通过手势 缩放图片 左--->右 放大 右 --->左 缩小 速度越快，缩放比例越大
 */
public class GestureZoom extends Activity implements GestureDetector.OnGestureListener, View.OnClickListener {
    // 定义手势检测器实例
    GestureDetector detector;
    ImageView imageView;
    // 初始化图片资源
    Bitmap bitmap;
    // 定义图片的高和宽
    int width, height;
    // 记录当前的缩放比
    float currentScale = 1;
    // 控制图片缩放的Matrix对象
    Matrix matrix;
    private TextView tv_photo_select;
    private Context mContext;
    private static final int FLAG_SELECT_PIC_ALONG = 1003;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom);
        mContext=GestureZoom.this;

        tv_photo_select=(TextView) findViewById(R.id.tv_photo_select);
        // 创建手势检测器
        detector = new GestureDetector(this);
        imageView = (ImageView) findViewById(R.id.show);
        matrix = new Matrix();
        // 获取被缩放的源图片
        bitmap = BitmapFactory.decodeResource(this.getResources(),
                R.drawable.photo1);
        // 获得位图的宽
        width = bitmap.getWidth();
        // 获得位图的高
        height = bitmap.getHeight();
        // 设置 ImageView初始化显示的图片
        imageView.setImageBitmap(BitmapFactory.decodeResource(
                this.getResources(), R.drawable.photo1));

        tv_photo_select.setOnClickListener(GestureZoom.this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 将该Activity上的触碰时间交个 GestureDetector处理
        return detector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {
        velocityX = velocityX > 4000 ? 4000 : velocityX;
        velocityY = velocityY < -4000 ? -4000 : velocityY;
        // 感觉手势的速度来计算缩放比，如果 velocityX>0,放大图像，否则缩小图像
        currentScale += currentScale * velocityX / 4000.0f;
        // 保证 currentScale 不会等于0
        currentScale = currentScale > 0.01 ? currentScale : 0.01f;
        // 重置 Matrix
        matrix.setScale(currentScale, currentScale, 160, 200);
        BitmapDrawable tmp = (BitmapDrawable) imageView.getDrawable();
        // 如果图片还未回收，先强制收回该图片
        if (!tmp.getBitmap().isRecycled()) {
            tmp.getBitmap().recycle();
        }
        // 根据原始位图和 Matrix创建新图片
        Bitmap bitmap2 = Bitmap.createBitmap(bitmap, 0, 0, width, height,
                matrix, true);
        //显示新的位图
        imageView.setImageBitmap(bitmap2);
        return true;
    }

    @Override
    public void onClick(View v) {
         switch (v.getId()){
             case R.id.tv_photo_select:
                 Intent intent1 = new Intent(mContext, SelectPicActivity.class);
                 startActivityForResult(intent1, FLAG_SELECT_PIC_ALONG);
                 break;
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
                String imagePath="";
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_pagebrowse, menu);
        return true;
    }
}
