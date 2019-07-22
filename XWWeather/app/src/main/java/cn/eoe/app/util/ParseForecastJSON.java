package cn.eoe.app.util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.eoe.app.entity.Forecast;

import static cn.eoe.app.ui.CitySearchActivity.forecastSQLiteAction;
import static cn.eoe.app.ui.WeatherActivity.k;

/**
 * 解析逐日天气预报
 * Created by 徐启 on 2019/5/8.
 */

public class ParseForecastJSON {
    private String date; //日期
    private String locationName;
    private String high;//当天最高温度
    private String low;  //当天最低温度
    private String textDay; //白天天气现象文字
    private String textNight;//晚间天气现象文字


    int m = 0;

    List<Forecast> forecastList = new ArrayList<>();

    public List<Forecast> getForecastList(){
        return forecastList;
    }
    public  void getForecastResult(String response) {

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("results");

            //   for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject job = (JSONObject) jsonArray.get(0);
            JSONObject locationObject = job.getJSONObject("location");
            locationName = locationObject.getString("name");

            JSONArray dailyArray = job.getJSONArray("daily");
            for (int j = 0; j < dailyArray.length(); j++) {
                Forecast forecast = new Forecast();
                JSONObject object = (JSONObject) dailyArray.get(j);
                date = object.getString("date");
                high = object.getString("high");
                low = object.getString("low");
                textDay = object.getString("text_day");
                textNight = object.getString("text_night");
                //保存数据
                forecast.setLocationName(locationName);
                forecast.setDate(date);
                forecast.setHigh(high);
                forecast.setLow(low);
                forecast.setTextDay(textDay);
                forecast.setTextNight(textNight);

                forecastList.add(forecast);

             //   System.out.println("++++++++++++++++++++++++++++++++++++++++++" + hourlyList.get(j).getDate());

            }

            if (forecastSQLiteAction.getForecastFromSQLite(k).size()!=0) {
                Log.i("测试数据库中有","不存入");

            } else {
                //遍历存入数据库
                for (int i = 0; i < 5; i++) {

                    forecastSQLiteAction.saveForecast(forecastList.get(i));
        //            System.out.println("遍历存入的forecastList---------------------------" + hourlyList.get(i).getDate());
                }
            }

            }catch(JSONException e){
                e.printStackTrace();
            }

    }


}
