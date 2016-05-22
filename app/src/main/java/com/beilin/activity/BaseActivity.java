package com.beilin.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.beilin.leancloud.get.IGetListener;
import com.beilin.leancloud.get.LeanCloudGetClient;
import com.beilin.request.IRequest;
import com.nostra13.universalimageloader.utils.L;

/**
 * Created by Lenovo on 2016/5/21.
 */
public abstract class BaseActivity extends Activity implements IGetListener {
    protected Handler mHandler;
    protected Context mContext;
    protected LeanCloudGetClient mGetClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutId() != 0){
            setContentView(getLayoutId());
        }
        mContext = this;
        mHandler = new Handler();
        mGetClient = new LeanCloudGetClient(mHandler,this);
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

    /**
     * 获取数据
     * @param request
     */
    protected void getData(IRequest request){
        Log.v("info","------------------");
        mGetClient.setClassName(request.getClassName());
        mGetClient.getData(request);
    }
}
