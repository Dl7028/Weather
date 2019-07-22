package cn.eoe.app.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weather.R;

import java.util.ArrayList;
import java.util.List;

import cn.eoe.app.adapter.MyCitiesAdapter;
import cn.eoe.app.db.ActualWeatherSQLiteAction;
import cn.eoe.app.entity.MyCities;

import static cn.eoe.app.db.ActualWeatherSQLiteAction.actDatabase;
import static cn.eoe.app.db.AirQualitySQLiteAction.airDatabase;
import static cn.eoe.app.db.ForecastSQLiteAction.forDatabase;
import static cn.eoe.app.db.MyCitiesSQLiteAction.myDatabase;
import static cn.eoe.app.ui.CitySearchActivity.flag;
import static cn.eoe.app.ui.CitySearchActivity.forecastSQLiteAction;
import static cn.eoe.app.ui.CitySearchActivity.mBeiJingBtn;
import static cn.eoe.app.ui.CitySearchActivity.myCitiesSQLiteAction;
import static cn.eoe.app.ui.WeatherActivity.k;
import static cn.eoe.app.ui.WeatherActivity.refresh;

/**
 * 这里存放查询过的城市记录：
 * 可以添加，长按多选删除城市
 */

public class MyCitiesActivity extends AppCompatActivity implements View.OnClickListener {
    private static  ArrayList<MyCities> arrayList, arrayList2, arrayList3;

    public static boolean isMultiSelect = false;
    public static List<MyCities> listDelete = new ArrayList<>();                                     //一个容器用于存放需要删除的数据
    public static Button bt_cancel,bt_delete;                                                     //多选删除的取消键和删除键
    public static TextView tv_sum;                                                                //显示有多少个城市被选中
    public static LinearLayout linearLayout;
    public static ListView nListView;
//    private ImageButton mAddCityBtn;
    public static MyCitiesAdapter myCitiesAdapter;
    private static final int NOSELECT_STATE = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_city);
        initialView();
        setMyCitiesAdapter();

    }
    private void initialView(){
        bt_cancel = (Button)findViewById(R.id.bt_cancel);
        bt_delete = (Button)findViewById(R.id.bt_delete);
        linearLayout =(LinearLayout)findViewById(R.id.delete_linearLayout);
        nListView = (ListView)findViewById(R.id.my_city_lv);
        tv_sum = (TextView)findViewById(R.id.tv_sum);
        bt_cancel.setOnClickListener(this);
        bt_delete.setOnClickListener(this);
        bt_cancel.setFocusable(false);                                                             //解决按钮与item点击冲突事件，不自动获取焦点
        bt_delete.setFocusable(false);

    }
    //加载适配器
    public void setMyCitiesAdapter() {
        arrayList = new ArrayList<MyCities>();
        arrayList2 = new ArrayList<MyCities>();
        arrayList = myCitiesSQLiteAction.getMyCitiesFromSQLite();                                   //获取数据存放在arrayList中

        //判断主界面天气有没有更新,如果天气界面更新，我添加的城市数据也要更新
        if(refresh) {
            deleteClick(); //删除原来显示

        }
          for (int i = arrayList.size(); i > 0; i--) {
            arrayList2.add(arrayList.get(i - 1));                                                   //arrayList2增加arrayList索引降序的数据
        }

        myCitiesAdapter = new MyCitiesAdapter(MyCitiesActivity.this, arrayList2,NOSELECT_STATE);  //创建一个NoteAdapter适配器类的对象，并传入参数进行初始化，未点击item，默认为-1
        nListView.setAdapter(myCitiesAdapter);                                                       //利用setAdapter建立ListView与数据之间的关联
        myCitiesAdapter.notifyDataSetChanged();
    }

    //取消选择方法
    public void cancelClick(){
        isMultiSelect = false;                                                                     // 退出多选模式
        listDelete.clear();                                                                         // 清除listDelete中的数据
        myCitiesAdapter = new MyCitiesAdapter(MyCitiesActivity.this, arrayList2,NOSELECT_STATE);  //更新界面
        nListView.setAdapter(myCitiesAdapter);
        linearLayout.setVisibility(View.GONE);                                                      //隐藏linearLayout
    }

    //删除方法
    public  void deleteClick(){

        listDelete =MyCitiesAdapter.getListMyDelete();

        System.out.println("进来删除方法块里的数组长度-----------》"+listDelete.size());
        isMultiSelect = false;                                                                      //多选状态设置为否
        for (int i = 0; i < listDelete.size(); i++) {
            System.out.println("i= "+i+"---------arrayList2的长度----------------------"+arrayList2.size());
            for (int j = 0; j < arrayList2.size(); j++) {
                if (arrayList2.get(j).getCityLocationName().equals( listDelete.get(i).getCityLocationName())) {               //arrayList2中的元素与listDelete中的元素一个一个比较
                    //数据库中删除
//                    String city = arrayList2.get(j).getCityLocationName();
                    actDatabase.delete("ActualWeather","locationName=?",new String[]{arrayList2.get(j).getCityLocationName()});
                    airDatabase.delete("AirQuality","locationName=?",new String[]{arrayList2.get(j).getCityLocationName()});
                    forDatabase.delete("Forecast","locationName=?",new String[]{arrayList2.get(j).getCityLocationName()});
                    myDatabase.delete("MyCities","cityLocationName=?",new String[]{arrayList2.get(j).getCityLocationName()});

                    arrayList2.remove(arrayList2.get(j));                                           //arrayList2也移除这个数据
                    System.out.println("if里面----------------"+arrayList2.size());
                    break;
                }
            }
        }

        listDelete.clear();                                                                         //清除 listDelete
        myCitiesAdapter = new MyCitiesAdapter(MyCitiesActivity.this, arrayList2,NOSELECT_STATE);   //更新界面
        nListView.setAdapter(myCitiesAdapter);
        linearLayout.setVisibility(View.GONE);                                                     //隐藏linearLayout
    }

    //对话框提示事件
    private void dialogBox() {


        AlertDialog.Builder bb = new AlertDialog.Builder(this);
        bb.setMessage("确认删除所选城市吗？");
        bb.setTitle("提示");
        bb.setCancelable(true);
        bb.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.out.println("未删除的数组的长度-----------》"+listDelete.size());
                deleteClick();
            }
        });
        bb.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        bb.show();
    }

    //返回上一个活动时同时设置CheckBox为不可见
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {                                       //setVisibility()设置CheckBox 的启动状态
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            cancelClick();

        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_cancel:
                cancelClick();
                break;
            case R.id.bt_delete:
                dialogBox();
                break;
        }


    }




}


