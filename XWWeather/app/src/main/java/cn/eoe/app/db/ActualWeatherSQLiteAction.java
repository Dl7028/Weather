package cn.eoe.app.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import cn.eoe.app.entity.ActualWeather;

/**
 * 天气实况的表的的操作类,增加和取出
 * Created by 徐启 on 2019/5/6.
 */

public class ActualWeatherSQLiteAction {
    private final String TABLE_ACTUAL_WEATHER = "ActualWeather";

    private final int VERSION = 2;

    public static SQLiteDatabase actDatabase;

    public static ActualWeatherSQLiteAction actualWeatherSQLiteAction;


    private ActualWeatherSQLiteAction(Context context) {
        WeatherSQLiteOpenHelper  weatherSQLiteOpenHelper = new WeatherSQLiteOpenHelper(context, TABLE_ACTUAL_WEATHER, null, VERSION);
        actDatabase = weatherSQLiteOpenHelper.getWritableDatabase();  //创建数据库
    }



    //获取实例
    public static ActualWeatherSQLiteAction getInstance(Context context) {
        if (actualWeatherSQLiteAction == null) {
            actualWeatherSQLiteAction = new ActualWeatherSQLiteAction(context);
        }
        return actualWeatherSQLiteAction;
    }

    //保存数据到空气实况表中
    public boolean saveActualWeather(ActualWeather actualWeather) {
        if (actualWeather != null) {
            ContentValues values = new ContentValues();
                values.put("locationName", actualWeather.getLocationName());
                values.put("locationId", actualWeather.getLocationId());
                values.put("nowText", actualWeather.getNowText());
                values.put("nowTemperature", actualWeather.getNowTemperature());
                values.put("nowFeelsLike", actualWeather.getNowFeelsLike());
               values.put("nowPressure", actualWeather.getNowPressure());
                values.put("nowHumidity", actualWeather.getNowHumidity());
               values.put("nowVisibility", actualWeather.getNowVisibility());
               values.put("nowWindDirection", actualWeather.getNowWindDirection());
               values.put("nowWindScale", actualWeather.getNowWindScale());
               values.put("nowCode", actualWeather.getNowCode());
                actDatabase.insert(TABLE_ACTUAL_WEATHER, null, values);
                values.clear();
            return true;
        }
        return false;
    }
    //在数据库中取出天气实况数据
    public ActualWeather getActualWeatherFromSQLite(String locationName) {
        ActualWeather actualWeather = new ActualWeather();
        Cursor cursor = actDatabase.query(TABLE_ACTUAL_WEATHER, null, "locationName=?", new String[]{locationName}, null, null, null);    //数据库中的查找操作，一行一行的查找
        //数据库中有
        if (cursor.getCount() > 0) {
            Log.d("测试", "数据库中有");
            if (cursor.moveToFirst()) {
                do {

                    actualWeather.setLocationName(cursor.getString(cursor.getColumnIndex("locationName")));
                    actualWeather.setLocationId(cursor.getString(cursor.getColumnIndex("locationId")));
                    actualWeather.setNowText(cursor.getString(cursor.getColumnIndex("nowText")));
                    actualWeather.setNowTemperature(cursor.getString(cursor.getColumnIndex("nowTemperature")));
                    actualWeather.setNowFeelsLike(cursor.getString(cursor.getColumnIndex("nowFeelsLike")));
                    actualWeather.setNowPressure(cursor.getString(cursor.getColumnIndex("nowPressure")));
                    actualWeather.setNowHumidity(cursor.getString(cursor.getColumnIndex("nowHumidity")));
                    actualWeather.setNowVisibility(cursor.getString(cursor.getColumnIndex("nowVisibility")));
                    actualWeather.setNowWindDirection(cursor.getString(cursor.getColumnIndex("nowWindDirection")));
                    actualWeather.setNowWindScale(cursor.getString(cursor.getColumnIndex("nowWindScale")));
                    actualWeather.setNowCode(cursor.getString(cursor.getColumnIndex("nowCode")));
                } while (cursor.moveToNext());
            }
            cursor.close();
        } else {
            Log.d("测试", "数据库中没有");
            cursor.close();
        }
        return actualWeather;

    }



    }

