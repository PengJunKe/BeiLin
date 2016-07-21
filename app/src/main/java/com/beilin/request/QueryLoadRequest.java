package com.beilin.request;

import android.content.Context;
import android.util.Log;

import com.avos.avoscloud.AVObject;
import com.beilin.leancloud.request.IRequest;
import com.beilin.leancloud.utils.LeanCloudUtils;

import java.util.List;

/**
 * Created by ChengTao on 2016-07-11.
 */
@SuppressWarnings("ALL")
public class QueryLoadRequest extends IRequest {
    public QueryLoadRequest(Context mContext, List<AVObject> lists) {
        super(mContext);
        mSimpleParams.put("lists",lists);
        mSimpleParams.put("limit",2);
        mSimpleParams.put("isRefresh",false);
        Log.e("TAG","QueryRefreshRequest");
    }

    @Override
    public String getClassName() {
        return LeanCloudUtils.SHE_TUAN;
    }

    @Override
    public LeanCloudUtils.RequestWay getWay() {
        return LeanCloudUtils.RequestWay.QUERY;
    }
}
