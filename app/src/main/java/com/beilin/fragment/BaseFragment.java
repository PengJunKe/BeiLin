package com.beilin.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Handler;
import android.view.View;

/**
 * Created by Lenovo on 2016/4/26.
 */
public abstract class BaseFragment extends Fragment {
    protected Context mContext;
    protected Handler mHandler;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mHandler = new Handler();
    }

    /**
     * 获取布局文件
     */
    protected  abstract View getLayout(int id);

    /**
     * 初始化数据
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 设置监听
     */
    protected abstract void setListener();
}
