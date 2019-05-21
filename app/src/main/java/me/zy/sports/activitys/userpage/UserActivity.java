package me.zy.sports.activitys.userpage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import me.zy.sports.R;
import me.zy.sports.activitys.FeedPage.FeedActivity;
import me.zy.sports.activitys.FeedPage.PhotoViewActivity;
import me.zy.sports.activitys.FeedPage.publish_activity;
import me.zy.sports.dao.bean.Myuser;
import me.zy.sports.dao.bean.Feed;
import me.zy.sports.dao.utils.FeedUtil;

/**
 * 项目名：sports
 * 包名：me.zy.sports.activitys.userpage
 * Created by Administrator on 2019/5/18.
 * 描述：
 */
public class UserActivity extends AppCompatActivity {

    Toolbar mToolbar;
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    AppBarLayout mAppBar;
    RelativeLayout mButtonBar;
    ImageView mParallax;
    TextView mTitleName;
    TextView mUserName;
    TextView mContact;
    TextView mFeedNum;
    TextView mDesc;
    Activity mActivity=this;
    String UserId;
    private List<Feed> mFeedList = new ArrayList<>();
    FeedAdapter mAdapter;

    Myuser Cuser;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity);
        Bmob.initialize(this,"623a4535779272842b9fe7411f7a6e0e");
        init();
    }

    private void init() {
        mToolbar=(Toolbar) findViewById(R.id.toolbar);
        mSwipeRefreshLayout=(SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        mRecyclerView=(RecyclerView) findViewById(R.id.recycler_view);
        mAppBar=(AppBarLayout) findViewById(R.id.app_bar);
        mButtonBar=(RelativeLayout) findViewById(R.id.button_bar);
        mParallax=(ImageView) findViewById(R.id.parallax);
        mTitleName=(TextView) findViewById(R.id.title_name);
        mUserName=(TextView) findViewById(R.id.user_name);
        mContact=(TextView) findViewById(R.id.content);
        mFeedNum=(TextView) findViewById(R.id.feed_num);
        mDesc=(TextView) findViewById(R.id.user_desc);


        mToolbar.setNavigationIcon(R.drawable.img_btn_back);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.onBackPressed();
            }
        });
        //通过当前登录ID搜索此用户的信息

        Bundle bundle = this.getIntent().getExtras();
        if (bundle == null) return;
        Cuser = (Myuser) bundle.getSerializable("user_info");
        UserId= Cuser .getObjectId();
        BmobQuery<Myuser> bmobQuery = new BmobQuery<Myuser>();
        bmobQuery.getObject(UserId, new QueryListener<Myuser>() {
            @Override
            public void done(Myuser object, BmobException e) {
                if(e==null){
                    initUser(object);
                }else{
                }
            }
        });
        initData();
        initRecyclerView();

    }


    private void initUser(Myuser user) {
        mTitleName.setText(user.getNickname());
        mUserName.setText(user.getNickname());
        mDesc.setText(user.getDesc());
        initEvent();
    }


    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter=new FeedAdapter(this,mFeedList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    private void initEvent() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });

    }




    private void initData() {
        mSwipeRefreshLayout.setRefreshing(true);
        BmobQuery<Feed> query = new BmobQuery<>();
        query.addWhereEqualTo("user", Cuser );
       // query.order("-id");//最新发布的在前面
        query.include("user");
        query.findObjects(new FindListener<Feed>() {
            @Override
            public void done(List<Feed> list, BmobException e) {
                mSwipeRefreshLayout.setRefreshing(false);
                if (e == null) {
                    refreshList(list);
                } else {
                    Toast.makeText(UserActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void refreshList(List<Feed> list) {
        mAdapter=new FeedAdapter(this,list);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        Toast.makeText(UserActivity.this, "刷新成功", Toast.LENGTH_SHORT).show();
        mAdapter.setOnItemListener(new FeedAdapter.OnItemListener() {
            @Override
            public void onItemClick(View view, Feed feed, int position) {
                switch (view.getId()) {
                    case R.id.feed_card:
                    case R.id.feed_comment_layout:
                        Toast.makeText(UserActivity.this, "跳转至动态详情", Toast.LENGTH_SHORT).show();
                        goToFeed(feed);
                        break;
                    case R.id.feed_like_layout:
                        Toast.makeText(UserActivity.this, "点赞按钮", Toast.LENGTH_SHORT).show();
                        feed.setLike(!feed.isLike());
                        postLike(feed);
                        break;
                    case R.id.feed_image_a:
                        Toast.makeText(UserActivity.this, "点击图片成功", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(UserActivity.this, PhotoViewActivity.class).putExtra("pictureUrl", feed.getIamge()));
                }
            }
        });

    }
    private void goToFeed(Feed feed) {
        Intent intent = new Intent(this, FeedActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("feed", feed);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    private void postLike(Feed feed) {
        if(feed.isLike())
        {
            feed.setLikeNum(feed.getLikeNum()+1);
        }
        else
        {
            feed.setLikeNum(feed.getLikeNum()-1);}
        feed.update(feed.getObjectId(),new UpdateListener() {
            public void done( BmobException e) {
                if (e == null) {

                } else {
                }
            }
        });
    }

}
