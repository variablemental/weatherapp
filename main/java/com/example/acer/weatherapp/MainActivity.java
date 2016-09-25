package com.example.acer.weatherapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<CityInfo> mCitylist=new ArrayList<CityInfo>();
    public static Map<String,String> mBackground=new HashMap<String,String>();
    public static String sunday="Sunday";
    public static String rainy="Rainy";
    public static String Snowy="Snowy";
    public static String Cloudy="Cloudy";
    public static String REQURST_OF_BACK="BACKGROUND";
    public static Integer REQUEST_OF_CITY=1;
    public static String City="CITY";
    private CityInfo item;
    private String cityname=new String("");
    private TextView mDetails_weather;
    private TextView mDetails_cityname;
    private TextView mDetails_winddirection;
    private TextView mDetails_windpower;
    private TextView mDetails_temperature;
    private TextView mDetails;
    private ListView mListView;
    private ListAdapter adapter;
    private Button mSelectButton;
    private TextView mCityLabel;
    Report report=Report.getInstance();

//    private class CityAdapter extends ArrayAdapter<CityInfo> {
//        CityAdapter(ArrayList<CityInfo> cityinfo) {
//            super(MainActivity.this,0,mCitylist);
//        }
//        @Override
//        public View getView(int position,View convertview,ViewGroup parent) {
//            View view=onCreate(position,convertview);
//            return view;
//        }
//
//        public View onCreate(int position,View convertview) {
//            convertview=getLayoutInflater().inflate(R.layout.acivity_list,null);
//            TextView city=(TextView)convertview.findViewById(R.id.city_name);
//            city.setText(mCitylist.get(position).toString());
//            return convertview;
//        }
//
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(android.os.Build.VERSION.SDK_INT>9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        loadArrays();

        mListView=(ListView)findViewById(R.id.city_list);
        adapter=new ListAdapter(this,mListView,mCitylist);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                item = mCitylist.get(position);
                item.setDetail(report.getRequest(item.getCity()));
                //mDetails.setText(item.toString());
                mDetails_cityname.setText(item.getCity());
                mDetails_temperature.setText(item.getTemperature().toString()+"℃");
                mDetails_weather.setText(item.getWeather());
                mDetails_winddirection.setText(item.getwind());
                mDetails_windpower.setText(item.getPower());
            }
        });
        //mDetails = (TextView) findViewById(R.id.detail_info);
        //mDetails.setTextColor(Color.YELLOW);
        mDetails_cityname=(TextView)findViewById(R.id.cityname_text);
        mDetails_cityname.setTextColor(Color.YELLOW);
        mDetails_temperature=(TextView)findViewById(R.id.temperature_text);
        mDetails_temperature.setTextColor(Color.YELLOW);
        mDetails_weather=(TextView)findViewById(R.id.weather_text);
        mDetails_weather.setTextColor(Color.YELLOW);
        mDetails_winddirection=(TextView)findViewById(R.id.winddirection_text);
        mDetails_winddirection.setTextColor(Color.YELLOW);
        mDetails_windpower=(TextView)findViewById(R.id.windpower_text);
        mDetails_windpower.setTextColor(Color.YELLOW);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        mSelectButton=(Button)findViewById(R.id.fragment_list);
        mSelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, CityListActivity.class);
                startActivity(i);
            }
        });

        /*
            以下注释代码为加载请求数据的新线程，但是没有经过测试。
            根据google的标准，HoneyComb及以上版本的Activity中不能在主线程(MainThread)中加入网络请求，
            因为这样体验会非常差，所以考虑加入线程，没有经过测试是因为主线程的版本JSON数据还没有
            完成需求，所以等待这个问题解决之后再测试该线程。
         */
//
//        final Handler handler=new Handler() {
//            @Override
//        public void handleMessage(Message message) {
//                super.handleMessage(message);
//                Bundle data=message.getData();
//                mDetails.setText(data.toString());
//            }
//        };
//
//        Runnable networkTask=new Runnable() {
//            @Override
//            public void run() {
//                while(true) {
//                    if(item!=null) {
//                        Message message=new Message();
//                        item.setDetail(report.getRequest(item.getCity()));
//                        Bundle data=new Bundle();
//                        data.putString("value",item.toString());
//                        message.setData(data);
//                        handler.handleMessage(message);
//                    }
//                }
//            }
//        };
//        new Thread(networkTask).start();
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
        mBackground.put("sunday", "sunday.jpg");
        mBackground.put("rainy","Rainy.jpg");
        mBackground.put("Cloudy", "Cloudy.jpg");
        mBackground.put("Snowy", "Snowy.jpg");
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data) {
        if(REQUEST_OF_CITY==requestCode) {
           Bundle extra=data.getBundleExtra(CityListActivity.PARCEL_KEY);
            CityInfo city=(CityInfo)extra.get(CityListActivity.INFO);
           // mDetails.setText(city.toString());
            mDetails.setText(city.getCity());
        }
    }

    public void ChangeBackground(String weather) {

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
