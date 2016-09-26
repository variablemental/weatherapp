package com.example.acer.weatherapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by acer on 2016/9/16.
 */
public class CityListActivity extends Activity {
    private ListView mListView;
    private ArrayList<CityInfo> mCitylist = new ArrayList<CityInfo>();
    public static String PARCEL_KEY="PARCEL_KEY";
    public static String INFO="INFO";
    public static String SELECT_CITY="SELECT_CITY";
    public static int ResultCode=1;
    private String getTag;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    @Override
    protected void onCreate(Bundle savedInstancedState) {
        super.onCreate(savedInstancedState);
        setContentView(R.layout.acivity_list);
        if(android.os.Build.VERSION.SDK_INT>9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        loadArrays();
        ListAdapter adapter = new ListAdapter(this, null, mCitylist);
        mListView = (ListView) findViewById(R.id.list_city);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CityInfo city = mCitylist.get(position);
                Intent intent=new Intent();
                intent.putExtra(SELECT_CITY,city.getCity());
                CityListActivity.this.setResult(ResultCode, intent);
                CityListActivity.this.finish();
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
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

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "CityList Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.acer.weatherapp/http/host/path")
        );
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "CityList Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.acer.weatherapp/http/host/path")
        );
    }
}
