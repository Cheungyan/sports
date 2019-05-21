package me.zy.sports.activitys.FeedPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import me.zy.sports.R;
import me.zy.sports.activitys.homepage.HomeActivity;
import me.zy.sports.activitys.userpage.FeedAdapter;
import me.zy.sports.activitys.userpage.UserActivity;
import me.zy.sports.dao.bean.Feed;
import me.zy.sports.dao.utils.FeedUtil;

/**
 * 项目名：sports
 * 包名：me.zy.sports.activitys.FeedPage
 * Created by Administrator on 2019/5/21.
 * 描述：
 */
public class AllFeedActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private List<Feed> mFeedList = new ArrayList<>();
    private FeedAdapter mAdapter;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allfeed_acticity);
        ButterKnife.bind(this);
        Bmob.initialize(this, "623a4535779272842b9fe7411f7a6e0e");
        init();
    }

    private void init() {
        mToolbar.setNavigationIcon(R.drawable.img_btn_back);
        mToolbar.setTitle("圈子详情");
        mToolbar.inflateMenu(R.menu.publish_menu);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_share:
                        startActivity(new Intent( AllFeedActivity.this, publish_activity.class));
                        break;

                    case R.id.aboutme:
                        startActivity(new Intent( AllFeedActivity.this,RelevantActivity.class));
                        break;
                }
                return false;
            }
        });
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllFeedActivity.this.onBackPressed();
            }
        });


        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter=new FeedAdapter(this,mFeedList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        initData();
        initEvent();
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
        query.include("user");
        query.findObjects(new FindListener<Feed>() {
            @Override
            public void done(List<Feed> list, BmobException e) {
                mSwipeRefreshLayout.setRefreshing(false);
                if (e == null) {
                    refreshList(list);
                } else {
                    Toast.makeText(AllFeedActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void refreshList(List<Feed> list) {

        mAdapter=new FeedAdapter(this,list);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        Toast.makeText(AllFeedActivity.this, "刷新成功", Toast.LENGTH_SHORT).show();
        mAdapter.setOnItemListener(new FeedAdapter.OnItemListener() {
            @Override
            public void onItemClick(View view, Feed feed, int position) {
                switch (view.getId()) {
                    case R.id.feed_card:
                    case R.id.feed_comment_layout:
                        Toast.makeText(AllFeedActivity.this, "跳转至动态详情", Toast.LENGTH_SHORT).show();
                        goToFeed(feed);
                        break;
                    case R.id.feed_like_layout:
                        feed.setLike(!feed.isLike());
                        postLike(feed);
                        break;
                    case R.id.feed_image_a:
                        Toast.makeText(AllFeedActivity.this, "点击图片成功", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AllFeedActivity.this, PhotoViewActivity.class).putExtra("pictureUrl", feed.getIamge()));
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
    public void postLike(Feed feed) {
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
