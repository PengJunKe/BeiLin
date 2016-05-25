package com.beilin.leancloud.query;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.FindCallback;
import com.beilin.request.IRequest;

import java.util.List;

/**
 * Created by Lenovo on 2016/5/21.
 * <p/>
 * LeanCloudGetResponse类，用于处理请求成功和失败，
 * 并将消息发送给LeanCloudGetHandler，
 * 将结果交给LeanCloudGetHandler处理
 *
 * @author ChengTao
 */
public class LeanCloudQueryResponse extends FindCallback<AVObject> {
    private IRequest request;//请求
    private LeanCloudQueryHandler getHandler;//自定义的消息处理类

    /**
     * 初始化request和getHandler
     * @param request 请求
     * @param getHandler 自定义的消息处理类
     */
    public LeanCloudQueryResponse(IRequest request, LeanCloudQueryHandler getHandler) {
        this.request = request;
        this.getHandler = getHandler;
    }

    /**
     * 继承FindCallback<AVObject>所要完成的方法
     * @param list List<AVObject>结果的数据结合
     * @param e AVException异常
     */
    @Override
    public void done(List<AVObject> list, AVException e) {
        if (e == null) {
            getHandler.sendSuccessMessage(request.getRequestId(), list);
        } else {
            getHandler.sendFailureMessage(request.getRequestId(), e);
        }
    }
}
