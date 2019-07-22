package cn.eoe.app.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weather.R;

import java.util.List;

import cn.eoe.app.entity.Hourly;
import cn.eoe.app.util.ImageUtil;

/**
 * 24小时天气预报的适配器
 * Created by 徐启 on 2019/5/15.
 */

public class HourlyAdapter extends RecyclerView.Adapter<HourlyAdapter.ViewHolder> {


    private List<Hourly> mHourly;

    public HourlyAdapter(List<Hourly> hourlyList) {
        mHourly = hourlyList;
    }

    //在这个方法将hours_item加载进来
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hours_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    //重写onBindViewHolder，完成item的赋值操作
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Hourly hourly = mHourly.get(position);
        holder.hourlyTime.setText(hourly.getMhourlyTime()+"时");
        holder.hourlyTemperature.setText(hourly.getMhourlyTemperature()+"℃");
        int i = ImageUtil.getImageID("text" + hourly.getMhourlyCode());
        holder.textImage.setImageResource(i);

    }

    //item总数
    @Override
    public int getItemCount() {
        return mHourly.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView textImage;
        TextView hourlyTime;
        TextView hourlyTemperature;


        public ViewHolder(View itemView) {
            super(itemView);
            textImage = (ImageView)itemView.findViewById(R.id.hourly_image);
            hourlyTemperature = (TextView)itemView.findViewById(R.id.hourly_temperature);
            hourlyTime = (TextView)itemView.findViewById(R.id.hourly_time);
        }
    }
}
