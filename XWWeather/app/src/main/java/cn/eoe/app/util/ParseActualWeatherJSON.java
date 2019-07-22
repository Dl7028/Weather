package cn.eoe.app.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.eoe.app.entity.ActualWeather;
import cn.eoe.app.entity.AirQuality;

/**
 * 解析天气实况api返回的数据
 * Created by 徐启 on 2019/5/6.
 */

public class ParseActualWeatherJSON {
    public    String locationName ; //地名
    public    String locationId;  //地方id
    public    String nowText ;
    public    String nowTemperature ;
    public    String nowFeelsLike;
    public    String nowPressure;
    public    String nowHumidity;
    public    String nowVisibility;
    public    String nowWindDirection;
    public    String nowWindScale;
    public    String nowCode;


    //获得ActualWeather实例
    ActualWeather actualWeather = new ActualWeather();


    public  ActualWeather getActualWeather(){
        return actualWeather;
    }

    //解析天气实况的数据
    public  void getActualWeatherResult( String response) {


        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            for (int i=0;i<jsonArray.length();i++){
                JSONObject job  =  (JSONObject)jsonArray.get(i);
                JSONObject locationObject = job.getJSONObject("location");
                locationName = locationObject.getString("name"); //地名
                locationId = locationObject.getString("id");  //地方id
                JSONObject nowObject = job.getJSONObject("now");
                nowText = nowObject.getString("text");
                nowTemperature = nowObject.getString("temperature");
                nowFeelsLike = nowObject.getString("feels_like");
                nowPressure  = nowObject.getString("pressure");
                nowHumidity = nowObject.getString("humidity");
                nowVisibility = nowObject.getString("visibility");
                nowWindDirection = nowObject.getString("wind_direction");
                nowWindScale = nowObject.getString("wind_scale");
                nowCode = nowObject.getString("code");
                //保存解析数据到对象
                setActualWeatherResult();

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    //保存空气实况
   public void  setActualWeatherResult(){

       actualWeather.setLocationId(locationId);
       actualWeather.setLocationName(locationName);
       actualWeather.setNowFeelsLike(nowFeelsLike);
       actualWeather.setNowHumidity(nowHumidity);
       actualWeather.setNowPressure(nowPressure);
       actualWeather.setNowTemperature(nowTemperature);
       actualWeather.setNowText(nowText);
       actualWeather.setNowVisibility(nowVisibility);
       actualWeather.setNowWindDirection(nowWindDirection);
       actualWeather.setNowWindScale(nowWindScale);
       actualWeather.setNowCode(nowCode);

    }

}
