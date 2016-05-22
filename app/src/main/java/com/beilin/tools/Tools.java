package com.beilin.tools;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * Created by Lenovo on 2016/5/2.
 */
public class Tools {
    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View itemView) {
            super(itemView);
        }

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
}
