package cn.eoe.app.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import cn.eoe.app.entity.Forecast;

/**
 * 5日预测的表的操作类
 * 增加和取出
 * Created by 徐启 on 2019/5/8.
 */

public class ForecastSQLiteAction {
    private final String TABLE_FORECAST = "Forecast";

    private final int VERSION = 1;

    public static SQLiteDatabase forDatabase;

    public static ForecastSQLiteAction forecastSQLiteAction;

    //构造器
    private ForecastSQLiteAction(Context context) {
        WeatherSQLiteOpenHelper weatherSQLiteOpenHelper = new WeatherSQLiteOpenHelper(context, TABLE_FORECAST, null, VERSION);
        forDatabase = weatherSQLiteOpenHelper.getWritableDatabase();  //创建数据库
    }


    //获取实例
    public static ForecastSQLiteAction getInstance(Context context) {
        if (forecastSQLiteAction == null) {
            forecastSQLiteAction = new ForecastSQLiteAction(context);
        }
        return forecastSQLiteAction;
    }

    //保存数据到5日预测的表中
    public boolean saveForecast(Forecast forecast) {
        if (forecast != null) {
            ContentValues values = new ContentValues();
            values.put("date",forecast.getDate());
            values.put("locationName",forecast.getLocationName());
            values.put("high",forecast.getHigh());
            values.put("low",forecast.getLow());
            values.put("textDay",forecast.getTextDay());
            values.put("textNight",forecast.getTextNight());
            forDatabase.insert(TABLE_FORECAST, null, values);
            values.clear();
            return true;
        }
        return false;
    }
    //从数据库中取出5日预测的数据
    public List<Forecast> getForecastFromSQLite(String locationName){

        List<Forecast> forecastList = new ArrayList<>();
        Cursor cursor = forDatabase.query(TABLE_FORECAST, null, "locationName=?", new String[]{locationName}, null, null, null);    //数据库中的查找操作，一行一行的查找
        //数据库中有
        if (cursor.getCount() > 0) {
//            Log.d("测试", "数据库中有");
            if (cursor.moveToFirst()) {
                do {
                    Forecast forecast = new Forecast();

                    forecast.setLocationName(cursor.getString(cursor.getColumnIndex("locationName")));
                    forecast.setTextNight(cursor.getString(cursor.getColumnIndex("textNight")));
                    forecast.setTextDay(cursor.getString(cursor.getColumnIndex("textDay")));
                    forecast.setLow(cursor.getString(cursor.getColumnIndex("low")));
                    forecast.setHigh(cursor.getString(cursor.getColumnIndex("high")));
                    forecast.setDate(cursor.getString(cursor.getColumnIndex("date")));

                    forecastList.add(forecast);
//                    System.out.println("do里面的forecast-------------------------------+++"+forecast.getDate());

                } while (cursor.moveToNext());
            }


            cursor.close();
        }else {
//            Log.d("测试", "数据库中没有");
            cursor.close();
        }
        return forecastList;
    }


}
