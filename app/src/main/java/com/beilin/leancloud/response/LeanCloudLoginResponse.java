package com.beilin.leancloud.response;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.beilin.App;
import com.beilin.leancloud.LeanCloudHandler;
import com.beilin.leancloud.request.IRequest;

/**
 * Created by ChengTao on 2016-07-21.
 * @author Cheng Tao
 */
@SuppressWarnings("ALL")
public class LeanCloudLoginResponse extends LogInCallback<AVUser>{
    private IRequest request;//请求
    private LeanCloudHandler handler;//自定义的消息处理类

    public LeanCloudLoginResponse(IRequest request, LeanCloudHandler handler) {
        this.request = request;
        this.handler = handler;
    }

    @Override
    public void done(AVUser avUser, AVException e) {
        if(e == null){
            App.currentUser = avUser;
            handler.sendSuccessMessage(request.getRequestId(),null);
        }else {
            handler.sendFailureMessage(request.getRequestId(),e);
        }
    }

}
