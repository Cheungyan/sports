package me.zy.sports.dao.bean;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * 项目名：sports
 * 包名：me.zy.sports.dao.bean
 * Created by Administrator on 2019/5/21.
 * 描述：
 */
public class Relevant extends BmobObject {

    private Feed feed;
    private Comment comment;

    public Feed getFeed() {
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
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
    private List<Reply>replyList;
}
