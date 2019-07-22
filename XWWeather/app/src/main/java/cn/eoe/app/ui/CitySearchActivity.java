package cn.eoe.app.ui;



import android.content.Context;
;
import android.content.Intent;

import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.weather.R;


import cn.eoe.app.db.ActualWeatherSQLiteAction;
import cn.eoe.app.db.AirQualitySQLiteAction;
import cn.eoe.app.db.ForecastSQLiteAction;
import cn.eoe.app.db.HourlySQLiteAction;
import cn.eoe.app.db.LivingIndexSQLiteAction;
import cn.eoe.app.db.MyCitiesSQLiteAction;
import cn.eoe.app.entity.ActualWeather;
import cn.eoe.app.entity.MyCities;
import cn.eoe.app.util.LocationUtils;

import static cn.eoe.app.ui.WeatherActivity.k;
import static cn.eoe.app.ui.WeatherActivity.refresh;


/**
 * 按钮点击事件
 * 搜索城市的界面
 */

public class CitySearchActivity extends AppCompatActivity implements View.OnClickListener {

    private SearchView mSearchView;
    private ConnectivityManager mConnectivity;
    public static Button mBeiJingBtn;
    private Button mShangHaiBtn;
    private Button mShenZhenBtn;
    private Button mFoShanBtn;
    private Button mZhuHaiBtn;
    private Button mNanJingBtn;
    private Button mSuZhouBtn;
    private Button mXiaMenBtn;
    private Button mChengDuBtn;
    private Button mChangShaBtn;
    private Button mFuZhouBtn;
    private Button mHangZhouBtn;
    private Button mWuHanBtn;
    private Button mXiAnBtn;
    private Button mQingDaoBtn;
    private Button mTaiYuanBtn;
    private Button mShiJiaZhuangBtn;
    private Button mShenYangBtn;
    private Button mChongQingBtn;
    private Button mTianJinBtn;
    private Button mNanNingBtn;
    private Button mGuangZhouBtn;
    private Button mLocationBtn;
    private Button mMyCitiesBtn;

    private ActualWeather actualWeather;
    private MyCities myCities;
    private  long exitTime;

    private LocationManager mLocationManager;
    private String provider;
    //我的定位
    public   String myCity;
    public  String myCountry;
    //给定位文字加颜色
    private boolean click = true;


    public static boolean flag = true ;


    //定义数据库操作类实例
    public static ActualWeatherSQLiteAction wthSQLiteAction;
    public static AirQualitySQLiteAction airQualitySQLiteAction;
    public static LivingIndexSQLiteAction livingIndexSQLiteAction;
    public static ForecastSQLiteAction forecastSQLiteAction;
    public static MyCitiesSQLiteAction myCitiesSQLiteAction;
    public static HourlySQLiteAction hourlySQLiteAction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_search);
        initialView();              //初始化按钮
        addClick();                //设置点击事件

        //第一次进入应用时自动切换到最近查询的城市的天气界面
        if(flag)
        if (myCitiesSQLiteAction.getMyCitiesFromSQLite().size() > 0)
            if (myCitiesSQLiteAction.getMyCitiesFromSQLite().get(0).getCityLocationName() != null) {
                k = myCitiesSQLiteAction.getMyCitiesFromSQLite().get(myCitiesSQLiteAction.getMyCitiesFromSQLite().size()-1).getCityLocationName();
                Intent intent = new Intent(CitySearchActivity.this, WeatherActivity.class);
                startActivity(intent);
            }

        //搜索事件
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                NetworkInfo info = mConnectivity.getActiveNetworkInfo();
                ActualWeather actWeather = wthSQLiteAction.getActualWeatherFromSQLite(query);
                //判断网络有没有连接
                if (info == null || !mConnectivity.getBackgroundDataSetting() && actWeather.getNowTemperature() == null) {
                    if (actWeather.getNowTemperature() != null) {
                        query =query.trim();                                                         //过滤前后空格
                        if(query.contains("市")){
                            query =query.replace("市"," ");
                        }
                        k = query;
                        goToWeatherActivity();
                    } else {
                        Toast.makeText(CitySearchActivity.this, "网络无连接！请稍后尝试！", Toast.LENGTH_SHORT).show();
                    }
                    return false;
                } else {
                    //过滤query
                    query =query.trim();                                                         //过滤前后空格
                    if(query.contains("市"))
                        query =query.replace("市"," ");
                    if(query.contains("区"))
                        query = query.replace("区"," ");
                    if(query.contains("县"))                           //查番禺县的情况
                        query = query.replace("县"," ");
                    k = query;
                    goToWeatherActivity();
                    return true;
                }
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


    }

    //初始化按钮控件
    public void initialView() {
        mBeiJingBtn = (Button) findViewById(R.id.beijing_bt);
        mShangHaiBtn = (Button) findViewById(R.id.shanghai_bt);
        mShenZhenBtn = (Button) findViewById(R.id.shenzhen_bt);
        mFoShanBtn = (Button) findViewById(R.id.foshang_bt);
        mZhuHaiBtn = (Button) findViewById(R.id.zhuhai_bt);
        mNanJingBtn = (Button) findViewById(R.id.nanjing_bt);
        mSuZhouBtn = (Button) findViewById(R.id.suzhou_bt);
        mXiaMenBtn = (Button) findViewById(R.id.xiamen_bt);
        mChengDuBtn = (Button) findViewById(R.id.chengdu_bt);
        mChangShaBtn = (Button) findViewById(R.id.changsha_bt);
        mFuZhouBtn = (Button) findViewById(R.id.fuzhou_bt);
        mHangZhouBtn = (Button) findViewById(R.id.hangzhou_bt);
        mWuHanBtn = (Button) findViewById(R.id.wuhan_bt);
        mXiAnBtn = (Button) findViewById(R.id.xian_bt);
        mQingDaoBtn = (Button) findViewById(R.id.qingdao_bt);
        mTaiYuanBtn = (Button) findViewById(R.id.taiyuan_bt);
        mShiJiaZhuangBtn = (Button) findViewById(R.id.shijiazhuang_bt);
        mShenYangBtn = (Button) findViewById(R.id.shenyang_bt);
        mChongQingBtn = (Button) findViewById(R.id.chongqing_bt);
        mTianJinBtn = (Button) findViewById(R.id.tianjin_bt);
        mNanNingBtn = (Button) findViewById(R.id.nanning_bt);
        mGuangZhouBtn = (Button) findViewById(R.id.guangzhou_bt);
        mLocationBtn = (Button) findViewById(R.id.location_bt);
        mMyCitiesBtn = (Button)findViewById(R.id.search_my_cities);

        ////获取数据库操作类实例
        wthSQLiteAction = ActualWeatherSQLiteAction.getInstance(this);
        airQualitySQLiteAction = AirQualitySQLiteAction.getInstance(this);
        livingIndexSQLiteAction = LivingIndexSQLiteAction.getInstance(this);
        forecastSQLiteAction = ForecastSQLiteAction.getInstance(this);
        myCitiesSQLiteAction = MyCitiesSQLiteAction.getInstance(this);
        hourlySQLiteAction = HourlySQLiteAction.getInstance(this);

        mConnectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);      //监控网络连接
        mSearchView = (SearchView) findViewById(R.id.city_search_sv);
        mSearchView.setSubmitButtonEnabled(true);                                                  //设置显示搜索按钮
        mSearchView.setIconifiedByDefault(false);//设置不自动缩小为图标，点搜索框就出现软键盘
        mSearchView.setFocusable(false);                                                            //设置进入活动不自动显示软键盘
        //不显示搜索图标
        int magId = getResources().getIdentifier("android:id/search_mag_icon", null, null);
        ImageView magImage = (ImageView) mSearchView.findViewById(magId);
        magImage.setLayoutParams(new LinearLayout.LayoutParams(0, 0));

    }

    //给按钮设置点击事件
    public void addClick() {
        mBeiJingBtn.setOnClickListener(this);
        mShangHaiBtn.setOnClickListener(this);
        mShenZhenBtn.setOnClickListener(this);
        mFoShanBtn.setOnClickListener(this);
        mZhuHaiBtn.setOnClickListener(this);
        mNanJingBtn.setOnClickListener(this);
        mSuZhouBtn.setOnClickListener(this);
        mXiaMenBtn.setOnClickListener(this);
        mChengDuBtn.setOnClickListener(this);
        mChangShaBtn.setOnClickListener(this);
        mFuZhouBtn.setOnClickListener(this);
        mHangZhouBtn.setOnClickListener(this);
        mWuHanBtn.setOnClickListener(this);
        mXiAnBtn.setOnClickListener(this);
        mQingDaoBtn.setOnClickListener(this);
        mTaiYuanBtn.setOnClickListener(this);
        mShiJiaZhuangBtn.setOnClickListener(this);
        mShenYangBtn.setOnClickListener(this);
        mChongQingBtn.setOnClickListener(this);
        mTianJinBtn.setOnClickListener(this);
        mNanNingBtn.setOnClickListener(this);
        mGuangZhouBtn.setOnClickListener(this);
        mLocationBtn.setOnClickListener(this);
        mMyCitiesBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.beijing_bt:
                k = "北京";
                goToWeatherActivity();
                break;
            case R.id.shanghai_bt:
                k = "上海";
                goToWeatherActivity();
                break;
            case R.id.shenzhen_bt:
                k = "深圳";
                goToWeatherActivity();
                break;
            case R.id.foshang_bt:
                k = "佛山";
                goToWeatherActivity();
                break;
            case R.id.zhuhai_bt:
                k = "珠海";
                goToWeatherActivity();
                break;
            case R.id.nanjing_bt:
                k = "南京";
                goToWeatherActivity();
                break;
            case R.id.suzhou_bt:
                k = "苏州";
                goToWeatherActivity();
                break;
            case R.id.xiamen_bt:
                k = "厦门";
                goToWeatherActivity();
                break;
            case R.id.chengdu_bt:
                k = "成都";
                goToWeatherActivity();
                break;
            case R.id.changsha_bt:
                k = "长沙";
                goToWeatherActivity();
                break;
            case R.id.fuzhou_bt:
                k = "福州";
                goToWeatherActivity();
                break;
            case R.id.hangzhou_bt:
                k = "杭州";
                goToWeatherActivity();
                break;
            case R.id.wuhan_bt:
                k = "武汉";
                goToWeatherActivity();
                break;
            case R.id.xian_bt:
                k = "西安";
                goToWeatherActivity();
                break;
            case R.id.qingdao_bt:
                k = "青岛";
                goToWeatherActivity();
                break;
            case R.id.taiyuan_bt:
                k = "太原";
                goToWeatherActivity();
                break;
            case R.id.shijiazhuang_bt:
                k = "石家庄";
                goToWeatherActivity();
                break;
            case R.id.shenyang_bt:
                k = "沈阳";
                goToWeatherActivity();
                break;
            case R.id.chongqing_bt:
                k = "重庆";
                goToWeatherActivity();
                break;
            case R.id.tianjin_bt:
                k = "天津";
                goToWeatherActivity();
                break;
            case R.id.nanning_bt:
                k = "南宁";
                goToWeatherActivity();
                break;
            case R.id.guangzhou_bt:
                k = "广州";
                goToWeatherActivity();
                break;
            case R.id.location_bt:
                startLocate();                            //定位开始操作
                LocateToCity();                           //开始定位
                mLocationBtn.setTextColor(Color.BLUE); //字体变为蓝色
                break;
            case R.id.search_my_cities:
                refresh = false;                         //搜索进入不属于手动刷新
                Intent intent = new Intent(CitySearchActivity.this, MyCitiesActivity.class);
                startActivity(intent);
                break;
        }

    }

    //切换到WeatherActivity的方法
    public void goToWeatherActivity() {
        Intent intent = new Intent(CitySearchActivity.this, WeatherActivity.class);  //构建Intent，TranslateActivity.this为上下文,NoteActivity.class为活动目标
        startActivity(intent);
    }


    //开始定位
    public void startLocate() {
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);          //获取定位服务
        boolean providerEnable = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);//判断GPS是否开启
        if (providerEnable) {                                                                         //GPS是开启状态
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        } else {
            Toast.makeText(this, "请打开GPS", Toast.LENGTH_SHORT).show();
        }
    }

    private LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {

        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {


        }
        @Override
        public void onProviderEnabled(String provider) {
            //GPS开启时触发
            Log.e("测试", "onProviderEnabled");
        }
        @Override
        public void onProviderDisabled(String provider) {
            //GPS禁用时触发
            Log.e("测试", "onProviderDisabled");
        }
    };

    //定位到当前城市
    public void LocateToCity() {
        LocationUtils.register(CitySearchActivity.this, 2000, 2, new LocationUtils.OnLocationChangeListener() {
            //获取定位最后保留的位置
            @Override
            public void getLastKnownLocation(Location location) {
                if (LocationUtils.getLocality(CitySearchActivity.this, location.getLatitude(), location.getLongitude()).equals("unknown")) {
                    Toast.makeText(CitySearchActivity.this, "正在获取当前位置", Toast.LENGTH_LONG).show();
                } else {
                    myCity = LocationUtils.getLocality(CitySearchActivity.this, location.getLatitude(), location.getLongitude()); //返回结果是xx市，和我的逻辑保持一致的话，要去掉后面的市
                    myCountry = LocationUtils.getStreet(CitySearchActivity.this, location.getLatitude(), location.getLongitude());
                    String str = myCountry;
                    str = str.substring(str.indexOf("市")+1, str.indexOf("区"));
                    System.out.println("我的街道------------"+str);
                     k = str;

                    goToWeatherActivity();
                }
            }


//            @Override
//            public void onLocationChanged(Location location) {
//
//            }

//            @Override
//            public void onStatusChanged(String provider, int status, Bundle extras) {


//            }
        });

    }

    //注销定位
    protected void onDestroy() {
        super.onDestroy();
        LocationUtils.unregister();
    }

    //双击返回键退出程序
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
