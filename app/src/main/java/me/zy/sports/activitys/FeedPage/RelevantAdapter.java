package me.zy.sports.activitys.FeedPage;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
 * Created by Administrator on 2019/5/21.
 * 描述：
 */
public class RelevantAdapter extends RecyclerView.Adapter<RelevantAdapter.RelevantViewHolder> {

    private Context mContext;
    private List<Comment> mList;

    private OnItemListener mOnItemListener;

    public interface OnItemListener {
        void onItemClick(View view, Comment relevant);
    }

    public void setOnItemListener(OnItemListener onItemListener) {
        this.mOnItemListener = onItemListener;
    }

    public RelevantAdapter(Context context, List<Comment> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public RelevantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_relevant_recylce, null);
        return new RelevantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RelevantViewHolder holder, int position) {
        holder.bindItem(mContext, mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class RelevantViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.user_img)
        ImageView mUserImg;
        @BindView(R.id.user_name)
        TextView mUserName;
        @BindView(R.id.relevant_time)
        TextView mRelevantTime;
        @BindView(R.id.relevant_info)
        AppCompatTextView mRelevantInfo;
        @BindView(R.id.feed_info)
        AppCompatTextView mMoodInfo;
        @BindView(R.id.feed_body)
        LinearLayout mMoodBody;

        private Comment mComment;

        public RelevantViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindItem(Context context, Comment comment) {
            mComment = comment;

            Myuser user = comment.getUser();
            Integer replyNum = comment.getReplyList().size();
            StringBuilder relevantInfo = new StringBuilder();
            String timeStr = "";
            if (replyNum > 0) {
                List<Reply> replyList = comment.getReplyList();
                for (int i = 0, size = replyList.size(); i < size; i++) {
                    Reply reply = replyList.get(i);
                    if (i == 0) {
                        user = reply.getUser();
                        timeStr = reply.getCreatedAt();
                        relevantInfo.append(reply.getCommentInfo());
                    } else {
                        relevantInfo.append("//@").append(reply.getUser().getNickname()).append(":")
                                .append(reply.getCommentInfo());
                    }

                }
                relevantInfo.append("//@").append(comment.getUser().getNickname()).append(":")
                        .append(comment.getCommentInfo());
            } else {
                timeStr = comment.getCreatedAt();
                relevantInfo.append(comment.getCommentInfo());
            }

            mUserName.setText(user.getNickname());
            mRelevantTime.setText(timeStr);
            mRelevantInfo.setText(relevantInfo.toString());


            Feed feed = comment.getFeed();
            String feedInfo = feed.getUser().getNickname()+ ":"+feed.getFeedInfo();
            mMoodInfo.setText(feedInfo);
        }

        @OnClick({R.id.user_img, R.id.feed_body})
        public void onClick(View view) {
            if (mOnItemListener != null) mOnItemListener.onItemClick(view, mComment);
        }
    }
}