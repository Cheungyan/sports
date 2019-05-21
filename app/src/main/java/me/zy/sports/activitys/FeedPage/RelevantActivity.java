package me.zy.sports.activitys.FeedPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import me.zy.sports.R;
import me.zy.sports.dao.bean.Comment;
import me.zy.sports.dao.bean.Feed;
import me.zy.sports.dao.bean.Myuser;
import me.zy.sports.dao.bean.Relevant;

/**
 * 项目名：sports
 * 包名：me.zy.sports.activitys.FeedPage
 * Created by Administrator on 2019/5/21.
 * 描述：
 */
public class RelevantActivity extends AppCompatActivity {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private RelevantAdapter mAdapter;
    private Myuser Cuser;
    private List<Comment> mComment = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.relevant_activity);
        ButterKnife.bind(this);
        Bmob.initialize(this, "623a4535779272842b9fe7411f7a6e0e");

        mToolbar.setNavigationIcon(R.drawable.img_btn_back);
        mToolbar.setTitle("与我相关");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RelevantActivity.this.onBackPressed();
            }
        });

        init();
    }

    private void init() {
        Cuser= BmobUser.getCurrentUser(Myuser.class);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);


        BmobQuery<Comment> eq = new BmobQuery<>();
        eq.addWhereEqualTo("toUser", Cuser);
        eq.include("user,replyList,feed,toUser,feed.user");
        eq.findObjects(new FindListener<Comment>() {
            @Override
            public void done(List<Comment> list, BmobException e) {
                if(e==null)
                {

                    Log.w("getEva", "查表成功"+list.get(0).getCommentInfo());
                    Log.w("getEva", "查表成功"+list.get(0).getReplyList().get(0).getCommentInfo());
                    Log.w("getEva", "原动态发送人"+list.get(0).getFeed());
                    initView(list);

                }
                else
                {}
            }
        });

    }
    private void initView(List<Comment> list) {
        mAdapter = new RelevantAdapter(this, list);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemListener(new RelevantAdapter.OnItemListener() {
            @Override
            public void onItemClick(View view, Comment comment) {
                switch (view.getId()) {
                    case R.id.user_img:
                        break;
                    case R.id.feed_body:
                        gotoFeed(comment.getFeed());
                        break;
                }
            }
        });
    }


    private void gotoFeed(Feed feed) {
        Intent intent = new Intent(RelevantActivity.this, FeedActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("feed", feed);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}
