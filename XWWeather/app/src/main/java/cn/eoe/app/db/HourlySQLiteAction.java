package cn.eoe.app.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import cn.eoe.app.entity.Hourly;

/**
 * 24小时天气预报的表的操作类
 * 增加和取出
 * Created by 徐启 on 2019/5/15.
 */

public class HourlySQLiteAction {
    private final String TABLE_HOURLY = "Hourly";

    private final int VERSION = 1;

    public static SQLiteDatabase hourlyDatabase;

    private static HourlySQLiteAction hourlySQLiteAction;


    //构造器
    private HourlySQLiteAction(Context context) {
        WeatherSQLiteOpenHelper weatherSQLiteOpenHelper = new WeatherSQLiteOpenHelper(context, TABLE_HOURLY, null, VERSION);
        hourlyDatabase = weatherSQLiteOpenHelper.getWritableDatabase();  //创建数据库
    }


    //获取实例
    public static HourlySQLiteAction getInstance(Context context) {
        if (hourlySQLiteAction == null) {
            hourlySQLiteAction = new HourlySQLiteAction(context);
        }
        return hourlySQLiteAction;
    }

    //保存数据到我城市表中
    public boolean saveHourly(Hourly hourly) {
        if (hourly != null) {

            ContentValues values = new ContentValues();
            values.put("hourlyLocationName",hourly.getMhourlyLocationName());
            values.put("hourlyCode", hourly.getMhourlyCode());
            values.put("hourlyTemperature", hourly.getMhourlyTemperature());
            values.put("hourlyTime",hourly.getMhourlyTime());

            hourlyDatabase.insert(TABLE_HOURLY, null, values);
            values.clear();
            return true;
        }
        return false;
    }



    //数据库中取出我的城市需要的数据
    public ArrayList<Hourly> getHourlyFromSQLite(String cityName) {

        ArrayList<Hourly> arrayList = null;
        arrayList = new ArrayList<>();

        Cursor cursor = hourlyDatabase.query(TABLE_HOURLY, null,"hourlyLocationName=?", new String[]{cityName}, null, null, null);    //数据库中的查找操作，一行一行的查找
        //数据库中有
        if (cursor.getCount() > 0) {
            Log.d("测试", "数据库中有");
            if (cursor.moveToFirst()) {
                do {
                    Hourly myCities = new Hourly();
                    myCities.setMhourlyCode(cursor.getString(cursor.getColumnIndex("hourlyCode")));
                    myCities.setMhourlyLocationName(cursor.getString(cursor.getColumnIndex("hourlyLocationName")));
                    myCities.setMhourlyTemperature(cursor.getString(cursor.getColumnIndex("hourlyTemperature")));
                    myCities.setMhourlyTime(cursor.getString(cursor.getColumnIndex("hourlyTime")));

                    arrayList.add(myCities);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }else {
            Log.d("测试", "数据库中没有");
            cursor.close();
        }
        return arrayList;
    }

    //查找数据库是否有这个数据
    public   boolean queryHourlySQLite(String k) {
        Cursor cursor = hourlyDatabase.query(TABLE_HOURLY, null, "hourlyLocationName=?", new String[]{k}, null, null, null);
        if (cursor.getCount() > 0) {
            return false;
        }
        return true;
    }
}


