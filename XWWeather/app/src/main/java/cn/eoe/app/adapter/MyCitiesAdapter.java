package cn.eoe.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.weather.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.eoe.app.entity.MyCities;
import cn.eoe.app.ui.WeatherActivity;

import static cn.eoe.app.ui.MyCitiesActivity.isMultiSelect;
import static cn.eoe.app.ui.MyCitiesActivity.linearLayout;
//import static cn.eoe.app.ui.MyCitiesActivity.listDelete;
import static cn.eoe.app.ui.MyCitiesActivity.nListView;
import static cn.eoe.app.ui.MyCitiesActivity.tv_sum;
import static cn.eoe.app.ui.WeatherActivity.k;

/**
 * 我增加的城市listView的适配器
 * Created by 徐启 on 2019/5/9.
 */

public class MyCitiesAdapter extends BaseAdapter {

    private List<MyCities> mList;
    private LayoutInflater inflater;
    private HashMap<Integer, Integer> isCheckBoxVisible;                                         // 用来记录是否显示checkBox
    private HashMap<Integer, Boolean> isChecked;                                                  // 用来记录是否被选中
    private HashMap<Integer,Boolean> storageArea =new HashMap<>();                               // 存放已被选中的CheckBox
    private static List<MyCities> listMyDelete = new ArrayList<MyCities>();
    public static List<MyCities> getListMyDelete(){
        return listMyDelete;
    }
    public MyCitiesAdapter(){

    }

    public MyCitiesAdapter(Context context, List<MyCities> list, int position) {                   //NoteAdapter构造器
        inflater = LayoutInflater.from(context);
        mList = list;
        isCheckBoxVisible = new HashMap<Integer, Integer>();                                     // 用来记录是否显示checkBox
        isChecked = new HashMap<Integer, Boolean>();                                              // 用来记录是否被选中

        // 如果处于多选状态，则显示CheckBox，否则不显示
        if (isMultiSelect) {
            for (int i = 0; i < mList.size(); i++) {
                isCheckBoxVisible.put(i, CheckBox.VISIBLE);                                      //若是多选状态，CheckBox均为可见
                isChecked.put(i, false);                                                          //初始化全未勾选
            }
        } else {
            for (int i = 0; i < mList.size(); i++) {
                isCheckBoxVisible.put(i, CheckBox.INVISIBLE);                                    //若不是多选状态，CheckBox均为隐藏
                isChecked.put(i, false);                                                           //初始化全未勾选
            }
        }
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {

        return mList.get(position);
    }
    @Override
    public long getItemId(int position) {

        return position;
    }


    //文艺式自定义适配器
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
//            Log.i("if内----------------------","convertView == null");
            viewHolder = new ViewHolder();

            convertView = inflater.inflate(R.layout.my_city_item, null);

            viewHolder.cityHighText = (TextView)convertView.findViewById(R.id.city_high_tv);
            viewHolder.cityHumidityText = (TextView)convertView.findViewById(R.id.city_humidity_tv);
            viewHolder.cityLocationNameText = (TextView)convertView.findViewById(R.id.city_name_tv);
            viewHolder.cityLowText = (TextView)convertView.findViewById(R.id.city_low_tv);
            viewHolder.cityQualityText = (TextView)convertView.findViewById(R.id.city_quality_tv);
            viewHolder.cityTemperatureText = (TextView)convertView.findViewById(R.id.city_temperature_tv);
            viewHolder.cityWindDirectionText = (TextView)convertView.findViewById(R.id.city_direction_tv);
            viewHolder.cityWindScaleText = (TextView)convertView.findViewById(R.id.city_scale_tv);
            viewHolder.cb = (CheckBox) convertView.findViewById(R.id.cb_select);
//            System.out.println("new后的viewHolder"+viewHolder);

            convertView.setTag(viewHolder);

        } else {
//            Log.i("if外----------------------","convertView ！= null");
            viewHolder = (ViewHolder) convertView.getTag();
            System.out.println(viewHolder);
        }
        System.out.println(viewHolder);

           viewHolder.cb.setTag(position);

        final MyCities myCities = mList.get(position);                                                   //获得当前点击的Words对象
//        viewHolder.textView_words.setText(words.getKey());                                        //显示生词本内容
//        viewHolder.textView_explains.setText(words.getPosAcceptation());
        viewHolder.cityWindScaleText.setText(myCities.getCityWindScale()+"级");
        viewHolder.cityWindDirectionText.setText(myCities.getCityWindDirection()+"风");
        viewHolder.cityTemperatureText.setText(myCities.getCityTemperature()+"℃");
        viewHolder.cityHighText.setText(myCities.getCityHigh()+" ℃");
        viewHolder.cityHumidityText.setText(myCities.getCityHumidity()+"%");
        viewHolder.cityLocationNameText.setText(myCities.getCityLocationName());
        viewHolder.cityLowText.setText(myCities.getCityLow()+" /");
        viewHolder.cityQualityText.setText(myCities.getCityQuality());
//        System.out.println("空气质量---------------------------------------------------------"+myCities.getCityQuality());


        // 根据position设置CheckBox是否可见，是否选中
        viewHolder.cb.setChecked(isChecked.get(position));                                         //setChecked更改CheckBox的选中状态
        viewHolder.cb.setVisibility(isCheckBoxVisible.get(position));                             //setVisibility()设置CheckBox 的启动状态

        convertView.setOnLongClickListener(new onMyLongClick(position, mList));                     // nListView每一个Item的长按事件
        /*
         * 在nListView中点击每一项的处理
         * 如果CheckBox未选中，则点击后选中CheckBox，并将数据添加到list_delete中
         * 如果CheckBox选中，则点击后取消选中CheckBox，并将数据从list_delete中移除
         */
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isMultiSelect) {
                    // 处于多选模式
                    if (viewHolder.cb.isChecked()) {                                                //若CheckBox已经是被选中的状态
                        //item点击事件
                        viewHolder.cb.setChecked(false);                                           //点击时变成未选中的状态
                        listMyDelete.remove(myCities);                                                   //在要删除的数组的移除点击的单词
                        storageArea.remove(position);                                              //若未选中，则不存放

                    } else {
                        viewHolder.cb.setChecked(true);
                        listMyDelete.add(myCities);
                        storageArea.put(position,true);                                           //存放这个复选框的状态,若被选中，则存放

                    }
                    tv_sum.setText("共选择了" + listMyDelete.size() + "项");
                } else {                                                                            //单击listView跳入对应单词的翻译
                    k= mList.get(position).getCityLocationName();
                    Intent intent2=new Intent(v.getContext(),WeatherActivity.class);             //构建Intent，TranslateActivity.this为上下文,NoteActivity.class为活动目标
                    v.getContext().startActivity(intent2);
                }
            }
        });

        //存储复选框的选中状态，防止勾选混乱
        if(storageArea!=null&&storageArea.containsKey(position)){
            viewHolder.cb.setChecked(true);
        }else {
            viewHolder.cb.setChecked(false);
        }
        return convertView;
    }

    public  static class ViewHolder {
        public TextView cityLocationNameText;
        public TextView cityTemperatureText;
        public TextView cityQualityText;
        public TextView cityHumidityText;
        public TextView cityWindDirectionText;
        public TextView cityWindScaleText;
        public TextView cityHighText;
        public TextView cityLowText;
        public CheckBox cb;
//        public ImageButton  iButton_sound;
    }


    // 自定义长按事件
    class onMyLongClick implements View.OnLongClickListener {
        private int position;

        public onMyLongClick(int position, List<MyCities> list) {
            this.position = position;
            mList = list;
        }
        @Override
        public boolean onLongClick(View v) {
            isMultiSelect = true;
            listMyDelete.clear();				                                                        // 添加长按Item到删除数据list中
            linearLayout.setVisibility(View.VISIBLE);

            tv_sum.setText("共选择了" + listMyDelete.size() + "项");
            for (int i = 0; i < mList.size(); i++) {
                isCheckBoxVisible.put(i, CheckBox.VISIBLE);
            }
            // 根据position，设置nListView中对应的CheckBox为选中状态
            MyCitiesAdapter myCitiesAdapter = new MyCitiesAdapter(v.getContext(), mList,position);
            nListView.setAdapter(myCitiesAdapter);
            myCitiesAdapter.notifyDataSetChanged();
            return true;                                                                           //返回true，不会触发单击事件
        }
    }
}
