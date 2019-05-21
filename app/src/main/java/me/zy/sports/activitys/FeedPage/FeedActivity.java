package me.zy.sports.activitys.FeedPage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import me.zy.sports.R;
import me.zy.sports.activitys.userpage.UserActivity;
import me.zy.sports.dao.bean.Comment;
import me.zy.sports.dao.bean.Feed;
import me.zy.sports.dao.bean.Myuser;
import me.zy.sports.dao.bean.Reply;
import me.zy.sports.dao.utils.FeedUtil;

/**
 * 项目名：sports
 * 包名：me.zy.sports.activitys.FeedPage
 * Created by Administrator on 2019/5/19.
 * 描述：
 */
public class FeedActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.user_img)
    ImageView mUserImg;
    @BindView(R.id.feed_user_name)
    TextView mUserName;
    @BindView(R.id.feed_time)
    TextView mFeedTime;
    @BindView(R.id.feed_info)
    AppCompatTextView mFeedInfo;
    @BindView(R.id.feed_body)
    LinearLayout mFeedBody;
    @BindView(R.id.feed_view_num)
    TextView mFeedSeeNum;
    @BindView(R.id.feed_comment_num)
    TextView mFeedCommentNum;
    @BindView(R.id.feed_comment_layout)
    LinearLayout mFeedComment;
    @BindView(R.id.feed_like_icon)
    ImageView mFeedLikeIcon;
    @BindView(R.id.feed_like_num)
    TextView mFeedLikeNum;
    @BindView(R.id.feed_like_layout)
    LinearLayout mFeedLikeLayout;
    @BindView(R.id.feed_action_layout)
    LinearLayout mFeedActionLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.edit_mask)
    View mEditMask;
    @BindView(R.id.edit_tu_cao)
    AppCompatEditText mEditTuCao;
    @BindView(R.id.btn_publish)
    Button mBtnPublish;
    @BindView(R.id.fa_photo_view)
    ImageView mFeedImage;

    private int MSG_MODE;
    private final int MSG_EVALUATE = 0;
    private final int MSG_REPLY = 1;
    private InputMethodManager imm;
    Feed feed;
    Comment mComment;
    Myuser  user;
    Myuser Cuser;
    private EvaluateAdapter mAdapter;
    List<Comment>lComment= new ArrayList<>();
    List<Reply> replyList=new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed_activity);
        ButterKnife.bind(this);
        Bmob.initialize(this, "623a4535779272842b9fe7411f7a6e0e");
        init();
    }

    private void init() {

        mToolbar.setNavigationIcon(R.drawable.img_btn_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               FeedActivity.this.onBackPressed();
            }
        });

        Cuser= BmobUser.getCurrentUser(Myuser.class);

        // 输入状态模式默认为评论
        MSG_MODE = MSG_EVALUATE;
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mEditTuCao.addTextChangedListener(new EditTextWatcher());
        // 开始禁用
        mBtnPublish.setClickable(false);
        mBtnPublish.setSelected(false);
        initView();
        initRecyclerView();

    }
    private void initView() {
        Bundle bundle = this.getIntent().getExtras();
        if (bundle == null) return;
        feed = (Feed) bundle.getSerializable("feed");
        if (feed == null) return;


        mUserName.setText(feed.getUser().getNickname());
       // Log.w("getEva", "昵称"+feed.getUser().getDesc());
        mFeedTime.setText(feed.getCreatedAt());
        mFeedInfo.setText(feed.getFeedInfo());
        // 查看评论点赞数
        mFeedLikeNum.setText(String.valueOf(feed.getLikeNum()));
        mFeedSeeNum.setText(String.valueOf(feed.getSeeNum()+1));
        feed.setSeeNum(feed.getSeeNum()+1);
        feed.update(new UpdateListener() {
            public void done( BmobException e) {
                if (e == null) {
                } else {
                }
            }
        });
        mFeedCommentNum.setText(String.valueOf(feed.getCommentNum()));
        // 是否已经点赞
        // 本人是否已经点赞
        if (feed.isLike()) {
            mFeedLikeIcon.setSelected(true);
        } else {
            mFeedLikeIcon.setSelected(false);
        }

        if (!TextUtils.isEmpty(feed.getIamge())) {
            //加载图片
            Picasso.with(this).load(feed.getIamge()).into(mFeedImage);
        }

    }

    private void initRecyclerView() {
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            mRecyclerView.setLayoutManager(layoutManager);
            getEva();


    }


    @OnClick({R.id.user_img, R.id.feed_like_layout, R.id.feed_comment_layout, R.id.edit_mask, R.id.edit_tu_cao, R.id.btn_publish})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_img:
                gotoUser(feed.getUser());
                break;
            case R.id.feed_like_layout:
                postLike(feed);
                Toast.makeText(this, "点赞", Toast.LENGTH_SHORT).show();
                break;
            case R.id.feed_comment_layout:
                mEditTuCao.setHint("吐槽一下");
                MSG_MODE = MSG_EVALUATE;
                openSofInput(mEditTuCao);
                mEditMask.setVisibility(View.VISIBLE);
                break;
            case R.id.edit_mask:
                mEditMask.setVisibility(View.GONE);
                hideSoftInput(mEditTuCao);
                break;
            case R.id.edit_tu_cao:
                mEditMask.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_publish:
                String msg = mEditTuCao.getText().toString().trim();
                switch (MSG_MODE) {
                    case MSG_EVALUATE:
                        //评论
                        addEvaluate(feed, Cuser, feed.getUser(), msg);
                        mEditTuCao.setText(null);
                        hideSoftInput(mEditTuCao);
                        getEva();
                        break;
                    case MSG_REPLY:
                        //回复
                        addReply(feed, mComment, Cuser, feed.getUser(), msg);
                        mEditTuCao.setText(null);
                        hideSoftInput(mEditTuCao);
                        getEva();
                        break;
                }
                break;
        }
    }


    //添加评论
    public void addEvaluate(final Feed feed, Myuser user, Myuser toUser, String commentInfo)
    {
        Comment comment=new Comment();
        comment.setFeed(feed);
        comment.setCommentInfo(commentInfo);
        comment.setUser(user);
        comment.setToUser(toUser);
        comment.setReplyList(new ArrayList<Reply>());
        comment.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    Toast.makeText(FeedActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
                    feed.setCommentNum(feed.getCommentNum()+1);
                    feed.update(new UpdateListener() {
                        public void done( BmobException e) {
                            if (e == null) {
                            } else {
                            }
                        }
                    });
                    mFeedCommentNum.setText(String.valueOf(Integer.valueOf(mFeedCommentNum.getText().toString()) + 1));
                } else {
                    Toast.makeText(FeedActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    //添加回复
    public void addReply(Feed feed,Comment comment, Myuser user,Myuser toUser,String commentInfo){
        Reply reply=new Reply();
        reply.setUser(user);
        reply.setToUser(toUser);
        reply.setComment(comment);
        reply.setCommentInfo(commentInfo);
        reply.setFeed(feed);

        comment.add("replyList",reply);
        comment.update(new UpdateListener() {
            public void done( BmobException e) {
                if (e == null) {
                    Toast.makeText(FeedActivity.this, "回复成功", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(FeedActivity.this, "点赞失败", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void getEva()
    {
        BmobQuery<Comment> eq = new BmobQuery<>();
        eq.addWhereEqualTo("feed", feed);
        eq.include("user,replyList");

        eq.findObjects(new FindListener<Comment>() {
            @Override
            public void done(List<Comment> object, BmobException e) {
                if (e == null) {
                    Log.w("getEva", "查表成功"+object.get(0).getCommentInfo());
                    mAdapter = new EvaluateAdapter(FeedActivity.this,object);
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                    mAdapter.setOnItemListener(new EvaluateAdapter.OnItemListener() {
                        @Override
                        public void onItemClick(View view, Comment comment) {
                            switch (view.getId()) {
                                case R.id.user_img:
                                    gotoUser(comment.getUser());
                                    break;
                                case R.id.evaluate_body:
                                    MSG_MODE = MSG_REPLY;
                                    mComment = comment;
                                    user= comment.getUser();

                                    mEditTuCao.setHint("回复：" + comment.getUser().getNickname());
                                    openSofInput(mEditTuCao);
                                    mEditMask.setVisibility(View.VISIBLE);
                                    break;
                            }
                        }

                        @Override
                        public void onItemChildClick(View view, Comment comment, Reply reply) {
                            MSG_MODE = MSG_REPLY;
                            mComment = comment;
                            user= comment.getUser();
                            replyList=comment.getReplyList();
                            mEditTuCao.setHint("回复：" + reply.getUser().getUsername());
                            openSofInput(mEditTuCao);
                            mEditMask.setVisibility(View.VISIBLE);
                        }
                    });

                } else {
                    Log.w("getEva", "查表失败");
                }
            }
        });
    }

    //跳转到用户主页
    private void gotoUser(Myuser user) {
        Intent intent = new Intent(this, UserActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("user_info", user);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    /**
     * 调用输入法
     */
    public void openSofInput(EditText edit) {
        edit.setText(null);
        edit.requestFocus();
//        edit.setFocusable(true);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 隐藏输入法
     */
    public void hideSoftInput(EditText edit) {
//        edit.setFocusable(false);
        imm.hideSoftInputFromWindow(edit.getWindowToken(), 0);
    }


    private class EditTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            boolean isEdit = mEditTuCao.getText().length() > 0;
            mBtnPublish.setClickable(isEdit);
            mBtnPublish.setSelected(isEdit);
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
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
