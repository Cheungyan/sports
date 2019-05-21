package me.zy.sports.activitys.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import me.zy.sports.R;
import me.zy.sports.activitys.LoginHeader.LoginHeader;
import me.zy.sports.utils.ShareUtils;
import me.zy.sports.utils.Utils;

/**
 * 项目名：sports
 * 包名：me.zy.sports.activitys.homepage
 * Created by Administrator on 2019/5/11.
 * 描述：
 */
public class SplashActivity extends AppCompatActivity {
    /**
     * 1.延时2000ms
     * 4.Activity全屏主题
     */

    private ImageView tv_splash;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1001:

                    startActivity(new Intent(SplashActivity.this, LoginHeader.class));
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();
    }
    //初始化View
    private void initView() {
        //延时2000ms
        handler.sendEmptyMessageDelayed( 1001, 2000);
        tv_splash = (ImageView) findViewById(R.id.tv_splash);
    }


}
