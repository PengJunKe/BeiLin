package com.beilin.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

/**
 * Created by Lenovo on 2016/4/26.
 */
public abstract class BaseActivity extends Activity{
    protected Handler mHandler;
    protected Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutId() != 0){
            setContentView(getLayoutId());
        }
        mContext = this;
        initView();
        setListener();
        initData();
    }

    /**
     * 返回当前布局
     */
    protected abstract int getLayoutId();

    /**
     * 初始化View
     */
    protected abstract void initView();

    /**
     * 设置监听
     */
    protected abstract void setListener();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 获取View,省去findViewById
     */
    public <T extends View> T obtainView(int id){
        return (T)findViewById(id);
    }
}

























