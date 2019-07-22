package cn.eoe.app.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 创建天气数据库的帮助类
 * Created by 徐启 on 2019/5/6.
 */

public class WeatherSQLiteOpenHelper extends SQLiteOpenHelper{

    //天气实况的表
    private final String CREATE_ACTUAL_WEATHER = "create table ActualWeather(" +
            "id Integer primary key autoincrement,"+
            "locationName text," +
            "locationId text," +
            "nowText text," +
            "nowTemperature text," +
            "nowFeelsLike text," +
            "nowPressure text," +
            "nowHumidity text," +
            "nowVisibility text," +
            "nowWindDirection text," +
            "nowCode,"+
            "nowWindScale text)";

    //空气质量的表
    private final String CREATE_AIR_QUALITY = "create table AirQuality(" +
            "id Integer primary key autoincrement,"+
            "aqi text," +
            "quality text," +
            " path text,"+
            "so2 text,"+
            "no2 text,"+
            "co text,"+
            "o3 text,"+
            "pm10 text,"+
            "pm25 text,"+
            "locationName text )";

    //生活指数的表
    private final String CREATE_LIVING_INDEX = "create table LivingIndex("+
            "id Integer primary key autoincrement,"+
            "locationName ,"+
            "acBrief,"+
            "carWashingBrief,"+
            "dressingBrief,"+
            "fishingBrief,"+
            "shoppingBrief,"+
            "sportBrief,"+
            "beerBrief,"+
            "umbrellaBrief,"+
            "allergyBrief,"+
            "uvBrief,"+
            "fluBrief,"+
            "morningSportBrief)";
    //5天天气预测的表
    private  final String CREATE_FORECAST = "create table Forecast("+
            "id Integer primary key autoincrement,"+
            "locationName,"+
            "date,"+
            "high,"+
            "low,"+
            "textDay,"+
            "textNight)";
    //我的城市数据的表
    private final  String CREATE_MY_CITIES = "create table MyCities("+
            "id Integer primary key autoincrement,"+
            "cityLocationName,"+
            "cityTemperature,"+
            "cityQuality,"+
            "cityHumidity,"+
            "cityWindDirection,"+
            "cityWindScale,"+
            "cityHigh,"+
            "cityLow,"+
            "cityText,"+
            "cityCode)";

    //24小时天气预报的表
    private final  String CREATE_HOURLY = "create table Hourly("+
            "id Integer primary key autoincrement,"+
            "hourlyLocationName,"+
            "hourlyTemperature,"+
            "hourlyCode,"+
            "hourlyTime)";




    public WeatherSQLiteOpenHelper(Context context, String DbName,
                                    SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DbName, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ACTUAL_WEATHER);
        db.execSQL(CREATE_AIR_QUALITY);
        db.execSQL(CREATE_LIVING_INDEX);
        db.execSQL(CREATE_FORECAST);
        db.execSQL(CREATE_MY_CITIES);
        db.execSQL(CREATE_HOURLY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
