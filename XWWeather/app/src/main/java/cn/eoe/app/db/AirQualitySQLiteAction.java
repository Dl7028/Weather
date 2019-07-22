package cn.eoe.app.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import cn.eoe.app.entity.AirQuality;

/**
 * 空气质量的表的操作类，增加和取出
 * Created by 徐启 on 2019/5/7.
 */

public class AirQualitySQLiteAction {

    private final String TABLE_AIR_QUALITY = "AirQuality";

    private final int VERSION = 2;

    public static SQLiteDatabase airDatabase;

    private static AirQualitySQLiteAction airQualitySQLiteAction;

    //构造器
    private AirQualitySQLiteAction(Context context) {
        WeatherSQLiteOpenHelper weatherSQLiteOpenHelper = new WeatherSQLiteOpenHelper(context, TABLE_AIR_QUALITY, null, VERSION);
        airDatabase = weatherSQLiteOpenHelper.getWritableDatabase();  //创建数据库
    }


    //获取实例
    public static AirQualitySQLiteAction getInstance(Context context) {
        if (airQualitySQLiteAction == null) {
            airQualitySQLiteAction = new AirQualitySQLiteAction(context);
        }
        return airQualitySQLiteAction;
    }

    //保存数据到空气质量的表中
    public boolean saveAirQuality(AirQuality airQuality) {
        if (airQuality != null) {
            System.out.println(airQuality+"-----------------airQuality不为空");
            System.out.println(airQuality.getQuality()+"saveAirQuality中的空气质量");
            ContentValues values = new ContentValues();
            values.put("locationName",airQuality.getLocationName());
            values.put("aqi", airQuality.getAqi());
            values.put("quality", airQuality.getQuality());
            values.put("path",airQuality.getPath());

            values.put("pm25",airQuality.getPm25());
            System.out.println("数据库插入操作的pm2.5"+airQuality.getPm25());
            values.put("pm10",airQuality.getPm10());
            values.put("so2",airQuality.getSo2());
            values.put("no2",airQuality.getNo2());
            values.put("co",airQuality.getCo());
            values.put("o3",airQuality.getO3());
            airDatabase.insert(TABLE_AIR_QUALITY, null, values);
            values.clear();
            return true;
        }
        return false;
    }

    //数据库中取出空气质量的数据
    public AirQuality getAirQualityFromSQLite(String locationName) {
        AirQuality airQuality = new AirQuality();
        Cursor cursor = airDatabase.query(TABLE_AIR_QUALITY, null, "locationName=?", new String[]{locationName}, null, null, null);    //数据库中的查找操作，一行一行的查找
        //数据库中有
        if (cursor.getCount() > 0) {
            Log.d("测试", "数据库中有");
            if (cursor.moveToFirst()) {
                do {
                    airQuality.setLocationName(cursor.getString(cursor.getColumnIndex("locationName")));
                    airQuality.setAqi(cursor.getString(cursor.getColumnIndex("aqi")));
                    airQuality.setQuality(cursor.getString(cursor.getColumnIndex("quality")));
                    airQuality.setPath(cursor.getString(cursor.getColumnIndex("path")));

                    airQuality.setPm25(cursor.getString(cursor.getColumnIndex("pm25")));
                    airQuality.setPm10(cursor.getString(cursor.getColumnIndex("pm10")));
                    airQuality.setSo2(cursor.getString(cursor.getColumnIndex("so2")));
                    airQuality.setNo2(cursor.getString(cursor.getColumnIndex("no2")));
                    airQuality.setCo(cursor.getString(cursor.getColumnIndex("co")));
                    airQuality.setO3(cursor.getString(cursor.getColumnIndex("o3")));

                } while (cursor.moveToNext());
            }
            cursor.close();
        }else {
            Log.d("测试", "数据库中没有");
            cursor.close();
        }
        return airQuality;
    }
}
