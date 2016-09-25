package com.example.acer.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by acer on 2016/9/12.
 */
public class CityListFragment extends ListFragment {
    public static String REQUEST_OF_CITY="REQUEST_OF_CITY";
    private static String Tag="CityListFragment";
    private ListView mListView=null;
    private ArrayList<CityInfo> mCitylist=new ArrayList<CityInfo>();

    @Override
    public void onCreate(Bundle savedInstancedState) {
        super.onCreate(savedInstancedState);
        loadArrays();
        ListAdapter adapter=new ListAdapter(getContext(),mListView,mCitylist);
        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup parent,Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.city_list_fragment,null);
        mListView=(ListView)view.findViewById(R.id.city_list);

        return view;

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        CityInfo cityname=mCitylist.get(position);
        Intent i=new Intent(getActivity(),MainActivity.class);
        i.putExtra(MainActivity.City,cityname.getCity());
        startActivityForResult(i,MainActivity.REQUEST_OF_CITY);

    }
    public void loadArrays() {
        mCitylist.add(new CityInfo(new String("北京")));
        mCitylist.add(new CityInfo(new String("上海")));
        mCitylist.add(new CityInfo(new String("广州")));
        mCitylist.add(new CityInfo(new String("南京")));
        mCitylist.add(new CityInfo(new String("杭州")));
        mCitylist.add(new CityInfo(new String("深圳")));
        mCitylist.add(new CityInfo(new String("重庆")));
        mCitylist.add(new CityInfo(new String("成都")));
    }


}
