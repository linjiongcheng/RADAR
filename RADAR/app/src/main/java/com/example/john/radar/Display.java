package com.example.john.radar;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

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

public class Display{
    public LatLng myPos;
    public LatLng Pos;
    public LatLng middle;
    public Bundle extraMsg;
    public OverlayOptions options;
    public OverlayOptions optionsNameTele;
    public OverlayOptions ooPolyline;
    public OverlayOptions optionsDistance;
    public BitmapDescriptor bitmap1;
    public Context context;

    Display(Context context){
        this.context = context;
    }

    public BaiduMap display(BaiduMap mBaiduMap, BitmapDescriptor bitmap, Double Latitude, Double Longitude, List<Info> infoList, String flag){
        extraMsg = new Bundle();
        String name;
        String number;
        String latitude;
        String longitude;
        myPos = new LatLng(Latitude,
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
            extraMsg.putString("name", name);
            extraMsg.putString("number", number);
            extraMsg.putString("longitude",longitude);
            extraMsg.putString("latitude",latitude);

            if(flag.equals("friend")){
                //显示朋友姓名和电话号码
                LayoutInflater factory = LayoutInflater.from(context);
                View textEntryView = factory.inflate(R.layout.name_tele, null); ////把视图转换成Bitmap 再转换成Drawable
                TextView Name = (TextView)textEntryView.findViewById(R.id.name);
                TextView Number = (TextView)textEntryView.findViewById(R.id.tele);
                Name.setText(name);
                Name.setTextColor(context.getResources().getColor(R.color.green));
                Number.setText(number);
                try{
                    bitmap1 = BitmapDescriptorFactory.fromView(textEntryView);
                    optionsNameTele = new MarkerOptions()
                            .position(Pos)
                            .icon(bitmap1)
                            .extraInfo(extraMsg);
                    mBaiduMap.addOverlay(optionsNameTele);
                }catch (Exception e){
                    e.printStackTrace();
                }

                // 添加直线
                List<LatLng> points = new ArrayList<LatLng>();
                points.add(myPos);
                points.add(Pos);
                ooPolyline = new PolylineOptions().width(10)
                        .color(0xAA00FF00).points(points);
                mBaiduMap.addOverlay(ooPolyline);
            }else if(flag.equals("enemy")){
                //显示敌人姓名和电话号码
                LayoutInflater factory = LayoutInflater.from(context);
                View textEntryView = factory.inflate(R.layout.name_tele, null); ////把视图转换成Bitmap 再转换成Drawable
                TextView Name = (TextView)textEntryView.findViewById(R.id.name);
                TextView Number = (TextView)textEntryView.findViewById(R.id.tele);
                Name.setText(name);
                Name.setTextColor(context.getResources().getColor(R.color.red));
                Number.setText(number);
                try{
                    bitmap1 = BitmapDescriptorFactory.fromView(textEntryView);
                    optionsNameTele = new MarkerOptions()
                            .position(Pos)
                            .icon(bitmap1)
                            .extraInfo(extraMsg);
                    mBaiduMap.addOverlay(optionsNameTele);
                }catch (Exception e){
                    e.printStackTrace();
                }

                // 添加直线
                List<LatLng> points = new ArrayList<LatLng>();
                points.add(myPos);
                points.add(Pos);
                ooPolyline = new PolylineOptions().width(10)
                        .color(0xAAFF0000).points(points);
                mBaiduMap.addOverlay(ooPolyline);
            }

            //显示位置图标
            options = new MarkerOptions()
                    .position(Pos)
                    .icon(bitmap)
                    .extraInfo(extraMsg);
            mBaiduMap.addOverlay(options);

            //计算距离并显示在连线中间位置
            Double temp = DistanceUtil. getDistance(myPos, Pos);
            String distance = String .format("%.2f",temp)+"m";
            middle = new LatLng((myPos.latitude+Pos.latitude)/2,(myPos.longitude+Pos.longitude)/2);
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
