package cn.eoe.app.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import cn.eoe.app.entity.MyCities;


/**
 * 我的城市的表的操作类
 * 增加和取出
 * Created by 徐启 on 2019/5/9.
 */

public class MyCitiesSQLiteAction {

    private final String TABLE_MY_CITIES = "MyCities";

    private final int VERSION = 1;

    public static SQLiteDatabase myDatabase;

    private static MyCitiesSQLiteAction myCitiesSQLiteAction;


    //构造器
    private MyCitiesSQLiteAction(Context context) {
        WeatherSQLiteOpenHelper weatherSQLiteOpenHelper = new WeatherSQLiteOpenHelper(context, TABLE_MY_CITIES, null, VERSION);
        myDatabase = weatherSQLiteOpenHelper.getWritableDatabase();  //创建数据库
    }


    //获取实例
    public static MyCitiesSQLiteAction getInstance(Context context) {
        if (myCitiesSQLiteAction == null) {
            myCitiesSQLiteAction = new MyCitiesSQLiteAction(context);
        }
        return myCitiesSQLiteAction;
    }

    //保存数据到我城市表中
    public boolean saveMyCities(MyCities myCities) {
        if (myCities != null) {
            ContentValues values = new ContentValues();
            values.put("cityLocationName", myCities.getCityLocationName());
            values.put("cityTemperature", myCities.getCityTemperature());
            values.put("cityQuality", myCities.getCityQuality());
            values.put("cityHumidity", myCities.getCityHumidity());
            values.put("cityWindDirection", myCities.getCityWindDirection());
            values.put("cityWindScale", myCities.getCityWindScale());
            values.put("cityHigh", myCities.getCityHigh());
            values.put("cityLow", myCities.getCityLow());
            values.put("cityText",myCities.getCityText());
            values.put("cityCode",myCities.getCityCode());
            myDatabase.insert(TABLE_MY_CITIES, null, values);
            values.clear();
            return true;
        }
        return false;
    }



    //数据库中取出我的城市需要的数据
    public ArrayList<MyCities> getMyCitiesFromSQLite() {

        ArrayList<MyCities> arrayList = null;
        arrayList = new ArrayList<>();

        Cursor cursor = myDatabase.query(TABLE_MY_CITIES, null, null,null, null, null, null);    //数据库中的查找操作，一行一行的查找
        //数据库中有
        if (cursor.getCount() > 0) {
            Log.d("测试", "数据库中有");
            if (cursor.moveToFirst()) {
                do {
                    MyCities myCities = new MyCities();
                    myCities.setCityHigh(cursor.getString(cursor.getColumnIndex("cityHigh")));
                    myCities.setCityHumidity(cursor.getString(cursor.getColumnIndex("cityHumidity")));
                    myCities.setCityLocationName(cursor.getString(cursor.getColumnIndex("cityLocationName")));
                    myCities.setCityLow(cursor.getString(cursor.getColumnIndex("cityLow")));
                    myCities.setCityQuality(cursor.getString(cursor.getColumnIndex("cityQuality")));
                    myCities.setCityTemperature(cursor.getString(cursor.getColumnIndex("cityTemperature")));
                    myCities.setCityWindDirection(cursor.getString(cursor.getColumnIndex("cityWindDirection")));
                    myCities.setCityWindScale(cursor.getString(cursor.getColumnIndex("cityWindScale")));
                    myCities.setCityCode(cursor.getString(cursor.getColumnIndex("cityCode")));
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
    public   boolean querySQLite(String k) {
        Cursor cursor = myDatabase.query(TABLE_MY_CITIES, null, "cityLocationName=?", new String[]{k}, null, null, null);
        if (cursor.getCount() > 0) {
            return false;
        }
        return true;
    }
}
