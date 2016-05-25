package com.beilin.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.beilin.leancloud.ILeanCloudListener;
import com.beilin.leancloud.LeanCloudClient;
import com.beilin.request.IRequest;

import java.util.List;

/**
 * Created by Lenovo on 2016/5/21.
 * @author ChengTao
 */
@SuppressWarnings("ALL")
public abstract class BaseActivity extends Activity implements ILeanCloudListener{
    /**
     * 主线程handler
     */
    protected Handler mHandler;
    /**
     * 本页面上下文
     */
    protected Context mContext;
    /**
     * LeanCloudClient的实体，用于进行增删改查的操作
     */
    protected LeanCloudClient mClient = new LeanCloudClient(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
        }
        mContext = this;
        mHandler = new Handler();
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
    public <T extends View> T obtainView(int id) {
        return (T) findViewById(id);
    }

    /**
     * 基本的查找数据
     *
     * @param request 发出的请求
     */
    protected void baseQueryData(IRequest request) {
        mClient.setClassName(request.getClassName());
        mClient.baseQuery(request);
    }

    /**
     * 基本的插入数据
     *
     * @param request 发出的请求
     */
    protected void baseInsertData(IRequest request) {
        mClient.baseInsert(request);
    }

    /**
     * 基本的删除数据
     *
     * @param request 发出的请求
     */
    protected void deleteData(IRequest request){
        mClient.baseDelete(request);
    }

    /**
     * 基本的更新数据
     * @param request 发出的请求
     */
    protected void baseUpDate(IRequest request){
        mClient.baseUpdate(request);
    }

    /**
     * 默认的请求失败处理
     * @param requestId 请求Id
     * @param e 异常
     */
    @Override
    public void onFailure(int requestId, AVException e) {
        Log.v("info","onFailure");
        Log.v("info","e-------"+e.toString());
        if (e.getCode() == 1){
            showToast("您没有权限...");
        }else {
            showToast("网络连接失败，请检查网路...");
        }
    }

    /**
     * 默认的请求开始处理
     * @param requestId 请求Id
     */
    @Override
    public void onStart(int requestId) {
        Log.v("info","onStart");
    }

    /**
     * 默认的请求成功处理
     * @param requestId 请求Id
     * @param list 返回的数据集合
     */
    @Override
    public void onSuccess(int requestId, List<AVObject> list) {
        Log.v("info","onSuccess");
    }

    /**
     * 弹出吐司
     * @param s 所要展示的字符串
     */
    protected void showToast(String s){
        Toast.makeText(mContext,s,Toast.LENGTH_SHORT).show();
    }
}
