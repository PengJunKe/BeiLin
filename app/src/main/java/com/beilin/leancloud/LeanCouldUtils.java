package com.beilin.leancloud;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.beilin.service.request.IRequest;

import java.util.List;
import java.util.Map;

/**
 * Created by Lenovo on 2016/4/27.
 *
 * @author 程涛
 */
public class LeanCouldUtils {
    private AVObject mAVObject;
    private AVQuery<AVObject> mAVQuery;

    /**
     * 设置所要操作leancloud表名
     *
     * @param className leancloud表名
     */
    public void setClassName(String className) {
        mAVObject = new AVObject(className);
        mAVQuery = new AVQuery<>(className);
    }

    /**
     * LeanCould增加数据后的相应
     */
    class LealnCloudPutDataRespose extends SaveCallback {
        private LeacouldPutDataListener listener;
        private IRequest mRequest;

        public LealnCloudPutDataRespose(IRequest mRequest, LeacouldPutDataListener listener) {
            this.listener = listener;
            this.mRequest = mRequest;
            onPutDataStart();
        }

        public void onPutDataStart() {
            if (listener != null) {
                listener.onPutDataStart(mRequest.getRequestId());
            }
        }

        @Override
        public void done(AVException e) {
            if (e != null) {
                listener.onPutDataFailure(mRequest.getRequestId(), e);
            } else {
                listener.onPutDataSucess(mRequest.getRequestId());
            }
        }
    }

    /**
     * 向LeanCloud的一个表中增加数据
     *
     * @param request  增加数据的请求
     * @param listener 增加数据的接口
     */
    public void putDataIntoClass(IRequest request, LeacouldPutDataListener listener) {
        if (request != null) {
            Map<String, Object> data = request.getPutData();
            for (String key : data.keySet()) {
                mAVObject.put(key,data.get(key));
            }
            mAVObject.saveInBackground(new LealnCloudPutDataRespose(request, listener));
        }
    }

    /**
     * LeanCould获取数据后的相应
     */
    class LealnCloudGetDataRespose extends FindCallback<AVObject> {
        private LeacouldGetDataListener listener;
        private IRequest mRequest;

        public LealnCloudGetDataRespose(IRequest mRequest, LeacouldGetDataListener listener) {
            this.listener = listener;
            this.mRequest = mRequest;
            onGetDataStart();
        }

        public void onGetDataStart() {
            if (listener != null) {
                listener.onGetDataStart(mRequest.getRequestId());
            }
        }

        @Override
        public void done(List<AVObject> list, AVException e) {
            if (e != null) {
                listener.onGetDataFailure(mRequest.getRequestId(), e);
            } else {
                listener.onGetDataSucess(mRequest.getRequestId(), list);
            }
        }
    }

    /**
     * 从LeanCloud表中获取数据
     *
     * @param request  获取数据的请求
     * @param listener 获取数据的接口
     */
    public void getDataFromClass(IRequest request, LeacouldGetDataListener listener) {
        if (request != null) {
            mAVQuery.findInBackground(new LealnCloudGetDataRespose(request, listener));
        }
    }
}
