package com.yanhao.myphonedirectory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by yons on 2015/3/31.
 */
public class PhoneInfoAdapter extends BaseAdapter{
    private List<PhoneInfo> phoneInfoList;
    private Context context;

    public PhoneInfoAdapter(List<PhoneInfo> phoneInfoList, Context context) {
        this.phoneInfoList = phoneInfoList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return phoneInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return phoneInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(context);
        ViewHolder holder=new ViewHolder();
        if (convertView==null){
            convertView=inflater.inflate(R.layout.list_phoneinfo_item,null);
            holder.name= (TextView) convertView.findViewById(R.id.name);
            holder.number= (TextView) convertView.findViewById(R.id.number);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.name.setText(phoneInfoList.get(position).getName());
        holder.number.setText(phoneInfoList.get(position).getPhoneNumber());
        return convertView;
    }
    class ViewHolder{
        TextView name;
        TextView number;
    }
}
