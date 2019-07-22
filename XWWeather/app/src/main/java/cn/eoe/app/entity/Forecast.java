package cn.eoe.app.entity;

/**
 * 逐日天气预报的实体类
 * Created by 徐启 on 2019/5/6.
 */

public class Forecast {
    private String date; //日期
    private String locationName;
    private String high;//当天最高温度
    private String low;  //当天最低温度
    private String textDay; //白天天气现象文字
    private String textNight;//晚间天气现象文字

    public Forecast(){
        this.date = "";
        this.locationName = "";
        this.high = "";
        this.low = "";
        this.textDay = "";
        this.textNight = "";

    }


    public void setDate(String date) {
        this.date = date;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public void setTextDay(String textDay) {
        this.textDay = textDay;
    }

    public void setTextNight(String textNight) {
        this.textNight = textNight;
    }

    public String getDate() {
        return date;
    }

    public String getHigh() {
        return high;
    }

    public String getLocationName() {
        return locationName;
    }

    public String getLow() {
        return low;
    }

    public String getTextDay() {
        return textDay;
    }

    public String getTextNight() {
        return textNight;
    }
}
