package com.beilin.entity;

import com.avos.avoscloud.AVObject;
import com.beilin.activity.BaseActivity;

import java.util.Date;

/**
 * Created by ChengTao on 2016/5/23.
 * @author ChengTao
 */
public class SheTuan extends AVObject{
    public String name;
    public String introduce;
    public String like;

    public String getName() {
        return (String) get("name");
    }

    public String getIntroduce() {
        return (String) get("introduce");
    }

    public String getLike() {
        return (String) get("like");
    }

}
