package com.beilin.request;

import android.content.Context;

import com.beilin.leancloud.request.IRequest;
import com.beilin.leancloud.utils.LeanCloudUtils;

/**
 * Created by ChengTao on 2016-07-21.
 */
@SuppressWarnings("ALL")
public class LoginRequest extends IRequest{
    /**
     *
     * @param mContext
     * @param userName
     * @param password
     */
    public LoginRequest(Context mContext,String userName,String password) {
        super(mContext);
        mSimpleParams.put("userName",userName);
        mSimpleParams.put("password",password);
    }

    @Override
    public String getClassName() {
        return null;
    }

    @Override
    public LeanCloudUtils.RequestWay getWay() {
        return LeanCloudUtils.RequestWay.LOGIN;
    }
}
