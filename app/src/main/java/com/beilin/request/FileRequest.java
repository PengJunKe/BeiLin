package com.beilin.request;

import android.content.Context;

import com.avos.avoscloud.AVFile;
import com.beilin.leancloud.request.IRequest;
import com.beilin.leancloud.utils.LeanCloudUtils;

import java.util.List;

/**
 * Created by ChengTao on 2016-07-20.
 */
@SuppressWarnings("ALL")
public class FileRequest extends IRequest{

    public FileRequest(Context mContext, List<AVFile> files) {
        super(mContext);
        mSimpleParams.put("file",files);
    }

    @Override
    public String getClassName() {
        return null;
    }

    @Override
    public LeanCloudUtils.RequestWay getWay() {
        return LeanCloudUtils.RequestWay.FILE;
    }
}
