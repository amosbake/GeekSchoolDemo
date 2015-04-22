package com.yanhao.tulingrobot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by yons on 2015/4/21.
 */
public class TextAdapter extends BaseAdapter {
    private List<ListData> listDatas;
    private Context context;

    public TextAdapter(List<ListData> listDatas, Context context) {
        this.listDatas = listDatas;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return listDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RelativeLayout v;
        TextView tv = null;
        TextView tv_time = null;
        LayoutInflater inflater=LayoutInflater.from(context);
        ListData data= (ListData) getItem(position);
            v= (RelativeLayout) inflater.inflate(R.layout.message_item,null);
        if (data.getFlag()==ListData.RECEIVER){
            RelativeLayout robotLayout= (RelativeLayout) v.findViewById(R.id.robot_layout);
            robotLayout.setVisibility(View.VISIBLE);
            tv= (TextView) v.findViewById(R.id.robot_text);
            tv_time= (TextView) v.findViewById(R.id.tv_robot_time);
        }
        if (data.getFlag()==ListData.SEND){
            RelativeLayout adminLayout= (RelativeLayout) v.findViewById(R.id.admin_layout);
            adminLayout.setVisibility(View.VISIBLE);
            tv= (TextView) v.findViewById(R.id.admin_text);
            tv_time= (TextView) v.findViewById(R.id.tv_admin_time);
        }
        tv.setText(data.getContent());
        tv_time.setText(data.getTime());

        return v;
    }
}
