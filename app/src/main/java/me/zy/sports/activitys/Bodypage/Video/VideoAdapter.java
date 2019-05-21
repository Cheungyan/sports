package me.zy.sports.activitys.Bodypage.Video;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.xiao.nicevideoplayer.TxVideoPlayerController;

import java.util.List;

import me.zy.sports.R;
import me.zy.sports.dao.bean.Video;

/**
 * 项目名：sports
 * 包名：me.zy.sports.activitys.Bodypage
 * Created by Administrator on 2019/5/9.
 * 描述：
 */
public class VideoAdapter extends RecyclerView.Adapter<VideoViewHolder> {
    private Context mContext;
    private List<Video> mVideoList;

    public VideoAdapter(Context context, List<Video> videoList) {
        mContext = context;
        mVideoList = videoList;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_video, parent, false);
        VideoViewHolder holder = new VideoViewHolder(itemView);

        TxVideoPlayerController controller = new TxVideoPlayerController(mContext);
        holder.setController(controller);
        return holder;
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        Video video = mVideoList.get(position);
        holder.bindData(video,mContext);
    }

    @Override
    public int getItemCount() {
        return mVideoList.size();
    }

}
