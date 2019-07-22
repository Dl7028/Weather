package cn.eoe.app.util;


import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 *  定位工具类：
 *  定位：获取当前经纬度
 * Created by 徐启 on 2019/5/8.
 */

public class LocationUtils {

    private static OnLocationChangeListener mListener;
    private static MyLocationListener myLocationListener;
    private static LocationManager mLocationManager;

    //判断定位是否可用
    public static boolean isLocationEnabled(Context context){
        LocationManager lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        return lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER) || lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }


    /**注册
     *
     * @param context
     * @param minTime  位置信息更新周期
     * @param minDistance  位置变化最小距离：当位置距离变化超过此值时，将更新位置信息
     * @param listener  位置刷新的回调接口
     * @return
     */

    public static boolean register(Context context, long minTime, long minDistance, OnLocationChangeListener listener){
        //若监听对象为空
        if(listener == null){
            return false;
        }
            mLocationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE); //获取系统位置服务实例
            mListener = listener;
            if(!isLocationEnabled(context)){                                                         //GPS不可用
                Toast.makeText(context, "无法定位，请打开定位服务", Toast.LENGTH_SHORT).show();
                return false;
            }
            String provider = mLocationManager.getBestProvider(getCriteria(),true);                //获得最佳服务对象network，gps二者选一
            Location location = mLocationManager.getLastKnownLocation(provider);                     //获取地理位置
            //判断获得的地理位置对象是否为空
            if(location!=null){
                listener.getLastKnownLocation(location);                                            //获得最后一次保留的坐标
            }
            //若监听接口对象为空
            if(myLocationListener == null){
                myLocationListener =new MyLocationListener();
            }
                mLocationManager.requestLocationUpdates(provider,minTime,minDistance,myLocationListener); //更新定位
                return true;
            }

    //注销
    public static void unregister(){
        if (mLocationManager!=null){
            mLocationManager.removeUpdates(myLocationListener);
            myLocationListener = null;
        }
    }
    //设置定位参数
    private static Criteria getCriteria(){
        Criteria criteria = new Criteria();
        //设置定位精确度 Criteria.ACCURACY_COARSE比较粗略，Criteria.ACCURACY_FINE则比较精细
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        //设置是否要求速度
        criteria.setSpeedRequired(false);
        // 设置是否允许运营商收费
        criteria.setCostAllowed(false);
        //设置是否需要方位信息
        criteria.setBearingRequired(false);
        //设置是否需要海拔信息
        criteria.setAltitudeRequired(false);
        // 设置对电源的需求
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        return criteria;
    }

    /**
     * 根据经纬度获取地理位置
     * @param context 上下文
     * @param latitude 维度
     * @param longitude 经度
     * @return
     */
    public static Address getAddress(Context context, double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());             //用Geocoder根据经纬度获取地理位置信息
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude,longitude,1);   //返回一个地址数组，数组长度为1
            System.out.println("-+-+-+---------"+addresses.size());
            if (addresses.size()>0) {
                Log.i("addresses-------------","长度大于0");
                return addresses.get(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i("addresses-------------","addresses.size()>0为假");
        return null;
    }

    /**
     * 根据经纬度获取所在国家
     * @param context
     * @param latitude
     * @param longitude
     * @return
     */

    //根据经纬度获取所在地
   public static String getLocality(Context context,double latitude,double longitude){
        Address address = getAddress(context, latitude, longitude);
        return address == null ? "unknown" : address.getLocality();
    }
    //根据经纬度获取所在街道
    public static String getStreet(Context context, double latitude, double longitude) {
        Address address = getAddress(context, latitude, longitude);
        return address == null ? "unknown" : address.getAddressLine(0);
    }

    //自定义接口
    private static class MyLocationListener implements LocationListener{

        //当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
        @Override
        public void onLocationChanged(Location location) {

        }

        /**
         * provider的在可用、暂时不可用和无服务三个状态直接切换时触发此函数
         * @param provider 提供者
         * @param status 状态
         * @param extras provider可选包
         */

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        //provider被enable时触发此函数，比如GPS被打开
        @Override
        public void onProviderEnabled(String provider) {
        }

        //provider被disable时触发此函数，比如GPS被关闭
        @Override
        public void onProviderDisabled(String provider) {

        }
    }
    //位置改变监听接口
    public interface OnLocationChangeListener {

        //  获取最后一次保留的坐标
        void getLastKnownLocation(Location location);
    }
}
