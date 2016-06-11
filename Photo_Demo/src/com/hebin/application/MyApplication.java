package com.hebin.application;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.widget.Toast;


import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;

/**
 * Created by zhangxin on 16/6/6.
 */
public class MyApplication extends Application {
    public static File fBaseDir;
    public static Context context;
    public static final String SENDMESSAGE_PHOTO = "sendmessage_photo"; // 当头像是相机拍照获得的照片的时候用来命名的
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        //创建默认的ImageLoader配置参数
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration
                .createDefault(this);

        //Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(configuration);
    }

    public static void getBaseDir() {
        // 下载到SD卡
        if (Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            fBaseDir = context
                    .getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        } else {
            fBaseDir = new File(MyApplication.context.getFilesDir(),
                    Environment.DIRECTORY_DOWNLOADS);
        }
        if (fBaseDir != null) {
            if (!fBaseDir.exists() && !fBaseDir.mkdir()) {
                Toast.makeText(context, "缓存目录不能写", Toast.LENGTH_SHORT).show();
            }

            if (!fBaseDir.isDirectory()) {
                Toast.makeText(context, "缓存目录名已存在但不是目录", Toast.LENGTH_SHORT)
                        .show();
            }
        }

    }

    public static File getImgDir() {
        getBaseDir();
        File dir = new File(fBaseDir, "img");
        if (!dir.exists()) {
            dir.mkdir();
        }
        return dir;
    }


    public static MyApplication getApplication(Context context) {
        return (MyApplication) context.getApplicationContext();
    }
}
