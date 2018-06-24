package com.example.pingliu.moneycost;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;


public class MyListViewAdapter extends BaseAdapter {
    private Context context;
    private List<Map<String,Object>> list;

    public MyListViewAdapter(Context context, int finance_row, List<Map<String, Object>> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.finance_row,null);
            holder.id = (TextView) convertView.findViewById(R.id.item_id);
            holder.name = (TextView) convertView.findViewById(R.id.item_name);
            holder.money = (TextView) convertView.findViewById(R.id.item_money);
            holder.datetime = (TextView) convertView.findViewById(R.id.item_datetime);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.id.setText(list.get(position).get("id").toString());
        holder.name.setText(list.get(position).get("name").toString());
        holder.money.setText(list.get(position).get("money").toString());
        holder.datetime.setText(list.get(position).get("datetime").toString());
        return convertView;
    }




    static class ViewHolder{
        TextView id;
        TextView name;
        TextView money;
        TextView datetime;
    }
    public void refreshList(List<Map<String,Object>> list){
        this.list = list;
        notifyDataSetChanged();
    }
}
