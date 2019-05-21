package me.zy.sports.dao.utils;

import android.widget.Toast;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import me.zy.sports.activitys.userpage.UserActivity;
import me.zy.sports.dao.bean.Feed;

/**
 * 项目名：sports
 * 包名：me.zy.sports.dao.utils
 * Created by Administrator on 2019/5/21.
 * 描述：
 */
public class FeedUtil {

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
