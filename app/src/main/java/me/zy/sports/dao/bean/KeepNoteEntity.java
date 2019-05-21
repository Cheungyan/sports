package me.zy.sports.dao.bean;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * 项目名：sports
 * 包名：me.zy.sports.dao.bean
 * Created by Administrator on 2019/5/10.
 * 描述：
 */
public class KeepNoteEntity extends BmobObject {
    private String date;//日期
    private Float exerciseDuration;//锻炼时长
    private Float runLength;// 跑步长度
    private Integer sitUps;//仰卧起坐
    private Integer sportsApparatusTimes;// 力量器械组数
    private String userId; // 当前用户id。也就是这条记录属于哪个用户

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Float getExerciseDuration() {
        return exerciseDuration;
    }

    public void setExerciseDuration(Float exerciseDuration) {
        this.exerciseDuration = exerciseDuration;
    }

    public Float getRunLength() {
        return runLength;
    }

    public void setRunLength(Float runLength) {
        this.runLength = runLength;
    }

    public Integer getSitUps() {
        return sitUps;
    }

    public void setSitUps(Integer sitUps) {
        this.sitUps = sitUps;
    }

    public Integer getSportsApparatusTimes() {
        return sportsApparatusTimes;
    }

    public void setSportsApparatusTimes(Integer sportsApparatusTimes) {
        this.sportsApparatusTimes = sportsApparatusTimes;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
