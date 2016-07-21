package com.beilin.utils;

import android.text.TextUtils;

/**
 * Created by ChengTao on 2016-07-21.
 */
@SuppressWarnings("ALL")
public class StringUtils {
    public static boolean isStrNotNull(String s){
        if (s != null && !TextUtils.isEmpty(s)){
            return true;
        }else {
            return false;
        }
    }
}
