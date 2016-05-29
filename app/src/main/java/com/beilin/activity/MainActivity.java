package com.beilin.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.beilin.R;
import com.beilin.adapter.TestAdapter;
import com.beilin.entity.SheTuan;
import com.beilin.request.TestRequest;
import com.beilin.tools.Table;
import com.beilin.tools.Tools;
import com.dd.CircularProgressButton;

import java.io.IOException;
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
        AdapterView.OnItemClickListener, View.OnClickListener, AdapterView.OnItemLongClickListener {
    private ListView lvMain;
    private TestAdapter adapter;
    private List<SheTuan> list;
    private CircularProgressButton btnInsert;
    private CircularProgressButton btnQuery;
    private CircularProgressButton btnUpdate;
    private CircularProgressButton btnFile;
    private CircularProgressButton btnPic;
    private ImageView ivPic;
    private Bitmap bitmap = null;
    private Uri uri;

    private final static int QUERY_REQUEST = 1;
    private final static int ADD_REQUEST = 2;
    private final static int DELETE_REQUEST = 3;
    private final static int UPDATE_REQUEST = 4;
    private final static int FILE_REQUEST = 5;

    private final static int FILE_REQUEST_CODE = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        lvMain = obtainView(R.id.lv_main);
        btnInsert = obtainView(R.id.btn_add);
        btnQuery = obtainView(R.id.btn_get);
        btnUpdate = obtainView(R.id.btn_update);
        btnFile = obtainView(R.id.btn_file);
        btnPic = obtainView(R.id.btn_pic);
        ivPic = obtainView(R.id.iv_pic);
        list = new ArrayList<>();
        adapter = new TestAdapter(mContext, list);
    }

    @Override
    protected void setListener() {
        lvMain.setOnItemClickListener(this);
        btnInsert.setOnClickListener(this);
        btnQuery.setOnClickListener(this);
        lvMain.setOnItemLongClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnFile.setOnClickListener(this);
        btnPic.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        lvMain.setAdapter(adapter);
        fillListView();
    }

    private void fillListView() {
        TestRequest getRequest = new TestRequest(mContext);
        getRequest.setRequestId(QUERY_REQUEST);
        getRequest.setClassName(Table.SHE_TUAN);
        baseQueryData(getRequest);
    }

    @Override
    public void onStart(int requestId) {
        super.onStart(requestId);
        switch (requestId) {
            case QUERY_REQUEST:
                btnQuery.setProgress(20);
                break;
            case ADD_REQUEST:
                btnInsert.setProgress(20);
                break;
            case DELETE_REQUEST:
                showToast("开始删除");
                break;
            case UPDATE_REQUEST:
                showToast("开始更新");
                btnUpdate.setProgress(0);
                break;
            case FILE_REQUEST:
                btnFile.setProgress(0);
                showToast("开始上传");
                break;
        }
    }

    @Override
    public void onSuccess(int requestId, List<AVObject> list) {
        super.onSuccess(requestId, list);
        switch (requestId) {
            case QUERY_REQUEST:
                btnQuery.setProgress(100);
                btnQuery.setProgress(0);
                this.list.clear();
                Log.v("info", list.toString());
                for (AVObject object : list) {
                    SheTuan sheTuan = JSON.parseObject(Tools.praseAVObjectToNeedString(object), SheTuan.class);
                    this.list.add(sheTuan);
                }
                adapter.notifyDataSetChanged();
                break;
            case ADD_REQUEST:
                btnInsert.setProgress(100);
                btnInsert.setProgress(0);
                showToast("添加成功");
                break;
            case DELETE_REQUEST:
                showToast("删除成功");
                fillListView();
                break;
            case UPDATE_REQUEST:
                btnUpdate.setProgress(100);
                btnUpdate.setProgress(0);
                showToast("更新成功");
                fillListView();
                break;
            case FILE_REQUEST:
                btnFile.setProgress(100);
                btnFile.setProgress(0);
                showToast("上传成功");
                break;
        }
    }


    @Override
    public void onFailure(int requestId, AVException e) {
        super.onFailure(requestId, e);
        switch (requestId) {
            case QUERY_REQUEST:
                btnQuery.setProgress(100);
                btnQuery.setProgress(0);
                break;
            case ADD_REQUEST:
                btnInsert.setProgress(100);
                btnInsert.setProgress(0);
                break;
            case DELETE_REQUEST:
                showToast("删除失败");
                break;
            case UPDATE_REQUEST:
                btnUpdate.setProgress(100);
                btnUpdate.setProgress(0);
                showToast("更新失败");
                break;
            case FILE_REQUEST:
                btnFile.setProgress(100);
                btnFile.setProgress(0);
                showToast("上传失败");
                break;
        }
    }

    @Override
    public void onFileProgress(int requestId, Integer integer) {
        super.onFileProgress(requestId, integer);
        switch (requestId){
            case FILE_REQUEST:
                btnFile.setProgress(integer);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        showToast(list.get(position).getObjectId() + "----" + list.get(position).getLike() + "-------" +
                list.get(position).getClassName());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                btnInsert.setProgress(0);
                TestRequest addRequest = new TestRequest(mContext);
                Map<String, Object> map = new HashMap<>();
                map.put("like", new Random().nextInt(10));
                map.put("name", "计协");
                map.put("introduce", "北京林业大学计算机与网络协会");
                AVObject object = Table.getSheTuanObject();
                Table.setData(object, map);

                map.clear();
                map.put("like", new Random().nextInt(10));
                map.put("place", "图书馆");
                map.put("content", "北京林业大学计算机与网络协会活动");

                AVObject object1 = Table.getHuoDongObject();
                Table.setData(object1, map);

                addRequest.insertObjectToList(object);
                addRequest.insertObjectToList(object);
                addRequest.insertObjectToList(object1);
                addRequest.insertObjectToList(object1);
                addRequest.setRequestId(ADD_REQUEST);
                baseInsertData(addRequest);
                break;
            case R.id.btn_get:
                btnQuery.setProgress(0);
                fillListView();
                break;
            case R.id.btn_update:
                btnUpdate.setProgress(0);
                TestRequest request1 = new TestRequest(mContext);
                AVObject object2 = list.get(0);
                object2.put("like", 10);
                request1.setUpDateObject(object2);
                request1.setRequestId(UPDATE_REQUEST);
                baseUpDate(request1);
                break;
            case R.id.btn_pic:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, FILE_REQUEST_CODE);
                break;
            case R.id.btn_file:
                if (bitmap != null){
                AVFile file = new AVFile(System.currentTimeMillis()+".jpg",Tools.getByteArrayFromUri(bitmap,uri));
                Map<String, Object> map2 = new HashMap<>();
                map2.put("like", new Random().nextInt(10));
                map2.put("name", "计协");
                map2.put("introduce", "北京林业大学计算机与网络协会");
                AVObject object3 = Table.getSheTuanObject();
                Table.setData(object3, map2);

                TestRequest request = new TestRequest(mContext);
                request.setUploadFile(file);
                request.setFileKey("pic");
                request.setUploadObject(object3);
                request.setRequestId(FILE_REQUEST);
                uploadObjectWithFile(request);
                }else {
                    showToast("请选择文件");
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case FILE_REQUEST_CODE:
                uri  = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    ivPic.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                default:
                    break;
            }
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        TestRequest request = new TestRequest(mContext);
        request.deleteObjectToList(list.get(position));
        request.setRequestId(DELETE_REQUEST);
        mClient.baseDelete(request);
        return true;
    }
}
