package com.example.john.radar;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 2016/10/27.
 */
public class Display {
    public LatLng Pos;
    public LatLng NamePos;
    public LatLng NumPos;
    public LatLng middle;
    public Bundle extraMsg;
    public OverlayOptions option;
    public OverlayOptions optionsName;
    public OverlayOptions optionsNum;
    public OverlayOptions ooPolyline;
    public OverlayOptions optionsDistance;
    public BaiduMap display(BaiduMap mBaiduMap, BitmapDescriptor bitmap, Double Latitude, Double Longitude, List<Info> infoList, String flag){
        extraMsg = new Bundle();
        String name;
        String number;
        String latitude;
        String longitude;
        LatLng p1 = new LatLng(Latitude,
                Longitude);
        for(Info x:infoList){
            name = x.getName();
            number = x.getTele();
            latitude = x.getLatitude();
            longitude = x.getLongitude();
            if(latitude.equals("")||longitude.equals("")){
                continue;
            }
            //定位到对象的位置
            Pos = new LatLng(Double.valueOf(latitude),Double.valueOf(longitude));
            //名字位置
            NamePos = new LatLng(Double.valueOf(latitude)-0.0001,Double.valueOf(longitude));
            //电话号码位置
            NumPos = new LatLng(Double.valueOf(latitude)-0.0011,Double.valueOf(longitude));
            extraMsg.putString("name", name);
            extraMsg.putString("number", number);
            extraMsg.putString("longitude",longitude);
            extraMsg.putString("latitude",latitude);
            //显示位置图标
            option = new MarkerOptions()
                    .position(Pos)
                    .icon(bitmap)
                    .extraInfo(extraMsg);
            mBaiduMap.addOverlay(option);

            if(flag.equals("friend")){
                //显示姓名
                optionsName = new TextOptions()
                        .position(NamePos)
                        .text(name)
                        .fontColor(0xFF00FF00)
                        .fontSize(36)
                        .typeface(Typeface.DEFAULT_BOLD);
                mBaiduMap.addOverlay(optionsName);

                // 添加直线
                List<LatLng> points = new ArrayList<LatLng>();
                points.add(p1);
                points.add(Pos);
                ooPolyline = new PolylineOptions().width(10)
                        .color(0xAA00FF00).points(points);
                mBaiduMap.addOverlay(ooPolyline);
            }else if(flag.equals("enemy")){
                //显示姓名
                optionsName = new TextOptions()
                            .position(NamePos)
                            .text(name)
                            .fontColor(0xFFFF0000)
                            .fontSize(36)
                            .typeface(Typeface.DEFAULT_BOLD);
                    mBaiduMap.addOverlay(optionsName);
                // 添加直线
                List<LatLng> points = new ArrayList<LatLng>();
                points.add(p1);
                points.add(Pos);
                ooPolyline = new PolylineOptions().width(10)
                        .color(0xAAFF0000).points(points);
                mBaiduMap.addOverlay(ooPolyline);
            }

            //显示电话号码
            optionsNum = new TextOptions()
                    .position(NumPos)
                    .text(number)
                    .fontColor(0xFF000000)
                    .fontSize(24)
                    .typeface(Typeface.DEFAULT_BOLD);
            mBaiduMap.addOverlay(optionsNum);

            //计算距离并显示在连线中间位置
            Double temp = DistanceUtil. getDistance(p1, Pos);
            String distance = String .format("%.2f",temp)+"m";
            middle = new LatLng((p1.latitude+Pos.latitude)/2,(p1.longitude+Pos.longitude)/2);
            optionsDistance = new TextOptions()
                    .position(middle)
                    .text(distance)
                    .fontColor(0xFF000000)
                    .fontSize(24)
                    .typeface(Typeface.DEFAULT_BOLD);
            mBaiduMap.addOverlay(optionsDistance);
        }
        return mBaiduMap;
    }
}
