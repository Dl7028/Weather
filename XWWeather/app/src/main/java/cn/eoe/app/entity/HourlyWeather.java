package cn.eoe.app.entity;

/**
 * 24小时天气预报实体类
 * Created by 徐启 on 2019/5/5.
 */

public class HourlyWeather {
    //预测时间
    private String time;
    //温度
    private String temp;
    //降水概率
    private String pop;
    //风力
    private String wind;

    public HourlyWeather(String time, String temp, String pop, String wind) {
        this.time = time;
        this.temp = temp;
        this.pop = pop;
        this.wind = wind;
    }

    public String getTime() {
        return time;
    }

    public String getTemp() {
        return temp;
    }

    public String getPop() {
        return pop;
    }

    public String getWind() {
        return wind;
    }
}
