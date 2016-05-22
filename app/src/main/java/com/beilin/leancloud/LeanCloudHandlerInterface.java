package com.beilin.leancloud;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;

import java.util.List;

/**
 * 发送消息的接口
 * Created by Lenovo on 2016/5/21.
 * @author ChengTao
 */
public interface LeanCloudHandlerInterface {
    /**
     * 发送开始消息
      * @param requestId 请求的Id
     */
    void sendStartMessage(int requestId);

    /**
     * 发送失败消息
     * @param requestId 请求的Id
     * @param e 异常
     */
    void sendFailureMessage(int requestId,AVException e);

    /**
     * 发送成功消息
     * @param requestId 请求的Id
     * @param list AVObject类型的返回值的集合
     */
    void sendSuccessMessage(int requestId,List<AVObject> list);
}
