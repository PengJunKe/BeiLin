package com.beilin.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.beilin.R;
import com.beilin.entity.SheTuan;
import com.beilin.tools.Tools;

import java.util.List;

/**
 * Created by ChengTao on 2016/5/23.
 * @author ChengTao
 */
public class TestAdapter extends BaseAdapter{
    private Context context;
    private List<SheTuan> list;
    private LayoutInflater layoutInflater;

    public TestAdapter(Context context,List<SheTuan> list) {
        this.list = list;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
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
        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.item,null);
        }
        TextView name = Tools.ViewHolder.get(convertView,R.id.tv_name);
        TextView introduce = Tools.ViewHolder.get(convertView,R.id.tv_introduce);
        TextView like = Tools.ViewHolder.get(convertView,R.id.tv_like);

        SheTuan sheTuan = list.get(position);
        name.setText(sheTuan.name);
        introduce.setText(sheTuan.introduce);
        like.setText(sheTuan.like);
        return convertView;
    }
}
