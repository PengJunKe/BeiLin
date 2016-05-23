package com.beilin.leancloud;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;

import java.util.List;

/**
 * Created by Lenovo on 2016/5/22.
 *处理结果的接口
 * @author ChengTao
 */
public interface ILeanCloudListener {
    /**
     * 开始
     * @param requestId 请求Id
     */
    void onStart(int requestId);

    /**
     * 失败
     * @param requestId 请求Id
     * @param e 异常
     */
    void onFailure(int requestId, AVException e);

    /**
     * 成功
     * @param requestId 请求Id
     * @param list 返回的数据集合
     */
    void onSuccess(int requestId, List<AVObject> list);
}
