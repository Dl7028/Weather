package cn.eoe.app.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weather.R;

import cn.eoe.app.entity.AirQuality;

import static cn.eoe.app.ui.CitySearchActivity.airQualitySQLiteAction;
import static cn.eoe.app.ui.WeatherActivity.k;

/**
 * 空气质量数据的dialog
 * Created by 徐启 on 2019/5/15.
 */

public class AirQualityDialog extends Dialog {
    private  TextView pm10Tv;
    private  TextView so2Tv;
    private  TextView no2Tv;
    private  TextView pm25Tv;
    private  TextView o3Tv;
    private  TextView coTv;
    private  TextView qualityTv;
    private  TextView aqiTv;
    private AirQuality airQuality;


    public AirQualityDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.air_quality_dialog);
        ImageView imageView = (ImageView)findViewById(R.id.image_delete);
       airQuality = airQualitySQLiteAction.getAirQualityFromSQLite(k);
        System.out.println("对话框的地区-----------------"+airQuality.getLocationName());
        System.out.println("对话框的pm2.5-----------------"+airQuality.getPm25());
        initialView();
        updateView();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AirQualityDialog.this.dismiss();                       //取消对话框
            }
        });
        setCanceledOnTouchOutside(false);

    }
    public void initialView(){
        pm10Tv = (TextView)findViewById(R.id.pm10_tv);
        so2Tv = (TextView)findViewById(R.id.so2_tv);
        no2Tv = (TextView)findViewById(R.id.no2_tv);
        pm25Tv = (TextView)findViewById(R.id.pm2_5_tv);
        o3Tv = (TextView)findViewById(R.id.o3_tv);
        coTv = (TextView)findViewById(R.id.co_tv);
        qualityTv = (TextView)findViewById(R.id.dialog_quality_tv);
        aqiTv = (TextView)findViewById(R.id.dialog_aqi_tv);
    }
    public void updateView(){
        pm10Tv.setText(airQuality.getPm10());
        so2Tv.setText(airQuality.getSo2());
        no2Tv.setText(airQuality.getNo2());
        pm25Tv.setText(airQuality.getPm25());
        o3Tv.setText(airQuality.getO3());
        coTv.setText(airQuality.getCo());
        qualityTv.setText(airQuality.getQuality());
        aqiTv.setText(airQuality.getAqi());
    }
    }

