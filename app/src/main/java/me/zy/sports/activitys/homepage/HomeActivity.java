package me.zy.sports.activitys.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import me.zy.sports.R;
import me.zy.sports.activitys.LoginHeader.LoginHeader;
import me.zy.sports.activitys.LoginHeader.RegisteredActivity;
import me.zy.sports.activitys.about.About;
import me.zy.sports.activitys.eat.eat_Activity;
import me.zy.sports.activitys.historylistpage.RecordListActivity;
import me.zy.sports.activitys.runningpage.RuningActivity;


/***
 * 软件主界面
 */
public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnStartRunning;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_home);

        initFindView();
        setOnclick();

    }


    public void initFindView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawLayout);
        btnStartRunning = (Button) findViewById(R.id.btn_start);
        btnStartRunning.setOnClickListener(this);
        mNavigationView = (NavigationView) findViewById(R.id.navigation);
        RelativeLayout relativeLayout = (RelativeLayout) mNavigationView.inflateHeaderView(R.layout.navi_heard_layout);
        ImageView imageView = (ImageView) relativeLayout.findViewById(R.id.login);
        imageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {


            case R.id.login:
                startActivity(new Intent(HomeActivity.this, LoginHeader.class));
                break;

            case R.id.btn_start:
                startActivity(new Intent(HomeActivity.this, RuningActivity.class));
                break;
        }
    }

    /**
     * 设置点击事件
     */
    public void setOnclick() {


        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.item_eat:
                        startActivity(new Intent(HomeActivity.this, eat_Activity.class));
                        break;
                    case R.id.item_home:
                        break;
                    case R.id.item_about:
                        startActivity(new Intent(HomeActivity.this, About.class));
                        break;

                    default:
                }

                mDrawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });


    }

    /**
     * 展示历史轨迹
     *
     * @param view
     */
    public void showHistoryTrace(View view) {

        Intent intent = new Intent(HomeActivity.this, RecordListActivity.class);
        startActivity(intent);
    }

    /**
     * 呼出抽屉菜单
     *
     * @param view
     */
    public void showNaviMenu(View view) {

        mDrawerLayout.openDrawer(GravityCompat.START);
    }
}