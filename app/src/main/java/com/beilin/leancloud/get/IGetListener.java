package com.beilin.leancloud.get;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;

import java.util.List;

/**
 * Created by Lenovo on 2016/5/22.
 *处理数据的接口
 * @author ChengTao
 */
public interface IGetListener{
    /**
     * 开始
     * @param requestId
     */
    void onStart(int requestId);

    /**
     * 失败
     * @param requestId
     * @param e
     */
    void onFailure(int requestId,AVException e);

    /**
     * 成功
     * @param requestId
     * @param list
     */
    void onSuccess(int requestId,List<AVObject> list);
}
