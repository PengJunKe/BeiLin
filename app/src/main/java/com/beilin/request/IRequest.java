package com.beilin.request;

import android.content.Context;

/**
 * Created by Lenovo on 2016/5/21.
 */
public abstract class IRequest {
    protected String className;
    protected Context context;

    public IRequest(Context context) {
        this.context = context;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    protected int requestId = 0;

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }
}
