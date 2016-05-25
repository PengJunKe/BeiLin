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
import com.beilin.leancloud.query.LeanCloudQueryClient;
import com.beilin.leancloud.insert.LeanCloudInsertClient;
import com.beilin.request.IRequest;

import java.util.List;

/**
 * Created by Lenovo on 2016/5/21.
 * @author ChengTao
 */
@SuppressWarnings("ALL")
public abstract class BaseActivity extends Activity implements ILeanCloudListener{
    protected Handler mHandler;
    protected Context mContext;
    protected LeanCloudQueryClient mQueryClient = new LeanCloudQueryClient(this);
    protected LeanCloudInsertClient mInsertClient = new LeanCloudInsertClient(this);

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
     * 查找数据
     *
     * @param request 发出的请求
     */
    protected void queryData(IRequest request) {
        mQueryClient.setClassName(request.getClassName());
        mQueryClient.customQuery(request);
    }

    /**
     * 插入数据
     *
     * @param request 发出的请求
     */
    protected void insertData(IRequest request) {
        mInsertClient.customInsert(request);
    }

    @Override
    public void onFailure(int requestId, AVException e) {
        showToast("网络连接失败，请检查网路");
        Log.v("info","onFailure");
    }

    @Override
    public void onStart(int requestId) {
        Log.v("info","onStart");
    }

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
