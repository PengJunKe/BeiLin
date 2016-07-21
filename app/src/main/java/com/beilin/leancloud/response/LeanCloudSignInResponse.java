package com.beilin.leancloud.response;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.SignUpCallback;
import com.beilin.leancloud.LeanCloudHandler;
import com.beilin.leancloud.request.IRequest;
import com.beilin.utils.Tools;

/**
 * Created by ChengTao on 2016-07-21.
 */
@SuppressWarnings("ALL")
public class LeanCloudSignInResponse extends SignUpCallback{
    private IRequest request;//请求
    private LeanCloudHandler handler;//自定义的消息处理类

    public LeanCloudSignInResponse(IRequest request, LeanCloudHandler handler) {
        this.request = request;
        this.handler = handler;
    }

    @Override
    public void done(AVException e) {
        if (e == null){
            handler.sendSuccessMessage(request.getRequestId(),null);
        }else {
            Tools.printLog("---LeanCloudSignInResponse-------------------"+e.toString());
            handler.sendFailureMessage(request.getRequestId(),e);
        }
    }
}
