package me.zy.sports.dao.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * 项目名：sports
 * 包名：me.zy.sports.dao.bean
 * Created by Administrator on 2019/5/10.
 * 描述：
 */
public class ClassContent extends BmobObject {
    private String title;
    private String desc;
    private BmobFile image;
    private String buildClassid;

    public String getBuildClassid() {
        return buildClassid;
    }

    public void setBuildClassid(String buildClassid) {
        this.buildClassid = buildClassid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public BmobFile getImage() {
        return image;
    }

    public void setImage(BmobFile image) {
        this.image = image;
    }
}
