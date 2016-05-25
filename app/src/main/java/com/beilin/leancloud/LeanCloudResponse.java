package com.beilin.leancloud;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.SaveCallback;
import com.beilin.request.IRequest;

/**
 * Created by Lenovo on 2016/5/21.
 * <p/>
 * LeanCloudResponse类，用于处理请求成功和失败，
 * 并将消息发送给LeanCloudHandler，
 * 将结果交给LeanCloudHandler处理
 *
 * @author ChengTao
 */
public class LeanCloudResponse extends SaveCallback {
    private IRequest request;//请求
    private LeanCloudHandler handler;//自定义的消息处理类

    /**
     * 初始化request和handler
     * @param request 请求
     * @param handler 自定义的消息处理类
     */
    public LeanCloudResponse(IRequest request, LeanCloudHandler handler) {
        this.request = request;
        this.handler = handler;
    }

    /**
     * 继承DeleteCallback所要完成的方法
     * @param e AVException异常
     */
    @Override
    public void done(AVException e) {
        if (e==null){
            handler.sendSuccessMessage(request.getRequestId(),null);
        }else {
            handler.sendFailureMessage(request.getRequestId(),e);
        }
    }
}
