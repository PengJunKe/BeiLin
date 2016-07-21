package com.beilin.leancloud.response;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.SaveCallback;
import com.beilin.leancloud.LeanCloudHandler;
import com.beilin.leancloud.request.IRequest;
import com.beilin.utils.Tools;

import java.util.List;

/**
 * Created by Lenovo on 2016/5/21.
 * <p/>
 * LeanCloudResponse类，用于处理请求成功和失败，
 * 并将消息发送给LeanCloudHandler，
 * 将结果交给LeanCloudHandler处理
 *
 * @author ChengTao
 */
@SuppressWarnings("ALL")
public class LeanCloudFileResponse extends SaveCallback {
    private IRequest request;//请求
    private LeanCloudHandler handler;//自定义的消息处理类
    private int position;
    private int fileCount;

    /**
     * 初始化request和handler
     * @param request 请求
     * @param handler 自定义的消息处理类
     */
    public LeanCloudFileResponse(IRequest request, LeanCloudHandler handler,int position,int fileCount) {
        this.request = request;
        this.handler = handler;
        this.position = position;
        this.fileCount = fileCount;
    }

    /**
     * 继承DeleteCallback所要完成的方法
     * @param e AVException异常
     */
    @Override
    public void done(AVException e) {
        if (e==null){
            handler.sendFileSuccessMessage(request.getRequestId(),position);
            List<AVFile> lists = (List<AVFile>) request.getmSimpleParams().get("file");
            request.addFileUrl(lists.get(position).getUrl());
            if (position == fileCount-1){
                Tools.printLog("--------LeanCloudFileResponse----------sendSuccessMessage---------");
                handler.sendSuccessMessage(request.getRequestId(),null);
            }
        }else {
            Tools.printLog("--------LeanCloudFileResponse----------sendFileFailureMessage---------");
            handler.sendFileFailureMessage(request.getRequestId(),e,position);
        }
    }
}
