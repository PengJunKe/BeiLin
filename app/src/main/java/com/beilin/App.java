package com.beilin;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;

/**
 * Created by Lenovo on 2016/4/26.
 */
public class App extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        AVOSCloud.initialize(this, "B2wc7iq7I3YGf6Qx9Guk0XO7-gzGzoHsz", "YONyHWyFSlUPyKIKq2J3Aku8");
    }
}