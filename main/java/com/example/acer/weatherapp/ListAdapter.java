package com.example.acer.weatherapp;

import android.content.Context;
import android.graphics.Color;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by acer on 2016/9/12.
 */
public class ListAdapter extends BaseAdapter {

    private TextView mTextView;
    private ListView mListView;
    private LayoutInflater inflater;
    private ArrayList<CityInfo> arrayList;
    public ListAdapter(Context context,ListView listView,ArrayList<CityInfo> arrayList) {
        inflater=LayoutInflater.from(context);
        this.mListView=listView;
        this.arrayList=arrayList;
    }

    @Override
    public int getCount() {
       return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=inflater.inflate(R.layout.activity_list_item,null);
        mTextView=(TextView)convertView.findViewById(R.id.city_name);
        mTextView.setTextColor(Color.RED);
        mTextView.setText(arrayList.get(position).getCity());
        return convertView;
    }
}
