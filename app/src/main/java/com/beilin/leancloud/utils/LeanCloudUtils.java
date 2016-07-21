package com.beilin.leancloud.utils;

import com.avos.avoscloud.AVObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ChengTao on 2016/5/25.
 *
 *LeanCloud中的表
 *
 * @author ChengTao
 */
@SuppressWarnings("ALL")
public class LeanCloudUtils {

    public enum RequestWay{
        INSERT,
        DELETE,
        UPDATE,
        QUERY,
        FILE,
        LOGIN,
        SIGNIN;
    };

    public enum QueryArg{
        LIMIT,
        SKIP,
        CURRENT_PAGE;
    }


    //LeanCloud数据库中的表名
    /**
     * 社团表名
     */
    public final static String SHE_TUAN = "SheTuan";
    /**
     * 报名表名
     */
    public final static String BAO_MING = "BaoMing";
    /**
     * 活动表名
     */
    public final static String HUO_DONG = "HuoDong";
    /**
     * 视频表名
     */
    public final static String SHI_PIN = "ShiPin";
    /**
     * 图片表名
     */
    public final static String TU_PIAN = "TuPian";
    /**
     * 消息表名
     */
    public final static String XIAO_XI = "XiaoXi";
    //LeanCloud数据库中的表名

    /**
     * 获取社团对象
     *
     * @return 社团对象
     */
    public static AVObject getSheTuanObject() {
        return new AVObject(SHE_TUAN);
    }

    /**
     * 获取报名对象
     *
     * @return 报名对象
     */
    public static AVObject getBaoMingObject() {
        return new AVObject(BAO_MING);
    }

    /**
     * 获取活动对象
     *
     * @return 活动对象
     */
    public static AVObject getHuoDongObject() {
        return new AVObject(HUO_DONG);
    }

    /**
     * 获取视频对象
     *
     * @return 视频对象
     */
    public static AVObject getShiPinObject() {
        return new AVObject(SHI_PIN);
    }

    /**
     * 获取图片对象
     *
     * @return 图片对象
     */
    public static AVObject getTuPianObject() {
        return new AVObject(TU_PIAN);
    }

    /**
     * 获取消息对象
     *
     * @return 消息对象
     */
    public static AVObject getXiaoXiObject() {
        return new AVObject(XIAO_XI);
    }

    /**
     * 向AVObject对象添加数据
     *
     * @param object AVObject对象
     * @param map    数据集合
     */
    public static void setData(AVObject object, Map<String, Object> map) {
        for (String key : map.keySet()
                ) {
            object.put(key, map.get(key));
        }
    }
}
