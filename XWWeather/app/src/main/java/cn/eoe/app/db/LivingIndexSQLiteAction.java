package cn.eoe.app.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import cn.eoe.app.entity.LivingIndex;

/**
 * LivingIndex生活指数的表的操作类
 * 增加和取出
 * Created by 徐启 on 2019/5/7.
 */

public class LivingIndexSQLiteAction {
    private final String TABLE_LIVING_INDEX = "LivingIndex";

    private final int VERSION = 1;

    public static SQLiteDatabase livDatabase;

    public static LivingIndexSQLiteAction livingIndexSQLiteAction;

    //构造器
    private LivingIndexSQLiteAction(Context context) {
        WeatherSQLiteOpenHelper weatherSQLiteOpenHelper = new WeatherSQLiteOpenHelper(context, TABLE_LIVING_INDEX, null, VERSION);
        livDatabase = weatherSQLiteOpenHelper.getWritableDatabase();  //创建数据库
    }


    //获取实例
    public static LivingIndexSQLiteAction getInstance(Context context) {
        if (livingIndexSQLiteAction == null) {
            livingIndexSQLiteAction = new LivingIndexSQLiteAction(context);
        }
        return livingIndexSQLiteAction;
    }

    //存放数据到生活指数的表
    public boolean saveLivingIndex(LivingIndex livingIndex) {
        if (livingIndex != null) {
            ContentValues values = new ContentValues();
            values.put("locationName",livingIndex.getLocationName());
            values.put("acBrief",livingIndex.getAcBrief());
            values.put("carWashingBrief",livingIndex.getCarWashingBrief());
            values.put("dressingBrief",livingIndex.getDressingBrief());
            values.put("fishingBrief",livingIndex.getFishingBrief());
            values.put("shoppingBrief",livingIndex.getShoppingBrief());
            values.put("sportBrief",livingIndex.getSportBrief());
            values.put("beerBrief",livingIndex.getBeerBrief());
            values.put("umbrellaBrief",livingIndex.getUmbrellaBrief());
            values.put("allergyBrief",livingIndex.getAllergyBrief());
            values.put("uvBrief",livingIndex.getUvBrief());
            values.put("fluBrief",livingIndex.getFluBrief());
            values.put("morningSportBrief",livingIndex.getMorningSportBrief());
            livDatabase.insert(TABLE_LIVING_INDEX,null,values);
            values.clear();
            return true;
        }
        return false;
    }
    //从数据库中取出生活指数数据
    public LivingIndex getLivingIndexFromSQLite(String locationName){
        LivingIndex livingIndex = new LivingIndex();
        Cursor cursor = livDatabase.query(TABLE_LIVING_INDEX, null, "locationName=?", new String[]{locationName}, null, null, null);    //数据库中的查找操作，一行一行的查找
        //数据库中有
        if(cursor.getCount()>0){
            Log.d("测试", "数据库中有");
            if (cursor.moveToFirst()) {
                do{
                    livingIndex.setLocationName(cursor.getString(cursor.getColumnIndex("locationName")));
                    livingIndex.setAcBrief(cursor.getString(cursor.getColumnIndex("acBrief")));
                    livingIndex.setCarWashingBrief(cursor.getString(cursor.getColumnIndex("carWashingBrief")));
                    livingIndex.setDressingBrief(cursor.getString(cursor.getColumnIndex("dressingBrief")));
                    livingIndex.setFishingBrief(cursor.getString(cursor.getColumnIndex("fishingBrief")));
                    livingIndex.setShoppingBrief(cursor.getString(cursor.getColumnIndex("shoppingBrief")));
                    livingIndex.setSportBrief(cursor.getString(cursor.getColumnIndex("sportBrief")));
                    livingIndex.setBeerBrief(cursor.getString(cursor.getColumnIndex("beerBrief")));
                    livingIndex.setUmbrellaBrief(cursor.getString(cursor.getColumnIndex("umbrellaBrief")));
                    livingIndex.setAllergyBrief(cursor.getString(cursor.getColumnIndex("allergyBrief")));
                    livingIndex.setUvBrief(cursor.getString(cursor.getColumnIndex("uvBrief")));
                    livingIndex.setFluBrief(cursor.getString(cursor.getColumnIndex("fluBrief")));
                    livingIndex.setMorningSportBrief(cursor.getString(cursor.getColumnIndex("morningSportBrief")));
                }while (cursor.moveToNext());
            }
            cursor.close();
        }else {
            cursor.close();
        }
        return livingIndex;
    }

}
