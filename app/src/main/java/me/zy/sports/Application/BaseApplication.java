package me.zy.sports.Application;

import android.app.Application;

import cn.bmob.v3.Bmob;

/**
 * 项目名：sports
 * 包名：me.zy.sports.Application
 * Created by Administrator on 2019/5/4.
 * 描述：
 */
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        Bmob.resetDomain("http://open-vip.bmob.cn/8/");
//        Bmob.initialize(this,"12784168944a56ae41c4575686b7b332");
        Bmob.initialize(this,"623a4535779272842b9fe7411f7a6e0e");
    }
}
