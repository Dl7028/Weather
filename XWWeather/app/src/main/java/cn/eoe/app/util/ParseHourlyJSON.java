package cn.eoe.app.util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.eoe.app.entity.Forecast;
import cn.eoe.app.entity.Hourly;

import static cn.eoe.app.ui.CitySearchActivity.forecastSQLiteAction;
import static cn.eoe.app.ui.CitySearchActivity.hourlySQLiteAction;
import static cn.eoe.app.ui.WeatherActivity.k;

/**
 * 解析24小时预报
 * Created by 徐启 on 2019/5/15.
 */

public class ParseHourlyJSON {
    private String mhourlyTemperature;
    private String mhourlyCode;
    private String mhourlyTime;
    private String mhourlyLocationName;

    int m = 0;

    List<Hourly> hourlyList = new ArrayList<>();

    public List<Hourly> getHourlyList(){
        return hourlyList;
    }
    public  void getHourlyResult(String response) {

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("results");

            //   for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject job = (JSONObject) jsonArray.get(0);
            JSONObject locationObject = job.getJSONObject("location");
            mhourlyLocationName = locationObject.getString("name");

            JSONArray hourlyArray = job.getJSONArray("hourly");
            for (int j = 0; j < hourlyArray.length(); j++) {
                Hourly hourly = new Hourly();
                JSONObject object = (JSONObject) hourlyArray.get(j);
                mhourlyTime = object.getString("time");
                mhourlyCode = object.getString("code");
                mhourlyTemperature = object.getString("temperature");
                String strTime = mhourlyTime.substring(11,13);
                //保存数据
                hourly.setMhourlyTime(strTime);
                hourly.setMhourlyTemperature(mhourlyTemperature);
                hourly.setMhourlyLocationName(mhourlyLocationName);
                hourly.setMhourlyCode(mhourlyCode);
                hourlyList.add(hourly);

                //   System.out.println("++++++++++++++++++++++++++++++++++++++++++" + hourlyList.get(j).getDate());

            }
            if (hourlySQLiteAction.getHourlyFromSQLite(k).size()!=0) {
                Log.i("测试数据库中有","不存入");
                Log.i("if为真,","保存导数据中");
            } else {
                System.out.println("进入else-------------------------------------------------------------");
                //遍历存入数据库
                for (int i = 0; i < hourlyList.size(); i++) {
                  Log.i("hourlyList"+hourlyList.size(),"---------保存导数据中");
                    hourlySQLiteAction.saveHourly(hourlyList.get(i));
                }
            }

        }catch(JSONException e){
            e.printStackTrace();
        }

    }
}
