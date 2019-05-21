package me.zy.sports.activitys.Bodypage;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import me.zy.sports.R;
import me.zy.sports.activitys.Bodypage.Video.VideoAdapter;
import me.zy.sports.activitys.Bodypage.Video.VideoViewHolder;
import me.zy.sports.dao.bean.Video;

/**
 * 项目名：sports
 * 包名：me.zy.sports.activitys.Bodypage
 * Created by Administrator on 2019/5/9.
 * 描述：
 */
public class VedioFragment extends Fragment {
    private RecyclerView mRecyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fg_vedio,null);
        return view;
    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        mRecyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);

        BmobQuery<Video> VideoBmobQuery = new BmobQuery<>();
        VideoBmobQuery.findObjects(new FindListener<Video>() {
            @Override
            public void done(List<Video> object, BmobException e) {
                if (e == null) {
                    Log.w("TTTTTT", "查视频成功");
                    VideoAdapter adapter = new VideoAdapter(getActivity(), object);
                    mRecyclerView.setAdapter(adapter);

                } else {
                }
            }
        });




        mRecyclerView.setRecyclerListener(new RecyclerView.RecyclerListener() {
            @Override
            public void onViewRecycled(RecyclerView.ViewHolder holder) {
                NiceVideoPlayer niceVideoPlayer = ((VideoViewHolder) holder).mVideoPlayer;
                if (niceVideoPlayer == NiceVideoPlayerManager.instance().getCurrentNiceVideoPlayer()) {
                    NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
                }
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
    }
}
