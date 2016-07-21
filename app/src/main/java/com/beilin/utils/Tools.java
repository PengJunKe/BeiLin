package com.beilin.utils;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.avos.avoscloud.AVObject;

import java.io.ByteArrayOutputStream;

/**
 * Created by Lenovo on 2016/5/2.
 * @author ChengTao
 */
@SuppressWarnings("ALL")
public class Tools {
    /**
     * ViewHolder基类
     */
    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View itemView) {
            super(itemView);
        }

        /**
         *  通过id返回布局中的指定的控件
         * @param view 父布局
         * @param id 子控件的id
         * @param <T> 泛型，继承View
         * @return 返回指定的控件
         */
        public static <T extends View> T get(View view, int id){
            SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
            if (viewHolder == null){
                viewHolder = new SparseArray<>();
                view.setTag(viewHolder);
            }
            View childView = viewHolder.get(id);
            if (childView == null){
                childView = view.findViewById(id);
                viewHolder.put(id,childView);
            }
        return (T) childView;
        }
    }

    /**
     * 将AVObject对象装换成所想要的Json字符串
     * @return
     */
    public static String praseAVObjectToNeedString(AVObject object){
        JSONObject wholeObject = new JSONObject();
        JSONObject serverDataObject = new JSONObject();
        JSONObject needObject = new JSONObject();
        wholeObject = JSON.parseObject(object.toString());
        serverDataObject = JSON.parseObject(JSON.parseObject(
                object.toString()).
                get("serverData").toString());
        for (String key:wholeObject.keySet()
             ) {
            if (key != "serverData"){
                needObject.put(key,wholeObject.get(key));
            }
        }
        for (String key:serverDataObject.keySet()
             ) {
            if (key!="@type"){
                needObject.put(key,serverDataObject.get(key));
            }
        }
        return JSONObject.toJSONString(needObject);
    }


    /**
     * 将Bitmap转换成字节数组
     * @param imageView
     * @param <T>
     * @return
     */
    public static <T extends ImageView>byte[] getByteArrayFromUri(T imageView){
        byte[] result = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap bitmap = (Bitmap) imageView.getTag();
        if (bitmap != null){
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
            result = baos.toByteArray();
        }
        return result;
    }

    /**
     *
     * @param s
     */
    public static void printLog(String s){
        Log.e("TAG",s);
    }
}
