package com.beilin.request;

import android.content.Context;

import com.avos.avoscloud.AVObject;
import com.beilin.leancloud.request.IRequest;
import com.beilin.leancloud.utils.LeanCloudUtils;

import java.util.ArrayList;

/**
 * Created by Lenovo on 2016/5/22.
 *
 * @author ChengTao
 */
public class InsertRequest extends IRequest {
    public InsertRequest(Context context, ArrayList<AVObject> objects) {
        super(context);
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
