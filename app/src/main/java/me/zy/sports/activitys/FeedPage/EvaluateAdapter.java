package me.zy.sports.activitys.FeedPage;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.zy.sports.R;
import me.zy.sports.dao.bean.Comment;
import me.zy.sports.dao.bean.Feed;
import me.zy.sports.dao.bean.Myuser;
import me.zy.sports.dao.bean.Reply;

/**
 * 项目名：sports
 * 包名：me.zy.sports.activitys.FeedPage
 * Created by Administrator on 2019/5/20.
 * 描述：
 */
public class EvaluateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Comment> mList;

    private static final int TYPE_FOOTER = -1;

    private OnItemListener mOnItemListener;

    public interface OnItemListener {
        void onItemClick(View view, Comment comment);

        void onItemChildClick(View view, Comment comment, Reply reply);
    }

    public void setOnItemListener(OnItemListener onItemListener) {
        this.mOnItemListener = onItemListener;
    }

    public EvaluateAdapter(Context context, List<Comment> list) {
        this.mList =list;
        this.mContext = context;

    }

    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为footerView
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return position;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = View.inflate(parent.getContext(), R.layout.item_feed_evaluate_recycle, null);
            return new EvaluateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            EvaluateViewHolder evaluateViewHolder = (EvaluateViewHolder) holder;
            evaluateViewHolder.bindItem(mContext, mList.get(position));

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    class EvaluateViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.evaluate_body)
        RelativeLayout mEvaluateBody;
        @BindView(R.id.user_img)
        ImageView mUserImg;
        @BindView(R.id.user_name)
        TextView mUserName;
        @BindView(R.id.evaluate_time)
        TextView mEvaluateTime;
        @BindView(R.id.evaluate_info)
        AppCompatTextView mEvaluateInfo;
        @BindView(R.id.recycler_view)
        RecyclerView mRecyclerView;
        private Comment mComment;

        public EvaluateViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            LinearLayoutManager layoutManager = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.VERTICAL, false);
            mRecyclerView.setLayoutManager(layoutManager);
        }

        public void bindItem(Context context, Comment comment) {
            mComment = comment;
            Myuser user = comment.getUser();
            mUserName.setText(user.getNickname());
            mEvaluateTime.setText(comment.getCreatedAt());
            mEvaluateInfo.setText(comment.getCommentInfo());
            ReplyAdapter adapter = new ReplyAdapter(context, comment.getReplyList());
            mRecyclerView.setAdapter(adapter);
            final Comment comment1=comment;
            adapter.setOnItemListener(new ReplyAdapter.OnItemListener() {
                @Override
                public void onItemClick(View view, Reply reply) {
                    if (mOnItemListener != null) mOnItemListener.onItemChildClick(view, comment1, reply);
                }
            });
        }

        @OnClick({R.id.user_img, R.id.evaluate_body})
        public void onClick(View view) {
            if (mOnItemListener != null) mOnItemListener.onItemClick(view, mComment);
        }
    }
}