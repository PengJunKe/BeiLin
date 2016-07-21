package com.beilin.request;

import android.content.Context;

import com.avos.avoscloud.AVObject;
import com.beilin.leancloud.request.IRequest;
import com.beilin.leancloud.utils.LeanCloudUtils;

/**
 * Created by ChengTao on 2016-07-20.
 */
@SuppressWarnings("ALL")
public class UpdateRequest extends IRequest{
    public UpdateRequest(Context mContext, AVObject object) {
        super(mContext);
        mObjectParams = object;
    }

    @Override
    public String getClassName() {
        return null;
    }

    @Override
    public LeanCloudUtils.RequestWay getWay() {
        return LeanCloudUtils.RequestWay.UPDATE;
    }
}
