package com.example.acer.weatherapp;

import android.util.Log;

import net.sf.json.JSONObject;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by acer on 2016/9/11.
 */
public class Report {
    private static final String url="http://op.juhe.cn/onebox/weather/query";
    public static final String charset="UTF-8";
    public static String userAgent =  "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";
    public static final String KEY="1b2b803307ebc1a2f989aca3a46ee8f2";
    public String getRequest(String query) {
        String result=null;
        Map<String,String> citymap=new HashMap<String,String>();
        JSONObject jsonObject=null;
        citymap.put("cityname",query);
        citymap.put("key",KEY);
        try {
            result=net(url,citymap,"GET");
           // jsonObject=JSONObject.fromObject(result);              不知何故，解释总是错误，并未报错

            if(jsonObject.getInt("error_code")==0) {
                Log.i("ERROR",jsonObject.get("result").toString());
            }else {
                Log.i("ERROR",jsonObject.get("error_code")+""+jsonObject.get("reason"));
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return result;
       // return jsonObject;
    }
    public String net(String surl,Map<String,String> map,String method)throws IOException,Exception{
        HttpURLConnection connection=null;
        BufferedReader reader=null;
        String rs=null;
        try {
            if(method==null||method.equals("GET")) {
                surl=surl+"?"+encoding(map);
            }
            URL url=new URL(surl);
            connection=(HttpURLConnection)url.openConnection();
            connection.setRequestProperty("User-Agent", userAgent);
            connection.setConnectTimeout(3000);
            connection.setReadTimeout(3000);
            connection.setInstanceFollowRedirects(false);
            connection.setDoOutput(true);
            connection.connect();
            if(method.equals("GET")) {
                DataOutputStream out=new DataOutputStream(connection.getOutputStream());
                out.writeBytes(encoding(map));
            }
            StringBuffer stringBuffer=new StringBuffer();
           reader=new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
            String str=null;
            while((str=reader.readLine())!=null) {
                stringBuffer.append(str);
            }
            rs=stringBuffer.toString();
        }catch(IOException e) {
            e.printStackTrace();
        }finally {
            if(reader!=null)
                reader.close();
            if(connection!=null)
                connection.disconnect();
        }
        return rs;
    }

    public String encoding(Map<String,String> maps) throws Exception {
        StringBuffer rs=new StringBuffer("");
        for(Map.Entry i:maps.entrySet()) {
            rs.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue()+"","UTF-8")).append("&");
        }
        return rs.toString();
    }

    public static Report getInstance() {
        return new Report();
    }
}
