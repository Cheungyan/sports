package me.zy.sports.dao.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * 项目名：sports
 * 包名：me.zy.sports.dao.bean
 * Created by Administrator on 2019/5/8.
 * 描述：
 */
public class MyArticle extends BmobObject {

    public BmobFile getImage_title() {
        return image_title;
    }

    public void setImage_title(BmobFile image_title) {
        this.image_title = image_title;
    }

    private BmobFile image_title;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    String desc;
    String Aurl;


    public String getAUrl() {
        return Aurl;
    }

    public void setAUrl(String url) {
        this.Aurl = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    String title;
}
