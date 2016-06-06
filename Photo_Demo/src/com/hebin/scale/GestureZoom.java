package com.hebin.scale;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.hebin.picturetest.R;


/**
 * 通过手势 缩放图片 左--->右 放大 右 --->左 缩小 速度越快，缩放比例越大
 */
public class GestureZoom extends Activity implements GestureDetector.OnGestureListener {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom);
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
}
