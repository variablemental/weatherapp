package com.example.acer.weatherapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.sf.json.JSON;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by acer on 2016/9/25.
 */
public class AnalyzeActivity extends Activity {
    private CityInfo[] src=new CityInfo[10];
    private Button mSelectCity;
    private TextView mTextView;
    private Button mShowButton;
    public static String REQUEST_OF_ANALYZE="REQUEST_OF_ANALYZE";
    public static final String REQUEST_OF_LIST="REQUEST_OF_LIST";
    public static final String REQUEST_OF_CITY="REQUEST_OF_CITY";
    public static final String JSON_DATE="date";
    public static final String JSON_INFO="info";
    public static final int requestCode=0;
    private ArrayList<CityInfo> mCitylist=new ArrayList<>();
    private Map<String,Integer> mTemperature=new HashMap<>();
    Report report=Report.getInstance();
    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
        setContentView(R.layout.activity_analyze);
        loadArray();
        mSelectCity=(Button)findViewById(R.id.analyze_button_for_select);
        mSelectCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AnalyzeActivity.this,CityListActivity.class);
                startActivityForResult(i,requestCode);
            }
        });
        mTextView=(TextView)findViewById(R.id.analyze_text_city);
        requestData(REQUEST_OF_LIST);
        TemperatureView tmpv=(TemperatureView)findViewById(R.id.analyze_temperature_city);
        tmpv.setDataForCitys(mCitylist);
        mShowButton=(Button)findViewById(R.id.analyze_button_show);
        mShowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mTextView.getText()!=null) {
                    requestData(REQUEST_OF_CITY);
                    TemperatureView tmpvOfCity=(TemperatureView)findViewById(R.id.analyze_temperature_predict);
                    tmpvOfCity.setDataForCentainCity(mTemperature);
                }
                else
                    return;
            }
        });
    }

    @Override
    protected void onActivityResult(int RequestCode,int ResultCode,Intent data) {
        String Cityname=data.getStringExtra(CityListActivity.SELECT_CITY);
        switch(RequestCode) {
            case requestCode:
                mTextView.setText("城市:"+Cityname);

        }
    }

    private void MergeSort(CityInfo[] arr,int start,int end) {
        if(start>end)
            return ;
        else {
            int mid=(start+end)/2;
            MergeSort(arr,start,mid);
            MergeSort(arr,mid+1,end);
            Merge(arr,start,mid,end);
        }
    }

    private void Merge(CityInfo[] arr,int s,int q,int e) {
        int length1=q-s+1;
        int length2=e-q;
        int length=e-s+1;
        CityInfo[] a=new CityInfo[length1];
        CityInfo[] b=new CityInfo[length2];
        for(int i=0;i<a.length;i++)             //P.S. :愚蠢的clone方法，迟早药丸，会修改
            a[i]=arr[s+i];
        for(int i=0;i<b.length;i++)
            b[i]=arr[q+i];
        int i=s,j=q+1,point=s;
        while(i<q+1&&j<e+1) {
            if(a[i].compare(a[i],b[i])==1) {
                arr[point++]=a[i++];
            }
            else {
                arr[point++]=b[j++];
            }
        }
        for(;i<q+1;i++)
            arr[point++]=a[i++];
        for(;j<e+1;j++)
            arr[point++]=b[j++];
    }

    private void loadArray() {
        mCitylist.add(new CityInfo(new String("北京")));
        mCitylist.add(new CityInfo(new String("上海")));
        mCitylist.add(new CityInfo(new String("广州")));
        mCitylist.add(new CityInfo(new String("南京")));
        mCitylist.add(new CityInfo(new String("杭州")));
        mCitylist.add(new CityInfo(new String("深圳")));
        mCitylist.add(new CityInfo(new String("重庆")));
        mCitylist.add(new CityInfo(new String("成都")));
    }
    private void requestData(String request) {
        switch (request) {
            case REQUEST_OF_LIST:
            for (int i = 0; i < mCitylist.size(); i++) {
                mCitylist.get(i).setDetail(report.getRequest(mCitylist.get(i).getCity()));
            }
            return;
            case REQUEST_OF_CITY:
            String cityname=mTextView.getText().toString();
            String data=report.getRequest(cityname);
            final int TOKEN=2;                       //    JSON数据中的"info"JSON数组中的温度数据是第三个
            try {
                JSONArray jsonArray=new JSONObject("data").getJSONObject("result").getJSONArray("weather");
                for(int i=0;i<jsonArray.length();i++) {
                    String date=jsonArray.getJSONObject(i).getString(JSON_DATE);
                    Integer temperature=new Integer( jsonArray.getJSONObject(i).getJSONArray(JSON_INFO).getJSONObject(TOKEN).toString());
                    mTemperature.put(date,temperature);
                }
                }catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


}
