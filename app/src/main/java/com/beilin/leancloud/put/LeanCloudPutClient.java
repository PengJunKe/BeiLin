package com.beilin.leancloud.put;

import android.os.Handler;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.SaveCallback;
import com.beilin.leancloud.ILeanCloudListener;
import com.beilin.request.IRequest;

/**
 * Created by Lenovo on 2016/5/21.
 * <p/>
 * LeanCloudGetClient类，用于从LeanCloud数据库中获取数据
 *
 * @author ChengTao
 */
public class LeanCloudPutClient {
    private AVObject mAVObject;
    private LeanCloudPutHandler handler;

    /**
     * 构造函数，初始化LeanCloudGetHandler(用于发送消息)和IGetListener(数据接口，用于activity页面做相应的处理)
     *
     * @param handler  主线程的handler
     * @param listener 数据接口
     */
    public LeanCloudPutClient(Handler handler, ILeanCloudListener listener) {
        this.handler = new LeanCloudPutHandler(handler, listener);
    }

    /**
     * 设置要查找的LeanCloud表名
     *
     * @param className LeanCloud表名
     */
    public void setClassName(String className) {
        mAVObject = new AVObject(className);
    }

    /**
     * 从LeanCloud数据库获取数据
     *
     * @param request 请求
     */
    public void putData(IRequest request) {
        if (request != null) {
            handler.sendStartMessage(request.getRequestId());
            for (String key:request.getPutDatas().keySet()){
                mAVObject.put(key,request.getPutDatas().get(key));
            }
            mAVObject.saveInBackground(new LeanCloudPutResponse(request,handler));
        }
    }
}
