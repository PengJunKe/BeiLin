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
import com.beilin.leancloud.get.LeanCloudGetClient;
import com.beilin.leancloud.put.LeanCloudPutClient;
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
    protected LeanCloudGetClient mGetClient;
    protected LeanCloudPutClient mPutClient;

    //LeanCloud数据库中的表名
    /**
     * 社团表名
     */
    protected final static String SHE_TUAN = "SheTuan";
    /**
     * 报名表名
     */
    protected final static String BAO_MING = "BaoMing";
    /**
     * 活动表名
     */
    protected final static String HUO_DONG = "HuoDong";
    /**
     * 视频表名
     */
    protected final static String SHI_PIN = "ShiPin";
    /**
     * 图片表名
     */
    protected final static String TU_PIAN = "TuPian";
    /**
     * 消息表名
     */
    protected final static String XIAO_XI = "XiaoXi";
    //LeanCloud数据库中的表名

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
        mGetClient = new LeanCloudGetClient(mHandler, this);
        mPutClient = new LeanCloudPutClient(mHandler,this);
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
     * 获取数据
     *
     * @param request 发出的请求
     */
    protected void getData(IRequest request) {
        mGetClient.setClassName(request.getClassName());
        mGetClient.getData(request);
    }

    /**
     * 获取数据
     *
     * @param request 发出的请求
     */
    protected void putData(IRequest request) {
        mPutClient.setClassName(request.getClassName());
        mPutClient.putData(request);
    }

    @Override
    public void onFailure(int requestId, AVException e) {
        showToast("请求失败");
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
