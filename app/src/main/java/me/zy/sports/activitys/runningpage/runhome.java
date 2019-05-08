package me.zy.sports.activitys.runningpage;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import cn.bmob.v3.BmobUser;
import me.zy.sports.R;
import me.zy.sports.activitys.LoginHeader.LoginHeader;
import me.zy.sports.activitys.historylistpage.RecordListActivity;
import me.zy.sports.activitys.homepage.HomeActivity;

/**
 * 项目名：sports
 * 包名：me.zy.sports.activitys.runningpage
 * Created by Administrator on 2019/5/8.
 * 描述：
 */
public class runhome extends AppCompatActivity implements View.OnClickListener {
    private Button btnStartRunning;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.runhome);

        initFindView();

    }

    public void initFindView() {
        btnStartRunning = (Button) findViewById(R.id.btn_start);
        btnStartRunning.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                startActivity(new Intent(this, RuningActivity.class));
                break;
        }
    }
    /**
     * 展示历史轨迹
     */
    public void showHistoryTrace(View view) {

        Intent intent = new Intent(this, RecordListActivity.class);
        startActivity(intent);
    }

}
