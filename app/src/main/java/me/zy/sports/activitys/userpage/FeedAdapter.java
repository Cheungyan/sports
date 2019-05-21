package me.zy.sports.activitys.userpage;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;
import me.zy.sports.R;
import me.zy.sports.dao.bean.Feed;
import me.zy.sports.dao.bean.Myuser;
import me.zy.sports.utils.Utils;

/**
 * 项目名：sports
 * 包名：me.zy.sports.activitys.userpage
 * Created by Administrator on 2019/5/18.
 * 描述：
 */
public class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Feed> mList;
    private static final int TYPE_FOOTER = -1;
    private Context mContext;
    private OnItemListener mOnItemListener;
    private LayoutInflater minflater;

    public interface OnItemListener {
        void onItemClick(View view, Feed feed, int position);
        // void onPhotoClick(String photo, int position);
    }

    public void setOnItemListener(OnItemListener onItemListener) {
        this.mOnItemListener = onItemListener;
    }

    public FeedAdapter(Context context, List<Feed> list) {
        this.mList = list == null ? new ArrayList<Feed>() : list;
        mContext = context;
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
        View feedView = minflater.from(mContext).inflate(R.layout.feed_detail_recycle_item, parent, false);
        return new MoodViewHolder(feedView);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MoodViewHolder moodViewHolder = (MoodViewHolder) holder;
        moodViewHolder.bindItem(mList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    class MoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView mUserImg;
        TextView mUserName;
        TextView mFeedTime;
        AppCompatTextView mFeedInfo;
        LinearLayout mFeedBody;
        TextView mFeedSeeNum;
        TextView mFeedCommentNum;
        LinearLayout mFeedCommentLayout;
        ImageView mFeedLikeIcon;
        TextView mFeedLikeNum;
        LinearLayout mFeedLikeLayout;
        LinearLayout mFeedActionLayout;
        LinearLayout mFeedCard;
        ImageView mFeedImage;

        private Feed mFeed;
        private int mPosition;

        public MoodViewHolder(View itemView) {
            super(itemView);
            mUserImg = (ImageView) itemView.findViewById(R.id.user_img);
            mUserName = (TextView) itemView.findViewById(R.id.feed_user_name);
            mFeedTime = (TextView) itemView.findViewById(R.id.feed_time);
            mFeedInfo = (AppCompatTextView) itemView.findViewById(R.id.feed_info);
            mFeedBody = (LinearLayout) itemView.findViewById(R.id.feed_body);
            mFeedSeeNum = (TextView) itemView.findViewById(R.id.feed_view_num);
            mFeedCommentNum = (TextView) itemView.findViewById(R.id.feed_comment_num);
            mFeedCommentLayout = (LinearLayout) itemView.findViewById(R.id.feed_comment_layout);
            mFeedLikeIcon = (ImageView) itemView.findViewById(R.id.feed_like_icon);
            mFeedLikeNum = (TextView) itemView.findViewById(R.id.feed_like_num);
            mFeedLikeLayout = (LinearLayout) itemView.findViewById(R.id.feed_like_layout);
            mFeedActionLayout = (LinearLayout) itemView.findViewById(R.id.feed_action_layout);
            mFeedCard = (LinearLayout) itemView.findViewById(R.id.feed_card);
            mFeedImage = (ImageView) itemView.findViewById(R.id.feed_image_a);
            mFeedCard.setOnClickListener(this);
            mFeedImage.setOnClickListener(this);
            mFeedLikeLayout.setOnClickListener(this);
            mFeedCommentLayout.setOnClickListener(this);

        }

        public void bindItem(Feed feed, int position) {
            mFeed = feed;
            mPosition = position;


            // 动态详情 设置头像先不做ContentUtil.loadUserAvatar(mUserImg, user.getAvatar());

            mUserName.setText(feed.getUser().getNickname());
            mFeedTime.setText(Utils.showTime(feed.getCreatedAt()));
            mFeedInfo.setText(feed.getFeedInfo());
            // 图片
            if (!TextUtils.isEmpty(feed.getIamge())) {
                //加载图片
                Picasso.with(mContext).load(feed.getIamge()).into(mFeedImage);
            }
            // 查看评论点赞数
            mFeedSeeNum.setText(String.valueOf(feed.getSeeNum()));
            mFeedCommentNum.setText(String.valueOf(feed.getCommentNum()));
            mFeedLikeNum.setText(String.valueOf(feed.getLikeNum()));
            // 本人是否已经点赞
            if (feed.isLike()) {
                mFeedLikeIcon.setSelected(true);
            } else {
                mFeedLikeIcon.setSelected(false);
            }
            // 点赞列表

        }

        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.feed_like_layout:
                case R.id.feed_image_a:
                case R.id.feed_card:
                case R.id.feed_comment_layout:
                    if (mOnItemListener != null)
                        mOnItemListener.onItemClick(view, mFeed, mPosition);
                    break;
            }
        }
    }
}

