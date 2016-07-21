package com.beilin.leancloud;

import android.util.Log;
import android.widget.Toast;

import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.beilin.App;
import com.beilin.leancloud.request.IRequest;
import com.beilin.leancloud.response.LeanCloudDeleteResponse;
import com.beilin.leancloud.response.LeanCloudFileProgressResponse;
import com.beilin.leancloud.response.LeanCloudFileResponse;
import com.beilin.leancloud.response.LeanCloudLoginResponse;
import com.beilin.leancloud.response.LeanCloudQueryResponse;
import com.beilin.leancloud.response.LeanCloudSaveResponse;
import com.beilin.leancloud.response.LeanCloudSignInResponse;
import com.beilin.utils.StringUtils;
import com.beilin.utils.Tools;
import com.sina.weibo.sdk.utils.MD5;

import java.util.List;

/**
 * Created by ChengTao on 2016/5/25.
 * <p/>
 * 基于LeanCloud数据库，用于处理页面的各种请求类
 *
 * @author ChengTao
 */
@SuppressWarnings("ALL")
public class LeanCloudClient {
    private static String TAG = "TAG";
    /**
     * 自定义的用于向主线程发送消息的handler
     */
    private LeanCloudHandler handler;
    /**
     * 用于查找的LeanCloud类
     */
    private AVQuery<AVObject> mAVQuery;
    /**
     * 查找限制数目
     */
    private int defaultLimit = 20;

    /**
     * 构造函数，初始化LeanCloudHandler(用于发送消息)和ILeanCloudListener(数据接口，用于activity页面做相应的处理)
     *
     * @param listener 数据接口
     */
    public LeanCloudClient(LeanCloudListener listener) {
        this.handler = new LeanCloudHandler(listener);
    }

    /**
     * 向LeanCloud数据库批量插入数据
     *
     * @param request 请求
     */
    public void leanCloudInsert(IRequest request) {
        if (request != null) {
            Tools.printLog("----------leanCloudInsert------sendStartMessage-----");
            handler.sendStartMessage(request.getRequestId());
            if (App.currentUser != null){
                for (AVObject object:request.getmObjectListParams()){
                    object.setACL(App.currentUser.getACL());
                }
            }
            Tools.printLog("----------leanCloudInsert------App.currentUser-----"+App.currentUser);
            AVObject.saveAllInBackground(request.getmObjectListParams(), new LeanCloudSaveResponse(request, handler));
        }
    }

    /**
     * 批量删除LeanCloud数据库中的数据
     *
     * @param request 请求
     */
    public void leanCloudDelete(IRequest request) {
        if (request != null) {
            Tools.printLog("--------leanCloudDelete----------sendStartMessage---------");
            handler.sendStartMessage(request.getRequestId());
            AVObject.deleteAllInBackground(request.getmObjectListParams(), new LeanCloudDeleteResponse(request, handler));
        }
    }

    /**
     * 更新LeanCloud数据库的数据
     *
     * @param request 请求
     */
    public void leanCloudUpdate(IRequest request) {
        if (request != null) {
            Tools.printLog("--------leanCloudUpdate----------sendStartMessage---------");
            handler.sendStartMessage(request.getRequestId());
            request.getmObjectParams().saveInBackground(new LeanCloudSaveResponse(request, handler));
        }
    }

    /**
     * 从LeanCloud数据库查找数据
     *
     * @param request 请求
     */
    public void leanCloudQuery(IRequest request) {
        if (request != null) {
            Tools.printLog("--------leanCloudQuery----------sendStartMessage---------");
            handler.sendStartMessage(request.getRequestId());
            mAVQuery = new AVQuery<>(request.getClassName());
            Log.e("TAG","findInBackground");
            //请求参数
            List<? extends AVObject> lists;
            int limit;
            boolean isRefresh;
            //
            //获取参数
            lists = (List<? extends AVObject>) request.getmSimpleParams().get("lists");
            limit = request.getmSimpleParams().get("limit") == null ? 0 : (int)request.getmSimpleParams().get("limit");
            isRefresh = request.getmSimpleParams().get("isRefresh")== null ? true :(boolean) request.getmSimpleParams().get("isRefresh");
            //
            if (lists != null && lists.size() > 0){
                if (isRefresh){//刷新
                    Log.e("TAG","刷新");
                    mAVQuery.whereGreaterThan("updatedAt",lists.get(0).getUpdatedAt());
                }else {//加载
                    Log.e("TAG","加载");
                    mAVQuery.whereLessThan("updatedAt",lists.get(lists.size() - 1).getUpdatedAt());
                }
            }
            if (limit == 0 ){
                mAVQuery.limit(defaultLimit);
            }else {
                mAVQuery.limit(limit);
            }
            Log.e("TAG","findInBackground");
            mAVQuery.orderByDescending("createdAt");
            mAVQuery.findInBackground(new LeanCloudQueryResponse(request, handler));
        }else {
            Tools.printLog("--------leanCloudQuery----------sendFailureMessage---------");
            handler.sendFailureMessage(request.getRequestId(),null);
        }
    }

    /**
     * 插入有图片的对象
     *
     * @param request 请求
     */
    @SuppressWarnings("unchecked")
    public void leanCloudInsertWithFile(IRequest request) {
        if (request != null) {
            Tools.printLog("--------leanCloudInsertWithFile----------sendStartMessage---------");
            handler.sendStartMessage(request.getRequestId());
            List<AVFile> lists = (List<AVFile>) request.getmSimpleParams().get("file");
            if (lists != null && lists.size() > 0){
                int position = 0;
                for (AVFile file:lists){
                    lists.get(position).saveInBackground(new LeanCloudFileResponse(request, handler,position,lists.size()),
                            new LeanCloudFileProgressResponse(request, handler,position));
                    position++;
                }
            }else {
                Tools.printLog("--------leanCloudInsertWithFile----------sendFileFailureMessage---------");
                handler.sendFileFailureMessage(request.getRequestId(),null,-1);
            }
        }
    }

    /**
     * 登录
     * @param request
     */
    public void leanCloudLogin(IRequest request){
        String userName = (String) request.getmSimpleParams().get("userName");
        String password = (String) request.getmSimpleParams().get("password");
        Tools.printLog("------MD5.hexdigest(password)--------"+MD5.hexdigest(password));
        handler.sendStartMessage(request.getRequestId());
        if (StringUtils.isStrNotNull(userName) && StringUtils.isStrNotNull(password)){
            AVUser.logInInBackground(userName,password,new LeanCloudLoginResponse(request,handler));
        }else {
            Toast.makeText(request.getmContext(),"请输入用户名或密码",Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 登录
     * @param request
     */
    public void leanCloudSignIn(IRequest request){
        String userName = (String) request.getmSimpleParams().get("userName");
        String password = (String) request.getmSimpleParams().get("password");
        Tools.printLog("------MD5.hexdigest(password)--------"+MD5.hexdigest(password));
        handler.sendStartMessage(request.getRequestId());
        if (StringUtils.isStrNotNull(userName) && StringUtils.isStrNotNull(password)){
            AVUser user = new AVUser();// 新建 AVUser 对象实例
            user.setUsername(userName);// 设置用户名
            user.setPassword(password);// 设置密码
            user.signUpInBackground(new LeanCloudSignInResponse(request,handler));
        }else {
            Toast.makeText(request.getmContext(),"请输入用户名或密码",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 选择处理方法
     *
     * @param request
     */
    public void chooseHandleWay(IRequest request) {
        switch (request.getWay()) {
            case INSERT://增加
                leanCloudInsert(request);
                break;
            case DELETE://删除
                leanCloudDelete(request);
                break;
            case UPDATE://修改
                leanCloudUpdate(request);
                break;
            case QUERY://查找
                leanCloudQuery(request);
                break;
            case FILE://文件
                leanCloudInsertWithFile(request);
                break;
            case LOGIN:
                leanCloudLogin(request);
                break;
            case SIGNIN:
                leanCloudSignIn(request);
                break;
            default:
                break;
        }
    }

}
