package com.beilin.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.beilin.R;
import com.beilin.adapter.TestAdapter;
import com.beilin.leancloud.utils.LeanCloudUtils;
import com.beilin.request.DeleteRequest;
import com.beilin.request.FileRequest;
import com.beilin.request.InsertRequest;
import com.beilin.request.InsertWithFileRequest;
import com.beilin.request.QueryLoadRequest;
import com.beilin.request.QueryRefreshRequest;
import com.beilin.request.UpdateRequest;
import com.beilin.utils.Tools;
import com.beilin.view.RefreshLayout;
import com.dd.CircularProgressButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Lenovo on 2016/5/22.
 *
 * @author ChengTao
 */
@SuppressWarnings("ALL")
public class MainActivity extends BaseActivity implements
        AdapterView.OnItemClickListener, View.OnClickListener, AdapterView.OnItemLongClickListener, RefreshLayout.OnRefreshListener ,RefreshLayout.OnLoadListener{
    private ListView lvMain;
    private TestAdapter adapter;
    private List<AVObject> list;
    private CircularProgressButton btnInsert;
    private CircularProgressButton btnQuery;
    private CircularProgressButton btnUpdate;
    private CircularProgressButton btnFile;
    private CircularProgressButton btnPic;
    private RefreshLayout refreshLayout;
    private ImageView ivPic;
    private Bitmap bitmap = null;
    private Uri uri;
    private boolean isFinish = false;
    private int limit = 2;

    private final static int QUERY_REFRESH_REQUEST = 1;
    private final static int ADD_REQUEST = 2;
    private final static int DELETE_REQUEST = 3;
    private final static int UPDATE_REQUEST = 4;
    private final static int FILE_REQUEST = 5;
    private final static int INSERT_WITH_FILE_REQUEST = 6;
    private final static int QUERY_LOAD_REQUEST = 7;

    private final static int FILE_REQUEST_CODE = 1;

    private FileRequest fileRequest;

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
        refreshLayout = obtainView(R.id.sr_container);
        list = new ArrayList<>();
        adapter = new TestAdapter(mContext, list);
        lvMain.setAdapter(adapter);
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
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadListener(this);
    }

    @Override
    protected void initData() {
        refreshListView();
    }

    private void refreshListView() {
        QueryRefreshRequest getRequest = new QueryRefreshRequest(mContext, 0, list);
        getRequest.setRequestId(QUERY_REFRESH_REQUEST);
        leanColud(getRequest);
        Log.e("TAG", "refreshListView");
    }

    @Override
    public void onStart(int requestId) {
        super.onStart(requestId);
        switch (requestId) {
            case QUERY_REFRESH_REQUEST:
                btnQuery.setProgress(20);
                showToast("刷新数据开始");
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
            case INSERT_WITH_FILE_REQUEST:
                showToast("上传带图片信息开始");
                break;
            case QUERY_LOAD_REQUEST:
                showToast("加载数据开始");
                break;
        }
    }

    @Override
    public void onSuccess(int requestId, List<AVObject> lists) {
        super.onSuccess(requestId, lists);
        switch (requestId) {
            case QUERY_REFRESH_REQUEST:
                btnQuery.setProgress(100);
                btnQuery.setProgress(0);
                showToast("刷新数据成功");
                Log.v("info", list.toString());
                Log.e("TAG", lists.toString());
                Collections.reverse(lists);
                for (AVObject object : lists
                        ) {
                    this.list.add(0,object);
                    Log.e("TAG", "--------object-----------" + object.toString());
                }
                Log.e("TAG", "--------list.size()-----------" + this.list.size());
                Log.e("TAG", "--------list-----------" + this.list.toString());
                Log.e("TAG", "--------getClass-----------" + this.list.getClass());
                adapter.notifyDataSetChanged();
                refreshLayout.setRefreshing(false);
                break;
            case ADD_REQUEST:
                btnInsert.setProgress(100);
                btnInsert.setProgress(0);
                showToast("添加成功");
                break;
            case DELETE_REQUEST:
                showToast("删除成功");
                refreshListView();
                break;
            case UPDATE_REQUEST:
                btnUpdate.setProgress(100);
                btnUpdate.setProgress(0);
                showToast("更新成功");
                refreshListView();
                break;
            case FILE_REQUEST:
                btnFile.setProgress(100);
                btnFile.setProgress(0);
                showToast("上传成功");
                break;
            case INSERT_WITH_FILE_REQUEST:
                showToast("上传带图片信息成功");
                break;
            case QUERY_LOAD_REQUEST:
                showToast("加载数据成功");
                if (lists.size() < limit) {
                    isFinish = true;
                }
                for (AVObject object : lists
                        ) {
                    this.list.add(object);
                    Log.e("TAG", "--------object-----------" + object.toString());
                }
                adapter.notifyDataSetChanged();
                refreshLayout.setLoading(false);
                break;
        }
    }


    @Override
    public void onFailure(int requestId, AVException e) {
        super.onFailure(requestId, e);
        Log.v("TAG", "onFailure");
        switch (requestId) {
            case QUERY_REFRESH_REQUEST:
                btnQuery.setProgress(100);
                btnQuery.setProgress(0);
                showToast("刷新数据失败");
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
            case INSERT_WITH_FILE_REQUEST:
                showToast("上传带图片信息失败");
                break;
            case QUERY_LOAD_REQUEST:
                showToast("加载数据失败");
                refreshLayout.setLoading(false);
                break;
        }
    }

    @Override
    public void onFileProgress(int requestId, Integer integer, int position) {
        super.onFileProgress(requestId, integer, position);
        switch (requestId) {
            case FILE_REQUEST:
                btnFile.setProgress(integer);
                break;
            default:
                break;
        }
    }

    @Override
    public void onFileSuccess(int requestId, int position) {
        super.onFileSuccess(requestId, position);
        switch (requestId){
            case FILE_REQUEST:
                ArrayList<AVObject> objects = new ArrayList<>();
                AVObject object = new AVObject(LeanCloudUtils.SHE_TUAN);
                object.put("like", new Random().nextInt(10));
                object.put("name", "计协");
                object.put("introduce", "北京林业大学计算机与网络协会");
                object.put("avatar_url",fileRequest.getFileUrls().toArray());
                objects.add(object);
                InsertWithFileRequest insertWithFileRequest = new InsertWithFileRequest(mContext,objects);
                insertWithFileRequest.setRequestId(INSERT_WITH_FILE_REQUEST);
                leanColud(insertWithFileRequest);
                break;
            default:
                break;
        }
    }

    @Override
    public void onFileFailure(int requestId, AVException e, int position) {
        super.onFileFailure(requestId, e, position);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position < list.size()){
            showToast(list.get(position).getObjectId() + "----" + list.get(position).get("like") + "-------" +
                    list.get(position).getClassName());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                btnInsert.setProgress(0);
                ArrayList<AVObject> objects = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    AVObject object = new AVObject(LeanCloudUtils.SHE_TUAN);
                    object.put("like", new Random().nextInt(10));
                    object.put("name", "计协");
                    object.put("introduce", "北京林业大学计算机与网络协会");
                    objects.add(object);
                }
                InsertRequest addRequest = new InsertRequest(mContext, objects);
                addRequest.setRequestId(ADD_REQUEST);
                leanColud(addRequest);
                break;
            case R.id.btn_get:
                btnQuery.setProgress(0);
                refreshListView();
                break;
            case R.id.btn_update:
                btnUpdate.setProgress(0);
                AVObject object2 = list.get(0);
                object2.put("like", 10);
                UpdateRequest request1 = new UpdateRequest(mContext, object2);
                request1.setRequestId(UPDATE_REQUEST);
                leanColud(request1);
                break;
            case R.id.btn_pic:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, FILE_REQUEST_CODE);
                break;
            case R.id.btn_file:
                if (ivPic.getDrawable() != null) {
                    List<AVFile> files = new ArrayList<>();
                    AVFile file = new AVFile(System.currentTimeMillis() + ".jpg", Tools.getByteArrayFromUri(ivPic));
                    files.add(file);
                    fileRequest = new FileRequest(mContext,files);
                    fileRequest.setRequestId(FILE_REQUEST);
                    leanColud(fileRequest);
                } else {
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
                    uri = data.getData();
                    loadImage(ivPic, uri.toString());
                default:
                    break;
            }
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        ArrayList<AVObject> objects = new ArrayList<>();
        objects.add(list.get(position));
        DeleteRequest request = new DeleteRequest(mContext, objects);
        request.setRequestId(DELETE_REQUEST);
        leanColud(request);
        return true;
    }

    @Override
    public void onRefresh() {
        refreshListView();
    }

    @Override
    public void onLoad() {
        loadListView();
    }

    private void loadListView() {
        if (!isFinish){
            QueryLoadRequest queryLoadRequest = new QueryLoadRequest(mContext,list);
            queryLoadRequest.setRequestId(QUERY_LOAD_REQUEST);
            leanColud(queryLoadRequest);
        }else {
            refreshLayout.setLoading(true);
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    showToast("没有更多了");
                    refreshLayout.setLoading(false);
                }
            },500);
        }
    }

    /**
     * 本界面跳转
     * @param context
     */
    public static void invoke(Context context){
        Intent intent = new Intent(context,MainActivity.class);
        context.startActivity(intent);
    }
}
