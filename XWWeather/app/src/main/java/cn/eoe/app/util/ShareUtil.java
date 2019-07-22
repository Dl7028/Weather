package cn.eoe.app.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.widget.ScrollView;

import java.io.File;
import java.io.FileOutputStream;

/**
 * 截图分享功能：截屏->保存图片->调用系统分享
 * Created by 徐启 on 2019/5/13.
 */

public class ShareUtil {

    //截屏
    private static Bitmap shotScrollView(ScrollView scrollView) {
        int h = 0;
        Bitmap bitmap = null;
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();    //获取整体高度
//            scrollView.getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"));
        }

        //创建同等高度的bitmap
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h, Bitmap.Config.ARGB_8888);
        //用canvas绘制ScrollView内容到bitmap实现截屏
        Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        return bitmap;
    }

  // 保存Bitmap为png图片的方法，需要开启手机读写内存权限
    private static File saveBitmap(Bitmap bitmap) {

        //保存的路径
        File file = new File(Environment.getExternalStorageDirectory(), "share.jpg");
        if (file.exists()) {                           //如果存在先删除
            file.delete();
        }
        try {
            //把bitmap写入存储卡为jpg图片
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);     //compress()按指定的图片格式以及画质，将图片转换为输出流。
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return file;
    }

    /**
     * 分享天气
     * @param context
     * @param scrollView
     */
    public static void shareWeather(Context context, ScrollView scrollView) {

        //分享前先截屏
        Bitmap bitmap = shotScrollView(scrollView);
        //截屏保存到存储卡
        File file = saveBitmap(bitmap);

        // 分享图片
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);//设置分享行为
        shareIntent.setType("image/*");  //设置分享内容的类型
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        //创建分享的Dialog
        shareIntent = Intent.createChooser(shareIntent, "分享天气");
        //开始分享
        context.startActivity(shareIntent);
    }
}
