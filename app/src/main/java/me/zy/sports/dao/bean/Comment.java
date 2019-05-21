package me.zy.sports.dao.bean;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * 项目名：sports
 * 包名：me.zy.sports.dao.bean
 * Created by Administrator on 2019/5/20.
 * 描述：
 */
public class Comment extends BmobObject {
    private Feed feed;
    private String commentInfo;
    private Myuser user;
    private Myuser toUser;

    public Feed getFeed() {
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }

    public String getCommentInfo() {
        return commentInfo;
    }

    public void setCommentInfo(String commentInfo) {
        this.commentInfo = commentInfo;
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

    public Integer getReplyNum() {
        return replyNum;
    }

    public void setReplyNum(Integer replyNum) {
        this.replyNum = replyNum;
    }

    public List<Reply> getReplyList() {
        return replyList;
    }

    public void setReplyList(List<Reply> replyList) {
        this.replyList = replyList;
    }

    private Integer replyNum;
    private List<Reply> replyList;
}
