package com.beilin.leancloud.query;

import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.beilin.leancloud.ILeanCloudListener;
import com.beilin.request.IRequest;

/**
 * Created by Lenovo on 2016/5/21.
 * <p/>
 * LeanCloudGetClient类，用于从LeanCloud数据库中获取数据
 *
 * @author ChengTao
 */
public class LeanCloudQueryClient {
    private AVQuery<AVObject> mAVQuery;
    private LeanCloudQueryHandler handler;

    /**
     * 构造函数，初始化LeanCloudGetHandler(用于发送消息)和IGetListener(数据接口，用于activity页面做相应的处理)
     *
     * @param listener 数据接口
     */
    public LeanCloudQueryClient(ILeanCloudListener listener) {
        this.handler = new LeanCloudQueryHandler(listener);
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
     * 从LeanCloud数据库查找数据
     *
     * @param request 请求
     */
    public void customQuery(IRequest request) {
        if (request != null) {
            handler.sendStartMessage(request.getRequestId());
            mAVQuery.findInBackground(new LeanCloudQueryResponse(request, handler));
        }
    }
}
