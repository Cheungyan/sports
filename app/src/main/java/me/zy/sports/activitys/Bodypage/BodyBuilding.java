package me.zy.sports.activitys.Bodypage;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import me.zy.sports.R;

/**
 * Created by Administrator on 2019/1/2.
 */

public class BodyBuilding extends AppCompatActivity {
    private Toolbar toolbar;
    //Tablayouy
    private TabLayout mTablayout;
    // viewPager
    private ViewPager mViewPager;
    private List<String> mTitle;
    //Fragment
    private List<Fragment>mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.body_activity);
        Bmob.initialize(this,"623a4535779272842b9fe7411f7a6e0e");
        toolbar = (Toolbar) findViewById(R.id.bodytb);
        setSupportActionBar(toolbar);//设置ToolBar
        getSupportActionBar();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initDate();
        initView();

    }
    //初始化 数据
    private void initDate() {
        mTitle=new ArrayList<>();
        mTitle.add(this.getString(R.string.first));
        mTitle.add(this.getString(R.string.second));

        mFragment=new ArrayList<>();
        mFragment.add(new PhotoFragment());
        mFragment.add(new VedioFragment());

    }
    //初始化
    private void initView(){

        mTablayout=(TabLayout) findViewById(R.id.mTabLayout);
        mViewPager=(ViewPager)findViewById(R.id.mViewPaper);
        //预加载
        mViewPager.setOffscreenPageLimit(mFragment.size());

        //mViewPager滑动监听
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                Log.i("TAG","position:"+position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        //设置适配器

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            //选中的item
            @Override
            public Fragment getItem(int position) {
                return mFragment.get(position);
            }
            //返回item的个数
            @Override
            public int getCount() {
                return mFragment.size();
            }

            //设置标题
            @Override
            public CharSequence getPageTitle(int position)
            {
                return mTitle.get(position);
            }
        });

        mTablayout.setupWithViewPager(mViewPager);
    }


}
