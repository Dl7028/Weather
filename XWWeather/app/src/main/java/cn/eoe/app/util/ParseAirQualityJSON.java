package cn.eoe.app.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.eoe.app.entity.AirQuality;

/**
 * 解析空气质量
 * Created by 徐启 on 2019/5/6.
 */

public class ParseAirQualityJSON {

    public  String aqi;          //空气质量指数
    public  String quality; //空气质量中文
    public  String locationName;
    public  String path;
    public String pm25;
    public String pm10;
    public String so2;
    public String no2;
    public String co;
    public String o3;

    AirQuality airQuality = new AirQuality();

    public AirQuality getAirQuality(){
        return airQuality;
    }

    //解析天气实况的数据
    public  void getAirQualityResult(String response) {

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject job = (JSONObject) jsonArray.get(i);
                JSONObject locationObject = job.getJSONObject("location");
                locationName = locationObject.getString("name");
                path = locationObject.getString("path");
                JSONObject airObject = job.getJSONObject("air");
                JSONObject cityObject = airObject.getJSONObject("city");
                quality = cityObject.getString("quality");
                aqi = cityObject.getString("aqi");
                pm25 = cityObject.getString("pm25");
                pm10 = cityObject.getString("pm10");
                so2 = cityObject.getString("so2");
                no2 = cityObject.getString("no2");
                co = cityObject.getString("co");
                o3 = cityObject.getString("o3");

                System.out.println("解析类的pm2.5-------------"+pm25);
                //保存解析数据到对象
                setAirQualityResult();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //保存空气质量
    public void  setAirQualityResult(){
        airQuality.setQuality(quality);
        airQuality.setAqi(aqi);
        airQuality.setLocationName(locationName);
        airQuality.setPath(path);
        airQuality.setPm25(pm25);
        airQuality.setPm10(pm10);
        airQuality.setSo2(so2);
        airQuality.setNo2(no2);
        airQuality.setCo(co);
        airQuality.setO3(o3);
    }
}
