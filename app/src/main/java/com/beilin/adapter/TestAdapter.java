package com.beilin.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.avos.avoscloud.AVObject;
import com.beilin.R;
import com.beilin.utils.Tools;

import java.util.List;

/**
 * Created by ChengTao on 2016/5/23.
 * @author ChengTao
 */
@SuppressWarnings("ALL")
public class TestAdapter extends BaseAdapter{
    private List<AVObject> list;
    private LayoutInflater layoutInflater;

    public TestAdapter(Context context,List<AVObject> list) {
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
        Log.e("TAG","====TestAdapter===list========"+list.size()+"");
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.e("TAG","==============="+list.size()+"");
        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.item,null);
        }
        TextView name = Tools.ViewHolder.get(convertView,R.id.tv_name);
        TextView introduce = Tools.ViewHolder.get(convertView,R.id.tv_introduce);
        TextView like = Tools.ViewHolder.get(convertView,R.id.tv_like);

        AVObject sheTuan = list.get(position);
        name.setText(sheTuan.get("name")+"");
        introduce.setText(sheTuan.get("introduce")+"");
        like.setText(sheTuan.get("like")+"");
        return convertView;
    }
}
