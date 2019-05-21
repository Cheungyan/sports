package me.zy.sports.activitys.FeedPage;

/**
 * 项目名：sports
 * 包名：me.zy.sports.activitys.FeedPage
 * Created by Administrator on 2019/5/20.
 * 描述：
 */
import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.zy.sports.R;
import me.zy.sports.dao.bean.Feed;
import me.zy.sports.dao.bean.Reply;

/**
 * Reply Adapter
 */
public class ReplyAdapter extends RecyclerView.Adapter<ReplyAdapter.ReplyViewHolder> {

    private Context mContext;
    private List<Reply> mList;

    private OnItemListener mOnItemListener;

    public interface OnItemListener {
        void onItemClick(View view, Reply reply);
    }

    public void setOnItemListener(OnItemListener onItemListener) {
        this.mOnItemListener = onItemListener;
    }

    public ReplyAdapter(Context context, List<Reply> list) {
        this.mContext = context;
        this.mList = list == null ? new ArrayList<Reply>() : list;
    }

    @Override
    public ReplyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_feed_reply_recycle, null);
        return new ReplyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReplyViewHolder holder, int position) {
        holder.bindItem(mContext, mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ReplyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.reply_info)
        AppCompatTextView mReplyInfo;
        private Reply mReply;

        public ReplyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindItem(Context context, Reply reply) {
            mReply = reply;
            String replyStr =  reply.getUser().getNickname() + "回复" + reply.getToUser().getNickname() + "：" + reply.getCommentInfo();
            mReplyInfo.setText(replyStr);
        }

        @OnClick(R.id.reply_info)
        void onClick(View view){
            if (mOnItemListener != null) mOnItemListener.onItemClick(view, mReply);
        }
    }
}
