package com.beilin.request;

import android.content.Context;

import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Lenovo on 2016/5/21.
 *
 * 请求的基类
 *
 * @author ChengTao
 */
public abstract class IRequest {
    /**
     * LeanCloud类名
     */
    private String className;
    /**
     * 上下文
     */
    protected Context context;
    /**
     * 请求Id,默认为0
     */
    private int requestId = 0;
    /**
     * 查找时获取数据的限制个数，默认20个
     */
    private int limit = 10;

    /**
     * 要批量插入的数据结合
     */
    private List<AVObject> insertList = new ArrayList<>();

    /**
     * 要更新的对象
     */
    private AVObject upDateObject;

    /**
     * 要批量删除的数据结合
     */
    private List<AVObject> deleteList = new ArrayList<>();

    /**
     * 要上传的文件
     */
    private AVFile uploadFile = null;

    /**
     * 文件的key值
     */
    private String fileKey = null;

    /**
     * 要上传的AVObject的对象
     */
    private AVObject uploadObject = null;

    /**
     * 获取要上传文件的Key
     * @return 要上传文件的key值
     */
    public String getFileKey() {
        return fileKey;
    }

    /**
     * 设置要上传文件的key值
     * @param fileKey 要上传文件的key值
     */
    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    /**
     * 设置要上传的AVObject的对象
     * @param uploadFile 要上传的AVObject的对象
     */
    public void setUploadFile(AVFile uploadFile) {
        this.uploadFile = uploadFile;
    }

    /**
     * 设置要上传的AVObject对象
     * @param uploadObject 要上传的AVObject对象
     */
    public void setUploadObject(AVObject uploadObject) {
        this.uploadObject = uploadObject;
    }

    /**
     * 获取要上传的AVObject的对象
     * @return 要上传的AVObject的对象
     */
    public AVObject getUploadObject() {
        return uploadObject;
    }

    /**
     * 初始化要上传的文件
     * @param name 文件名
     * @param fileByte 文件字节数组
     */
    public void initFile(String name,byte fileByte[]){
        uploadFile = new AVFile(name,fileByte);
    }

    /**
     * 获取要上传的文件
     * @return 要上传的文件
     */
    public AVFile getUploadFile() {
        return uploadFile;
    }

    /**
     *构造函数，设置上下文
     * @param context 上下文
     */
    public IRequest(Context context) {
        this.context = context;
    }

    /**
     * 将AVObject对象加入插入的数据结合中
     * @param object 要插入的AVObject对象
     */
    public void insertObjectToList(AVObject object){
        insertList.add(object);
    }

    /**
     * 获取要插入的AVObject对象的数据结合
     * @return List<AVObject>要插入的AVObject对象的数据结合
     */
    public List<AVObject> getInsertList() {
        return insertList;
    }

    /**
     * 将AVObject对象加入删除的数据结合中
     * @param object 要删除的AVObject对象
     */
    public void deleteObjectToList(AVObject object){
        deleteList.add(object);
    }

    /**
     * 获取要删除的AVObject对象的数据结合
     * @return List<AVObject>要删除的AVObject对象的数据结合
     */
    public List<AVObject> getDeleteList() {
        return deleteList;
    }

    /**
     * 获取要更新的AVObject对象
     * @return AVObject对象
     */
    public AVObject getUpDateObject() {
        return upDateObject;
    }

    /**
     * 设置要更新的AVObject对象
     * @param upDateObject 要更新的AVObject对象
     */
    public void setUpDateObject(AVObject upDateObject) {
        this.upDateObject = upDateObject;
    }

    /**
     * 获取限制个数
     * @return 限制个数
     */
    public int getLimit() {
        return limit;
    }

    /**
     * 设置限制个数
     * @param limit 限制个数
     */
    public void setLimit(int limit) {
        this.limit = limit;
    }


    /**
     * 获取LeanCloud类名
     * @return LeanCloud类名
     */
    public String getClassName() {
        return className;
    }

    /**
     * 设置LeanCloud类名
     * @param className LeanCloud类名
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * 获取请求id
     * @return 请求id
     */
    public int getRequestId() {
        return requestId;
    }

    /**
     * 设置请求id
     * @param requestId 请求id
     */
    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    /**
     * 获取上下文
     * @return 上下文
     */
    public Context getContext() {
        return context;
    }
}
