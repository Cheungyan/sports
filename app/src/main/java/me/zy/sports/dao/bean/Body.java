package me.zy.sports.dao.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.http.I;

/**
 * 项目名：sports
 * 包名：me.zy.sports.dao.bean
 * Created by Administrator on 2019/5/21.
 * 描述：
 */
public class Body extends BmobObject {
    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public Float getNewweight() {
        return newweight;
    }

    public void setNewweight(Float newweight) {
        this.newweight = newweight;
    }

    private Float weight;



    private Float height;
    private Float newweight;




    private boolean sex;
    private Myuser user;


    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public Myuser getUser() {
        return user;
    }

    public void setUser(Myuser user) {
        this.user = user;
    }
}
