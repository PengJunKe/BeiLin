package com.beilin.leancloud.insert;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.avos.avoscloud.AVException;
import com.beilin.leancloud.ILeanCloudListener;
import com.beilin.leancloud.LeanCloudInsertHandlerInterface;

/**
 * Created by Lenovo on 2016/5/21.
 * <p/>
 * LeanCloudGetHandler类，用于发送消息和处理消息
 *
 * @author ChengTao
 */
public class LeanCloudInsertHandler implements LeanCloudInsertHandlerInterface {
    private static final int START_MESSAGE = 1;//开始消息
    private static final int FAILURE_MESSAGE = 2;//失败消息
    private static final int SUCCESS_MESSAGE = 3;//成功消息

    private PutResponseHandler handler;//自定义Handler
    private ILeanCloudListener listener;//数据接口

    /**
     * 初始化getHandler，和数据接口
     *
     * @param listener 数据接口
     */
    public LeanCloudInsertHandler(ILeanCloudListener listener) {
        this.handler = new PutResponseHandler(Looper.myLooper(), this);
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
                    listener.onSuccess((Integer) response[0],null);
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
    public void sendSuccessMessage(int requestId) {
        sendMessage(obtainMessage(SUCCESS_MESSAGE, new Object[]{requestId}));
    }

    /**
     * 发送消息
     *
     * @param msg 要发送的message
     */
    protected void sendMessage(Message msg) {
        if (handler == null) {
            handleMessage(msg);
        } else {
            handler.sendMessage(msg);
        }
    }

    /**
     * 生成消息
     *
     * @param responseMessageId   消息的Id
     * @param responseMessageData 要发送的message
     * @return 要发送的message
     */
    protected Message obtainMessage(int responseMessageId, Object responseMessageData) {
        return Message.obtain(handler, responseMessageId, responseMessageData);
    }

    /**
     * 自定义的Handler类，用于将消息处理交给LeanCloudGetHandler
     */
    public static class PutResponseHandler extends Handler {
        private LeanCloudInsertHandler handler;

        public PutResponseHandler(Looper looper, LeanCloudInsertHandler handler) {
            super(looper);
            this.handler = handler;
        }

        @Override
        public void handleMessage(Message msg) {
            handler.handleMessage(msg);
        }
    }
}
