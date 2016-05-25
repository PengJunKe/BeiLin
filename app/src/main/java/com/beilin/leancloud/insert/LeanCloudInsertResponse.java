package com.beilin.leancloud.insert;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.SaveCallback;
import com.beilin.request.IRequest;

/**
 * Created by Lenovo on 2016/5/21.
 * <p/>
 * LeanCloudGetResponse类，用于处理请求成功和失败，
 * 并将消息发送给LeanCloudGetHandler，
 * 将结果交给LeanCloudGetHandler处理
 *
 * @author ChengTao
 */
public class LeanCloudInsertResponse extends SaveCallback {
    private IRequest request;//请求
    private LeanCloudInsertHandler putHandler;//自定义的消息处理类

    /**
     * 初始化request和getHandler
     * @param request 请求
     * @param putHandler 自定义的消息处理类
     */
    public LeanCloudInsertResponse(IRequest request, LeanCloudInsertHandler putHandler) {
        this.request = request;
        this.putHandler = putHandler;
    }

    /**
     * 继承SaveCallback所要完成的方法
     * @param e AVException异常
     */
    @Override
    public void done(AVException e) {
        if (e==null){
            putHandler.sendSuccessMessage(request.getRequestId());
        }else {
            putHandler.sendFailureMessage(request.getRequestId(),e);
        }
    }
}
