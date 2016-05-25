package com.beilin.entity;

import com.avos.avoscloud.AVObject;

/**
 * Created by ChengTao on 2016/5/23.
 * @author ChengTao
 */
public class SheTuan extends AVObject{
    public String name;
    public String introduce;
    public String like;

    public SheTuan() {
    }

    public SheTuan(String theClassName) {
        super(theClassName);
    }
}
