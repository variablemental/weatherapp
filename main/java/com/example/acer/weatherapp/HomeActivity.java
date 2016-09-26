package com.example.acer.weatherapp;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TabHost;

/**
 * Created by acer on 2016/9/25.
 */
public class HomeActivity extends TabActivity implements CompoundButton.OnCheckedChangeListener {
    private TabHost mTabHost;
    private Intent  mWeather;
    private Intent mAnalyze;
    private Intent mContacts;
    private Intent mLogContent;
    private static final String WEATHER_ICON="weather";
    private static final String ANALYZE_ICON="analyze";
    private static final String CONTACTS_ICON="contacts";
    private static final String LOGCONTENT_ICON="logcontent";

    @Override
    public void onCreate(Bundle onSaveInstanceState) {
        super.onCreate(onSaveInstanceState);
        setContentView(R.layout.navigateframework);
        mWeather=new Intent(this,WeatherActivity.class);
        mAnalyze=new Intent(this,AnalyzeActivity.class);
        mContacts=new Intent(this,ContactsActivity.class);
        mLogContent=new Intent(this,LogContentActivity.class);
        ((RadioButton)findViewById(R.id.Tabs_weather)).setOnCheckedChangeListener(this);
        ((RadioButton)findViewById(R.id.Tabs_analyze)).setOnCheckedChangeListener(this);
        ((RadioButton)findViewById(R.id.Tabs_logcontent)).setOnCheckedChangeListener(this);
        ((RadioButton)findViewById(R.id.Tabs_contact)).setOnCheckedChangeListener(this);
        setIntentSetup();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked) {
            switch(buttonView.getId()) {
                case R.id.Tabs_weather:
                    this.mTabHost.setCurrentTabByTag(WEATHER_ICON);
                    break;
                case R.id.Tabs_analyze:
                    this.mTabHost.setCurrentTabByTag(ANALYZE_ICON);
                    break;
                case R.id.Tabs_logcontent:
                    this.mTabHost.setCurrentTabByTag(LOGCONTENT_ICON);
                    break;
                case R.id.Tabs_contact:
                    this.mTabHost.setCurrentTabByTag(CONTACTS_ICON);
                    break;
            }
        }
    }

    private void setIntentSetup() {
        this.mTabHost=getTabHost();
        TabHost localTabHost=this.mTabHost;                                                              //或许是关键
        localTabHost.addTab(buildTabSpec(WEATHER_ICON, R.string.tabs_weather, R.drawable.weather, this.mWeather));
        localTabHost.addTab(buildTabSpec(ANALYZE_ICON, R.string.tabs_analyze, R.drawable.line_graph, this.mAnalyze));
        localTabHost.addTab(buildTabSpec(LOGCONTENT_ICON, R.string.tabs_logcontent, R.drawable.logcontent, this.mLogContent));
        localTabHost.addTab(buildTabSpec(CONTACTS_ICON,R.string.tabs_getcontact,R.drawable.contacts,this.mContacts));
    }

    private TabHost.TabSpec buildTabSpec(String tag,int resLabel,int resIcon,final Intent content) {
        return mTabHost.newTabSpec(tag).setIndicator(getString(resLabel),getResources().getDrawable(resIcon)).setContent(content);
    }
}
