package com.example.acer.weatherapp;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

import org.json.JSONException;

import java.io.Serializable;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by acer on 2016/9/10.
 */
public class CityInfo implements Serializable,Comparator {
    private String cityname;
    private String weather;
    private Integer temperature;
    private String wind;
    private String wind_de;
    private static final String KEYWORD_WEATHER="^\"weather\":[{*],$";              //截取所有有关weather数据段
    private static final String KEY_INFO="^\"day\":{]$";                              //截取第一天所有信息
    private static final String KEYWORD_DATE="date";
    CityInfo(String cityname,String weather,Integer temperature,float wet,String wind)
    {
        this.cityname=cityname;
        this.weather=weather;
        this.temperature=temperature;
        this.wind=wind;
    }

//    CityInfo(JSONObject detail) {
//        CityInfo information=(CityInfo)JSONObject.toBean(detail,CityInfo.class);
//        this.cityname=information.getCity();
//        this.weather=information.getWeather();
//        this.degree=information.getDegree();
//        this.wet=information.getWet();
//        this.wind=information.getwind();
//    }

    CityInfo(String cityname) {
        this.cityname=cityname;
        this.weather=null;
        this.temperature=null;
        this.wind=null;
        this.wind_de=null;
    }

//    public void setDetail(JSONObject detail) {
//        CityInfo info=(CityInfo)JSONObject.toBean(detail,CityInfo.class);
//        this.weather=info.getWeather();
//        this.degree=info.getDegree();
//        this.wind=info.getwind();
//        this.wet=info.getWet();
//    }

    /**
     *
     * @param detail String类型的JSON信息，在这个函数里面接收和解析
     */

    public void setDetail(String detail) {
        try {
            org.json.JSONObject jsonObject = new org.json.JSONObject(detail).getJSONObject("result").getJSONObject("data")
                    .getJSONObject("realtime");
            weather=jsonObject.getJSONObject("weather").getString("info");
            temperature=Integer.parseInt(jsonObject.getJSONObject("weather").getString("temperature"));
            wind=jsonObject.getJSONObject("wind").getString("direct");
            wind_de=jsonObject.getJSONObject("wind").getString("power");

        }catch(JSONException e) {
            e.printStackTrace();
        }
       // analaizeJSON(detail,KEYWORD_WEATHER);
    }

    /**
     * 用正则表达式手动解析JSON数据
     * @param src 原JSON形式的String数据
     * @param regex 解析的正则表达式
     * @return 返回正常数据
     */
    private String analaizeJSON(String src,String regex) {
        Pattern pattern=Pattern.compile(regex);
        Matcher matcher=pattern.matcher(src);
        if(regex.equals(KEY_INFO)) {
            if(matcher.find())
                return matcher.group();
        }
        if(matcher.find()) {
            String content=matcher.group();
            String[] data=analaizeJSON(content,KEY_INFO).split(",");
            weather=data[1];
            temperature=Integer.parseInt(data[2]);
            wind=data[3];
            wind_de=data[4];
        }
        return null;
    }

    String getCity() {
        return cityname;
    }
     public String getWeather() {
        return weather;
    }
     public Integer getTemperature(){
        return temperature;
    }
     public String getwind() {
        return wind;
    }
    public String getPower() {
        return wind_de;
    }
    public String toString() {
        return new String("城市:"+cityname+"\n"
                            +"天气:"+weather+"\n"
                            +"温度:"+temperature+"\n"
                            +"风向:"+wind+"\n"
                            +"风力:"+wind_de+ "\n");
    }

    @Override
    public int compare(Object lhs,Object rhs) {
        CityInfo a=(CityInfo)lhs;
        CityInfo b=(CityInfo)rhs;
        return a.getTemperature()>b.getTemperature()?1:0;
    }
}
