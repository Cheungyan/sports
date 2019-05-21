package me.zy.sports.dao.bean;

import cn.bmob.v3.BmobObject;

/**
 * 项目名：sports
 * 包名：me.zy.sports.dao.bean
 * Created by Administrator on 2019/5/20.
 * 描述：
 */
public class Reply extends BmobObject {
    private Feed feed;
    private Myuser user;
    private Myuser toUser;
    private Comment comment;

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public Feed getFeed() {
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }

    public Myuser getUser() {
        return user;
    }

    public void setUser(Myuser user) {
        this.user = user;
    }

    public Myuser getToUser() {
        return toUser;
    }

    public void setToUser(Myuser toUser) {
        this.toUser = toUser;
    }

    public String getCommentInfo() {
        return commentInfo;
    }

    public void setCommentInfo(String commentInfo) {
        this.commentInfo = commentInfo;
    }

    private String commentInfo;
}
