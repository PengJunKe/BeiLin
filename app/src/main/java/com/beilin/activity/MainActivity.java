package com.beilin.activity;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.beilin.R;
import com.beilin.adapter.TestAdapter;
import com.beilin.entity.SheTuan;
import com.beilin.request.TestRequest;
import com.dd.CircularProgressButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Lenovo on 2016/5/22.
 *
 * @author ChengTao
 */
public class MainActivity extends BaseActivity implements
        AdapterView.OnItemClickListener,View.OnClickListener{
    private ListView lvMain;
    private TestAdapter adapter;
    private List<SheTuan> list;
    private CircularProgressButton btnAdd;
    private CircularProgressButton btnGet;

    private final static int GET_REQUEST = 1;
    private final static int ADD_REQUEST = 2;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        lvMain = obtainView(R.id.lv_main);
        btnAdd = obtainView(R.id.btn_add);
        btnGet = obtainView(R.id.btn_get);
        list = new ArrayList<>();
        adapter = new TestAdapter(mContext,list);
    }

    @Override
    protected void setListener() {
        lvMain.setOnItemClickListener(this);
        btnAdd.setOnClickListener(this);
        btnGet.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        lvMain.setAdapter(adapter);
        fillListView();
    }

    private void fillListView() {
        TestRequest getRequest = new TestRequest(mContext);
        getRequest.setClassName(SHE_TUAN);
        getRequest.setRequestId(GET_REQUEST);
        getData(getRequest);
    }

    @Override
    public void onStart(int requestId) {
        super.onStart(requestId);
        switch (requestId){
            case GET_REQUEST:
                btnGet.setProgress(20);
                break;
            case ADD_REQUEST:
                btnAdd.setProgress(20);
                break;
        }
    }

    @Override
    public void onSuccess(int requestId, List<AVObject> list) {
        super.onSuccess(requestId, list);
        switch (requestId){
            case GET_REQUEST:
                btnGet.setProgress(100);
                btnGet.setProgress(0);
                this.list.clear();
                for (AVObject object:list){
                    SheTuan sheTuan = new SheTuan();
                    sheTuan.name =  object.get("name")+"";
                    sheTuan.introduce =  object.get("introduce")+"";
                    sheTuan.like =  object.get("like") + "";
                    this.list.add(sheTuan);
                }
                adapter.notifyDataSetChanged();
                break;
            case ADD_REQUEST:
                btnAdd.setProgress(100);
                btnAdd.setProgress(0);
                showToast("添加成功");
                break;
        }
    }


    @Override
    public void onFailure(int requestId, AVException e) {
        super.onFailure(requestId, e);
        switch (requestId){
            case GET_REQUEST:
                btnGet.setProgress(100);
                btnGet.setProgress(0);
                break;
            case ADD_REQUEST:
                btnAdd.setProgress(100);
                btnAdd.setProgress(0);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        showToast("position"+position);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add:
                btnAdd.setProgress(0);
                TestRequest addRequest = new TestRequest(mContext);
                addRequest.setClassName(SHE_TUAN);
                Map<String,Object> map = new HashMap<>();
                map.put("like",new Random().nextInt(10));
                map.put("name","计协");
                map.put("introduce","北京林业大学计算机与网络协会");
                addRequest.setPutDatas(map);
                addRequest.setRequestId(ADD_REQUEST);
                putData(addRequest);
                break;
            case R.id.btn_get:
                btnGet.setProgress(0);
                fillListView();
                break;
            default:
                break;
        }
    }
}
