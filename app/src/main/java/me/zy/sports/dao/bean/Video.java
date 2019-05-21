package me.zy.sports.dao.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * 项目名：sports
 * 包名：me.zy.sports.dao.bean
 * Created by Administrator on 2019/5/9.
 * 描述：
 */
public class Video extends BmobObject {
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BmobFile getImage() {
        return image;
    }

    public void setImage(BmobFile image) {
        this.image = image;
    }

    public BmobFile getVideo() {
        return video;
    }

    public void setVideo(BmobFile video) {
        this.video = video;
    }

    private BmobFile image;
    private BmobFile video;



}
