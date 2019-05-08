package me.zy.sports.activitys.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import me.zy.sports.R;
import me.zy.sports.activitys.LoginHeader.LoginHeader;
import me.zy.sports.activitys.LoginHeader.RegisteredActivity;
import me.zy.sports.activitys.about.About;
import me.zy.sports.activitys.eat.eat_Activity;
import me.zy.sports.activitys.historylistpage.RecordListActivity;
import me.zy.sports.activitys.runningpage.RuningActivity;
import me.zy.sports.activitys.runningpage.runhome;
import me.zy.sports.dao.bean.MyArticle;
import me.zy.sports.dao.bean.Test;


/***
 * 软件主界面
 */
public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView recyclerView;
    private Button btn_add;
    private DrawerLayout mDrawerLayout;

    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_home);
        Bmob.initialize(this,"623a4535779272842b9fe7411f7a6e0e");
        initFindView();
        setOnclick();

    }
    public void initFindView() {
        btn_add=(Button)findViewById(R.id.home_add);
        btn_add.setOnClickListener(this);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawLayout);
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
            case R.id.home_add:
                BmobQuery<Test> query = new BmobQuery<Test>();
                query.getObject( "DVWI6669", new QueryListener<Test>() {
                    public void done(Test ob,BmobException e) {
                        if(e==null){
                            Log.w("TTTTTT", "查表成功展示数据结果显示如下:"+ob.getImage().getFilename()+ob.getImage().getFileUrl());
                        }else{
                            Log.w("TTTTTT", "查询失败"+e.getMessage());
                        }
                    }
                });
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
                    case R.id.item_home:
                        break;
                    case R.id.item_eat:
                        startActivity(new Intent(HomeActivity.this, eat_Activity.class));
                        break;
                    case R.id.run_home:
                        startActivity(new Intent(HomeActivity.this, runhome.class));
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

    //呼出抽屉菜单
    public void showNaviMenu(View view) {

        mDrawerLayout.openDrawer(GravityCompat.START);
    }
}