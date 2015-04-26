package com.yanhao.secret.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.yanhao.secret.utils.ViewHolder;

import java.util.List;

/**
 * Created by yons on 2015/4/26.
 */
public abstract class CommonAdapter <T> extends BaseAdapter {
    protected Context mContext;
    protected List<T> datas;
    protected LayoutInflater inflater;
    protected int layoutId;

    public CommonAdapter(Context mContext, List<T> datas,int layoutId) {
        this.mContext = mContext;
        this.datas = datas;
        this.layoutId=layoutId;
        inflater=LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public T getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder= ViewHolder.get(mContext,convertView,parent,position,layoutId);
        convert(holder,getItem(position));
        return holder.getConverView();
    }

    public abstract void convert(ViewHolder holder,T t);
}
