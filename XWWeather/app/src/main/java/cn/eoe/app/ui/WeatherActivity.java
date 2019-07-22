package cn.eoe.app.ui;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.weather.R;

import java.util.ArrayList;
import java.util.List;

import cn.eoe.app.adapter.HourlyAdapter;
import cn.eoe.app.entity.ActualWeather;
import cn.eoe.app.entity.AirQuality;
import cn.eoe.app.entity.Forecast;
import cn.eoe.app.entity.Hourly;
import cn.eoe.app.entity.LivingIndex;
import cn.eoe.app.entity.MyCities;
import cn.eoe.app.https.HttpsSendsRequests;
import cn.eoe.app.https.IHttpCallBackListener;
import cn.eoe.app.util.ImageUtil;
import cn.eoe.app.util.ParseActualWeatherJSON;
import cn.eoe.app.util.ParseAirQualityJSON;
import cn.eoe.app.util.ParseForecastJSON;
import cn.eoe.app.util.ParseHourlyJSON;
import cn.eoe.app.util.ParseLivingIndexJSON;
import cn.eoe.app.util.RefreshListener;
import cn.eoe.app.util.ShareUtil;
import cn.eoe.app.widget.RefreshHeadbgView;
import cn.eoe.app.widget.RefreshScrollView;

import static android.view.View.INVISIBLE;
import static cn.eoe.app.db.ActualWeatherSQLiteAction.actDatabase;
import static cn.eoe.app.db.ActualWeatherSQLiteAction.actualWeatherSQLiteAction;
import static cn.eoe.app.db.AirQualitySQLiteAction.airDatabase;
import static cn.eoe.app.db.ForecastSQLiteAction.forDatabase;
import static cn.eoe.app.db.HourlySQLiteAction.hourlyDatabase;
import static cn.eoe.app.db.LivingIndexSQLiteAction.livDatabase;
import static cn.eoe.app.db.MyCitiesSQLiteAction.myDatabase;
import static cn.eoe.app.https.HttpsSendsRequests.startRefresh;
import static cn.eoe.app.ui.CitySearchActivity.airQualitySQLiteAction;
import static cn.eoe.app.ui.CitySearchActivity.forecastSQLiteAction;
import static cn.eoe.app.ui.CitySearchActivity.hourlySQLiteAction;
import static cn.eoe.app.ui.CitySearchActivity.livingIndexSQLiteAction;
import static cn.eoe.app.ui.CitySearchActivity.myCitiesSQLiteAction;
import static cn.eoe.app.ui.CitySearchActivity.wthSQLiteAction;

/**
 * 特定城市的天气预报主界面
 */

public class WeatherActivity extends AppCompatActivity implements RefreshListener, View.OnClickListener {
    //天气实况
    private TextView mNowTemperatureText;
    private TextView mNowLocationText;
    private TextView mNowText;  //当前气象文字
    private TextView mNowFeelsLikeText;
    private TextView mNowPressureText;
    private TextView mNowHumidityText;
    private TextView mNowVisibilityText;
    private TextView mNowWindDirectionText;
    private TextView mNowWindScaleText;
    //空气质量
    private TextView mAqiText;
    private TextView mQualityText;
    //生活指数
    private TextView mAcText;
    private TextView mCarWashingText;
    private TextView mDressingText;
    private TextView mFishingText;
    private TextView mShoppingText;
    private TextView mSportText;
    private TextView mBeerText;
    private TextView mUmbrellaText;
    private TextView mAllergyText;
    private TextView mUvText;
    private TextView mFluText;
    private TextView mMorningSportText;
    private ImageView mNowTextImage;
    private ImageButton mShareImage;

    //预测布局
    private LinearLayout forecastLayout;
    //跳转到我的城市的按钮
//    private ImageButton mToMyCitiesBtn;
    private ImageButton mAddCityBtn;

    private RefreshScrollView mReScrollView;
    private RelativeLayout mHeadViewRtLayout;

    private RefreshHeadbgView mReHeadbgView;
    private TextView mTextTv;
    public static String k;
    public static boolean refresh = false;

    //网络监控对象
    public static ConnectivityManager mConnectivity;
    //主布局
    private RelativeLayout mReWeather;
  //24小时预报
   private  RecyclerView recyclerView;


    public ImageView refreshImagView;

    public ActualWeather actWeather = new ActualWeather();
    public AirQuality airQuality = new AirQuality();
    public LivingIndex livingIndex = new LivingIndex();
    public List<Hourly> hourlyList = new ArrayList<>();


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 111:                                                                           //接收信息111，完成UI更新
                    upDateView01();  //UI更新天气实况
                    addImageView(); //加载图片

                    //如果在更新，网络访问成功后停止更新
//                    finishRefresh();
//                    Toast.makeText(WeatherActivity.this,"已更新",Toast.LENGTH_SHORT).show();
                    break;
                case 112:
                    upDateView02();  //UI更新空气质量
//                    finishRefresh();
                    break;
                case 113:
                    upDateView03();  //UI更新生活指数
//                    finishRefresh();
                    break;
                case 114:
                    upDateView04();
//                    finishRefresh();                    break;
                case 115:
                    upDateView04();
//                    finishRefresh();

                    mReScrollView.stopRefresh();      //停止刷新
                    refreshImagView.setVisibility(View.GONE);
                    break;
                case 116:
                    upDateView05();  //更新24小时预测
                    finishRefresh();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        initialView();

        NetworkInfo info = mConnectivity.getActiveNetworkInfo();
        //有网络
        if (info == null || !mConnectivity.getBackgroundDataSetting()) {
            if (actualWeatherSQLiteAction.getActualWeatherFromSQLite(k).getLocationName() != "") {
                actWeather = actualWeatherSQLiteAction.getActualWeatherFromSQLite(k);
                airQuality = airQualitySQLiteAction.getAirQualityFromSQLite(k);
                livingIndex = livingIndexSQLiteAction.getLivingIndexFromSQLite(k);
                hourlyList = hourlySQLiteAction.getHourlyFromSQLite(k);
                upDateView01();
                upDateView02();
                upDateView03();
                upDateView04();
                upDateView05();
            } else {
                Toast.makeText(WeatherActivity.this, "网络无连接！请稍后尝试！", Toast.LENGTH_SHORT).show();
            }
        } else {
            loadActualWeather(k); //网络获取天气实况数据
            loadAirQuality(k);    //网络获取空气质量数据
            loadLivingIndex(k);   //网络获取生活指数数据
            loadForecast(k);      //网络获取五日预测
            loadHourly(k);        //获取24小时预测

        }
    }

    //初始化控件
    public void initialView() {
        forecastLayout = (LinearLayout) findViewById(R.id.forecast_layout);
        //天气实况
        mNowTemperatureText = (TextView) findViewById(R.id.air_temperature_tv);
        mNowLocationText = (TextView) findViewById(R.id.district_tv);
        mNowText = (TextView) findViewById(R.id.current_weather_tv);
        mNowFeelsLikeText = (TextView) findViewById(R.id.feels_like_tv);
        mNowPressureText = (TextView) findViewById(R.id.air_pressure_tv);
        mNowHumidityText = (TextView) findViewById(R.id.shi_du__tv);
        mNowVisibilityText = (TextView) findViewById(R.id.visibility_tv);
        mNowWindDirectionText = (TextView) findViewById(R.id.kong_qi_tv);
        mNowWindScaleText = (TextView) findViewById(R.id.wind_scale_tv);
        //空气质量
        mAqiText = (TextView) findViewById(R.id.air_aqi_tv);
        mQualityText = (TextView) findViewById(R.id.air_quality_tv);
        //生活指数
        mAcText = (TextView) findViewById(R.id.air_conditioner_tv);
        mCarWashingText = (TextView) findViewById(R.id.car_tv);
        mDressingText = (TextView) findViewById(R.id.cloth_tv);
        mFishingText = (TextView) findViewById(R.id.fishing_tv);
        mShoppingText = (TextView) findViewById(R.id.shopping_tv);
        mSportText = (TextView) findViewById(R.id.basketball_tv);
        mBeerText = (TextView) findViewById(R.id.beer_tv);
        mUmbrellaText = (TextView) findViewById(R.id.umbrella_tv);
        mAllergyText = (TextView) findViewById(R.id.allergy_tv);
        mUvText = (TextView) findViewById(R.id.ultraviolet_tv);
        mFluText = (TextView) findViewById(R.id.flu_tv);
        mMorningSportText = (TextView) findViewById(R.id.morning_sport_tv);
        mAddCityBtn = (ImageButton) findViewById(R.id.to_my_city__bt);

        mNowTextImage = (ImageView) findViewById(R.id.now_text_iv);
        mShareImage = (ImageButton) findViewById(R.id.share_bt);

        refreshImagView = (ImageView) findViewById(R.id.refresh_picture_iv);

        mReScrollView = (RefreshScrollView) findViewById(R.id.my_refresh_sv);
        mHeadViewRtLayout = (RelativeLayout) findViewById(R.id.Rt_head_view);
        mReHeadbgView = (RefreshHeadbgView) findViewById(R.id.Rhv_head_bg);
        mTextTv = (TextView) findViewById(R.id.head_view_text);
        //24小时预报
        recyclerView = (RecyclerView)findViewById(R.id.hours_weather_relv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        HourlyAdapter adapter = new HourlyAdapter(hourlyList);
        recyclerView.setAdapter(adapter);
        //主布局
        mReWeather = (RelativeLayout) findViewById(R.id.activity_weather);
        mReScrollView.setListener(this);
        mReScrollView.setHeadView(mReHeadbgView);

       //监控网络连接实例
        mConnectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        mAddCityBtn.setFocusable(false);
        mAddCityBtn.setOnClickListener(this);
        mShareImage.setOnClickListener(this);
        mAqiText.setOnClickListener(this);
    }

    //获取天气实况数据,刷新的时候需要先删除，在访问成功回调的函数里删除，1分钟只能刷新10次
    public void loadActualWeather(final String cityName) {
        if (refresh) {
//            actWeather.setLocationName("");                                                        //设置只要是刷新操作就重新访问网络
            startRefresh = true;
        } else {
            actWeather = wthSQLiteAction.getActualWeatherFromSQLite(cityName);                     //不是刷新操作，优先从数据库中判断
        }
        if (actWeather.getLocationName() == ""||refresh) {                                                 //数据库中没有
            final String address = "https://api.seniverse.com/v3/weather/now.json?key=SVZegE6GtILoWoOgq&location=" + cityName + "&language=zh-Hans&unit=c";
            //网络访问
            new HttpsSendsRequests().sentHttpRequest(address, new IHttpCallBackListener() {
                @Override
                public void onFinish(String string) {
                    if (refresh)
                    actDatabase.delete("ActualWeather", "locationName=?", new String[]{cityName});       //删除数据库中的数据

                    ParseActualWeatherJSON pActWeatherJSON = new ParseActualWeatherJSON();
                    pActWeatherJSON.getActualWeatherResult(string);
                    actWeather = pActWeatherJSON.getActualWeather();
                    handler.sendEmptyMessage(111);
                 //传递信息给主线程，以完成UI更新
                    wthSQLiteAction.saveActualWeather(actWeather);
                }

                @Override
                public void onError() {
                    Looper.prepare();
                    Toast.makeText(WeatherActivity.this, "访问网络过于频繁，请稍后再试", Toast.LENGTH_LONG).show();
                    handler.sendEmptyMessage(115);
                    Looper.loop();
                }
            });
        } else {
//            Log.i("测试------------","actWeather不为空");
            upDateView01();
            addImageView();
            System.out.println(actWeather);
        }
    }

    //获取空气质量数据
    public void loadAirQuality(String cityName) {

        if (refresh) {

//            airQuality.setLocationName("");
            startRefresh = true;
        } else {
            airQuality = airQualitySQLiteAction.getAirQualityFromSQLite(cityName);
        }
        if (airQuality.getLocationName() == ""||refresh) {
            String address = "https://api.seniverse.com/v3/air/now.json?key=SVZegE6GtILoWoOgq&location=" + cityName + "&language=zh-Hans&scope=city";
            //网络访问
            HttpsSendsRequests.sentHttpRequest(address, new IHttpCallBackListener() {
                @Override
                public void onFinish(String string) {
                    if (refresh)
                    airDatabase.delete("AirQuality", "locationName=?", new String[]{k});
                    ParseAirQualityJSON pAQualityJSON = new ParseAirQualityJSON();
                    pAQualityJSON.getAirQualityResult(string);
                    airQuality = pAQualityJSON.getAirQuality();

                    System.out.println("发送112------");
                    handler.sendEmptyMessage(112);                                             //传递信息给主线程，以完成UI更新
//                    }
                    airQualitySQLiteAction.saveAirQuality(airQuality);
                    System.out.println("要保存的airQuality---------" + airQuality.getQuality());
                }

                @Override
                public void onError() {

                }
            });
        } else {
            upDateView02();
        }
    }

    //获取生活指数数据
    public void loadLivingIndex(String cityName) {
        if (refresh) {            //判断是否是刷新操作

//            livingIndex.setLocationName("");
            startRefresh = true;
        } else {
            livingIndex = livingIndexSQLiteAction.getLivingIndexFromSQLite(cityName);
        }
        //网络访问
        if (livingIndex.getLocationName() == ""||refresh) {
            String address = "https://api.seniverse.com/v3/life/suggestion.json?key=SVZegE6GtILoWoOgq&location=" + cityName + "&language=zh-Hans";

            //网络访问
            HttpsSendsRequests.sentHttpRequest(address, new IHttpCallBackListener() {
                @Override
                public void onFinish(String string) {
                    if (refresh)
                    livDatabase.delete("LivingIndex", "locationName=?", new String[]{k});
//                    System.out.println("解析成功----------------------"+string);
                    ParseLivingIndexJSON pLIndexJSON = new ParseLivingIndexJSON();
                    pLIndexJSON.getLivingIndexResult(string);
                    livingIndex = pLIndexJSON.getLivingIndex();
                    handler.sendEmptyMessage(113);                                             //传递信息给主线程，以完成UI更新
//                    }
                    livingIndexSQLiteAction.saveLivingIndex(livingIndex);
//                    System.out.println("网络访问中的livingIndex-----------"+livingIndex.getAcBrief().toString());
                }

                @Override
                public void onError() {

                }
            });
        } else {
            upDateView03();
        }

    }

    //获取五日天气预测数据
    public void loadForecast(String cityName) {

        if (refresh) {            //判断是否是刷新操作

            String address = "https://api.seniverse.com/v3/weather/daily.json?key=SVZegE6GtILoWoOgq&location=" + cityName + "&language=zh-Hans&unit=c&start=-1&days=5";
            //网络访问
            HttpsSendsRequests.sentHttpRequest(address, new IHttpCallBackListener() {
                @Override
                public void onFinish(String string) {
                    if (refresh)
                    forDatabase.delete("Forecast", "locationName=?", new String[]{k});
                    ParseForecastJSON parseForecastJSON = new ParseForecastJSON();
                    parseForecastJSON.getForecastResult(string);
                    handler.sendEmptyMessage(115);                                             //传递信息给主线程，以完成UI更新
                }
                @Override
                public void onError() {
                }
            });
        } else {
            if (forecastSQLiteAction.getForecastFromSQLite(k).size() > 0) {
                upDateView04();
            } else {
                String address = "https://api.seniverse.com/v3/weather/daily.json?key=SVZegE6GtILoWoOgq&location=" + cityName + "&language=zh-Hans&unit=c&start=0&days=5";

                //网络访问
                HttpsSendsRequests.sentHttpRequest(address, new IHttpCallBackListener() {
                    @Override
                    public void onFinish(String string) {
//                    System.out.println("五日预测返回-----------------------"+string);
                        ParseForecastJSON parseForecastJSON = new ParseForecastJSON();
//                    System.out.println("解析成功----------------------"+string);
                        parseForecastJSON.getForecastResult(string);
                        handler.sendEmptyMessage(114);                                             //传递信息给主线程，以完成UI更新
                    }

                    @Override
                    public void onError() {
                    }
                });
            }
        }
    }

    //获取24小时天气预测数据
    public void loadHourly(String cityName){
        System.out.println("进入loadHourly*******************");
        boolean flag = false;
        if (refresh) {            //判断是否是刷新操作
//            flag = true;
            startRefresh = true;
        } else {
            hourlyList = hourlySQLiteAction.getHourlyFromSQLite(cityName);
            System.out.println("无网络---------hourlyList长度---------------------"+hourlyList.size());

        }
        //网络访问
//        if(flag)
            System.out.println("进入if（）flag*******************");
        if (hourlySQLiteAction.getHourlyFromSQLite(cityName).size()>0) {
                upDateView05();
            } else {
                String address = "https://api.seniverse.com/v3/weather/hourly.json?key=SVZegE6GtILoWoOgq&location=" + cityName + "&language=zh-Hans&unit=c&start=0&hours=24";

                //网络访问
                HttpsSendsRequests.sentHttpRequest(address, new IHttpCallBackListener() {
                    @Override
                    public void onFinish(String string) {
                        if (refresh)
                        hourlyDatabase.delete("Hourly", "hourlyLocationName=?", new String[]{k});
                        System.out.println("获取24小时天气预测网络访问成功");
                        ParseHourlyJSON hourlyJSON = new ParseHourlyJSON();
                        hourlyJSON.getHourlyResult(string);
                        hourlyList = hourlyJSON.getHourlyList();
                        System.out.println("hourlyList网络访问成功-----"+hourlyList);
                        handler.sendEmptyMessage(116);                                             //传递信息给主线程，以完成UI更新
                    }

                    @Override
                    public void onError() {
                    }
                });
            }
        }

    //更新UI界面01，天气实况
    public void upDateView01() {
        mNowTemperatureText.setText(actWeather.getNowTemperature() + "℃");
        mNowLocationText.setText(actWeather.getLocationName());
        mNowText.setText(actWeather.getNowText());
        mNowFeelsLikeText.setText(actWeather.getNowFeelsLike() + "℃");
        mNowPressureText.setText(actWeather.getNowPressure() + "mb");
        mNowHumidityText.setText(actWeather.getNowHumidity() + "%");
        mNowVisibilityText.setText(actWeather.getNowVisibility() + "km");
        mNowWindDirectionText.setText(actWeather.getNowWindDirection() + "风");
        mNowWindScaleText.setText(actWeather.getNowWindScale() + "级");

    }

    //更新UI界面02，空气质量
    public void upDateView02() {
        Log.i("upDateView02", "测试airQuality");
        mAqiText.setText(airQuality.getAqi());
        mQualityText.setText("空气" + airQuality.getQuality());
        System.out.println(airQuality.quality);
    }

    //更新UI界面03，生活指数
    public void upDateView03() {
        mAcText.setText(livingIndex.getAcBrief());
        mCarWashingText.setText(livingIndex.getCarWashingBrief());
        mDressingText.setText(livingIndex.getDressingBrief());
        mFishingText.setText(livingIndex.getFishingBrief());
        mShoppingText.setText(livingIndex.getShoppingBrief());
        mSportText.setText(livingIndex.getSportBrief());
        mBeerText.setText(livingIndex.getBeerBrief());
        mUmbrellaText.setText(livingIndex.getUmbrellaBrief());
        mAllergyText.setText(livingIndex.getAllergyBrief());
        mUvText.setText(livingIndex.getUvBrief());
        mFluText.setText(livingIndex.getFluBrief());
        mMorningSportText.setText(livingIndex.getMorningSportBrief());
    }

    //更新UI界面04，5日预测
    public void upDateView04() {
        forecastLayout.removeAllViews();
        if(forecastSQLiteAction.getForecastFromSQLite(k).size()>0)
        for (int i = 0; i < forecastSQLiteAction.getForecastFromSQLite(k).size(); i++) {

            View view = LayoutInflater.from(this).inflate(R.layout.forecast_item, forecastLayout, false);
            Forecast forecast = new Forecast();
            if(forecastSQLiteAction.getForecastFromSQLite(k).size()>0)
            forecast = forecastSQLiteAction.getForecastFromSQLite(k).get(i);
//            System.out.println("测试forecast是否为空-------------------"+forecastSQLiteAction.getForecastFromSQLite(k).get(i).getDate());

            TextView mDateText = (TextView) view.findViewById(R.id.date_tv);
            TextView mHighText = (TextView) view.findViewById(R.id.high_tv);
            TextView mLowText = (TextView) view.findViewById(R.id.low_tv);
            TextView mTextDayText = (TextView) view.findViewById(R.id.day_tv);
            TextView mTextNightText = (TextView) view.findViewById(R.id.night_tv);
            TextView mZhuang = (TextView) view.findViewById(R.id.zhuang);

            mDateText.setText(forecast.getDate());
            mHighText.setText(forecast.getHigh()+ "℃");
            mLowText.setText(forecast.getLow() );
            //如果早上和晚上的天气相同，则只现在早上
            if (forecast.getTextDay().equals(forecast.getTextNight())) {
                mTextDayText.setText(forecast.getTextDay());
                mTextNightText.setText("  ");
                mZhuang.setVisibility(INVISIBLE);
            } else {
                mTextDayText.setText(forecast.getTextDay());
                mTextNightText.setText(forecast.getTextNight());
            }
            forecastLayout.addView(view);
        }

        //加载的时候顺便保存
        //如果是更新之后
        System.out.println("即将进行是否更新判断refresh------------------" + refresh);
        if (refresh) {
            System.out.println("即将删除--------" + myCitiesSQLiteAction.getMyCitiesFromSQLite().size());
            //先把数据库的原数据删除
            myDatabase.delete("MyCities", "cityLocationName=?", new String[]{k});
            System.out.println("已删除--------" + myCitiesSQLiteAction.getMyCitiesFromSQLite().size());
        }
        saveMyCity();                 //保存到我添加的城市中
        System.out.println("已更新--------" + myCitiesSQLiteAction.getMyCitiesFromSQLite().size());
    }

    //更新24小时预报
    public  void upDateView05(){


//      System.out.println("网络访问成功，进入更新05-hourlyList--------------"+hourlyList);
        HourlyAdapter adapter = new HourlyAdapter(hourlyList);
        System.out.println("hourlyList长度---------------------"+hourlyList.size());
        recyclerView.setAdapter(adapter);
        //更新UI，布局设置为可见
        mReWeather.setVisibility(View.VISIBLE);



    }


    //获取MyCities对象
    public MyCities getMyCities() {
        if (forecastSQLiteAction.getForecastFromSQLite(k).size() > 0) {

            Forecast forecast02 = forecastSQLiteAction.getForecastFromSQLite(k).get(0);

            MyCities myCities = new MyCities();
            myCities.setCityHigh(forecast02.getHigh());
            myCities.setCityHumidity(actWeather.getNowHumidity());
            myCities.setCityLocationName(actWeather.getLocationName());
            myCities.setCityLow(forecast02.getLow());
            myCities.setCityQuality(airQuality.getQuality());
            myCities.setCityCode(actWeather.getNowCode());
            myCities.setCityText(actWeather.getNowText());

            System.out.println("保存Cities对象的airQuality" + airQuality.getQuality());

            myCities.setCityTemperature(actWeather.nowTemperature);
            myCities.setCityWindDirection(actWeather.nowWindDirection);
            myCities.setCityWindScale(actWeather.nowWindScale);
            return myCities;
        } else
            return null;

    }

    //保存我的城市到数据库中
    public void saveMyCity() {
        if (refresh) {
            myDatabase.delete("MyCities", "cityLocationName=?", new String[]{k});
        }
        if (!myCitiesSQLiteAction.querySQLite(k)) {
//            Toast.makeText(WeatherActivity.this,"我的城市中有这个城市",Toast.LENGTH_LONG).show();
        } else if (getMyCities() != null) {
            myCitiesSQLiteAction.saveMyCities(getMyCities());
//            Toast.makeText(WeatherActivity.this,"已添加到我的城市中",Toast.LENGTH_LONG).show();
            Log.i("测试", "保存到数据库中成功");
        }
    }

    //动态改变天气气象图片
    public void addImageView() {
        int i = ImageUtil.getImageID("text" + actWeather.getNowCode());
        System.out.println("气象代码--------------"+actWeather.getNowCode());
        System.out.println("气象文字--------------"+actWeather.getNowText());
        mNowTextImage.setImageResource(i);
    }

    //更新操作
    @Override
    public void startRefresh() {
        refresh = true;
        loadActualWeather(k); //网络刷新天气实况数据
        loadAirQuality(k);    //网络刷新空气质量数据
        loadLivingIndex(k);   //网络刷新生活指数数据
        loadForecast(k);      //网络刷新五日预测
        loadHourly(k);        //网络刷新24小时预测
        upDateView05();
//        Toas`t.makeText(WeatherActivity.this,"已更新4",Toast.LENGTH_SHORT).show();
        refreshImagView.setVisibility(View.VISIBLE);
        System.out.println("开始刷新refresh------------------" + refresh);
    }

    @Override
    public void loadMore() {      //加载

    }

    @Override
    public void setWidthX(int x) { //设置横坐标
//        mReHeadbgView.setWidthX(x);
    }

    @Override
    public void hintChange(String hint) {  //设置提示文字

        mTextTv.setText(hint);
    }

    //刷新成功时的操作
    public void finishRefresh() {
        if (refresh) {
            refreshImagView.setVisibility(View.GONE);
            mReScrollView.stopRefresh();      //停止刷新
            Toast.makeText(WeatherActivity.this, "更新成功", Toast.LENGTH_LONG).show();
            refresh = false;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.to_my_city__bt:
                AirQualityDialog customImageDialog = new AirQualityDialog(WeatherActivity.this);
                customImageDialog.show();
                break;
            case R.id.share_bt:
                ShareUtil.shareWeather(this, mReScrollView);
                break;
            case R.id.air_aqi_tv:


        }
    }
}
