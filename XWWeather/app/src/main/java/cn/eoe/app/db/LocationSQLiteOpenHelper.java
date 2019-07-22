package cn.eoe.app.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 创建数据库的帮助类
 * Created by 徐启 on 2019/5/5.
 */

/*public class LocationSQLiteOpenHelper extends SQLiteOpenHelper{


    private final String CREATE_PROVINCE = "create table Province ("                          //省份的表
            + "provinceName text," + "provinceId text )";

    private final String CREATE_CITY = "create table City("                                    //城市的表
            + "cityName text," + "cityId text," + "provinceId text)";

    private final String CREATE_COUNTY = "create table County("                               //县的表
            + "countyName text," + "countyId text," + "cityId text)";


    public LocationSQLiteOpenHelper(Context context, String DbName,
                                    SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DbName, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PROVINCE);
        db.execSQL(CREATE_CITY);
        db.execSQL(CREATE_COUNTY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}*/

