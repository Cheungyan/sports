package me.zy.sports.activitys.homepage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import me.zy.sports.R;
import me.zy.sports.activitys.BodyKeep.bodykeepActicity;
import me.zy.sports.activitys.Bodypage.BodyBuilding;
import me.zy.sports.activitys.FeedPage.AllFeedActivity;
import me.zy.sports.activitys.FeedPage.publish_activity;
import me.zy.sports.activitys.LoginHeader.LoginHeader;
import me.zy.sports.activitys.eat.eat_Activity;
import me.zy.sports.activitys.runningpage.runhome;
import me.zy.sports.activitys.userpage.UserActivity;
import me.zy.sports.dao.bean.KeepNoteEntity;
import me.zy.sports.dao.bean.Myuser;
import me.zy.sports.utils.DecimalUtils;


/***
 * 软件主界面
 */
public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout srl;
    private List<KeepNoteEntity> keepNoteEntityList;

    private List<KeepNoteEntity>knde=new ArrayList<>();
    private Context mContext;
    private Button btn_add;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private sportsAdapter sportsAdapter;
    private Button btn_delete;
    private Button btn_weight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_home);
        Bmob.initialize(this,"623a4535779272842b9fe7411f7a6e0e");
        mContext = this;
        initView();
        initData();
        setOnclick();

    }

    public void initView() {
        btn_add=(Button)findViewById(R.id.home_add);
        btn_add.setOnClickListener(this);
        btn_delete=(Button)findViewById(R.id.home_delete);
        btn_delete.setOnClickListener(this);
        btn_weight=(Button)findViewById(R.id.weight_add);
        btn_weight.setOnClickListener(this);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawLayout);
        mNavigationView = (NavigationView) findViewById(R.id.navigation);
        RelativeLayout relativeLayout = (RelativeLayout) mNavigationView.inflateHeaderView(R.layout.navi_heard_layout);
        ImageView imageView = (ImageView) relativeLayout.findViewById(R.id.login);
        ImageView imageView2 = (ImageView) relativeLayout.findViewById(R.id.exit);
        imageView.setOnClickListener(this);
        imageView2.setOnClickListener(this);
        recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        srl=(SwipeRefreshLayout)findViewById(R.id.srl) ;

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        keepNoteEntityList = new ArrayList<>();
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
    }

    private void initData() {
        srl.setRefreshing(true);
        BmobQuery<KeepNoteEntity> query = new BmobQuery<>();
        query.addWhereEqualTo("userId", BmobUser.getCurrentUser(Myuser.class).getObjectId());
        query.setLimit(100);
        query.order("-id");//最新发布的在前面
        query.findObjects(new FindListener<KeepNoteEntity>() {
            @Override
            public void done(List<KeepNoteEntity> list, BmobException e) {
                srl.setRefreshing(false);
                if (e == null) {
                    refreshList(list);
                } else {
                    Toast.makeText(HomeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void refreshList(List<KeepNoteEntity> list)
    {
        keepNoteEntityList = list;
        sportsAdapter = new sportsAdapter(this, keepNoteEntityList);
        sportsAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        recyclerView.setAdapter(sportsAdapter);
        float totalTime = 0f;
        float totalRunLength = 0f;
        float totalSitUp = 0f;
        float totalSportsApparatusTimes = 0f;
        // 计算总数
        for (int i = 0; i < keepNoteEntityList.size(); i++) {
            KeepNoteEntity bean = keepNoteEntityList.get(i);
            totalTime += bean.getExerciseDuration();
            totalRunLength += bean.getRunLength();
            totalSitUp += bean.getSitUps();
            totalSportsApparatusTimes += bean.getSportsApparatusTimes();
        }
        sportsAdapter.addHeaderView(getHeaderView(1, getRemoveHeaderListener(),
                totalTime,
                totalRunLength,
                totalSitUp,
                totalSportsApparatusTimes,
                list.size()));
        sportsAdapter.notifyDataSetChanged();
    }

    private View getHeaderView(int type, View.OnClickListener listener, float string1, float string2, float string3, float string4, int string5) {
        View view = getLayoutInflater().inflate(R.layout.item_home_top, (ViewGroup) recyclerView.getParent(), false);
        if (type == 1) {
            TextView totalTime = (TextView) view.findViewById(R.id.item_top_total_fit_length_tv);
            TextView totalRunLength = (TextView) view.findViewById(R.id.item_top_total_run_length_tv);
            TextView totalSitUp = (TextView) view.findViewById(R.id.item_top_total_situp_tv);
            TextView totalSportsApparatusTimes = (TextView) view.findViewById(R.id.item_top_total_sports_apparatus_tv);
            TextView totalDay = (TextView) view.findViewById(R.id.item_top_total_day_length_tv);
            totalTime.setText(DecimalUtils.formatDecimalWithZero(string1, 2));
            totalRunLength.setText(DecimalUtils.formatDecimalWithZero(string2, 2));
            totalSitUp.setText(String.valueOf((int) string3));
            totalSportsApparatusTimes.setText(String.valueOf((int) string4));
            totalDay.setText(string5 + "");
        }
        view.setOnClickListener(listener);
        return view;
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_delete:
                Date todayDate = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String dateString = dateFormat.format(todayDate);
                KeepNoteEntity kn=new KeepNoteEntity();
                String userid=BmobUser.getCurrentUser(Myuser.class).getObjectId();
                Log.d("TAG", "initView: " + userid);
                BmobQuery<KeepNoteEntity> eq1 = new BmobQuery<KeepNoteEntity>();
                eq1.addWhereEqualTo("date", dateString);
                BmobQuery<KeepNoteEntity> eq2 = new BmobQuery<KeepNoteEntity>();
                eq2.addWhereEqualTo("userId", userid);
                List<BmobQuery<KeepNoteEntity>> andQuerys = new ArrayList<BmobQuery<KeepNoteEntity>>();
                andQuerys.add(eq1);
                andQuerys.add(eq2);//组合查询
                BmobQuery<KeepNoteEntity> query = new BmobQuery<KeepNoteEntity>();
                query.and(andQuerys);
                query.findObjects(new FindListener<KeepNoteEntity>() {
                    @Override
                    public void done(List<KeepNoteEntity> object, BmobException e) {
                        if (e == null) {
                            Log.w("TTTTTT", "查表成功");
                            knde=object;
                            for(int i=0;i<knde.size();i++) {
                                knde.get(i).delete(knde.get(i).getObjectId(), new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e == null) {
                                            Toast.makeText(HomeActivity.this, "删除今日目标成功", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(HomeActivity.this, "删除今日目标失败", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }

                        } else {
                            Toast.makeText(HomeActivity.this, "查询失败"+e.getMessage() , Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                break;
            case R.id.login:
                Intent intent = new Intent(this, UserActivity.class);
                Bundle bundle = new Bundle();
                Myuser user=BmobUser.getCurrentUser(Myuser.class);
                bundle.putSerializable("user_info", user);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.home_add:
                startActivity(new Intent(mContext, AddKeepNoteActivity.class));
                break;
            case R.id.weight_add:
                startActivity(new Intent(HomeActivity.this, bodykeepActicity.class));
                break;
            case R.id.exit:
                BmobUser.logOut();
                startActivity(new Intent(HomeActivity.this, LoginHeader.class));
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

                    case R.id.item_sports:
                        startActivity(new Intent(HomeActivity.this, BodyBuilding.class));

                        break;
                    case R.id.item_publish:
                        startActivity(new Intent(HomeActivity.this, AllFeedActivity.class));
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

    private View.OnClickListener getRemoveHeaderListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                keepFitAdapter.removeHeaderView(v);
            }
        };
    }
}