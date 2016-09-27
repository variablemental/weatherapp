package com.example.acer.weatherapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by acer on 2016/9/26.
 */
public class TemperatureView extends View {
    private static final float PointX=60;
    private static final float PointY=260;
    private static final float Xlength=380;
    private static final float Ylength=240;
    private static final float perDx=Ylength/100;          //代表每一度在画布上所占据的距离
    private float ScaleX;
    private float ScaleY=20;
    private List<CityInfo> mCitylist=new ArrayList<>();
    private Map<String,Integer> mTemperature=new HashMap<>();
    private Map<Float,Float> mPointSet=new HashMap<>();

    public TemperatureView(Context context,AttributeSet attributeSet) {
        super(context,attributeSet);
    }
    @Override
    public void onDraw(Canvas canvas) {
        Paint paint=new Paint();
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawLine(PointX,PointY,PointX,PointY-Ylength,paint);                         //Y轴
        for(int i=1;i*ScaleY<=Ylength;i++) {
            canvas.drawLine(PointX,PointY-Ylength+i*ScaleY,PointX+5,PointY-Ylength,paint);
            canvas.drawText(new Integer(100-(i+1)*10).toString(),PointX-50,PointY-Ylength+i*ScaleY,paint);
        }
        canvas.drawLine(PointX,PointY,PointX+Xlength,PointY,paint);                 //X轴
         for(int i=1;i<mCitylist.size();i++) {
            canvas.drawLine(PointX+i*ScaleX,PointY-Ylength,PointX+ScaleX*i,PointY-Ylength+5,paint);
            canvas.drawText(mCitylist.get(i-1).getCity(),PointX+i*ScaleX-10,PointY-Ylength-20,paint);
        }
        try {
            if (mCitylist != null) {
                for (int i = 2; i < mCitylist.size(); i++) {
                    float startX = PointX + ScaleX * (i - 1);
                    float endX = PointX + ScaleX * i;
                    canvas.drawLine(startX, mPointSet.get(startX), endX, mPointSet.get(endX), paint);
                }
            }
            if (mTemperature != null) {
                for (int i = 2; i < mTemperature.size(); i++) {
                    float startX = PointX + (i - 1) * ScaleX;
                    float endX = PointX + i * ScaleX;
                    canvas.drawLine(startX, mPointSet.get(startX), endX, mPointSet.get(endX), paint);
                }
            }
        }finally {
            if(mCitylist!=null)
                mCitylist=null;
            if(mTemperature!=null)
                mTemperature=null;
        }
    }
    public void setDataForCitys(ArrayList<CityInfo> citylist) {
        this.mCitylist=citylist;
        ScaleX=Xlength/(citylist.size()+1);
        CalPoint();
    }

    public void setDataForCentainCity(Map<String,Integer> mTemperature) {
        this.mTemperature=mTemperature;
        ScaleX=Xlength/(mTemperature.size()+1);
        CalPoint();
    }

    private void CalPoint() {
        if(mCitylist!=null) {
            for (int i = 1; i < mCitylist.size(); i++) {
                mPointSet.put(PointX + i * ScaleX, PointY - (Ylength - mCitylist.get(i - 1).getTemperature() * perDx));
            }
        }
        if(mTemperature!=null) {
            int i=1;
           for(Map.Entry entry:mTemperature.entrySet()) {
               mPointSet.put(PointX+i*ScaleX,PointY-(Ylength-(float)entry.getValue()*perDx));
           }
        }
    }
}
