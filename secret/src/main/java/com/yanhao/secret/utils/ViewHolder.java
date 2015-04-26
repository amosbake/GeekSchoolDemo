package com.yanhao.secret.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URI;

/**
 * Created by yons on 2015/4/26.
 */
public class ViewHolder {
    private SparseArray<View> mViews;
    private int mPosition;
    private View mConvertView;

    public ViewHolder(Context context,ViewGroup parent,int layoutId,int position) {
        this.mPosition=position;
        this.mViews=new SparseArray<>();
        mConvertView= LayoutInflater.from(context).inflate(layoutId,parent,false);
        mConvertView.setTag(this);
    }
    public static ViewHolder get(Context context,View convertView,ViewGroup parent,int position,int layoutId){
        if (convertView==null){
            return new ViewHolder(context,parent,layoutId,position);
        }else{
            ViewHolder holder= (ViewHolder) convertView.getTag();
            holder.mPosition=position;
            return holder;
        }
    }

    /**
     * 通过viewId获取控件
     * @param viewId 控件id
     * @param <T> 控件类型
     * @return
     */
    public <T extends View> T getView(int viewId){
        View v=mViews.get(viewId);
        if (v==null){
            v=mConvertView.findViewById(viewId);
            mViews.put(viewId,v);
        }
        return (T)v;
    }
    public View getConverView(){
        return mConvertView;
    }

    /**
     * 设置TextView的值
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolder setText(int viewId,String text){
        TextView tv=getView(viewId);
        tv.setText(text);
        return this;
    }

    /**
     * 设置ImageView的图像
     * @param viewId
     * @param resId
     * @return
     */
    public ViewHolder setImageResource(int viewId,int resId){
        ImageView iv=getView(viewId);
        iv.setImageResource(resId);
        return this;
    }
    public ViewHolder setImageBitmap(int viewId,Bitmap bitmap){
        ImageView iv=getView(viewId);
        iv.setImageBitmap(bitmap);
        return this;
    }
    public ViewHolder setImageURI(int viewId,String uri){
        ImageView iv=getView(viewId);
        //ImageLoader.load(view, uri)
        iv.setImageURI(Uri.parse(uri));
        return this;
    }
}
