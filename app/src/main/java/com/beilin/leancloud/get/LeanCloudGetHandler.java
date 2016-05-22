package com.beilin.leancloud.get;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.beilin.leancloud.LeanCloudHandlerInterface;

import java.util.List;

/**
 * Created by Lenovo on 2016/5/21.
 * <p/>
 * LeanCloudGetHandler类，用于发送消息和处理消息
 *
 * @author ChengTao
 */
public class LeanCloudGetHandler implements LeanCloudHandlerInterface {
    private static final int START_MESSAGE = 1;//开始消息
    private static final int FAILURE_MESSAGE = 2;//失败消息
    private static final int SUCCESS_MESSAGE = 3;//成功消息

    private GetResponseHandler getHandler;//自定义Handler
    private IGetListener listener;//数据接口
    private Handler handler;//主线程的handler

    /**
     * 初始化getHandler，和数据接口
     *
     * @param handler  主线程的handler
     * @param listener 数据接口
     */
    public LeanCloudGetHandler(Handler handler, IGetListener listener) {
        this.handler = handler;
        this.getHandler = new GetResponseHandler(handler.getLooper(), this);
        this.listener = listener;
    }

    /**
     * 自定义的handleMessage，用于处理消息
     *
     * @param message 消息
     */
    protected void handleMessage(Message message) {
        Object[] response;
        switch (message.what) {
            case START_MESSAGE:
                response = (Object[]) message.obj;
                if (response != null) {
                    listener.onStart((Integer) response[0]);
                } else {
                    listener.onFailure(0, new AVException(0, "请求失败"));
                }
                break;
            case SUCCESS_MESSAGE:
                response = (Object[]) message.obj;
                if (response != null) {
                    listener.onSuccess((Integer) response[0], (List<AVObject>) response[1]);
                } else {
                    listener.onFailure(0, new AVException(0, "请求失败"));
                }
                break;
            case FAILURE_MESSAGE:
                response = (Object[]) message.obj;
                if (response != null) {
                    listener.onFailure((Integer) response[0], (AVException) response[1]);
                } else {
                    listener.onFailure(0, new AVException(0, "请求失败"));
                }
                break;
        }
    }

    @Override
    public void sendStartMessage(int requestId) {
        sendMessage(obtainMessage(START_MESSAGE, new Object[]{requestId}));
    }

    @Override
    public void sendFailureMessage(int requestId, AVException e) {
        sendMessage(obtainMessage(FAILURE_MESSAGE, new Object[]{requestId, e}));
    }

    @Override
    public void sendSuccessMessage(int requestId, List<AVObject> list) {
        sendMessage(obtainMessage(SUCCESS_MESSAGE, new Object[]{requestId, list}));
    }

    /**
     * 发送消息
     *
     * @param msg 要发送的message
     */
    protected void sendMessage(Message msg) {
        if (getHandler == null) {
            handleMessage(msg);
        } else {
            getHandler.sendMessage(msg);
        }
    }

    /**
     * 生成消息
     *
     * @param responseMessageId   消息的Id
     * @param responseMessageData 要发送的message
     * @return
     */
    protected Message obtainMessage(int responseMessageId, Object responseMessageData) {
        return Message.obtain(handler, responseMessageId, responseMessageData);
    }

    /**
     * 自定义的Handler类，用于将消息处理交给LeanCloudGetHandler
     */
    public static class GetResponseHandler extends Handler {
        private LeanCloudGetHandler handler;

        public GetResponseHandler(Looper looper, LeanCloudGetHandler handler) {
            super(looper);
            this.handler = handler;
        }

        @Override
        public void handleMessage(Message msg) {
            handler.handleMessage(msg);
        }
    }
}
