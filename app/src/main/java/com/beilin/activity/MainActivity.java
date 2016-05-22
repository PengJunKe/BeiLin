package com.beilin.activity;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.beilin.R;
import com.beilin.request.TestRequest;
import com.dd.CircularProgressButton;

import java.util.List;

/**
 * Created by Lenovo on 2016/5/22.
 * @author ChengTao
 */
public class MainActivity extends BaseActivity{
    private CircularProgressButton button;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        button = obtainView(R.id.circularButton1);
    }

    @Override
    protected void setListener() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestRequest request = new TestRequest(mContext);
                request.setRequestId(1);
                request.setClassName("SheTuan");
                getData(request);
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onStart(int requestId) {
        button.setProgress(50);
        Log.v("info","onStart");
    }

    @Override
    public void onFailure(int requestId, AVException e) {
        Log.v("info","--------onFailure");
        Log.v("info","--------onFailure"+e+"");
    }

    @Override
    public void onSuccess(int requestId, List<AVObject> list) {
        Log.v("info","onSuccess");
        button.setProgress(100);
        button.setProgress(0);
        Toast.makeText(MainActivity.this, list.toString(), Toast.LENGTH_SHORT).show();
        Log.v("info",list.toString());
    }
}
