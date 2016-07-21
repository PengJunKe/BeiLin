package com.beilin.leancloud;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;

import java.util.List;

/**
 * Created by Lenovo on 2016/5/22.
 *处理结果的接口
 * @author ChengTao
 */
@SuppressWarnings("ALL")
public interface LeanCloudListener {
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

    /**
     * 文件上传成功
     * @param requestId
     * @param position
     */
    void onFileSuccess(int requestId,int position);

    /**
     * 文件上传失败
     * @param requestId
     * @param e
     * @param position
     */
    void onFileFailure(int requestId,AVException e,int position);

    /**
     * 文件上传进度
     * @param requestId 请求Id
     * @param integer 文件上传进度值
     * @param position
     */
    void onFileProgress(int requestId,Integer integer,int position);
}
