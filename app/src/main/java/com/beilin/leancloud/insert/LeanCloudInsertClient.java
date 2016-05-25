package com.beilin.leancloud.insert;

import com.avos.avoscloud.AVObject;
import com.beilin.leancloud.ILeanCloudListener;
import com.beilin.leancloud.query.LeanCloudQueryResponse;
import com.beilin.request.IRequest;

/**
 * Created by Lenovo on 2016/5/21.
 * <p/>
 * LeanCloudGetClient类，用于从LeanCloud数据库中获取数据
 *
 * @author ChengTao
 */
public class LeanCloudInsertClient {
    private AVObject mAVObject;
    private LeanCloudInsertHandler handler;

    /**
     * 构造函数，初始化LeanCloudGetHandler(用于发送消息)和IGetListener(数据接口，用于activity页面做相应的处理)
     *
     * @param listener 数据接口
     */
    public LeanCloudInsertClient(ILeanCloudListener listener) {
        this.handler = new LeanCloudInsertHandler(listener);
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
     * 向LeanCloud数据库插入数据
     *
     * @param request 请求
     */
    public void customInsert(IRequest request) {
        if (request != null) {
            handler.sendStartMessage(request.getRequestId());
            /*for (String key:request.getPutDatas().keySet()){
                mAVObject.put(key,request.getPutDatas().get(key));
            }
            mAVObject.saveInBackground(new LeanCloudInsertResponse(request,handler));*/
            AVObject.saveAllInBackground(request.getInsertList(),new LeanCloudInsertResponse(request,handler));
        }
    }
}
