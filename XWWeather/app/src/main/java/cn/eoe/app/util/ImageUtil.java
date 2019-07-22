package cn.eoe.app.util;

import com.example.weather.R;

import java.lang.reflect.Field;

/**
 * 动态给天气配图的工具类
 * Created by 徐启 on 2019/5/15.
 */

public class ImageUtil {

    public static int getImageID(String name){

        int id = -1;
        try {
            Field field=R.mipmap.class.getDeclaredField(name);
            String str=field.get(null).toString();
            id=Integer.parseInt(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }



}
