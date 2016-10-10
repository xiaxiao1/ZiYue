package com.example.xiaxiao.ziyue;

import cn.bmob.v3.BmobObject;

/**
 * Created by xiaxi on 2016/10/10.
 *
 *
 * BmobObject类本身包含objectId、createdAt、updatedAt、ACL四个默认的属性，
 * objectId是数据的唯一标示，相当于数据库中表的主键，
 * createdAt是数据的创建时间，
 * updatedAt是数据的最后修改时间，
 * ACL是数据的操作权限。
 */
public class Story extends BmobObject {
    private Integer tellerId;
    private String tellerName;
    private String storyIntroduce;
    private String stroyPictureUrl;


    public Story() {
    }

    public Story(int tellerId, String tellerName, String storyIntroduce, String stroyPictureUrl) {
        this.tellerId = tellerId;
        this.tellerName = tellerName;
        this.storyIntroduce = storyIntroduce;
        this.stroyPictureUrl = stroyPictureUrl;
    }

    public int getTellerId() {
        return tellerId;
    }

    public void setTellerId(int tellerId) {
        this.tellerId = tellerId;
    }

    public String getTellerName() {
        return tellerName;
    }

    public void setTellerName(String tellerName) {
        this.tellerName = tellerName;
    }

    public String getStoryIntroduce() {
        return storyIntroduce;
    }

    public void setStoryIntroduce(String storyIntroduce) {
        this.storyIntroduce = storyIntroduce;
    }

    public String getStroyPictureUrl() {
        return stroyPictureUrl;
    }

    public void setStroyPictureUrl(String stroyPictureUrl) {
        this.stroyPictureUrl = stroyPictureUrl;
    }

    @Override
    public String toString() {
        return "Story{" +
                "tellerId=" + tellerId +
                ", tellerName='" + tellerName + '\'' +
                ", storyIntroduce='" + storyIntroduce + '\'' +
                ", stroyPictureUrl='" + stroyPictureUrl + '\'' +
                '}';
    }
}
