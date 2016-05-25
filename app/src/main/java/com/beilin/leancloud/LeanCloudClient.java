package com.beilin.leancloud;

import android.util.Log;

import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.beilin.request.IRequest;

/**
 * Created by ChengTao on 2016/5/25.
 *
 * 基于LeanCloud数据库，用于处理页面的各种请求类
 *
 * @author ChengTao
 */
public class LeanCloudClient {
    /**
     * 自定义的用于向主线程发送消息的handler
     */
    private LeanCloudHandler handler;
    /**
     * 用于查找的LeanCloud类
     */
    private AVQuery<AVObject> mAVQuery;

    /**
     * 构造函数，初始化LeanCloudHandler(用于发送消息)和ILeanCloudListener(数据接口，用于activity页面做相应的处理)
     *
     * @param listener 数据接口
     */
    public LeanCloudClient(ILeanCloudListener listener) {
        this.handler = new LeanCloudHandler(listener);
    }

    /**
     * 在查找时所需要的LeanCloud类名
     * @param className LeanCloud类名
     */
    public void setClassName(String className){
        mAVQuery = new AVQuery<>(className);
    }

    /**
     * 向LeanCloud数据库批量插入数据
     *
     * @param request 请求
     */
    public void baseInsert(IRequest request) {
        if (request != null) {
            handler.sendStartMessage(request.getRequestId());
            AVObject.saveAllInBackground(request.getInsertList(),new LeanCloudResponse(request,handler));
        }
    }
    /**
     * 批量删除LeanCloud数据库中的数据
     *
     * @param request 请求
     */
    public void baseDelete(IRequest request) {
        if (request != null) {
            handler.sendStartMessage(request.getRequestId());
            Log.v("info",request.getDeleteList().toString());
            AVObject.deleteAllInBackground(request.getDeleteList(),new LeanCloudDeleteResponse(request,handler));
        }
    }

    /**
     * 更新LeanCloud数据库的数据
     *
     * @param request 请求
     */
    public void baseUpdate(IRequest request) {
        if (request != null) {
            handler.sendStartMessage(request.getRequestId());
            request.getUpDateObject().saveInBackground(new LeanCloudResponse(request,handler));
        }
    }

    /**
     * 从LeanCloud数据库查找数据
     *
     * @param request 请求
     */
    public void baseQuery(IRequest request) {
        if (request != null) {
            handler.sendStartMessage(request.getRequestId());
            mAVQuery.findInBackground(new LeanCloudQueryResponse(request, handler));
        }
    }
}
