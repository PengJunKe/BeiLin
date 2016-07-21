package com.beilin.request;

import android.content.Context;

import com.avos.avoscloud.AVObject;
import com.beilin.leancloud.request.IRequest;
import com.beilin.leancloud.utils.LeanCloudUtils;

import java.util.ArrayList;

/**
 * Created by ChengTao on 2016-07-20.
 */
@SuppressWarnings("ALL")
public class InsertWithFileRequest extends IRequest{
    public InsertWithFileRequest(Context mContext,ArrayList<AVObject> objects) {
        super(mContext);
        mObjectListParams = objects;
    }

    @Override
    public String getClassName() {
        return null;
    }

    @Override
    public LeanCloudUtils.RequestWay getWay() {
        return LeanCloudUtils.RequestWay.INSERT;
    }
}
