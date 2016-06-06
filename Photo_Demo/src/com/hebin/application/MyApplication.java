package com.hebin.application;

import android.app.Application;


import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by zhangxin on 16/6/6.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //创建默认的ImageLoader配置参数
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration
                .createDefault(this);

        //Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(configuration);
    }
}
