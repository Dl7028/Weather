package cn.eoe.app.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.weather.R;

import cn.eoe.app.db.MyCitiesSQLiteAction;
import cn.eoe.app.entity.ActualWeather;
import cn.eoe.app.entity.AirQuality;
import cn.eoe.app.entity.MyCities;
import cn.eoe.app.ui.CitySearchActivity;
import cn.eoe.app.util.ImageUtil;

/**
 * 桌面小控件，用widget
 */
public class NewWeatherWidget extends AppWidgetProvider {

    private ActualWeather actualWeather;
    private AirQuality airQuality;
    private MyCities myCities;
    public static final String ACTION_APPWIDGET_UPDATE  = "android.appwidget.action.APPWIDGET_UPDATE";
    private MyCitiesSQLiteAction  citiesSQLiteAction ;



    @Override
    public void onUpdate(final Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;
        System.out.println("长度----------------------"+N);
        for (int i=0; i<N; i++) {
            int appWidgetId = appWidgetIds[i];

            super.onUpdate(context, appWidgetManager, appWidgetIds);
            System.out.println("                                   ---------第一次调用onUpdate");
            updateMyWidget(context, appWidgetManager,appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    //更新小主件上的内容
    private void updateMyWidget(Context context,AppWidgetManager appWidgetManager,int appWidgetId){

        citiesSQLiteAction = MyCitiesSQLiteAction.getInstance(context);
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.new_weather_widget);
      /*  if(myCity!=null) {

            if (wthSQLiteAction.getActualWeatherFromSQLite(myCity).getLocationName() != "") {
                actualWeather = wthSQLiteAction.getActualWeatherFromSQLite(myCity);
                airQuality = airQualitySQLiteAction.getAirQualityFromSQLite(myCity);
                rv.setTextViewText(R.id.widget_city_tv, actualWeather.getLocationName());
                rv.setTextViewText(R.id.widget_quality_tv, airQuality.getQuality());
                rv.setTextViewText(R.id.widget_temperature_tv, actualWeather.getNowTemperature() + "℃");

                int i = ImageUtil.getImageID("text" + actualWeather.getNowCode());
                //动态换图
                rv.setImageViewResource(R.id.widget_text_iv, i);

            }
        }else {*/
            if(citiesSQLiteAction.getMyCitiesFromSQLite().size()>0) {
                myCities = citiesSQLiteAction.getMyCitiesFromSQLite().get(citiesSQLiteAction.getMyCitiesFromSQLite().size()-1);
                rv.setTextViewText(R.id.widget_city_tv, myCities.getCityLocationName());
                rv.setTextViewText(R.id.widget_quality_tv, myCities.getCityQuality());
                rv.setTextViewText(R.id.widget_temperature_tv, myCities.getCityTemperature() + "℃");
                int i = ImageUtil.getImageID("text" + myCities.getCityCode());
//                动态换图
                rv.setImageViewResource(R.id.widget_text_iv, i);


                //调用组件管理器修改所有小组件，让刚才的更改生效
                ComponentName componentName = new ComponentName(context, NewWeatherWidget.class);
                //添加点击事件，点击小部件时会启动app
                Intent intent = new Intent(context, CitySearchActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
                rv.setOnClickPendingIntent(R.id.Re_widget, pendingIntent);

                appWidgetManager.updateAppWidget(componentName, rv); ////用appwidgetmanager回调update方法
            }

        }
//    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }


    //收到广播后就更新小组件
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if(intent.getAction().equals(ACTION_APPWIDGET_UPDATE)){
            Log.i("桌面小组件","接收ACTION_APPWIDGET_UPDATE广播");
            Intent updateIntent = new Intent();
            updateIntent.setAction(ACTION_APPWIDGET_UPDATE);




        }
    }
}

