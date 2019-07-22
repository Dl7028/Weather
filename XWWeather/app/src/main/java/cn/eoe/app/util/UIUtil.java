package cn.eoe.app.util;

import android.content.Context;

/**
 * ui工具类
 * Created by 徐启 on 2019/5/11.
 */

public class UIUtil {


        //dip转换px
        public static int dip2px(Context context, int dip) {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (dip * scale + 0.5f);
        }

        // pxz转换dip
        public static int px2dip(Context context,int px) {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (px / scale + 0.5f);
        }

}
