package com.beilin.leancloud.get;

import android.os.Handler;

import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.beilin.request.IRequest;

/**
 * Created by Lenovo on 2016/5/21.
 * <p/>
 * LeanCloudGetClient类，用于从LeanCloud数据库中获取数据
 *
 * @author ChengTao
 */
public class LeanCloudGetClient {
    private AVQuery<AVObject> mAVQuery;
    private LeanCloudGetHandler handler;

    /**
     * 构造函数，初始化LeanCloudGetHandler(用于发送消息)和IGetListener(数据接口，用于activity页面做相应的处理)
     *
     * @param handler  主线程的handler
     * @param listener 数据接口
     */
    public LeanCloudGetClient(Handler handler, IGetListener listener) {
        this.handler = new LeanCloudGetHandler(handler, listener);
    }

    /**
     * 设置要查找的LeanCloud表名
     *
     * @param className LeanCloud表名
     */
    public void setClassName(String className) {
        mAVQuery = new AVQuery<>(className);
    }

    /**
     * 从LeanCloud数据库获取数据
     *
     * @param request 请求
     */
    public void getData(IRequest request) {
        if (request != null) {
            handler.sendStartMessage(request.getRequestId());
            mAVQuery.findInBackground(new LeanCloudGetResponse(request, handler));
        }
    }
}
