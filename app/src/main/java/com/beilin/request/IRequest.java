package com.beilin.request;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;

import java.util.List;

/**
 * Created by Lenovo on 2016/4/26.
 */
public class IRequest {
    private List<AVObject> mlist;//Avobject List对象

    public IRequest(List<AVObject> list){
        mlist = list;
    }
    /**
     * 从leacloud获取刷新数据数据，默认加载10个数据
     * @param className 表明
     * @param lastTime list中最新的时间
     */
    private void getRefreshData(String className,AVObject lastTime){
        AVQuery<AVObject> query = new AVQuery<>(className);
        query.limit(10);
        query.whereGreaterThan("updatedAt", lastTime.getUpdatedAt());
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if ( e!=null ){
                    Log.v("info",e.toString());
                }else {
                    mlist = list;
                }
            }
        });
    }
}
