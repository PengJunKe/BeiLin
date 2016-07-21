package com.beilin;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ChengTao on 2016-07-18.
 */
@SuppressWarnings("ALL")
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static String TAG ="CrashHandler";
    //系统默认的UncaughtException处理类
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    //CrashHandler实体
    private static CrashHandler INSTANCE = new CrashHandler();
    //程序的上下文对象
    private Context mContext;
    //用来储存信息和异常
    private HashMap<String,String> infos = new HashMap<>();
    //用于格式化日期,作为日志文件名的一部分
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    //保证只有一个CrashHandler实体
    private CrashHandler(){

    }

    /**
     * 获取CrashHandler对象
     * @return
     */
    public static CrashHandler getInstance(){
        return INSTANCE;
    }

    public void init(Context context){
        mContext = context;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     * @param thread
     * @param ex
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null){
            //如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread,ex);
        }else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Log.e(TAG,"error:"+e);
            }
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    private boolean handleException(Throwable ex){
        if (ex == null){
            return false;
        }else {
            new Thread(){
                @Override
                public void run() {
                    Looper.prepare();
                    Toast.makeText(mContext,"程序出现异常，即将退出",Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            }.start();
            //收集设备参数信息
            collectDeviceInfo(mContext);
            //保存日志文件
            saveCrashInfoToFile(ex);
            return true;
        }
    }

    /**
     * 保存日志
     * @param ex
     */
    private void saveCrashInfoToFile(Throwable ex) {
        StringBuffer buffer = new StringBuffer();
        for (Map.Entry<String,String> entry: infos.entrySet()
             ) {
            buffer.append(entry.getKey() + "=" +entry.getValue()+"\n");
        }
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null){
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        buffer.append(result);
        try{
            long timestamp = System.currentTimeMillis();
            String time = formatter.format(new Date());
            String fileName = "crash" +time+timestamp+".log";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                String path = "/sdcard/crash/";
                File file = new File(path);
                if (!file.exists()){
                    file.mkdir();
                }
                FileOutputStream fos = new FileOutputStream(path+fileName);
                fos.write(buffer.toString().getBytes());
                fos.close();
            }else {
                String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
                FileOutputStream fos = new FileOutputStream(path+fileName);
                fos.write(buffer.toString().getBytes());
                fos.close();
            }
        }catch (Exception e){
            Log.e(TAG, "an error occured while writing file...", e);
        }
}

    /**
     * 手机设备信息
     * @param mContext
     */
    private void collectDeviceInfo(Context mContext) {
        PackageManager pm = mContext.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(mContext.getPackageName(),PackageManager.GET_ACTIVITIES);
            if (info != null){
                String versionName = info.versionName == null ? "" : info.versionName;
                String versionCode = info.versionCode +"";
                infos.put("versionName",versionName);
                infos.put("versionCode",versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "an error occured when collect package info", e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field:fields
             ) {
            field.setAccessible(true);
            try {
                infos.put(field.getName(),field.get(null).toString());
                Log.d(TAG, field.getName() + " : " + field.get(null));
            } catch (IllegalAccessException e) {
                Log.e(TAG, "an error occured when collect crash info", e);
            }
        }
    }
}
