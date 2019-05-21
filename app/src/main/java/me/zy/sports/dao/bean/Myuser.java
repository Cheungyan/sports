package me.zy.sports.dao.bean;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

/**
 * 项目名：sports
 * 包名：me.zy.sports.dao.bean
 * Created by Administrator on 2019/5/5.
 * 描述：
 */
public class Myuser extends BmobUser {

    private int age;

    private String desc;


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    private String nickname;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }



}
