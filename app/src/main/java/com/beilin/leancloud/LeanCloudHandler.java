package com.beilin.leancloud;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.beilin.utils.Tools;

import java.util.List;

/**
 * Created by Lenovo on 2016/5/21.
 * <p/>
 * LeanCloudHandler类，用于发送消息和处理消息
 *
 * @author ChengTao
 */
@SuppressWarnings("ALL")
public class LeanCloudHandler implements LeanCloudHandlerInterface {
    private static final int START_MESSAGE = 1;//开始消息
    private static final int FAILURE_MESSAGE = 2;//失败消息
    private static final int SUCCESS_MESSAGE = 3;//成功消息
    private static final int FILE_FAILURE_MESSAGE = 4;//文件上传失败消息
    private static final int FILE_SUCCESS_MESSAGE = 5;//文件上传成功消息
    private static final int FILE_PROGRESS_MESSAGE = 6;//文件进度消息

    private ResponseHandler handler;//自定义Handler
    private LeanCloudListener listener;//数据接口

    /**
     * 初始化handler，和数据接口
     *
     * @param listener 数据接口
     */
    public LeanCloudHandler(LeanCloudListener listener) {
        this.handler = new ResponseHandler(Looper.myLooper(), this);
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
            case FILE_SUCCESS_MESSAGE:
                response = (Object[]) message.obj;
                if (response != null){
                    listener.onFileSuccess((Integer)response[0],(Integer)response[1]);
                }else {
                    listener.onFailure(0, new AVException(0, "请求失败"));
                }
                break;
            case FILE_FAILURE_MESSAGE:
                response = (Object[]) message.obj;
                if (response != null){
                    listener.onFileFailure((Integer)response[0],(AVException) response[1],(Integer)response[2]);
                }else {
                    listener.onFailure(0, new AVException(0, "请求失败"));
                }
                break;
            case FILE_PROGRESS_MESSAGE:
                response = (Object[]) message.obj;
                if (response != null){
                    listener.onFileProgress((Integer)response[0],(Integer)response[1],(Integer)response[2]);
                }else {
                    listener.onFailure(0, new AVException(0, "请求失败"));
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void sendStartMessage(int requestId) {
        Tools.printLog("--------LeanCloudHandler----------sendStartMessage---------");
        sendMessage(obtainMessage(START_MESSAGE, new Object[]{requestId}));
    }

    @Override
    public void sendFailureMessage(int requestId, AVException e) {
        Tools.printLog("--------LeanCloudHandler----------sendFailureMessage---------");
        sendMessage(obtainMessage(FAILURE_MESSAGE, new Object[]{requestId, e}));
    }

    @Override
    public void sendSuccessMessage(int requestId, List<AVObject> list) {
        Tools.printLog("--------LeanCloudHandler----------sendSuccessMessage---------");
        sendMessage(obtainMessage(SUCCESS_MESSAGE, new Object[]{requestId,list}));
    }


    @Override
    public void sendFileProgressMessage(int requestId,Integer integer,int position) {
        Tools.printLog("--------LeanCloudHandler----------sendFileProgressMessage---------");
        sendMessage(obtainMessage(FILE_PROGRESS_MESSAGE,new Object[]{requestId,integer,position}));
    }

    @Override
    public void sendFileSuccessMessage(int requestId, int position) {
        Tools.printLog("--------LeanCloudHandler----------sendFileSuccessMessage---------");
        sendMessage(obtainMessage(FILE_SUCCESS_MESSAGE,new Object[]{requestId,position}));
    }

    @Override
    public void sendFileFailureMessage(int requestId, AVException e, int position) {
        Tools.printLog("--------LeanCloudHandler----------sendFileFailureMessage---------");
        sendMessage(obtainMessage(FILE_SUCCESS_MESSAGE,new Object[]{requestId,e,position}));
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
    public static class ResponseHandler extends Handler {
        private LeanCloudHandler handler;

        public ResponseHandler(Looper looper, LeanCloudHandler handler) {
            super(looper);
            this.handler = handler;
        }

        @Override
        public void handleMessage(Message msg) {
            handler.handleMessage(msg);
        }
    }
}
