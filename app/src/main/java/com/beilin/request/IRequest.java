package com.beilin.request;

import android.content.Context;

import java.util.Map;

/**
 * Created by Lenovo on 2016/5/21.
 *
 * 请求的基类
 *
 * @author ChengTao
 */
public abstract class IRequest {
    //LeanCloud表名
    private String className;
    //上下文
    protected Context context;
    //请求Id
    private int requestId = 0;
    //要保存的数据集合
    protected Map<String,Object> putDatas;
    //查找时获取数据的限制个数，默认10个
    private int limit = 10;

    
    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setPutDatas(Map<String, Object> putDatas) {
        this.putDatas = putDatas;
    }

    public Map<String, Object> getPutDatas() {
        return putDatas;
    }

    public IRequest(Context context) {
        this.context = context;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }
}
