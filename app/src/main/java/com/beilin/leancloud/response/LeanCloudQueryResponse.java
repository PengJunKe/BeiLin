package com.beilin.leancloud.response;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.FindCallback;
import com.beilin.leancloud.LeanCloudHandler;
import com.beilin.leancloud.request.IRequest;
import com.beilin.utils.Tools;

import java.util.List;

/**
 * Created by Lenovo on 2016/5/21.
 * <p/>
 * LeanCloudQueryResponse类，用于处理请求成功和失败，
 * 并将消息发送给LeanCloudHandler，
 * 将结果交给LeanCloudHandler处理
 *
 * @author ChengTao
 */
public class LeanCloudQueryResponse extends FindCallback<AVObject> {
    private IRequest request;//请求
    private LeanCloudHandler handler;//自定义的消息处理类

    /**
     * 初始化request和handler
     * @param request 请求
     * @param handler 自定义的消息处理类
     */
    public LeanCloudQueryResponse(IRequest request, LeanCloudHandler handler) {
        this.request = request;
        this.handler = handler;
    }

    /**
     * 继承FindCallback<AVObject>所要完成的方法
     * @param list List<AVObject>结果的数据结合
     * @param e AVException异常
     */
    @Override
    public void done(List<AVObject> list, AVException e) {
        if (e == null) {
            Tools.printLog("--------LeanCloudQueryResponse----------sendFailureMessage---------");
            handler.sendSuccessMessage(request.getRequestId(), list);
        } else {
            Tools.printLog("--------LeanCloudQueryResponse----------sendFailureMessage---------");
            handler.sendFailureMessage(request.getRequestId(), e);
        }
    }
}
